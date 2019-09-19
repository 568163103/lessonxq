package com.beyeon.nvss.device.model.bpo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.util.BaiduMapUtil;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.bussiness.model.dao.GroupsDao;
import com.beyeon.nvss.device.model.dao.ChannelDao;
import com.beyeon.nvss.device.model.dao.EncoderDao;
import com.beyeon.nvss.device.model.dto.EncoderDto;
import com.beyeon.nvss.device.model.dto.EncoderImpDto;
import com.beyeon.nvss.device.model.dto.TreeEncoderImpDto;
import com.beyeon.nvss.dmu.model.dao.DmuEquipmentDao;

@Cache(cacheName = "编码器")
@Component("encoderBpo")
public class EncoderBpo extends BaseBpo {
	private static Logger logger =LoggerFactory.getLogger(EncoderBpo.class);
	private static Double DEFAULT_LNG = 113.606589, DEFAULT_LAT = 34.799814;
	private EncoderDao encoderDao;
	private GroupsDao groupsDao;
	private ChannelDao channelDao;
	private DmuEquipmentDao dmuEquipmentDao;

	public void setGroupsDao(GroupsDao groupsDao) {
		this.groupsDao = groupsDao;
	}

	public void setEncoderDao(EncoderDao encoderDao) {
		this.encoderDao = encoderDao;
	}

	public void setChannelDao(ChannelDao channelDao) {
		this.channelDao = channelDao;
	}

	public void setDmuEquipmentDao(DmuEquipmentDao dmuEquipmentDao) {
		this.dmuEquipmentDao = dmuEquipmentDao;
	}

	public <T> T get(Class<T> clazz,String id) {
		return this.encoderDao.getFivs(clazz,id);
	}

	public EncoderDto get(String id) {
		EncoderDto encoderDto = new EncoderDto();
		encoderDto.setEncoder(this.encoderDao.getFivs(Encoder.class, id));
		encoderDto.setServerEncoder(this.encoderDao.getServerEncoder(id));
		encoderDto.setEncoderExtra(this.encoderDao.getEncoderExtra(id));
		encoderDto.setGroups(this.groupsDao.findGroupsByEncoderId(encoderDto.getEncoder().getId()));
		encoderDto.setChannels(this.channelDao.findByEncoderId(id));
		List<Channel> platformReses = this.channelDao.findPlatformResByEncoderId(id);
		if (platformReses != null && platformReses.size() > 0){
			encoderDto.getChannels().addAll(platformReses);
		}
		if(encoderDto.getChannels().size() > 0 ) {
			encoderDto.setMsuChannel(this.channelDao.findMsuChannel(encoderDto.getChannels().get(0).getId()));
		}
		return encoderDto;
	}

	public Map<String,Double> getDefaultLocation(){
		if (DEFAULT_LNG == 113.606589 || DEFAULT_LAT == 34.799814) {
			try {
				Map location = BaiduMapUtil.getBaiduMapByIp(null);
				if (location != null) {
					DEFAULT_LNG = Double.valueOf(location.get("x").toString());
					DEFAULT_LAT = Double.valueOf(location.get("y").toString());
				} else {
					logger.error("获取百度地图数据失败");
				}
			} catch (NumberFormatException e) {
				logger.error("初始化经纬度出错", e);
			}
		}
		Map location = new HashMap<>();
		location.put("lng",DEFAULT_LNG);
		location.put("lat",DEFAULT_LAT);
		return location;
	}

