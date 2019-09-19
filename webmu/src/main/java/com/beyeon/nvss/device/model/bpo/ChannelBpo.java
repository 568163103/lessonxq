package com.beyeon.nvss.device.model.bpo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.beyeon.bean.xml.cInterface.ReqCamResStateResBean.Url;
import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.device.model.dao.ChannelDao;
import com.beyeon.nvss.device.model.dto.ChannelDto;
import com.beyeon.nvss.device.model.dto.ChannelImpDto;

@Cache(cacheName = "通道")
@Component("channelBpo")
public class ChannelBpo {
    private ChannelDao channelDao;

    public void setChannelDao(ChannelDao channelDao) {
        this.channelDao = channelDao;
    }

    public ChannelDto get(String id) {
        ChannelDto channelDto = new ChannelDto();
        Channel channelAndMsu = this.channelDao.findChannelAndMsu(id);
        channelDto.setChannel((Channel) channelAndMsu);
        channelDto.setMsuChannel(new MssChannel(channelAndMsu.getId(), channelAndMsu.getServerId()));
        channelDto.setChannelRecordPlan(this.channelDao.findRecordPlan(id));
        channelDto.setImsGisInfo(this.channelDao.findGisInfo(id));
        channelDto.setPresets(this.channelDao.findPreset(id));
        return channelDto;
    }

    public void save(Object o) {
        this.channelDao.saveFivs(o);
    }

    public void save(ChannelDto channelDto) {
        this.channelDao.saveFivs(channelDto.getChannel());
        if (null != channelDto.getMsuChannel() && null != channelDto.getMsuChannel().getServerId()) {
            this.channelDao.saveFivs(channelDto.getMsuChannel());
        }
    }

    public void update(ChannelDto channelDto) {
        String old_name = this.channelDao.findById(channelDto.getChannel().getId()).getName();
        if (!old_name.equals(channelDto.getChannel().getName())){
            List<TbAlarmRes> list = this.channelDao.findAlarmResById(channelDto.getChannel().getId());
            for (TbAlarmRes tbAlarmRes : list){
                String name = tbAlarmRes.getName();
                if (name.lastIndexOf("(")>0){
                    name = channelDto.getChannel().getName()+name.substring(name.lastIndexOf("("));
                    tbAlarmRes.setName(name);
                    this.channelDao.updateFivs(tbAlarmRes);
                }
            }

        }
        this.channelDao.updateFivs(channelDto.getChannel());
        if (StringUtils.isNotBlank(channelDto.getChannelRecordPlan().getPlanName())) {
            this.channelDao.saveOrUpdateFivs(channelDto.getChannelRecordPlan());
        } else {
            this.channelDao.deleteRecordPlan(channelDto.getChannelRecordPlan().getChannelId());
        }
        if (StringUtils.isNotBlank(channelDto.getMsuChannel().getServerId())) {
//			this.channelDao.saveOrUpdateFivs(channelDto.getMsuChannel());
            this.channelDao.deleteMsuChannel(channelDto.getMsuChannel().getChannelId());
            this.channelDao.saveFivs(channelDto.getMsuChannel());

        } else {
            this.channelDao.deleteMsuChannel(channelDto.getMsuChannel().getChannelId());
        }

        this.channelDao.saveOrUpdateFivs(channelDto.getImsGisInfo());
        if (StringUtils.isNotBlank(channelDto.getChannel().getId()) && StringUtils.isNotBlank(channelDto.getChannel().getName()))
            this.channelDao.updateUserTree(channelDto.getChannel().getName(), channelDto.getChannel().getId());

    }

    public void updatePlan(ChannelDto channelDto) {
        String ids = channelDto.getIds();
        String[] channelIds = ids.split(",");
        String planname = channelDto.getChannelRecordPlan().getPlanName();
        for (String channelId : channelIds) {
            if (StringUtils.isNotBlank(channelId)) {
                ChannelRecordPlan plan = new ChannelRecordPlan();
                plan.setChannelId(channelId);
                plan.setPlanName(planname);
                channelDto.setChannelRecordPlan(plan);
                if (StringUtils.isNotBlank(planname)) {
                    this.channelDao.saveOrUpdateFivs(channelDto.getChannelRecordPlan());
                } else {
                    this.channelDao.deleteRecordPlan(channelDto.getChannelRecordPlan().getChannelId());
                }
            }
        }

    }

    public void delete(String[] ids) {
        this.channelDao.delete(ids);
        this.channelDao.deletePlatformResByChannelId(ids);
    }

    public void find(PageObject pageObject) {
        this.channelDao.find(pageObject);
    }

    public void findExternal(PageObject pageObject) {
        this.channelDao.findExternal(pageObject);
    }

    public void updatePreset(Preset preset) {
        this.channelDao.saveOrUpdateFivs(preset);
    }

    @Cache(cacheName = "通道", refreshExpre = Cache.Bm5)
    public void findChannel() {
        List<Map> list = channelDao.find();
        list.addAll(channelDao.findPlatformRes());
        Map<String, String> currObjectlMap = new HashMap<String, String>();
        for (Map map : list) {
            currObjectlMap.put((String) map.get("id"), (String) map.get("name"));
        }
        Channel.setObjectMap(currObjectlMap);
    }

