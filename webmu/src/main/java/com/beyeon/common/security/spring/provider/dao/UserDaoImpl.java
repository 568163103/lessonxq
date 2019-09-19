package com.beyeon.common.security.spring.provider.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;

import com.beyeon.common.security.spring.detail.UserDetail;
import com.beyeon.hibernate.fivsdb.TPrivData;
import com.beyeon.hibernate.fivsdb.TUserLink;

/**
 * 通过数据库方式获取User 和 Resource 实例
 * @author liwf
 *
 */
public class UserDaoImpl extends JdbcDaoImpl{
	//获取用户名，密码，状态(name,passwd,status)
	private String usersQuery;
	private String userAreaByUseridQuery;
	private String linkUserByUseridQuery;

	public void setUsersQuery(String usersQuery) {
		this.usersQuery = usersQuery;
	}

	public String getUsersQuery() {
		return usersQuery;
	}

	public void setUserAreaByUseridQuery(String userAreaByUseridQuery) {
		this.userAreaByUseridQuery = userAreaByUseridQuery;
	}

	public void setLinkUserByUseridQuery(String linkUserByUseridQuery) {
		this.linkUserByUseridQuery = linkUserByUseridQuery;
	}

	protected void addCustomAuthorities(String username,List<GrantedAuthority> authorities) {
		authorities.add(new SimpleGrantedAuthority("public"));
	}

	/**
	 * 获取所有用户实例
	 */
	public List<UserDetails> getUserDetails() {
		List<UserDetails> users = loadUsersByUsername(null);
		List<UserDetails> authUsers = new ArrayList<UserDetails>();
		for (Iterator iter = users.iterator(); iter.hasNext();) {
			UserDetail user = (UserDetail) iter.next();
			Set <GrantedAuthority> auths = new HashSet<GrantedAuthority>();
			if (getEnableAuthorities()){
				auths.addAll(loadUserAuthorities(user.getId()));
			}

			if (getEnableGroups()) {
				auths.addAll(loadGroupAuthorities(user.getUsername()));
			}
			List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(auths);
			addCustomAuthorities(user.getUsername(),dbAuths);
			authUsers.add(createUserDetails(user.getUsername(),user,dbAuths));
		}
		return authUsers;
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		List<UserDetails> users = loadUsersByUsername(username);
		if (users.size() == 0) {
			throw new UsernameNotFoundException(messages.getMessage(
					"JdbcDaoImpl.notFound", new Object[] { username },
					"Username {0} not found"));
		}

		UserDetail user = (UserDetail)users.get(0); // contains no GrantedAuthority[]

		Set<GrantedAuthority> dbAuthsSet = new HashSet<GrantedAuthority>();

		if (getEnableAuthorities()) {
			dbAuthsSet.addAll(loadUserAuthorities(user.getId()));
		}

		if (getEnableGroups()) {
			dbAuthsSet.addAll(loadGroupAuthorities(user.getUsername()));
		}

		List<GrantedAuthority> dbAuths = new ArrayList<GrantedAuthority>(dbAuthsSet);
		addCustomAuthorities(user.getUsername(), dbAuths);

		return createUserDetails(username, user, dbAuths);
	}

	public List<UserDetails> loadUsersByUsername(String username) {
		StringBuilder sb = new StringBuilder();
		List<String> params = new ArrayList<String>();
		if (null != username) {
			params.add(username);
			sb.append(getUsersByUsernameQuery());
		} else {
			sb.append(getUsersQuery());
		}
		return getJdbcTemplate().query(sb.toString(),
				params.toArray(), new RowMapper<UserDetails>() {
			public UserDetails mapRow(ResultSet rs, int rowNum) throws SQLException {
				String username = rs.getString(1);
				String password = rs.getString(2);
				boolean enabled = rs.getBoolean(3);
				String name = rs.getString(4);
				String id = rs.getString(5);
				String mobile = rs.getString(6);
				String mail = rs.getString(7);
				UserDetail user = new UserDetail(username, password, enabled, true, true, true, AuthorityUtils.NO_AUTHORITIES);
				user.setId(id);
				user.setName(name);
				user.setMobile(mobile);
				user.setMail(mail);
				user.setPrivDatas(loadUserPrivData(id));
				user.setUserLinks(loadLinkUser(id));
				return user;
			}

		});
	}

	public UserDetails createUserDetails(String username, UserDetails userFromUserQuery, List<GrantedAuthority> combinedAuthorities) {
		String returnUsername = userFromUserQuery.getUsername();
		if (!isUsernameBasedPrimaryKey()) {
			returnUsername = username;
		}
		UserDetail user = new UserDetail(returnUsername, userFromUserQuery.getPassword(), userFromUserQuery.isEnabled(),true, true, true, combinedAuthorities);
		user.setId(((UserDetail) userFromUserQuery).getId());
		user.setName(((UserDetail) userFromUserQuery).getName());
		user.setMobile(((UserDetail) userFromUserQuery).getMobile());
		user.setMail(((UserDetail) userFromUserQuery).getMail());
		user.setPrivDatas(((UserDetail) userFromUserQuery).getPrivDatas());
		user.setUserLinks(((UserDetail) userFromUserQuery).getUserLinks());
		return user;
	}

	protected List<GrantedAuthority> loadUserAuthorities(String username) {
		StringBuilder sb = new StringBuilder(getAuthoritiesByUsernameQuery());
		List<String> params = new ArrayList<String>();
		params.add(username);
		return getJdbcTemplate().query(sb.toString(),
				params.toArray(), new RowMapper<GrantedAuthority>() {
			public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
				String roleName = getRolePrefix() + rs.getString(1);
				SimpleGrantedAuthority authority = new SimpleGrantedAuthority(roleName);
				return authority;
			}
		});
	}

	public List loadUserPrivData(final String userId){
		return getJdbcTemplate().query(this.userAreaByUseridQuery, new String [] {userId}, new RowMapper<TPrivData>() {
			public TPrivData mapRow(ResultSet rs, int rowNum) throws SQLException {
				Short privType = rs.getShort(1);
				String privCode = rs.getString(2);
				TPrivData privData = new TPrivData();
				privData.setPrivType(privType);
				privData.setUserId(userId);
				privData.setPrivCode(privCode);
				return privData;
			}
        });
	}

	public List loadLinkUser(final String amid){
		return getJdbcTemplate().query(this.linkUserByUseridQuery, new String [] {amid}, new RowMapper<TUserLink>() {
			public TUserLink mapRow(ResultSet rs, int rowNum) throws SQLException {
				String preamid = rs.getString(1);
				String fullAmid = rs.getString(2);
				TUserLink linkUser = new TUserLink();
				linkUser.setPreamid(preamid);
				linkUser.setFullAmid(fullAmid);
				linkUser.setAmid(amid);
				return linkUser;
			}
		});
	}
}