	public void save(EncoderDto encoderDto) {
		Boolean isPlatform = (encoderDto.getEncoder().getModel().equals("platform"));
		String eid = null;
//		if (isPlatform){
//			eid = encoderDao.createId("platform","",encoderDto.getEncoder().getChannelCount()+"");
//		}else{
		eid = encoderDao.createId("encoder","",encoderDto.getEncoder().getChannelCount()+"",encoderDto.getEncoder().getPosition());
//		}
		System.out.println(StringUtils.isNotBlank(encoderDto.getEncoder().getId()));
		System.out.println();
		encoderDto.getEncoder().setId(eid);
//		encoderDto.getServerEncoder().setEncoderId(encoderDto.getEncoder().getId());
		encoderDto.getEncoderExtra().setId(encoderDto.getEncoder().getId());
		this.encoderDao.saveFivs(encoderDto.getEncoder());
//		this.encoderDao.saveFivs(encoderDto.getServerEncoder());
		this.encoderDao.saveFivs(encoderDto.getEncoderExtra());

		if (!isPlatform){
			DmuEquipment dmuEquipment = new DmuEquipment();
			dmuEquipment.setId(eid);
			dmuEquipment.setName(encoderDto.getEncoder().getName());
			dmuEquipment.setCorp(encoderDto.getEncoderExtra().getCorp());
			Integer num = encoderDto.getEncoder().getChannelCount();
			if (num == 1){
				dmuEquipment.setType("IP摄像机");
			}else{
				dmuEquipment.setType("编码器");
			}
			dmuEquipment.setVersion(encoderDto.getEncoderExtra().getVersion());
			dmuEquipment.setPos(encoderDto.getEncoder().getAddress());
			dmuEquipment.setIp(encoderDto.getEncoder().getIp());
			dmuEquipment.setMac(encoderDto.getEncoderExtra().getMac());
			dmuEquipment.setPort(encoderDto.getEncoder().getPort());
			dmuEquipment.setRemark(encoderDto.getEncoder().getDescription());
			dmuEquipment.setStatus(0);
			this.encoderDao.saveFivs(dmuEquipment);
		}


		Groups groups = new Groups();
		String gid = this.encoderDao.createId("group","","0",encoderDto.getEncoder().getPosition());
		groups.setId(gid);
		groups.setName(encoderDto.getEncoder().getName());
		groups.setSubnum(encoderDto.getEncoder().getChannelCount());
		if (!isPlatform){
			groups.setType(Groups.TYPE_VIDEO);
		} else {
			groups.setType(Groups.TYPE_PLATFORM);
		}
		this.encoderDao.saveFivs(groups);

		EncoderGroups encoderGroups = new EncoderGroups(encoderDto.getEncoder().getId(),groups.getId());
		this.encoderDao.saveFivs(encoderGroups);

		String uid = this.encoderDao.findAdminUid();
		UserGroupRight userGroupRight = new UserGroupRight(uid,groups.getId(),23);
		this.encoderDao.saveFivs(userGroupRight);
		if (!isPlatform) {
			List<Channel> channels = new ArrayList<Channel>();
			String[] name = ServletActionContext.getRequest().getParameterValues("channel.cameraType");
			for (int i = 0; i < encoderDto.getEncoder().getChannelCount(); i++) {
				String channelId = encoderDao.createId("channel","",i+1+"",encoderDto.getEncoder().getPosition());
				StringBuilder sb = new StringBuilder(channelId);
				String replacestr = eid.substring(10, 14);
				sb.replace(6, 14, name[i]+replacestr);
				Channel channel = new Channel(sb.toString(), encoderDto.getEncoder().getName() + "-channel " + i, encoderDto.getEncoder().getId(), i );
				Channel.getObjectMap().put(channel.getId(), channel.getName());
				this.encoderDao.saveFivs(channel);
				channels.add(channel);
				if (null != encoderDto.getMsuChannel() && StringUtils.isNotBlank(encoderDto.getMsuChannel().getServerId())) {
					this.encoderDao.saveFivs(new MssChannel(channel.getId(), encoderDto.getMsuChannel().getServerId()));
				}
				this.encoderDao.saveFivs(new GroupResource(groups.getId(), channel.getId()));
				Collection<AutoCompleteDto> types = AlarmType.getTypes();
				Iterator<AutoCompleteDto> it = types.iterator();
				while (it.hasNext()) {
					AutoCompleteDto autoCompleteDto = it.next();
					String alarmType = autoCompleteDto.getValue();
					if("1".equals(alarmType)||"3".equals(alarmType)
							||"5".equals(alarmType)||"10".equals(alarmType)
							||"12".equals(alarmType)||"16".equals(alarmType)
							||"17".equals(alarmType)||"17".equals(alarmType)
							||"19".equals(alarmType)){
						TbAlarmRes alarmRes = new TbAlarmRes(channel.getId(), alarmType, channel.getName()+"("+autoCompleteDto.getLabel()+")");
						this.encoderDao.saveFivs(alarmRes);
					}
				}


			}
			if (channels.size() > 0) {
				saveImgGisInfo(encoderDto.getEncoder(), channels);
			}
		} else {
			this.encoderDao.saveFivs(new GroupResource(groups.getId(), encoderDto.getEncoder().getId()));
		}
		PositionEncoder positionEncoder = new PositionEncoder();
		positionEncoder.setEncoderId(eid);
		positionEncoder.setPositionId(encoderDto.getEncoder().getPosition());
		this.encoderDao.saveFivs(positionEncoder);
	}

