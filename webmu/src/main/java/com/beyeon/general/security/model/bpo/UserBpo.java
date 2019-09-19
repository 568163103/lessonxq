package com.beyeon.general.security.model.bpo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.aop.annotation.OpernateLog;
import com.beyeon.common.cache.CacheSupport;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.exception.AppExceptionRtImpl;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.security.model.dao.RoleDao;
import com.beyeon.general.security.model.dao.UserDao;
import com.beyeon.general.security.model.dto.UserDto;
import com.beyeon.hibernate.fivsdb.Channel;
import com.beyeon.hibernate.fivsdb.TPrivData;
import com.beyeon.hibernate.fivsdb.TRole;
import com.beyeon.hibernate.fivsdb.TUserLink;
import com.beyeon.hibernate.fivsdb.TUserRole;
import com.beyeon.hibernate.fivsdb.TUserRoleId;
import com.beyeon.hibernate.fivsdb.User;
import com.beyeon.hibernate.fivsdb.UserInfo;
import com.beyeon.nvss.bussiness.model.dao.UserTreeDao;

@Cache(cacheName = "用户")
@Component("userBpo")
public class UserBpo extends BaseBpo {
    private UserDao userDao;
    private RoleDao roleDao;
    private UserTreeDao userTreeDao;
    private CacheSupport cacheSupport;

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void setRoleDao(RoleDao roleDao) {
        this.roleDao = roleDao;
    }

    public void setUserTreeDao(UserTreeDao userTreeDao) {
        this.userTreeDao = userTreeDao;
    }

    public void setCacheSupport(CacheSupport cacheSupport) {
        this.cacheSupport = cacheSupport;
    }

    public UserDto get(String amid) {
        UserDto userDto = new UserDto();
        userDto.setUserInfo((UserInfo) this.userDao.getFivs(UserInfo.class, amid));
        userDto.setPrivDatas(this.userDao.getUserPrivData(amid));
        return userDto;
    }