    public List<Object[]> findNotInitGisInfos() {
        return this.channelDao.findNotInitGisInfos();
    }

    public List<Map> findGisInfos(PageObject pageObject, String userId) {
        List<Map> imsGisInfos = new ArrayList<Map>();
        List<Map> igis = this.channelDao.findGisInfos(pageObject, userId, "noplatform");
        if (null != igis) {
            imsGisInfos.addAll(igis);
        }
        igis = this.channelDao.findGisInfos(pageObject, userId, "platform");
        if (null != igis) {
            imsGisInfos.addAll(igis);
        }
        return imsGisInfos;
    }

    public List<Map> findGisInfos(Map pos, String userId) {
        List<Map> imsGisInfos = new ArrayList<Map>();
        List<Map> igis = this.channelDao.findGisInfos(pos, userId, "noplatform");
        if (null != igis) {
            imsGisInfos.addAll(igis);
        }
        igis = this.channelDao.findGisInfos(pos, userId, "platform");
        if (null != igis) {
            imsGisInfos.addAll(igis);
        }
        return imsGisInfos;
    }

    public void updateChannelPosition(String channelId, String lng, String lat) {
        ImsGisInfo imsGisInfo = this.channelDao.findGisInfo(channelId);
        if (null == imsGisInfo) {
            imsGisInfo = new ImsGisInfo();
            imsGisInfo.setChannelId(channelId);
        }
        imsGisInfo.setLongitude(Double.valueOf(lng));
        imsGisInfo.setLatitude(Double.valueOf(lat));
        this.channelDao.saveOrUpdateFivs(imsGisInfo);
    }

    public void saveImsGisInfo(ImsGisInfo imsGisInfo) {
        this.channelDao.saveFivs(imsGisInfo);
    }

    public void findSnapshot(PageObject pageObject) {
        this.channelDao.findSnapshot(pageObject);
    }

    public List<ChannelSnapshot> findUserSnapshot(PageObject pageObject, String userId) {
        return this.channelDao.findUserSnapshot(pageObject, userId);
    }

    public String getSnapshot(String channelId) {
        return this.channelDao.getSnapshot(channelId);
    }

    public List findAllChannel() {
        return this.channelDao.findAllChannel();
    }

    public List findAllByMsuChannel() {
        return this.channelDao.findAllByMsuChannel();
    }

    public ArrayList<ArrayList<String>> findExcelFieldData(
            PageObject pageObject) {
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        // 存放excel的内容的所有数据
        List<Channel> list = channelDao
                .findByNoPageWithExcel(pageObject);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                Channel channel = (Channel) list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (channel != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(String.valueOf(i + 1));
                    data.add(channel.getId());
                    data.add(channel.getName());
                    data.add(channel.getPositionZH());
                    data.add(String.valueOf(channel.getNum() + 1));
                    data.add(channel.getServerName());
                    data.add(channel.getRecordPlanName());
                    data.add(channel.getEnabledZh());
                    fieldData.add(data);
                }
            }
        }
        return fieldData;
    }

    public ArrayList<ArrayList<String>> findExternalExcelFieldData(
            PageObject pageObject) {
        ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
        // 存放excel的内容的所有数据
        List<PlatformRes> list = channelDao
                .findExternalByNoPageWithExcel(pageObject);

        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {

                // 存放每一行的数据
                PlatformRes channel = (PlatformRes) list.get(i);
                // 将每一行的数据，放置到ArrayList<WmuOperationLog>
                if (channel != null) {
                    ArrayList<String> data = new ArrayList<String>();
                    data.add(String.valueOf(i + 1));
                    data.add(channel.getId());
                    data.add(channel.getName());
                    data.add(channel.getPositionZH());
                    data.add(channel.getPurpose());
                    data.add(channel.getServerId());
                    data.add(channel.getEncoderName());
                    data.add(channel.getStatusZh());
                    fieldData.add(data);
                }
            }
        }
        return fieldData;
    }

    public void updateExternalChannel(List<Url> urls) {
        List<PlatformRes> list = new ArrayList<PlatformRes>();
        for (Url url : urls) {
            PlatformRes res = this.channelDao.getFivs(PlatformRes.class, url.getResId());
            res.setStatus(url.getState().equals("online"));
            list.add(res);
        }
        this.channelDao.saveAllFivs(list);
    }

    public void updateChannelName(List<ChannelImpDto> channelImpDtos) {
        for (ChannelImpDto dto : channelImpDtos) {
            this.channelDao.updateChannelName(dto.getChannelName(), dto.getChannelId());
            this.channelDao.updateUserTree(dto.getChannelName(), dto.getChannelId());
            this.channelDao.updateAlarmResName(dto.getChannelName(), dto.getChannelId());
        }

    }

    public PlatformRes findPlatformRes(String channel_id) {
        return this.channelDao.findPlatformRes(channel_id);
    }

    public Channel findById(String channel_id){
        return this.channelDao.findById(channel_id);
    }
}