	public void update(EncoderDto encoderDto) {
//		String oldName = this.encoderDao.findById(encoderDto.getEncoder().getId()).getName();
		this.encoderDao.updateFivs(encoderDto.getEncoder());
		this.encoderDao.updateServerEncoder(encoderDto.getServerEncoder());
		this.encoderDao.updateEncoderExtra(encoderDto.getEncoderExtra());

		Boolean isPlatform = (encoderDto.getEncoder().getModel().equals("platform"));
		if (!isPlatform){
			DmuEquipment dmuEquipment = new DmuEquipment();
			dmuEquipment.setId(encoderDto.getEncoder().getId());
			dmuEquipment.setName(encoderDto.getEncoder().getName());
			dmuEquipment.setCorp(encoderDto.getEncoderExtra().getCorp());
			Integer num = encoderDto.getEncoder().getChannelCount();
			if (num == 1){
				dmuEquipment.setType("IP摄像机");
			}else{
				dmuEquipment.setType("编码器");
			}
			dmuEquipment.setVersion(encoderDto.getEncoderExtra().getVersion());
			dmuEquipment.setPos(encoderDto.getEncoder().getAddress());
			dmuEquipment.setIp(encoderDto.getEncoder().getIp());
			dmuEquipment.setMac(encoderDto.getEncoderExtra().getMac());
			dmuEquipment.setPort(encoderDto.getEncoder().getPort());
			dmuEquipment.setRemark(encoderDto.getEncoder().getDescription());
			dmuEquipment.setStatus(encoderDto.getEncoder().getStatus() ? 1 : 0);
			DmuEquipment equipment = dmuEquipmentDao.findById(encoderDto.getEncoder().getId());
			if (equipment !=null){
				this.encoderDao.updateFivs(dmuEquipment);
			}else{
				this.encoderDao.saveFivs(dmuEquipment);
			}

//			List<TbAlarmRes> list = this.encoderDao.findAlarmResById(encoderDto.getEncoder().getId());
//			for (TbAlarmRes tbAlarmRes : list){
//				String name = tbAlarmRes.getName().replace(oldName,encoderDto.getEncoder().getName());
//				tbAlarmRes.setName(name);
//				this.encoderDao.saveFivs(tbAlarmRes);
//			}
		}



		if (null != encoderDto.getGroups()) {
			encoderDto.getGroups().setName(encoderDto.getEncoder().getName());
			this.channelDao.updateFivs(encoderDto.getGroups());
		} else {
			Groups groups = new Groups();
			String gid = this.encoderDao.createId("group","","0",encoderDto.getEncoder().getPosition());
			groups.setId(gid);
			groups.setName(encoderDto.getEncoder().getName());
			groups.setSubnum(encoderDto.getEncoder().getChannelCount());
			if (!isPlatform){
				groups.setType(Groups.TYPE_VIDEO);
			} else {
				groups.setType(Groups.TYPE_PLATFORM);
			}
			encoderDto.setGroups(groups);
			this.channelDao.saveFivs(encoderDto.getGroups());
			List<String> uids = this.encoderDao.findUids();
			for (String uid : uids) {
				UserGroupRight userGroupRight = new UserGroupRight(uid,groups.getId(),23);
				this.encoderDao.saveFivs(userGroupRight);
			}
		}
		if (!isPlatform) {
			if (null != encoderDto.getEncoder().getUpdateChannel() && "1".equals(encoderDto.getEncoder().getUpdateChannel())) {
				for (int i = 0; i < encoderDto.getChannels().size(); i++) {
					encoderDto.getChannels().get(i).setName(encoderDto.getEncoder().getName() + "-channel " + i);
				}
				this.encoderDao.saveAllFivs(encoderDto.getChannels());
				updateImgGisInfo(encoderDto.getEncoder());
			}
			int newChannel = encoderDto.getEncoder().getChannelCount()- encoderDto.getChannels().size();
			if(newChannel > 0){
				String[] name = ServletActionContext.getRequest().getParameterValues("channel.cameraType");
				for(int i = encoderDto.getChannels().size();i<encoderDto.getEncoder().getChannelCount();i++){
					String channelId = encoderDao.createId("channel","",i+1+"",encoderDto.getEncoder().getPosition());
					StringBuilder sb = new StringBuilder(channelId);
					String replacestr = encoderDto.getEncoder().getId().substring(10, 14);
					sb.replace(6, 14, name[i]+replacestr);
					Channel channel = new Channel(sb.toString(), encoderDto.getEncoder().getName() + "-channel " + i, encoderDto.getEncoder().getId(), i);
					Channel.getObjectMap().put(channel.getId(),channel.getName());
					this.encoderDao.saveFivs(channel);
					this.encoderDao.saveFivs(new GroupResource(encoderDto.getGroups().getId(), channel.getId()));
				}
				saveImgGisInfo(encoderDto.getEncoder(), encoderDto.getChannels());
			} else if(newChannel < 0){
				this.channelDao.deleteChannelAndMsu(encoderDto.getEncoder().getId(),encoderDto.getEncoder().getChannelCount());
			}
			if(null != encoderDto.getMsuChannel() && StringUtils.isNotBlank(encoderDto.getMsuChannel().getServerId())) {
				this.channelDao.updateMsu(encoderDto.getEncoder().getId(), encoderDto.getMsuChannel().getServerId());
				for(int i = 0;i<encoderDto.getChannels().size();i++) {
					this.encoderDao.saveOrUpdateFivs(new MssChannel(encoderDto.getChannels().get(i).getId(), encoderDto.getMsuChannel().getServerId()));
				}
			}
		} else {
			updateImgGisInfo(encoderDto.getEncoder());
		}
	}

