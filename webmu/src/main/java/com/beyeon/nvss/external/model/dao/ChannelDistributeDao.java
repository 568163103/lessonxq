package com.beyeon.nvss.external.model.dao;

import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.ChannelDistribute;
import com.beyeon.hibernate.fivsdb.TbShieldRes;
import com.beyeon.hibernate.fivsdb.TbShieldUser;
import com.beyeon.nvss.common.model.dao.NvsBaseDao;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("channelDistributeDao")
public class ChannelDistributeDao extends NvsBaseDao {

    public void find(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select * from (SELECT c.id,d.id as distributeId,c.name,c.server_id AS serverId,c.has_ptz AS hasPtz,c.description,");
        sb.append("c.status,c.location,c.purpose,e.name AS encoderName  ,d.create_time as createTime,d.create_user_id as createUserId,d.create_user_name as createUserName ");
        sb.append(" from  platform_res c left join encoder e on c.server_id = e.id left join server_encoder se on e.id = se.encoder_id  ");
        sb.append("INNER JOIN channel_distribute d on c.id = d.channel_id  where 1=1  ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("sid"))) {
            params.add(paramMap.get("sid"));
            sb.append("and d.server_id = ? ");
        }
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
        if (StringUtils.isNotBlank(paramMap.get("msuId"))) {
            params.add(paramMap.get("msuId"));
            sb.append("and mc.server_id = ? ");
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
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(Boolean.valueOf(paramMap.get("status")));
            sb.append("and c.status = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append(" union SELECT c.id,d.id as distributeId,c.name,c.encoder_id AS serverId,c.has_ptz AS hasPtz,c.description,");
        sb.append(" c.status,c.location,c.purpose,e.name AS encoderName  ,d.create_time as createTime,d.create_user_id as createUserId,d.create_user_name as createUserName");
        sb.append(" from  channel c left join encoder e on c.encoder_id = e.id left join server_encoder se on e.id = se.encoder_id ");
        sb.append(" INNER JOIN channel_distribute d on c.id = d.channel_id  where 1=1 ");
        if (StringUtils.isNotBlank(paramMap.get("sid"))) {
            params.add(paramMap.get("sid"));
            sb.append("and d.server_id = ? ");
        }
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
        if (StringUtils.isNotBlank(paramMap.get("msuId"))) {
            params.add(paramMap.get("msuId"));
            sb.append("and mc.server_id = ? ");
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
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(Boolean.valueOf(paramMap.get("status")));
            sb.append("and c.status = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append(") a order by a.createTime desc,a.id asc ");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }


    public void findRes(PageObject pageObject) {
        StringBuilder sb = new StringBuilder("select * from (SELECT c.id,c.name,c.server_id AS serverId,c.has_ptz AS hasPtz,c.description,");
        sb.append("c.status,c.location,c.purpose,e.name AS encoderName  ,d.create_time as createTime,d.create_user_id as createUserId,d.create_user_name as createUserName ");
        sb.append(" from  platform_res c left join encoder e on c.server_id = e.id left join server_encoder se on e.id = se.encoder_id  ");
        sb.append("LEFT JOIN channel_distribute d on c.id = d.channel_id  where 1=1 ");
        List params = new ArrayList();
        Map<String, String> paramMap = pageObject.getParams();
        if (StringUtils.isNotBlank(paramMap.get("sid"))) {
            params.add(paramMap.get("sid"));
            sb.append("and c.server_id != ? ");
            params.add(paramMap.get("sid"));
            sb.append("and (d.server_id!= ? or d.server_id is null) ");
        }
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
        if (StringUtils.isNotBlank(paramMap.get("msuId"))) {
            params.add(paramMap.get("msuId"));
            sb.append("and mc.server_id = ? ");
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
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(Boolean.valueOf(paramMap.get("status")));
            sb.append("and c.status = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append(" union SELECT c.id,c.name,c.encoder_id AS serverId,c.has_ptz AS hasPtz,c.description,c.status,c.location,c.purpose,e.name AS encoderName");
        sb.append(" ,d.create_time as createTime,d.create_user_id as createUserId,d.create_user_name as createUserName from  channel c ");
        sb.append(" left join encoder e on c.encoder_id = e.id left join server_encoder se on e.id = se.encoder_id  LEFT JOIN channel_distribute d on c.id = d.channel_id  where 1=1 ");
        if (StringUtils.isNotBlank(paramMap.get("sid"))) {
            params.add(paramMap.get("sid"));
            sb.append("and c.encoder_id != ? ");
            params.add(paramMap.get("sid"));
            sb.append("and (d.server_id!= ? or d.server_id is null) ");
        }
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
        if (StringUtils.isNotBlank(paramMap.get("msuId"))) {
            params.add(paramMap.get("msuId"));
            sb.append("and mc.server_id = ? ");
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
        if (StringUtils.isNotBlank(paramMap.get("status"))) {
            params.add(Boolean.valueOf(paramMap.get("status")));
            sb.append("and c.status = ? ");
        }
        if (StringUtils.isNotBlank(paramMap.get("position"))) {
            params.add(paramMap.get("position") + "%");
            sb.append("and c.id like ? ");
        }
        sb.append(" ) a order by a.createTime desc ,a.id asc ");
        this.getFivs_r().findSQLToMap(pageObject, sb.toString(), params.toArray());
    }

    public void delete(String[] ids) {
        StringBuilder sql = new StringBuilder("delete c from channel_distribute c ");
        sql.append("where c.id in (:ids)");
        this.getFivs_w().bulkUpdateSQLByParamName(sql.toString(), new String[]{"ids"}, new Object[]{ids});
    }

    public ChannelDistribute findById(int id) {
        List list = this.getFivs_r().find("select t from ChannelDistribute t where t.id = ? ", new Object[]{id});
        if (null != list && list.size() > 0) {
            ChannelDistribute record = (ChannelDistribute) list.get(0);
            return record;
        }
        return null;
    }


}
