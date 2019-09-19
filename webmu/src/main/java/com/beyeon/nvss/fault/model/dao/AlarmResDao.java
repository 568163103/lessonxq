package com.beyeon.nvss.fault.model.dao;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Groups;
import com.beyeon.hibernate.fivsdb.TbAlarmRes;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("alarmResDao")
public class AlarmResDao extends NvsBaseDao {

    public void delete(String[] sids) {
        String[] paramNames = {"sids"};
        this.getFivs_w().bulkUpdateSQLByParamName("delete  s,us from tb_alarm_res s left join tb_user_alarm_res us  on s.id = us.alarm_res_id where s.id in (:sids)",
                paramNames, new Object[]{sids});
    }

    public void deleteByMaster(String sid) {
        this.getFivs_w().bulkUpdate("delete from TbAlarmRes t where t.sid =?", new Object[]{sid});
    }

    public void find(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select s from TbAlarmRes s where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("resId"))) {
            params.add(paramMap.get("resId"));
            sb.append("and s.resId = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("type"))) {
            params.add("______120%");
            if ("0".equals(paramMap.get("type"))) {
                sb.append("and s.resId like ? ");
            } else {
                sb.append("and s.resId not like ? ");
            }
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and s.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append("and s.alarmType = ? ");
        }

        sb.append(" order by s.resId desc");
        this.getFivs_r().find(pageObject, sb.toString(), params.toArray());
    }

    public List<TbAlarmRes> findByNoPageWithExcel(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select s from TbAlarmRes s where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("resId"))) {
            params.add(paramMap.get("resId"));
            sb.append("and s.resId = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("type"))) {
            params.add("______120%");
            if ("0".equals(paramMap.get("type"))) {
                sb.append("and s.resId like ? ");
            } else {
                sb.append("and s.resId not like ? ");
            }
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and s.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append("and s.alarmType = ? ");
        }

        sb.append(" order by s.resId desc");
        List list = this.getFivs_r().findList(sb.toString(), params.toArray());
        return list;
    }

    public List<TbAlarmRes> findByParam(Map<String, String> paramMap) {
        StringBuilder sb = new StringBuilder("select s from TbAlarmRes s where 1=1 ");
        List params = new ArrayList();
        if (StringUtils.isNotBlank(paramMap.get("id"))) {
            params.add(paramMap.get("id"));
            sb.append("and s.id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and s.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("ip"))) {
            params.add(paramMap.get("ip"));
            sb.append("and s.ip = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("type"))) {
            params.add(paramMap.get("type"));
            sb.append("and s.type = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(Integer.valueOf(paramMap.get("status")));
            sb.append("and s.status = ? ");
        }
        sb.append(" order by s.status desc");
        List list = this.getFivs_r().findList(sb.toString(), params.toArray());
        return list;
    }

    public TbAlarmRes findById(String id) {
        String hql = new String("select e from TbAlarmRes e where e.id=?");
        List resList = this.getFivs_r().find(hql, new Object[]{id});
        if (resList.size() > 0)
            return (TbAlarmRes) resList.get(0);
        return null;
    }


    public TbAlarmRes findByResIdAndType(String resId, String type) {
        String hql = new String("select e from TbAlarmRes e where e.resId=? and e.alarmType = ? ");
        List resList = this.getFivs_r().find(hql, new Object[]{resId, type});
        if (resList.size() > 0)
            return (TbAlarmRes) resList.get(0);
        return null;
    }

    public List findAllAlarmRes() {
        String sql = "select c from TbAlarmRes c";
        return this.getFivs_r().find(sql, null);
    }

    public List findDmuAlarmRes() {
        String sql = "select c.id,c.res_id as resId,b.type_id as alarmType,c.name,concat(b.level,'') as sid,c.description from tb_alarm_res c inner join tb_alarm_type b on c.alarm_type = b.id where c.alarm_type in ('27','28','29','30','31','32','33','34','35')";
        return this.getFivs_r().findSQLToBean(sql, null, TbAlarmRes.class);
    }

    public void findPageForUserAlarmRes(PageObject pageObject) {
        int type = Groups.TYPE_VIDEO;
        int type2 = Groups.TYPE_PLATFORM;
        StringBuilder sb = new StringBuilder("select a.id,a.name,a.status,a.encoderName,a.description from (SELECT c.id,ar.name,(CASE WHEN uar.alarm_res_id is null then 0 else 1 end ) as status ,g.name AS encoderName,u.name as description  ");
        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
        sb.append("INNER JOIN channel c ON c.id = gr.resource_id  INNER JOIN user u on ugr.user_id = u.id  ");
        sb.append("left join user_info ui on u.id = ui.id left join t_user_link ul on ul.amid = u.id  ");
        sb.append("LEFT JOIN tb_alarm_res ar ON  c.id = ar.res_id ");
        sb.append("LEFT JOIN tb_user_alarm_res uar ON ugr.user_id = uar.user_id AND ar.id = uar.alarm_res_id WHERE 1=1  and g.type = ? ");
        List params = new ArrayList();
        params.add(type);

        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("id"))) {
            params.add(paramMap.get("id"));
            sb.append("and c.id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("channelName"))) {
            params.add("%" + paramMap.get("channelName") + "%");
            sb.append("and c.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("encodeName"))) {
            params.add(paramMap.get("encodeName"));
            sb.append("and c.encoder_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            if ("0".equals(paramMap.get("status"))) {
                sb.append("and uar.alarm_res_id is null ");
            } else {
                sb.append("and uar.alarm_res_id is not null ");
            }
        }
        if (StringUtils.isNotBlank(paramMap.get("userid"))) {
            params.add(paramMap.get("userid"));
            sb.append("and ugr.user_id = ? ");
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
        sb.append(" union SELECT c.id,c.name,c.status,g.name as encoderName,u.name as description  ");
        sb.append("from groups g INNER JOIN group_resource gr ON g.id = gr.group_id ");
        sb.append("INNER JOIN user_group_right ugr ON g.id = ugr.group_id ");
        sb.append("INNER JOIN platform_res c ON c.server_id = gr.resource_id  INNER JOIN user u on ugr.user_id = u.id  ");
        sb.append("left join user_info ui on u.id = ui.id left join t_user_link ul on ul.amid = u.id  ");
        sb.append("LEFT JOIN tb_alarm_res ar ON  c.id = ar.res_id ");
        sb.append("LEFT JOIN tb_user_alarm_res uar ON ugr.user_id = uar.user_id AND ar.id = uar.alarm_res_id WHERE 1=1  and g.type = ? ");
        params.add(type2);
        if (StringUtils.isNotBlank(paramMap.get("id"))) {
            params.add(paramMap.get("id"));
            sb.append("and c.id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("channelName"))) {
            params.add("%" + paramMap.get("channelName") + "%");
            sb.append("and c.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("encodeName"))) {
            params.add(paramMap.get("encodeName"));
            sb.append("and c.server_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            if ("0".equals(paramMap.get("status"))) {
                sb.append("and uar.alarm_res_id is null ");
            } else {
                sb.append("and uar.alarm_res_id is not null ");
            }
        }

        if (StringUtils.isNotBlank(paramMap.get("userid"))) {
            params.add(paramMap.get("userid"));
            sb.append("and ugr.user_id = ? ");
        }
        if (null == SpringUtil.getCurrUser() || !"superadmin".equals(SpringUtil.getCurrUser().getUsername())) {
            sb.append("and u.name != 'superadmin'");
        }
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
        sb.append(") a order by a.status asc,a.id asc ");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }

    public List<Map> getUserGroupResources(String uid, Boolean isChannel) {
//		StringBuilder sb = new StringBuilder();
//		sb.append("select g.id as groupId, g.name as groupName,c.id as channelId,c.name as channelName ");
//		sb.append("from groups g inner join group_resource gr on g.id=gr.group_id inner join user_group_right ugr on g.id = ugr.group_id ");
//		int type = Groups.TYPE_VIDEO;
//		if (isChannel){
//			sb.append("inner join channel ");
//			sb.append("c on c.id = gr.resource_id ");
//			sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
//			sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
//		} else {
//			type = Groups.TYPE_PLATFORM;
//			sb.append("inner join platform_res ");
//			sb.append("c on c.server_id = gr.resource_id ");
//			sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
//			sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
//		}
        return getUserGroupResources(uid, isChannel, null);
    }

    public List<Map> getUserGroupResources(String uid, Boolean isChannel, String param) {
        StringBuilder sb = new StringBuilder();

        sb.append("select g.id as groupId, g.name as groupName,c.id as channelId,c.name as channelName ");
        sb.append("from groups g inner join group_resource gr on g.id=gr.group_id inner join user_group_right ugr on g.id = ugr.group_id ");
        int type = Groups.TYPE_VIDEO;
        if (isChannel) {
            sb.append("inner join channel ");
            sb.append("c on c.id = gr.resource_id ");
            sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
            sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
        } else {
            type = Groups.TYPE_PLATFORM;
            sb.append("inner join platform_res ");
            sb.append("c on c.server_id = gr.resource_id ");
            sb.append("LEFT JOIN user_tree ut on ugr.user_id = ut.user_id and c.id = ut.res_id ");
            sb.append("where ut.res_id is null and g.type = ? and ugr.user_id = ? ");
        }
        Object[] obj = new Object[]{type, uid};
        if (StringUtils.isNotBlank(param)) {
            param = "%" + param + "%";
            sb.append("and c.name like ?");
            obj = new Object[]{type, uid, param};
        }
        return this.getFivs_r().findSQLToMap(sb.toString(), obj);
    }

    public List<Map> getUserGroupPlatformRess(String uid) {
        StringBuilder sb = new StringBuilder();
        sb.append("select g.id as groupId, g.name as groupName,pr.id as channelId,pr.name as channelName,pr.parent_id as parentId ");
        sb.append("from groups g inner join group_resource gr on g.id=gr.group_id inner join user_group_right ugr on g.id = ugr.group_id ");
        sb.append("left join platform_res pr on pr.server_id = gr.resource_id where g.type = ? and ugr.user_id = ? ");
        return this.getFivs_r().findSQLToMap(sb.toString(), new Object[]{Groups.TYPE_PLATFORM, uid});
    }

    public List<Map<String, Object>> getUserGroupIds(String id) {
        StringBuilder hql = new StringBuilder("select t.group_id as groupId,t.group_right as groupRight from user_group_right t where t.user_id = ? ");
        return this.getFivs_r().findSQLToMap(hql.toString(), new String[]{id});
    }

    public List<Map<String, Object>> getUserAlarmRes(String id) {
        StringBuilder hql = new StringBuilder("select t.user_id as userId,t.alarm_res_id as alarmResId from tb_user_alarm_res t where t.user_id = ? ");
        return this.getFivs_r().findSQLToMap(hql.toString(), new String[]{id});
    }

    public void findRes(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select a.resId,a.alarmType,a.alarmTypeName,a.name,a.sid,a.description,a.type from ("
                + "select a.id as resId,b.value as alarmType,b.name as alarmTypeName,CONCAT(a.`name`,'(',b.name,')') as name ,null as sid ,null as description,'本级编码器' as type from  channel a "
                + "LEFT JOIN (select * from t_dict where pre_id = 7 and value in ('1','3','5','10','12','16','17','19')) b ON 1=1  "
                + "where not exists (select t.id from tb_alarm_res t where a.id = t.res_id and b.value = t.alarm_type)  UNION  "
                + "select a.id as resId,b.value as alarmType,b.name as alarmTypeName,CONCAT(a.`name`,'(',b.name,')') as name ,a.server_id as sid ,a.status as description,'下级编码器' as type from platform_res a "
                + "LEFT JOIN (select * from t_dict where pre_id = 7 and value in ('1','3','5','10','12','16','17','19')) b ON 1=1 "
                + "where not exists (select t.id from tb_alarm_res t where a.id = t.res_id and b.value = t.alarm_type) UNION  "
                + "select a.id as resId,b.value as alarmType,b.name as alarmTypeName,CONCAT(a.`name`,'(',b.name,')') as name ,a.master as sid ,a.status as description,a.type from dmu_equipment a "
                + "LEFT JOIN (select * from t_dict where pre_id = 7 and value in ('27','28','29','31','34','35')) b ON 1=1 "
                + "where not exists (select t.id from tb_alarm_res t where a.id = t.res_id and b.value = t.alarm_type) and a.type = '服务器' UNION "
                + "select a.id as resId,b.value as alarmType,b.name as alarmTypeName,CONCAT(a.`name`,'(',b.name,')') as name ,a.master as sid ,a.status as description ,a.type from dmu_equipment a "
                + "LEFT JOIN (select * from t_dict where pre_id = 7 and value in ('27','28','29','30','31','32','33','34','35')) b ON 1=1 "
                + "where not exists (select t.id from tb_alarm_res t where a.id = t.res_id and b.value = t.alarm_type) and a.type != '服务器' and a.type!='编码器' and a.type !='IP摄像机' "
                + ") a where 1 = 1  ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("resId"))) {
            params.add(paramMap.get("resId"));
            sb.append("and a.resId = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("type"))) {
            params.add(paramMap.get("type"));
            sb.append("and a.type = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("name"))) {
            params.add("%" + paramMap.get("name") + "%");
            sb.append("and a.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("alarmType"))) {
            params.add(paramMap.get("alarmType"));
            sb.append("and a.alarmType = ? ");
        }
        sb.append("and a.sid is null order by a.resId ,a.alarmType");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }


}