	public List<String> findOutChannel(String encoderId, Integer channelCount) {
		return this.channelDao.findOutChannel(encoderId, channelCount);
	}

	public void delete(String[] ids) {
		this.encoderDao.delete(ids);
		this.channelDao.deleteChannelAndMsu(ids);
		this.channelDao.deletePlatformResByEncoderId(ids);
	}

	public void find(PageObject pageObject) {
		this.encoderDao.find(pageObject);
	}

	public Long findEncoderNum() {
		return this.encoderDao.findEncoderNum();
	}

	@Cache(cacheName = "编码器型号")
	public void findEncoderModel() {
		List<EncoderModel> list = this.encoderDao.findEncoderModel();
		for (EncoderModel o : list) {
			Encoder.getEncoderModelMap().put(o.getModel(),o.getProvider());
		}
	}

	@Cache(cacheName = "外域设备")
	public void findPlatform() {
		List<Encoder> list = this.encoderDao.findPlatformEncoder();
		for (Encoder o : list) {
			Encoder.getTypeMap().put(o.getId().toString(),o.getName());
		}
	}
	public boolean checkUnique(String attrName,String value,String id) {
		return encoderDao.checkEncoderUnique(id,value);
	}

	public List<EncoderImpDto> findFilaLineBus(String ver){
		return this.encoderDao.findFilaLineBus(ver);
	}

