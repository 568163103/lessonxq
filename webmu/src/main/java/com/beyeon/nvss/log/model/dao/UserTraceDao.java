package com.beyeon.nvss.log.model.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.TDict;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.TUserTrace;
import com.beyeon.hibernate.fivsdb.User;
import com.beyeon.hibernate.fivsdb.UserSecurity;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("userTraceDao")
public class UserTraceDao extends NvsBaseDao {

    public void findUserTrace(PageObject pageObject) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from (select t.user_name as userName,t.amid as amid,d.name as menuName,t.user_trace as userTrace,t.operate_date as operateDate,t.terminal_ip as terminalIp,'' as objectTime ");
        sb.append("from t_user_trace t left join t_user_link u on t.amid = u.amid LEFT JOIN (select * from t_dict where pre_id = ? )d on t.menu_name = d.value   where 1 = 1 ");
        List params = new ArrayList();
        params.add(TDict.OPERATION_TYPE);
        String users = ResourceUtil.getCoreConf("app.user");
        if(!users.contains(SpringUtil.getCurrUser().getUsername()+",")){
            if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
                sb.append("and (u.full_amid like ? or u.full_amid = ?) ");
                params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
                params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
            } else {
                sb.append("and (t.amid = ? or u.preamid = ?) ");
                params.add(SpringUtil.getCurrUser().getId());
                params.add(SpringUtil.getCurrUser().getId());
            }
        }

        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("userName"))) {
            params.add(paramMap.get("userName"));
            sb.append("and t.user_name = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("amid"))) {
            params.add(paramMap.get("amid"));
            sb.append("and t.amid = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("terminalIp"))) {
            params.add(paramMap.get("terminalIp"));
            sb.append("and t.terminal_ip = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("userTrace"))) {
            params.add(paramMap.get("userTrace"));
            sb.append("and t.menu_name = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("userType"))) {
            params.add(Short.valueOf(paramMap.get("userType")));
            sb.append("and t.user_type = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("openTimeOne"))) {
            paramMap.put("openTimeOne", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeOne"));
        sb.append("and t.operate_date>=? ");
        if (StringUtils.isBlank(paramMap.get("openTimeTwo"))) {
            paramMap.put("openTimeTwo", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeTwo"));
        sb.append("and t.operate_date<=? ");
        sb.append("union all  select  o.user_name as userName,o.user_id as  amid,d.name  as  menuName,IFNULL(c.name,p.name) as userTrace,str_to_date(o.time,'%Y-%m-%d %H:%i:%s') as operateDate,o.terminal_ip as terminalIp,o.object_time as objectTime  from operation_log o ");
        sb.append("LEFT JOIN channel c on o.description = c.id LEFT JOIN platform_res p on o.description = p.id LEFT JOIN (select * from t_dict where pre_id = ? )d on o.operation = d.value where 1=1 ");
        params.add(TDict.OPERATION_TYPE);
        if (StringUtils.isNotBlank(paramMap.get("amid"))) {
            System.out.println(paramMap.get("amid"));
            params.add(paramMap.get("amid"));
            sb.append("and o.user_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("userName"))) {
            params.add(paramMap.get("userName"));
            sb.append("and o.user_name = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("terminalIp"))) {
            params.add(paramMap.get("terminalIp"));
            sb.append("and o.terminal_ip = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("openTimeOne"))) {
            paramMap.put("openTimeOne", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeOne"));
        sb.append("and o.time between ? ");
        if (StringUtils.isBlank(paramMap.get("openTimeTwo"))) {
            paramMap.put("openTimeTwo", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeTwo"));
        sb.append("and ? ");
        if (StringUtils.isNotBlank(paramMap.get("userTrace"))) {
            params.add(paramMap.get("userTrace"));
            sb.append("and o.operation = ? ");
        }
        sb.append(")a order by operateDate desc ");
        System.out.println(sb.toString());
        this.getFivs_r().findSQLToBean(pageObject, sb.toString(), params.toArray(), TUserTrace.class);
    }


    public List findByNoPageWithExcel(PageObject pageObject) {
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from (select t.user_name as userName,t.amid as amid,t.menu_name as menuName,t.user_trace as userTrace,t.operate_date as operateDate,t.terminal_ip as terminalIp,'' as objectTime ");
        sb.append("from t_user_trace t left join t_user_link u on t.amid = u.amid    where 1 = 1 ");
        List params = new ArrayList();

        String users = ResourceUtil.getCoreConf("app.user");
        if(!users.contains(SpringUtil.getCurrUser().getUsername()+",")){
            if(SpringUtil.getCurrUser().getUserLinks().size() > 0 ){
                sb.append("and (u.full_amid like ? or u.full_amid = ?) ");
                params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid()+"-%");
                params.add(SpringUtil.getCurrUser().getUserLinks().get(0).getFullAmid());
            } else {
                sb.append("and (t.amid = ? or u.preamid = ?) ");
                params.add(SpringUtil.getCurrUser().getId());
                params.add(SpringUtil.getCurrUser().getId());
            }
        }

        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("userName"))) {
            params.add(paramMap.get("userName"));
            sb.append("and t.user_name = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("amid"))) {
            params.add(paramMap.get("amid"));
            sb.append("and t.amid = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("terminalIp"))) {
            params.add(paramMap.get("terminalIp"));
            sb.append("and t.terminal_ip = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("userTrace"))) {
            params.add(paramMap.get("userTrace"));
            sb.append("and t.menu_name = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("userType"))) {
            params.add(Short.valueOf(paramMap.get("userType")));
            sb.append("and t.user_type = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("openTimeOne"))) {
            paramMap.put("openTimeOne", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeOne"));
        sb.append("and t.operate_date>=? ");
        if (StringUtils.isBlank(paramMap.get("openTimeTwo"))) {
            paramMap.put("openTimeTwo", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeTwo"));
        sb.append("and t.operate_date<=? ");
        sb.append("union all  select  o.user_name as userName,o.user_id as  amid,t.name  as  menuName,IFNULL(c.name,p.name) as userTrace,str_to_date(o.time,'%Y-%m-%d %H:%i:%s') as operateDate,o.terminal_ip as terminalIp,o.object_time as objectTime  from operation_log o ");
        sb.append("LEFT JOIN channel c on o.description = c.id LEFT JOIN platform_res p on o.description = p.id LEFT JOIN (select * from t_dict where pre_id = ? )d on o.operation = d.value  LEFT JOIN t_dict t  on o.operation=t.value where 1=1 ");
        params.add(TDict.OPERATION_TYPE);
        if (StringUtils.isNotBlank(paramMap.get("amid"))) {
            System.out.println(paramMap.get("amid"));
            params.add(paramMap.get("amid"));
            sb.append("and o.user_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("userName"))) {
            params.add(paramMap.get("userName"));
            sb.append("and o.user_name = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("terminalIp"))) {
            params.add(paramMap.get("terminalIp"));
            sb.append("and o.terminal_ip = ? ");
        }
        if (StringUtils.isBlank(paramMap.get("openTimeOne"))) {
            paramMap.put("openTimeOne", DateUtil.format(DateUtil.addHours(new Date(), -1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeOne"));
        sb.append("and o.time between ? ");
        if (StringUtils.isBlank(paramMap.get("openTimeTwo"))) {
            paramMap.put("openTimeTwo", DateUtil.format(DateUtil.addDays(new Date(), 1), "yyyy-MM-dd HH:mm:ss"));
        }
        params.add(paramMap.get("openTimeTwo"));
        sb.append("and ? ");
        if (StringUtils.isNotBlank(paramMap.get("userTrace"))) {
            params.add(paramMap.get("userTrace"));
            sb.append("and o.operation = ? ");
        }
        sb.append(")a order by operateDate desc ");
        System.out.println(sb.toString());
        List list = this.getFivs_r().findSQLToBean(sb.toString(), params.toArray(), TUserTrace.class);
        return list;
    }

    public UserSecurity findUserSecurity(String userName){
        String sql = "select s.user_id as userId,s.error_count as errorCount,s.last_error_login_time as lastErrorLoginTime,s.description  from user_security s left join user u on s.user_id = u.id where u.name = ? ";
        List params = new ArrayList();
        params.add(userName);
        List list = this.getFivs_r().findSQLToBean(sql, params.toArray(), UserSecurity.class);
        if (list!=null && list.size()>0){
            UserSecurity userSecurity = (UserSecurity) list.get(0);
            return userSecurity;
        }
        return null;
    }

    public void resetUserSecurity(String userName){
        String sql = "update user_security s left join user u on s.user_id = u.id  set s.error_count = 0 , s.last_error_login_time = null where u.name = ? ";
        List params = new ArrayList();
        params.add(userName);
        this.getFivs_w().bulkUpdateSQL(sql, params.toArray());
    }

    public String findUserIdByUserName(String userName){
        String sql = "select u from User u where name = ? ";
        List params = new ArrayList();
        params.add(userName);
        List list = this.getFivs_r().find(sql, params.toArray());
        if (list !=null && list.size() > 0 ){
            User user = (User) list.get(0);
            return user.getId();
        }
        return null;
    }

}