    @OpernateLog
    public void save(UserDto userDto) {
        if (null != this.userDao.getUserByUsername(userDto.getUserInfo().getName())) {
            throw new AppExceptionRtImpl("用户已存在");
        }
        String uid = this.userDao.createId("user", userDto.getUserInfo().getTbUType() + userDto.getUserInfo().getTbDept(), "0", userDto.getUserInfo().getPosition());
        userDto.getUserInfo().setId(uid);
        userDto.getUserInfo().setProhibited_status(1);
        this.userDao.saveFivs(userDto.getUserInfo());
        if (userDto.getRoleMaps().size() > 0) {
            userDto.setAmidToRole();
            this.userDao.saveAllFivs(userDto.getRoleMaps());
        }
        TUserLink linkUser = new TUserLink();
        if ("0".equals(userDto.getTopManager())) {
            linkUser.setPreamid(SpringUtil.getCurrUser().getId());
            List<TUserLink> linkUsers = SpringUtil.getCurrUser().getUserLinks();
            if (null == linkUsers || linkUsers.size() == 0) {
                linkUser.setFullAmid(linkUser.getPreamid() + "-" + userDto.getUserInfo().getId());
            } else {
                linkUser.setFullAmid(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid() + "-" + userDto.getUserInfo().getId());
            }
        } else {
            linkUser.setPreamid("0");
            linkUser.setFullAmid(userDto.getUserInfo().getId());
        }

        linkUser.setAmid(userDto.getUserInfo().getId());
        this.userDao.saveFivs(linkUser);
        if (SpringUtil.getCurrUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).size() > 0 || StringUtils.isNotBlank(userDto.getCorpId())) {
            TPrivData privData = new TPrivData();
            privData.setUserId(uid);
            privData.setPrivCode(SpringUtil.getCurrUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).size() > 0 ? SpringUtil.getCurrUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).get(0) : userDto.getCorpId());
            privData.setPrivType(TPrivData.PRIV_TYPE_CORP);
            privData.setDmlflag(BaseEntity.insert);
            privData.setDmltime(new Timestamp(System.currentTimeMillis()));
            this.userDao.saveFivs(privData);
        }
		/*List<UserGroupRight> userGroupRights = this.userTreeDao.findUserGroupRightByUid(this.userTreeDao.findAdminUid());
		for (UserGroupRight userGroupRight : userGroupRights) {
			UserGroupRight newUserGroupRight = new UserGroupRight();
			newUserGroupRight.setUserId(uid);
			newUserGroupRight.setGroupId(userGroupRight.getGroupId());
			newUserGroupRight.setGroupRight(userGroupRight.getGroupRight());
			this.userTreeDao.saveFivs(newUserGroupRight);
		}*/
    }

    @OpernateLog
    public void update(UserDto userDto) {
        if (SpringUtil.getCurrUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).size() > 0 || StringUtils.isNotBlank(userDto.getCorpId())) {
            this.userDao.deletePrivDatasByUsername(new String[]{userDto.getUserInfo().getName()});
            TPrivData privData = new TPrivData();
            privData.setUserId(userDto.getUserInfo().getId());
            privData.setPrivCode(SpringUtil.getCurrUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).size() > 0 ? SpringUtil.getCurrUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).get(0) : userDto.getCorpId());
            privData.setPrivType(TPrivData.PRIV_TYPE_CORP);
            privData.setDmlflag(BaseEntity.insert);
            privData.setDmltime(new Timestamp(System.currentTimeMillis()));
            this.userDao.saveFivs(privData);
        }
        this.userDao.updateFivs(userDto.getUserInfo());
    }

    public void updateProStatus(String id, int proStatus) {
        userDao.updateProStatus(id, proStatus);
    }

    @OpernateLog
    public void delete(String[] usernames) {
        this.userDao.deleteUserByUserName(usernames);
    }

    @OpernateLog
    public void deleteExternalUser(String[] ids) {
        this.userDao.deleteExternalUser(ids);
    }

    public void find(PageObject pageObject) {
        this.userDao.find(pageObject);
    }

    public void findExternal(PageObject pageObject) {
        this.userDao.findExternal(pageObject);
    }

    public List<TRole> getRoles() {
        List<Integer> rids = new ArrayList<Integer>();
        String users = ResourceUtil.getCoreConf("app.user");
        if (!users.contains(SpringUtil.getCurrUser().getUsername() + ",")) {
            for (Iterator<GrantedAuthority> iterator = SpringUtil.getCurrUser().getAuthorities().iterator(); iterator.hasNext(); ) {
                GrantedAuthority next = iterator.next();
                if (!"public".equals(next.getAuthority()))
                    rids.add(Integer.valueOf(next.getAuthority().split("-")[0]));
            }
        }
        return this.roleDao.findRoleByRid(rids.toArray());
    }

    public List<Integer> getUserRoleIds(String amid) {
        return this.userDao.getUserRoleIds(amid);
    }

    public void authForUser(String amid, String[] rids) {
        this.userDao.deleteRoleIdsByAmid(amid);
        if (null != rids && rids.length > 0) {
            List<TUserRole> userRoles = new ArrayList<TUserRole>();
            for (int i = 0; i < rids.length; i++) {
                TUserRole userRole = new TUserRole(new TUserRoleId(Integer.valueOf(rids[i]), amid), (short) 1, new Date());
                userRoles.add(userRole);
            }
            this.userDao.saveAllFivs(userRoles);
        }
    }

    public void modifyPasswd(String username, String password) {
        this.userDao.modifyPasswd(username, password);
    }

    public UserInfo getUserInfoByUsername(String username) {
        return this.userDao.getUserInfoByUsername(username);
    }

    public User getUserByUsername(String username) {
        return this.userDao.getUserByUsername(username);
    }

    public boolean checkUnique(String attrName, String value, String id) {
        return this.userDao.checkUserUnique(value);
    }

    public TRole getRoleByRoleType(Short roleType) {
        return this.roleDao.getRoleByRoleType(roleType);
    }

    public List<Integer> getAmidsByUsernames(String[] usernames) {
        return this.userDao.getAmidsByUsernames(usernames);
    }

    public List findByDeptId(String deptId) {
        return this.userDao.findByDeptId(deptId);
    }


    public void unfreezeUser(String user) {
        this.userDao.resetUserSecurity(user);
    }

    public long findUserNum() {
        return this.userDao.findUserNum();
    }

    public List findUserNameAndId(List<String> uids) {
        return this.userDao.findUserNameAndId(uids);
    }

    @Cache(cacheName = "用户", refreshExpre = Cache.Bm5)
    public void findUser() {
        List<User> list = userDao.findAllUser();
        Map<String, String> currObjectlMap = new HashMap<String, String>();
        for (User user : list) {
            currObjectlMap.put(user.getId(), user.getName());
        }
        User.setObjectMap(currObjectlMap);
    }
}