	public Object[] handleEncoderNoExistAndNew(List<EncoderImpDto> encoderImpDtos, List<EncoderImpDto> allEncoderImpDtos) {
		Map<String, EncoderImpDto> encoderInfoMap = new LinkedHashMap<String, EncoderImpDto>();
		for (EncoderImpDto encoderImpDto : encoderImpDtos) {
			encoderInfoMap.put(encoderImpDto.getEncoderNo(), encoderImpDto);
		}
		Map<String, EncoderImpDto> allEncoderInfoMap = new HashMap<String, EncoderImpDto>();
		for (EncoderImpDto encoderImpDto : allEncoderImpDtos) {
			if (encoderImpDto instanceof TreeEncoderImpDto) {
				TreeEncoderImpDto treeEncoderImpDto = (TreeEncoderImpDto) encoderImpDto;
				allEncoderInfoMap.put(treeEncoderImpDto.getEncoderParNo() + "-" + treeEncoderImpDto.getEncoderNo(), treeEncoderImpDto);
			} else {
				allEncoderInfoMap.put(encoderImpDto.getEncoderNo(), encoderImpDto);
			}
		}
		List<String> noExistEncoderNos = new ArrayList<String>();
		List<Object[]> encoderInfoeds = this.encoderDao.findEncoderNos();
		for (Object[] encoderInfoed : encoderInfoeds) {
			String uuid = (String) encoderInfoed[1];
			if (!uuid.equals((String) encoderInfoed[2])) {
				uuid = (String) encoderInfoed[1] + "-" + (String) encoderInfoed[2];
			}
			if (!allEncoderInfoMap.containsKey(uuid)) {
				noExistEncoderNos.add((String) encoderInfoed[0]);
			} else {
				encoderInfoMap.remove(encoderInfoed[2]);
			}
		}
		return new Object[]{noExistEncoderNos,encoderInfoMap.values()};
	}

//	public void initEncoderAndGroupAndUserGroupsAndChannel(Collection<EncoderImpDto> encoderImpDtos, EncoderImpDto publicEncoderImpDto) {
//		int createSize = 0;
//		String mduId = Server.getObjectMap(ServerType.MDU.toString()).keySet().iterator().next();
//		for (EncoderImpDto encoderImpDto : encoderImpDtos) {
//			Boolean isPlatform = (encoderImpDto.getModel().equals("platform"));
//			String encoderNo = encoderImpDto.getEncoderNo();
//			Encoder encoder = new Encoder();
//			String channelId1 = this.encoderDao.createId("encoder","",publicEncoderImpDto.getOutputCount()+"");
//			encoder.setId(channelId1);
//			encoder.setName(encoderImpDto.getEncoderName());
//			encoder.setAddress(encoderNo);
//			if (null != publicEncoderImpDto){
//				encoderImpDto = publicEncoderImpDto;
//			}
//			encoder.setModel(encoderImpDto.getModel());
//			encoder.setIp(encoderImpDto.getIp());
//			encoder.setPort(encoderImpDto.getPort());
//			encoder.setUsername(encoderImpDto.getUsername());
//			encoder.setPassword(encoderImpDto.getPassword());
//			encoder.setChannelCount(encoderImpDto.getOutputCount());
//			this.encoderDao.saveFivs(encoder);
//			ServerEncoder serverEncoder = new ServerEncoder();
//			serverEncoder.setEncoderId(encoder.getId());
//			serverEncoder.setServerId(mduId);
//			this.encoderDao.saveFivs(serverEncoder);
//			
//			if (!isPlatform){
//				DmuEquipment dmuEquipment = new DmuEquipment();
//				dmuEquipment.setId(channelId1);
//				dmuEquipment.setName(encoder.getName());
//				dmuEquipment.setCorp("");
//				Integer num = encoder.getChannelCount();
//				if (num == 1){
//					dmuEquipment.setType("IP摄像机");
//				}else{
//					dmuEquipment.setType("编码器");
//				}
//				dmuEquipment.setVersion("");
//				dmuEquipment.setPos(encoder.getAddress());
//				dmuEquipment.setIp(encoder.getIp());
//				dmuEquipment.setMac("");
//				dmuEquipment.setPort(encoder.getPort());
//				dmuEquipment.setRemark(encoder.getDescription());
//				dmuEquipment.setStatus(0);
//				this.encoderDao.saveFivs(dmuEquipment);
//			}
//			
//			Groups groups = new Groups();
//			groups.setId(this.encoderDao.createId("group","","0"));
//			groups.setName(encoder.getName());
//			groups.setSubnum(encoder.getChannelCount());
//			if (!isPlatform){
//				groups.setType(Groups.TYPE_VIDEO);
//			} else {
//				groups.setType(Groups.TYPE_PLATFORM);
//			}
//			this.encoderDao.saveFivs(groups);
//
//			EncoderGroups encoderGroups = new EncoderGroups(encoder.getId(),groups.getId());
//			this.encoderDao.saveFivs(encoderGroups);
//
//			List<String> uids = this.encoderDao.findUids();
//			for (String uid : uids) {
//				UserGroupRight userGroupRight = new UserGroupRight(uid,groups.getId(),23);
//				this.encoderDao.saveFivs(userGroupRight);
//			}
//
//			for (int i = 0; i < encoder.getChannelCount(); i++) {
//				String channelId = encoderDao.createId("channel","",i+1+"");
//				StringBuilder sb = new StringBuilder(channelId);
//				String replacestr = channelId1.substring(10, 14);
//				sb.replace(10, 14, replacestr);
//				Channel channel = new Channel(channelId, encoder.getName() + "-channel " + i, encoder.getId(), i);
//				Channel.getObjectMap().put(channel.getId(),channel.getName());
//				if (StringUtils.isNotBlank(encoderImpDto.getDescription())){
//					if (encoder.getChannelCount() > 1){
//						channel.setDescription(encoderImpDto.getDescription()+i);
//					} else {
//						channel.setDescription(encoderImpDto.getDescription());
//					}
//				}
//				this.encoderDao.saveFivs(channel);
//				this.encoderDao.saveFivs(new GroupResource(groups.getId(), channelId));
//			}
//			createSize++;
//		}
//		logger.debug("新加编码器："+createSize);
//	}

