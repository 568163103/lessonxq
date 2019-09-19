package com.beyeon.general.security.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.dao.BaseDao;
import com.beyeon.common.web.model.entity.BaseEntity;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TPrivData;
import com.beyeon.hibernate.fivsdb.TbUser;
import com.beyeon.hibernate.fivsdb.User;
import com.beyeon.hibernate.fivsdb.UserInfo;

@Component("userDao")
public class UserDao extends BaseDao {

    public void find(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select u.*,ui.* from user u left join user_info ui on u.id = ui.id left join t_user_link ul on ul.amid = u.id where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("userName"))) {
            params.add("%" + paramMap.get("userName") + "%");
            sb.append("and u.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and u.id like ? ");
        }
        if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
            sb.append("and u.name != 'superadmin'");
        }
        String userNames = ResourceUtil.getCoreConf("app.user");
        if (!userNames.contains(SpringUtil.getCurrUser().getUsername() + ",")) {
            if (SpringUtil.getCurrUser().getUserLinks().size() > 0) {
                sb.append("and (ul.full_amid like ? or ul.full_amid = ?) ");
                params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid() + "-%");
                params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
            } else {
                sb.append("and (u.id = ? or ul.preamid = ?) ");
                params.add(SpringUtil.getCurrUser().getId());
                params.add(SpringUtil.getCurrUser().getId());
            }
        }
        sb.append(" order by u.id desc");
        this.getFivs_r().findSQL(pageObject, sb.toString(), params.toArray(), UserInfo.class);
    }

    public void findExternal(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select u.id,u.name,u.sid,e.name as saname from tb_user u left join encoder e on e.id = u.sid where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("userName"))) {
            params.add("%" + paramMap.get("userName") + "%");
            sb.append("and u.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and u.id like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("sid"))) {
            params.add(paramMap.get("sid"));
            sb.append("and u.sid = ? ");
        }
        sb.append(" order by u.id desc");
        this.getFivs_r().findSQL(pageObject, sb.toString(), params.toArray());
    }

    public List<TbUser> findExternalUserBySid(String sid) {
        StringBuilder sb = new StringBuilder("select u.id,u.name,u.sid from tb_user u where 1=1 and u.sid = ?");
        sb.append(" order by u.id desc");
        return this.getFivs_r().findSQL(sb.toString(), new Object[]{sid}, TbUser.class);
    }

    public List findUserByIds(String[] ids) {
        String hql = new String("select u.id,u.name,u.sid from tb_user u where 1=1 and u.id in (:ids) ");
        return this.getFivs_r().findSQLToMapByParamName(hql, new String[]{"ids"}, new Object[]{ids});
    }

    public List<Integer> getUserRoleIds(String amid) {
        return this.getFivs_r().find("select t.id.rid from TUserRole t where t.id.amid =?",
                new Object[]{amid});
    }

    public List<Integer> getRoleIds() {
        return this.getFivs_r().find("select t.rid from TRole", null);
    }

    public void deleteRoleIdsByAmid(String amid) {
        this.getFivs_w().bulkUpdate("delete from TUserRole t where t.id.amid =?", new Object[]{amid});
    }

    public void deleteUserByUserName(String[] usernames) {
        String[] paramNames = {"users"};
        this.getFivs_w().bulkUpdateSQLByParamName("DELETE u,ui,ul,ur,pd FROM `user` u left join user_info ui on u.id = ui.id left join t_user_link ul on u.id = ul.amid left join t_user_role ur on u.id=ur.amid left join t_priv_data pd on pd.user_id = u.id where u.name in (:users)",
                paramNames,
                new Object[]{usernames});
    }

    public void deleteExternalUser(String[] ids) {
        String[] paramNames = {"ids"};
        this.getFivs_w().bulkUpdateSQLByParamName("DELETE u  FROM `tb_user` u where u.id in (:ids)",
                paramNames,
                new Object[]{ids});
    }

    public void modifyPasswd(String username, String password) {
        String last = DateUtil.getTime();
        String sql = "update user u join user_info ui on u.id = ui.id  set u.`password` = ? , ui.last_login_time = ? where   u.`name` = ?";
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{password, last, username});
    }

    public UserInfo getUserInfoByUsername(String username) {
        StringBuilder sb = new StringBuilder("select t from UserInfo t where t.name = ? ");
        List params = new ArrayList();
        params.add(username);
        List list = this.getFivs_r().find(sb.toString(), params.toArray());
        if (list.size() > 0) {
            return (UserInfo) list.get(0);
        }
        return null;
    }

    public boolean checkUserUnique(String username) {
        StringBuilder sql = new StringBuilder("select t from User t where t.name = ? ");
        List params = new ArrayList();
        params.add(username);
        List list = this.getFivs_r().find(sql.toString(), params.toArray());
        return list.size() == 0;
    }

    public void deletePrivDatasByUsername(String[] username) {
        String[] paramNames = {"users"};
        this.getFivs_w().bulkUpdateByParamName("delete from TPrivData t where t.userId in (select u.id from User u where u.name in (:users)) ",
                paramNames, new Object[]{username});
    }

    public List<TPrivData> getUserPrivData(String amid) {
        return this.getFivs_r().find("select t from TPrivData t where t.id.userId = ?", new Object[]{amid});
    }

    public List<Integer> getAmidsByUsernames(String[] usernames) {
        return this.getFivs_r().findByParamName("select t.id from User t where t.name in (:users)",
                new String[]{"users"},
                new Object[]{usernames});
    }

    public User getUserByUsername(String userName) {
        return (User) this.getFivs_r().findUnique("select t from User t where t.name = ?",
                new Object[]{userName});
    }

    public List findByDeptId(String deptId) {
        String sql = "select t.amid as amid,CONCAT(t.name,'---',t.py) as label,t.mobile as mobile from t_user t,t_dept u where t.appCode = ? and u.dept_id = t.dept_id and t.dmlflag != ? and (u.dept_id = ? or u.preid = ? or u.full_did like ?) ";
        return this.getFivs_r().findSQLToMap(sql,
                new Object[]{ResourceUtil.getAppCode(), BaseEntity.delete,
                        Integer.parseInt(deptId), Integer.parseInt(deptId),
                        "%_" + deptId + "_%"});
    }

    public long findUserNum() {
        StringBuilder sb = new StringBuilder("select count(*) from User s ");
        return (Long) this.getFivs_r().findUnique(sb.toString(), null);
    }

    public List findUserNameAndId(List<String> uids) {
        return this.getFivs_r().findSQLToMapByParamName("select id,name from user where id in (:uids) ", new String[]{"uids"}, new Object[]{uids});
    }

    public List findAllUser() {
        String hql = new String("select s from User s ");
        return this.getFivs_r().find(hql, null);
    }

    public void resetUserSecurity(String userName) {
        String sql = "update user_security s left join user u on s.user_id = u.id  set s.error_count = 0 , s.last_error_login_time = null where u.name = ? ";
        List params = new ArrayList();
        params.add(userName);
        this.getFivs_w().bulkUpdateSQL(sql, params.toArray());
    }

    public void updateProStatus(String id, int proStatus) {
        String sql = "update user_info ui set ui.prohibited_status = ? where ui.id = ?";
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{proStatus, id});
    }
}
