package com.beyeon.nvss.device.model.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;

@Component("channelDao")
public class ChannelDao extends NvsBaseDao {

    public void find(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select c.id,c.name,c.encoder_id as encoderId,c.num,c.has_ptz as hasPtz,c.has_audio as hasAudio,c.stream_count as streamCount,c.description,c.enabled,crp.plan_name as recordPlanName,e.name as encoderName  ");
        sb.append("from channel c  left join channel_record_plan crp on crp.channel_id = c.id left join encoder e on c.encoder_id = e.id left join server_encoder se on e.id = se.encoder_id where 1=1 ");
        List params = new ArrayList();
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
            params.add("%" + paramMap.get("encodeName") + "%");
            sb.append("and e.name like ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("mduId"))) {
            params.add(paramMap.get("mduId"));
            sb.append("and se.server_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasPtz"))) {
            params.add(Boolean.valueOf(paramMap.get("hasPtz")));
            sb.append("and c.has_ptz = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasAudio"))) {
            params.add(Boolean.valueOf(paramMap.get("hasAudio")));
            sb.append("and c.has_audio = ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append("order by c.id asc ");
        this.getFivs_r().findSQLToBean(pageObject, sb.toString(), params.toArray(), Channel.class);
    }

    public void findExternal(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("SELECT c.id,c.name,c.server_id AS serverId,c.subnum,c.has_ptz AS hasPtz,c.description,c.location,c.purpose,e.name AS encoderName  ");
        sb.append("from platform_res c left join encoder e on c.server_id = e.id left join server_encoder se on e.id = se.encoder_id where 1=1 ");
        List params = new ArrayList();
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
            sb.append("and e.id = ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("mduId"))) {
            params.add(paramMap.get("mduId"));
            sb.append("and se.server_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasPtz"))) {
            params.add(Boolean.valueOf(paramMap.get("hasPtz")));
            sb.append("and c.has_ptz = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasAudio"))) {
            params.add(Boolean.valueOf(paramMap.get("hasAudio")));
            sb.append("and c.has_audio = ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append("order by c.id asc ");
        this.getFivs_r().findSQLToBean(pageObject, sb.toString(), params.toArray(), PlatformRes.class);
    }

    public List<Map> find() {
        StringBuilder sb = new StringBuilder("select c.name ,c.id from channel c ");
        return this.getFivs_r().findSQLToMap(sb.toString(), null);
    }

    public List<Map> findPlatformRes() {
        StringBuilder sb = new StringBuilder("select pr.name ,pr.id from platform_res pr where pr.id like '%______120______%' ");
        return this.getFivs_r().findSQLToMap(sb.toString(), null);
    }

    public PlatformRes findPlatformRes(String channel_id) {
        List list = this.getFivs_r().find("select t from PlatformRes t where t.id = ? ", new Object[]{channel_id});
        if (null != list && list.size() > 0) {
            PlatformRes record = (PlatformRes) list.get(0);
            return record;
        }
        return null;
    }

    public Channel findChannelAndMsu(String id) {
        StringBuilder sb = new StringBuilder("select c.id,c.name,c.encoder_id as encoderId,c.num,c.has_ptz as hasPtz,c.has_audio as hasAudio,c.stream_count as streamCount,c.description,c.enabled from channel c left join mss_channel mc on c.id = mc.channel_id where c.id = ? ");
        return (Channel) this.getFivs_r().findUniqueSqlToBean(sb.toString(), new String[]{id}, Channel.class);
    }

    public Channel findById(String id) {
        String hql = new String("select e from Channel e where e.id=?");
        List resList = this.getFivs_r().find(hql, new Object[]{id});
        if (resList.size() > 0)
            return (Channel) resList.get(0);
        return null;
    }

    public List<TbAlarmRes> findAlarmResById(String id) {
        String hql = new String("select e from TbAlarmRes e where   e.resId=?");
        return this.getFivs_r().find(hql, new Object[]{id});
    }

    public List findByEncoderId(String encoderId) {
        StringBuilder sb = new StringBuilder("select c.id,c.name,c.encoder_id as encoderId,c.num,c.has_ptz as hasPtz,c.has_audio as hasAudio,c.stream_count as streamCount,c.description,");
        sb.append("crp.plan_name as recordPlanName from channel c left join channel_record_plan crp on crp.channel_id = c.id where c.encoder_id = ? ");
        return this.getFivs_r().findSQLToBean(sb.toString(), new String[]{encoderId}, Channel.class);
    }

    public List findChannelIdByEncoderId(String[] encoderIds) {
        StringBuilder sb = new StringBuilder("select c.id from channel c where c.encoder_id in (:encoderIds) ");
        return this.getFivs_r().findSQLByParamName(sb.toString(), new String[]{"encoderIds"}, new Object[]{encoderIds});
    }

    public List findPlatformResByEncoderId(String encoderId) {
        StringBuilder sql = new StringBuilder("select p.id,p.name,p.server_id as encoderId,p.has_ptz as hasPtz,p.status ");
        sql.append("from platform_res p where p.server_id = ?");
        return this.getFivs_r().findSQLToBean(sql.toString(), new Object[]{encoderId}, Channel.class);
    }

    public List<String> findOutChannel(String encoderId, Integer channelCount) {
        StringBuilder sql = new StringBuilder("select c.id from channel c where c.encoder_id = ? and c.num >= ? ");
        return this.getFivs_w().findSQL(sql.toString(), new Object[]{encoderId, channelCount});
    }

    public void delete(String[] ids) {
        StringBuilder sql = new StringBuilder("delete c,mc,crp,a,p,gr from channel c left join mss_channel mc on c.id = mc.channel_id ");
        sql.append("left join channel_record_plan crp on crp.channel_id = c.id left join action a on c.id = a.target left join preset p on c.id = p.channel_id ");
        sql.append("left join group_resource gr on gr.resource_id = c.id ");
        sql.append("where c.id in (:channelIds)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"channelIds"}, new Object[]{ids});
    }

    public void deleteChannelAndMsu(String encoderId, Integer channelCount) {
        StringBuilder sql = new StringBuilder("delete c,mc,crp,a,p,gr from channel c left join mss_channel mc on c.id = mc.channel_id ");
        sql.append("left join channel_record_plan crp on crp.channel_id = c.id left join action a on c.id = a.target left join preset p on c.id = p.channel_id ");
        sql.append("left join group_resource gr on gr.resource_id = c.id ");
        sql.append("where c.encoder_id = ? and c.num >= ?");
        this.getFivs_w().bulkUpdateSQL(sql.toString(), new Object[]{encoderId, channelCount});
    }

    public void deleteChannelAndMsu(String[] encoderIds) {
        StringBuilder sql = new StringBuilder("delete c,crp,gr,tar,a,p from channel c ");
        sql.append("left join channel_record_plan crp on crp.channel_id = c.id left join action a on c.id = a.target left join preset p on c.id = p.channel_id ");
        sql.append("left join group_resource gr on gr.resource_id = c.id ");
        sql.append("left join tb_alarm_res tar on tar.res_id = c.id ");
        sql.append("where c.encoder_id in (:encoderIds)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"encoderIds"}, new Object[]{encoderIds});
    }

    public void deletePlatformResByEncoderId(String[] encoderIds) {
        StringBuilder sql = new StringBuilder("delete p from platform_res p ");
        sql.append("where p.server_id in (:encoderIds)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"encoderIds"}, new Object[]{encoderIds});
    }

    public void deletePlatformResByChannelId(String[] channelIds) {
        StringBuilder sql = new StringBuilder("delete p from platform_res p ");
        sql.append("where p.id in (:channelIds)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"channelIds"}, new Object[]{channelIds});
    }

    public void updateMsu(String encoderId, String serverId) {
        String sql = new String("update channel c ,mss_channel mc set mc.server_id =? where c.id = mc.channel_id and c.encoder_id = ?");
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{serverId, encoderId});
    }

    public void updateUserTree(String name, String resId) {
        String sql = new String("update channel c ,user_tree ut set ut.name = ? where c.id = ut.res_id and c.id = ?");
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{name, resId});
    }

    public void updateChannelName(String name, String resId) {
        String sql = new String("update channel c  set c.name = ? where c.id  = ?");
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{name, resId});
    }

    public void updateAlarmResName(String name, String resId) {
        String sql = new String("update tb_alarm_res t left JOIN channel c on t.res_id = c.id  left JOIN (select * from t_dict where pre_id = ? ) d on t.alarm_type = d.value set t.name = CONCAT( ?,'(',d.name,')') where  c.id = ? ");
        this.getFivs_w().bulkUpdateSQL(sql, new Object[]{TDict.ALARM_TYPE, name, resId});
    }

    public MssChannel findMsuChannel(String channelId) {
        StringBuilder sb = new StringBuilder("select t from MssChannel t where t.channelId = ? ");
        return (MssChannel) this.getFivs_r().findUnique(sb.toString(), new String[]{channelId});
    }

    public ImsGisInfo findGisInfo(String channelId) {
        StringBuilder sb = new StringBuilder("select t from ImsGisInfo t where t.channelId = ?");
        return (ImsGisInfo) this.getFivs_r().findUnique(sb.toString(), new String[]{channelId});
    }

    public List<Object[]> findNotInitGisInfos() {
        StringBuilder sb = new StringBuilder();
        sb.append("select c.id,c.server_id from platform_res c left join ims_gis_info igi on igi.channel_id = c.id where igi.channel_id is null and c.id like '______120%' ");
        return (List<Object[]>) this.getFivs_r().findSQL(sb.toString(), null);
    }

    public List<ImsGisInfo> findPlatformResGisInfos(String encoderId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select igi from PlatformRes c , ImsGisInfo igi where igi.channelId = c.id and c.serverId = ? and c.id like '______120%' ");
        return (List<ImsGisInfo>) this.getFivs_r().find(sb.toString(), new Object[]{encoderId});
    }

    public List<ImsGisInfo> findChannelGisInfos(String encoderId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select igi from Channel c , ImsGisInfo igi where igi.channelId = c.id and c.encoderId = ? ");
        return (List<ImsGisInfo>) this.getFivs_r().find(sb.toString(), new Object[]{encoderId});
    }

    public List<Map> findGisInfos(PageObject pageObject, String userId, String encoderModel) {
        StringBuilder sb = new StringBuilder("select c.id as channelId,c.name as channelName,igi.longitude,igi.latitude from user_tree ut inner join ");
        if (!"platform".equals(encoderModel)) {
            sb.append("channel ");
        } else {
            sb.append("platform_res ");
        }
        sb.append("c on ut.res_id  = c.id left join ims_gis_info igi on igi.channel_id = c.id where ut.user_id = ? order by igi.longitude,igi.latitude");
        return (List<Map>) this.getFivs_r().findSQLToMap(pageObject, sb.toString(), new Object[]{userId});
    }

    public List<Map> findGisInfos(Map<String, String> pos, String userId, String encoderModel) {
        StringBuilder sb = new StringBuilder("select c.id as channelId,c.name as channelName,igi.longitude,igi.latitude from user_tree ut inner join ");
        if (!"platform".equals(encoderModel)) {
            sb.append("channel ");
        } else {
            sb.append("platform_res ");
        }
        sb.append("c on ut.res_id  = c.id left join ims_gis_info igi on igi.channel_id = c.id where ut.user_id = ? ");
        sb.append("and igi.longitude >= ? and igi.longitude <= ? ").append("and igi.latitude >= ? and igi.latitude <= ? ");
        return (List<Map>) this.getFivs_r().findSQLToMap(sb.toString(),
                new Object[]{userId, Double.valueOf(pos.get("longitude").split(";")[0]), Double.valueOf(pos.get("longitude").split(";")[1]),
                        Double.valueOf(pos.get("latitude").split(";")[0]), Double.valueOf(pos.get("latitude").split(";")[1])});
    }

    public List<Preset> findPreset(String channelId) {
        StringBuilder sb = new StringBuilder("select t from Preset t where t.channelId = ?");
        return (List<Preset>) this.getFivs_r().find(sb.toString(), new String[]{channelId});
    }

    public ChannelRecordPlan findRecordPlan(String channelId) {
        return (ChannelRecordPlan) this.getFivs_r().findUnique("select crp from ChannelRecordPlan crp where crp.channelId = ?", new Object[]{channelId});
    }

    public void deleteRecordPlan(String channelId) {
        this.getFivs_w().bulkUpdateSQL("delete crp from channel_record_plan crp where crp.channel_id = ?", new Object[]{channelId});
    }

    public void deleteMsuChannel(String channelId) {
        this.getFivs_w().bulkUpdateSQL("delete crp from mss_channel crp where crp.channel_id = ?", new Object[]{channelId});
    }

    public void findSnapshot(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select c.id, c.ot_id as otId,c.res_id as resId,e.name as encoderName,c.event_type as eventType,c.create_time as createTime,c.upload_time as uploadTime,c.url as url,c.remark as remark ");
        sb.append("from channel_snapshot c left join encoder e on e.id = c.ot_id where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("resId"))) {
            params.add(paramMap.get("resId"));
            sb.append("and c.res_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("remark"))) {
            params.add(paramMap.get("remark"));
            sb.append("and c.remark = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("isNew")) && "true".equals(paramMap.get("isNew"))) {
            sb.append("group by c.res_id ");
        } else {
            if (StringUtils.isNotBlank(paramMap.get("beginTime"))) {
                params.add(DateUtil.parse(paramMap.get("beginTime"), DateUtil.yyyyMMddHHmmssSpt));
                sb.append("and c.upload_time > ? ");
            }
            if (StringUtils.isNotBlank(paramMap.get("endTime"))) {
                params.add(DateUtil.parse(paramMap.get("endTime"), DateUtil.yyyyMMddHHmmssSpt));
                sb.append("and c.upload_time < ? ");
            }
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.res_id like ? ");
        }
        sb.append("order by c.id desc");
        this.getFivs_r().findSQLToBean(pageObject, sb.toString(), params.toArray(), StringUtils.isNotBlank(paramMap.get("isNew")) && "true".equals(paramMap.get("isNew")), ChannelSnapshot.class);
    }

    public List<ChannelSnapshot> findUserSnapshot(PageObject pageObject, String userId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ut.res_id as resId,cs.url as url,pr.status as platStatus from user_tree ut left join (select res_id,max(create_time) create_time from channel_snapshot group by res_id ) t ");
        sb.append("on ut.res_id = t.res_id left join channel_snapshot cs on cs.res_id = t.res_id and cs.create_time = t.create_time left join channel c on c.id = ut.res_id ");
        sb.append("left join platform_res pr on pr.id = ut.res_id ");
        sb.append("where ut.user_id = ? and ut.res_id like ? ");
        return this.getFivs_r().findSQLToBean(pageObject, sb.toString(), new Object[]{userId, "%______120______%"}, ChannelSnapshot.class);
    }

    public String getSnapshot(String channelId) {
        StringBuilder sb = new StringBuilder();
        sb.append("select cs.url as url from channel_snapshot cs inner join (select res_id,max(create_time) create_time from channel_snapshot where res_id = ? group by res_id ) t ");
        sb.append("on cs.res_id = t.res_id and cs.create_time = t.create_time ");
        List<String> urls = this.getFivs_r().findSQL(sb.toString(), new Object[]{channelId});
        if (urls.size() > 0) {
            for (String url : urls) {
                if (StringUtils.isNotBlank(url)) {
                    return url;
                }
            }
        }
        return "";
    }

    public List findAllChannel() {
        String sql = "select c from Channel c";
        return this.getFivs_r().find(sql, null);
    }

    public List findAllByMsuChannel() {
        StringBuilder sb = new StringBuilder();
        sb.append("select c.id,c.name,c.encoder_id as encoderId,c.num,c.has_ptz as hasPtz");
        sb.append(",c.has_audio as hasAudio,c.stream_count as streamCount,c.description");
        sb.append(" from mss_channel mc left join channel c on mc.channel_id=c.id");
        return this.getFivs_r().findSQLToBean(sb.toString(), null, Channel.class);
    }


    public List<Channel> findByNoPageWithExcel(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select c.id,c.name,c.encoder_id as encoderId,c.num,c.has_ptz as hasPtz,c.has_audio as hasAudio,c.stream_count as streamCount,c.description,c.enabled,crp.plan_name as recordPlanName,e.name as encoderName ");
        sb.append("from channel c left join mss_channel mc on c.id = mc.channel_id left join channel_record_plan crp on crp.channel_id = c.id left join encoder e on c.encoder_id = e.id left join server_encoder se on e.id = se.encoder_id where 1=1 ");
        List params = new ArrayList();
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
            params.add("%" + paramMap.get("encodeName") + "%");
            sb.append("and e.name like ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("mduId"))) {
            params.add(paramMap.get("mduId"));
            sb.append("and se.server_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasPtz"))) {
            params.add(Boolean.valueOf(paramMap.get("hasPtz")));
            sb.append("and c.has_ptz = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasAudio"))) {
            params.add(Boolean.valueOf(paramMap.get("hasAudio")));
            sb.append("and c.has_audio = ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append("order by c.id asc ");
        List list = this.getFivs_r().findSQLToBean(sb.toString(), params.toArray(), Channel.class);
        return list;
    }

    public List<PlatformRes> findExternalByNoPageWithExcel(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("SELECT c.id,c.name,c.server_id AS serverId,c.subnum,c.has_ptz AS hasPtz,c.description,c.location,c.purpose,e.name AS encoderName  ");
        sb.append("from platform_res c left join encoder e on c.server_id = e.id left join server_encoder se on e.id = se.encoder_id where 1=1 ");
        List params = new ArrayList();
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
            params.add("%" + paramMap.get("encodeName") + "%");
            sb.append("and e.name like ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("mduId"))) {
            params.add(paramMap.get("mduId"));
            sb.append("and se.server_id = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasPtz"))) {
            params.add(Boolean.valueOf(paramMap.get("hasPtz")));
            sb.append("and c.has_ptz = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("hasAudio"))) {
            params.add(Boolean.valueOf(paramMap.get("hasAudio")));
            sb.append("and c.has_audio = ? ");
        }

        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append("order by c.id asc ");
        List list = this.getFivs_r().findSQLToBean(sb.toString(), params.toArray(), PlatformRes.class);
        return list;
    }

    public List findChannelByIds(String[] channelIds) {
        StringBuilder sb = new StringBuilder("select c.name,c.id from platform_res c where c.id in (:channelIds) ");
        return this.getFivs_r().findSQLToMapByParamName(sb.toString(), new String[]{"channelIds"}, new Object[]{channelIds});
    }

}