	public List<String> findChannelByEncoderIds(String[] ids) {
		return this.channelDao.findChannelIdByEncoderId(ids);
	}

	public void deleteAllPlatformRes(String encoderId) {
		this.channelDao.deletePlatformResByEncoderId(new String[]{encoderId});
	}

	public void deleteInitUserTree() {
		StringBuilder sb = new StringBuilder();
		sb.append("alarm_device_channel, channel,channel_param,channel_record_plan,channel_snapshot,channel_stream,decoder,decoder_channel, ");
		sb.append("encoder,encoder_groups,encoder_input,encoder_output, groups,group_resource,ims_gis_info,msu_channel, ");
		sb.append("platform,platform_res,platform_server,preset,preset_jpg,res_alarm_channel,res_decoder_encoder, ");
		sb.append("server_channel,server_encoder, user_group_right,user_tree ");
		String[] tables = sb.toString().split(",");
		for (String table : tables) {
			this.encoderDao.deleteInitUserTree("delete from "+table);
		}
	}

	public String findMduId(String id) {
		return encoderDao.getServerEncoder(id).getServerId();
	}

	public void saveImgGisInfo(Encoder encoder, List<Channel> channels){
		Map location = null;
		if (StringUtils.isNotBlank(encoder.getAddress()) && !encoder.getName().contains("车载设备")) {
			try {
				location = BaiduMapUtil.getBaiduMapByLocation(encoder.getAddress(), "中国");
			} catch (Exception e) {
				logger.debug("获取经纬度失败");
			}
		}
		if (null == location){
			location = getDefaultLocation();
		}
		double x = -0.1,y = -0.1;
		if (null != location) {
			for (Channel channel : channels) {
				if (y > 0.1) {
					x = x + 0.01;y = -0.1;
				}
				ImsGisInfo imsGisInfo = new ImsGisInfo();
				imsGisInfo.setChannelId(channel.getId());
				imsGisInfo.setLongitude(Double.valueOf(location.get("lng").toString()) + x);
				imsGisInfo.setLatitude(Double.valueOf(location.get("lat").toString()) + y);
				channelDao.saveFivs(imsGisInfo);
				y = y + 0.01;
			}
		}
	}

	public void updateImgGisInfo(Encoder encoder){
		Map location = null;
		if (StringUtils.isNotBlank(encoder.getAddress()) && !encoder.getName().contains("车载设备")) {
			List<ImsGisInfo> imsGisInfos = null;
			if (!"platform".equals(encoder.getModel())){
				imsGisInfos = channelDao.findChannelGisInfos(encoder.getId());
			} else {
				imsGisInfos = channelDao.findPlatformResGisInfos(encoder.getId());
			}
			if (null != imsGisInfos && imsGisInfos.size() > 0){
				try {
					location = BaiduMapUtil.getBaiduMapByLocation(encoder.getAddress(), "中国");
				} catch (Exception e) {
					logger.debug("获取经纬度失败");
				}
				if (null != location) {
					double x = -0.1,y = -0.1;
					for (ImsGisInfo imsGisInfo : imsGisInfos) {
						if (y > 0.1) {
							x = x + 0.01;y = -0.1;
						}
						imsGisInfo.setLongitude(Double.valueOf(location.get("lng").toString()) + x);
						imsGisInfo.setLatitude(Double.valueOf(location.get("lat").toString()) + y);
						channelDao.updateFivs(imsGisInfo);
						y = y + 0.01;
					}
				}
			}
		}
	}

	public List findAllEncoder() {
		return encoderDao.findAllEncoder();
	}
	public EncoderExtra getEncoderExtra (String id ){
		return encoderDao.getEncoderExtra(id);
	}

	public Encoder findById(String id) {
		return encoderDao.findById(id);
	}

	public String findServerStatus(Integer serverType) {
		return encoderDao.findServerStatus(serverType);
	}

	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<Object[]> list = encoderDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				Object[] obj = (Object[]) list.get(i);
				Encoder encoder = (Encoder) obj[0];
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (encoder != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(encoder.getId());
					data.add(encoder.getName());
					data.add(encoder.getPositionZH());
					data.add(encoder.getModelName());
					data.add(encoder.getIp());
					data.add(encoder.getEnabledZh());
					data.add(encoder.getStatusZh());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}

