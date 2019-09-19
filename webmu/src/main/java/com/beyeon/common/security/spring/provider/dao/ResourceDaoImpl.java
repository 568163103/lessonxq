package com.beyeon.common.security.spring.provider.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.security.spring.SecurityHelper;
import com.beyeon.common.security.spring.detail.ResourceDetail;


/**
 * 通过数据库方式获取ResourceDetail 实例
 * @author liwf
 *
 */
public class ResourceDaoImpl extends JdbcDaoSupport{
	
	//获取所有资源的资源串和资源类型
	private String loadResourceQuery;
	private String loadResourcesQuery;
	
	//获取相应资源下的所有权限(role.name)
	private String authoritiesByResourceQuery;
	private String authoritiesByResourceUrlQuery;
	
	private String rolePrefix = "";

	public void setAuthoritiesByResourceUrlQuery(String authoritiesByResourceUrlQuery) {
		this.authoritiesByResourceUrlQuery = authoritiesByResourceUrlQuery;
	}

	public void setAuthoritiesByResourceQuery(String authoritiesByResourceQuery) {
		this.authoritiesByResourceQuery = authoritiesByResourceQuery;
	}

	public void setLoadResourcesQuery(String loadResourcesQuery) {
		this.loadResourcesQuery = loadResourcesQuery;
	}

	public void setLoadResourceQuery(String loadResourceQuery) {
		this.loadResourceQuery = loadResourceQuery;
	}

	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

	/* 
	 * 获取所有资源实例
	 */
	public List<ResourceDetail> getResourceDetails() {
		List<ResourceDetail> resources = getResourceDetails(ResourceUtil.getCoreConf("app.all.module"));
		for (Iterator<ResourceDetail> iter = resources.iterator(); iter.hasNext();) {
			ResourceDetail resc = iter.next();
			List<GrantedAuthority> auths = getJdbcTemplate().query(authoritiesByResourceQuery,
					new Object[] {resc.getMid()}, new RowMapper<GrantedAuthority>() {
	            public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
	            	String roleName = rolePrefix + rs.getString(1);
	                GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
	                return authority;
	            }
	        });
			GrantedAuthority[] arrayAuths = SecurityHelper.convertToGrantedAuthority(auths);
			resc.setAuthorities(arrayAuths);
		}
		return resources;
	}

	public ResourceDetail getResourceDetail(Integer mid) {
		ResourceDetail resource = getJdbcTemplate().queryForObject(loadResourceQuery, new Integer[] {mid}, new RowMapper<ResourceDetail>() {
			public ResourceDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
				Integer mid = rs.getInt(1);
				String resType = rs.getString(2);
				String url = rs.getString(3);
				ResourceDetail resourceDetail = new ResourceDetail(mid, resType,url, new GrantedAuthority[]{new SimpleGrantedAuthority("HOLDER")});
				return resourceDetail;
			}
		});
		List<GrantedAuthority> auths = getJdbcTemplate().query(authoritiesByResourceQuery,
			new Object[] {mid},new RowMapper<GrantedAuthority>() {
				public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
					String roleName = rolePrefix + rs.getString(1);
					GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
					return authority;
				}
			}
		);
		GrantedAuthority[] arrayAuths = SecurityHelper.convertToGrantedAuthority(auths);
		resource.setAuthorities(arrayAuths);
		return resource;
	}

	public List<GrantedAuthority> getGrantedAuthoritys(String url) {
		List<GrantedAuthority> auths = getJdbcTemplate().query(authoritiesByResourceUrlQuery,
				new Object[] {url+"%"},new RowMapper<GrantedAuthority>() {
					public GrantedAuthority mapRow(ResultSet rs, int rowNum) throws SQLException {
						String roleName = rolePrefix + rs.getString(1);
						GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
						return authority;
					}
				}
		);
		return auths;
	}

	List<ResourceDetail> getResourceDetails(String topId) {
		List<ResourceDetail> allResources = new ArrayList<ResourceDetail>();
		String[] topIds = topId.split(",");
		for (String tempParentId : topIds) {
			List<ResourceDetail> resources = getJdbcTemplate().query(
				loadResourcesQuery, new String[] {tempParentId+"-%"}, 
				new RowMapper<ResourceDetail>() {
		            public ResourceDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
						Integer mid = rs.getInt(1);
						String resType = rs.getString(2);
						String url = rs.getString(3);
						ResourceDetail resourceDetail = new ResourceDetail(mid, resType,url, new GrantedAuthority[]{new SimpleGrantedAuthority("HOLDER")});
						return resourceDetail;
		            }
	        });
			ResourceDetail resource = getJdbcTemplate().queryForObject(loadResourceQuery, new Integer[] {Integer.valueOf(tempParentId)}, new RowMapper<ResourceDetail>() {
				public ResourceDetail mapRow(ResultSet rs, int rowNum) throws SQLException {
					Integer mid = rs.getInt(1);
					String resType = rs.getString(2);
					String url = rs.getString(3);
					ResourceDetail resourceDetail = new ResourceDetail(mid, resType,url, new GrantedAuthority[]{new SimpleGrantedAuthority("HOLDER")});
					return resourceDetail;
				}
			});
			resources.add(resource);
			for (ResourceDetail resourceDetail : resources) {
				if(StringUtils.isNotBlank(resourceDetail.getUrl())){
					allResources.add(resourceDetail);
				}
			}
		}
		return allResources;
	}
}