	public Object[] handleEncoderNoExistAndNew2(List<EncoderImpDto> encoderImpDtos, List<EncoderImpDto> allEncoderImpDtos) {
		Map<String, EncoderImpDto> encoderInfoMap = new LinkedHashMap<String, EncoderImpDto>();
		for (EncoderImpDto encoderImpDto : encoderImpDtos) {
			if (encoderImpDto.getIndexCount() <= encoderImpDto.getOutputCount())
				encoderInfoMap.put(encoderImpDto.getEncoderNo()+ "-" + encoderImpDto.getIndexCount(), encoderImpDto);
		}
		Map<String, String> allEncoderInfoMap = new HashMap<String, String>();
		for (EncoderImpDto encoderImpDto : allEncoderImpDtos) {
			if (encoderImpDto.getIndexCount() <= encoderImpDto.getOutputCount())
				allEncoderInfoMap.put(encoderImpDto.getEncoderNo()+ "-" + encoderImpDto.getIndexCount(),encoderImpDto.getEncoderNo());
		}
		List<String> noExistEncoderNos = new ArrayList<String>();
		List<Object[]> encoderInfoeds = this.encoderDao.findEncoderNos();
		for (Object[] encoderInfoed : encoderInfoeds) {
			String uuid = (String) encoderInfoed[1];
			if (!uuid.equals((String) encoderInfoed[2])) {
				uuid = (String) encoderInfoed[1] + "-" + (String) encoderInfoed[2];
			}
			if (!allEncoderInfoMap.containsValue(uuid)) {
				noExistEncoderNos.add((String) encoderInfoed[0]);
			} else {
				for (Map.Entry<String,String> entry : allEncoderInfoMap.entrySet()) {
					if (uuid.equals(entry.getValue()))
						encoderInfoMap.remove(entry.getKey());
				}

			}
		}
		return new Object[]{noExistEncoderNos,encoderInfoMap.values()};
	}

	public void initEncoderAndGroupAndUserGroupsAndChannel2(Collection<EncoderImpDto> encoderImpDtos, EncoderImpDto publicEncoderImpDto) {
		int createSize = 0;
		String mduId = Server.getObjectMap(ServerType.MDU.toString()).keySet().iterator().next();
		HashMap<String,EncoderGroups> encoderMap = new HashMap<String,EncoderGroups>();
		for (EncoderImpDto encoderImpDto : encoderImpDtos) {
			String mdu = encoderImpDto.getMduId();
			String msu = encoderImpDto.getMsuId();
			Map<String, String> mdus = Server.getObjectMap(ServerType.MDU.toString());
			Map<String, String> msus = Server.getObjectMap(ServerType.MSU.toString());
			if (!mdus.containsKey(mdu)){
				mdu = mduId;
			}
			if (!msus.containsKey(msu)){
				msu = null;
			}
			String groupId = "";
			Boolean isPlatform = (encoderImpDto.getModel().equals("platform"));
			String encoderNo = encoderImpDto.getEncoderNo();
			String encoderId = "";
			EncoderGroups d = encoderMap.get(encoderNo);
			if (d !=null ){
				encoderId = d.getEncoderId();
				groupId = d.getGroupId();
			}else{
				Encoder encoder = new Encoder();
				encoderId = this.encoderDao.createId("encoder","",encoderImpDto.getOutputCount()+"",encoderImpDto.getPosition());
				encoder.setId(encoderId);
				encoder.setName(encoderImpDto.getEncoderName());
				encoder.setAddress(encoderNo);
				if (null != publicEncoderImpDto){
					encoderImpDto = publicEncoderImpDto;
				}
				encoder.setModel(encoderImpDto.getModel());
				encoder.setIp(encoderImpDto.getIp());
				encoder.setPort(encoderImpDto.getPort());
				encoder.setUsername(encoderImpDto.getUsername());
				encoder.setPassword(encoderImpDto.getPassword());
				encoder.setChannelCount(encoderImpDto.getOutputCount());

				this.encoderDao.saveFivs(encoder);
				ServerEncoder serverEncoder = new ServerEncoder();
				serverEncoder.setEncoderId(encoder.getId());
				serverEncoder.setServerId(mdu);
				this.encoderDao.saveFivs(serverEncoder);


				if (!isPlatform){
					DmuEquipment dmuEquipment = new DmuEquipment();
					dmuEquipment.setId(encoderId);
					dmuEquipment.setName(encoder.getName());
					dmuEquipment.setCorp("");
					Integer num = encoder.getChannelCount();
					if (num == 1){
						dmuEquipment.setType("IP摄像机");
					}else{
						dmuEquipment.setType("编码器");
					}
					dmuEquipment.setVersion("");
					dmuEquipment.setPos(encoder.getAddress());
					dmuEquipment.setIp(encoder.getIp());
					dmuEquipment.setMac("");
					dmuEquipment.setPort(encoder.getPort());
					dmuEquipment.setRemark(encoder.getDescription());
					dmuEquipment.setStatus(0);
					this.encoderDao.saveFivs(dmuEquipment);
				}

				Groups groups = new Groups();
				groups.setId(this.encoderDao.createId("group","","0",encoderImpDto.getPosition()));
				groups.setName(encoder.getName());
				groups.setSubnum(encoder.getChannelCount());
				if (!isPlatform){
					groups.setType(Groups.TYPE_VIDEO);
				} else {
					groups.setType(Groups.TYPE_PLATFORM);
				}
				this.encoderDao.saveFivs(groups);

				EncoderGroups encoderGroups = new EncoderGroups(encoder.getId(),groups.getId());
				this.encoderDao.saveFivs(encoderGroups);

				encoderMap.put(encoderNo, encoderGroups);
				groupId = encoderGroups.getGroupId();
				encoderId = encoderGroups.getEncoderId();
				String uid = this.encoderDao.findAdminUid();
				UserGroupRight userGroupRight = new UserGroupRight(uid,groups.getId(),23);
				this.encoderDao.saveFivs(userGroupRight);

			}

			if (!isPlatform){

				int i = encoderImpDto.getIndexCount();
				String channelId = encoderDao.createId("channel","",i+1+"",encoderImpDto.getPosition());
				StringBuilder sb = new StringBuilder(channelId);
				String replacestr = encoderId.substring(10, 14);
//				sb.replace(10, 14, replacestr);
				String channelType =  encoderImpDto.getChannelType();
				if (StringUtils.isNotBlank(channelType)){
					sb.replace(6, 14, channelType+replacestr);
				}else{
					sb.replace(10, 14, replacestr);
				}
				channelId = sb.toString();
				String channelName = encoderImpDto.getChannelName();
				if (StringUtils.isBlank(channelName)){
					channelName =encoderNo + "-channel " + i;

				}
				Channel channel = new Channel(channelId, channelName, encoderId, i);
				Channel.getObjectMap().put(channel.getId(),channel.getName());
				if (StringUtils.isNotBlank(encoderImpDto.getDescription())){
					if (encoderImpDto.getOutputCount() > 1){
						channel.setDescription(encoderImpDto.getDescription()+i);
					} else {
						channel.setDescription(encoderImpDto.getDescription());
					}
				}
				this.encoderDao.saveFivs(channel);
				if (StringUtils.isNotBlank(msu))
					this.encoderDao.saveFivs(new MssChannel(channelId, msu));

				Collection<AutoCompleteDto> types = AlarmType.getTypes();
				Iterator<AutoCompleteDto> it = types.iterator();
				while (it.hasNext()) {
					AutoCompleteDto autoCompleteDto = it.next();
					String alarmType = autoCompleteDto.getValue();
					if("1".equals(alarmType)||"3".equals(alarmType)
							||"5".equals(alarmType)||"10".equals(alarmType)
							||"12".equals(alarmType)||"16".equals(alarmType)
							||"17".equals(alarmType)||"17".equals(alarmType)
							||"19".equals(alarmType)){
						TbAlarmRes alarmRes = new TbAlarmRes(channel.getId(), alarmType, channel.getName()+"("+autoCompleteDto.getLabel()+")");
						this.encoderDao.saveFivs(alarmRes);
					}
				}
				this.encoderDao.saveFivs(new GroupResource(groupId, channelId));
			}else{
				this.encoderDao.saveFivs(new GroupResource(groupId, encoderId));
			}
			createSize++;
		}
		logger.debug("新加编码器："+createSize);
	}

	public void updateEncoderName(List<EncoderImpDto> encoderImpDtos){
		for (EncoderImpDto dto : encoderImpDtos){
			this.encoderDao.updateEncoderlName(dto.getEncoderName(), dto.getEncoderNo());
		}

	}

	/**
	 * 查询所有server_encoder关联
	 */
	public List<ServerEncoder> findServerEncoderAll(){
		return  this.encoderDao.getMssChanleAll();
	}

	public  List<ServerEncoder> findByServerEncoderId(String id){
		return  this.encoderDao.findByServerEncoderId(id);
	}
}
