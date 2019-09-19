package com.beyeon.nvss.device.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.BaiduMapUtil;
import com.beyeon.hibernate.fivsdb.Encoder;
import com.beyeon.hibernate.fivsdb.ImsGisInfo;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.nvss.device.model.bpo.ChannelBpo;
import com.beyeon.nvss.device.model.bpo.EncoderBpo;
import com.beyeon.nvss.device.model.bpo.EquipmentBpo;
import com.beyeon.nvss.device.model.bpo.ServerBpo;
import com.beyeon.nvss.device.model.bpo.TerminalBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuAlarmInfoBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.fault.model.bpo.AlarmInfoBpo;
import com.beyeon.nvss.fault.model.bpo.AlarmResBpo;
import com.beyeon.nvss.fault.model.bpo.TbAlarmsubcriTypeBpo;
import com.beyeon.nvss.system.model.bpo.SystemConfigBpo;

/**
 * User: Administrator
 * Date: 2015/12/31
 * Time: 15:48
 */
@Cache(cacheName = "设备Facade")
@Component("deviceFacade")
public class DeviceFacade {
	private static Logger logger = LoggerFactory.getLogger(DeviceFacade.class);
	private static Map<String,Map> encoderLocationMap = new HashMap<String,Map>();
	private ServerBpo serverBpo;
	private EncoderBpo encoderBpo;
	private ChannelBpo channelBpo;
	private TerminalBpo terminalBpo;
	private EquipmentBpo equipmentBpo;
	private DmuEquipmentBpo dmuEquipmentBpo;
	private DmuAlarmInfoBpo dmuAlarmInfoBpo ;
	private AlarmInfoBpo alarmInfoBpo ;
	private AlarmResBpo alarmResBpo ;
	private TbAlarmsubcriTypeBpo   tbAlarmsubcriTypeBpo  ;
	private SystemConfigBpo systemConfigBpo;
	public ServerBpo getServerBpo() {
		return serverBpo;
	}

	public void setServerBpo(ServerBpo serverBpo) {
		this.serverBpo = serverBpo;
	}

	public EncoderBpo getEncoderBpo() {
		return encoderBpo;
	}

	public void setEncoderBpo(EncoderBpo encoderBpo) {
		this.encoderBpo = encoderBpo;
	}

	public ChannelBpo getChannelBpo() {
		return channelBpo;
	}

	public void setChannelBpo(ChannelBpo channelBpo) {
		this.channelBpo = channelBpo;
	}

	public TerminalBpo getTerminalBpo() {
		return terminalBpo;
	}

	public void setTerminalBpo(TerminalBpo terminalBpo) {
		this.terminalBpo = terminalBpo;
	}
	
	
	public EquipmentBpo getEquipmentBpo() {
		return equipmentBpo;
	}

	public void setEquipmentBpo(EquipmentBpo equipmentBpo) {
		this.equipmentBpo = equipmentBpo;
	}	
	
	public DmuEquipmentBpo getDmuEquipmentBpo() {
		return dmuEquipmentBpo;
	}

	public void setDmuEquipmentBpo(DmuEquipmentBpo dmuEquipmentBpo) {
		this.dmuEquipmentBpo = dmuEquipmentBpo;
	}
	
	public DmuAlarmInfoBpo getDmuAlarmInfoBpo() {
		return dmuAlarmInfoBpo;
	}

	public void setDmuAlarmInfoBpo(DmuAlarmInfoBpo dmuAlarmInfoBpo) {
		this.dmuAlarmInfoBpo = dmuAlarmInfoBpo;
	}
	
	
	public AlarmInfoBpo getAlarmInfoBpo() {
		return alarmInfoBpo;
	}

	public void setAlarmInfoBpo(AlarmInfoBpo alarmInfoBpo) {
		this.alarmInfoBpo = alarmInfoBpo;
	}	
	

	public AlarmResBpo getAlarmResBpo() {
		return alarmResBpo;
	}

	public void setAlarmResBpo(AlarmResBpo alarmResBpo) {
		this.alarmResBpo = alarmResBpo;
	}
	
	
	public TbAlarmsubcriTypeBpo getTbAlarmsubcriTypeBpo() {
		return tbAlarmsubcriTypeBpo;
	}

	public void setTbAlarmsubcriTypeBpo(TbAlarmsubcriTypeBpo tbAlarmsubcriTypeBpo) {
		this.tbAlarmsubcriTypeBpo = tbAlarmsubcriTypeBpo;
	}
	
	public SystemConfigBpo getSystemConfigBpo() {
		return systemConfigBpo;
	}

	public void setSystemConfigBpo(SystemConfigBpo systemConfigBpo) {
		this.systemConfigBpo = systemConfigBpo;
	}

	@Cache(cacheName = "初始化地理位置坐标",refreshExpre = Cache.Bm5,exclusive = true)
	public void initImsGisInfo(){
		List<Object[]> encoderIdandChannelIds = getChannelBpo().findNotInitGisInfos();
		double x = -0.1,y = -0.1;
		for (Object[] encoderIdandChannelId : encoderIdandChannelIds) {
			if (y > 0.1) {
				x = x + 0.01;y = -0.1;
			}

			Map location = encoderLocationMap.get(encoderIdandChannelId[1].toString());
			if (null == location){
				Encoder encoder = getEncoderBpo().get(Encoder.class, encoderIdandChannelId[1].toString());
				if (StringUtils.isNotBlank(encoder.getAddress()) && !encoder.getName().contains("车载设备")) {
					location = BaiduMapUtil.getBaiduMapByLocation(encoder.getAddress(), "中国");
					encoderLocationMap.put(encoder.getId(), location);
				}
			}
			Double lng = null , lat = null ;
			if (null != location){
				lng = Double.valueOf(location.get("lng").toString());
				lat = Double.valueOf(location.get("lat").toString());
			} else {
				lng = getEncoderBpo().getDefaultLocation().get("lng");
				lat = getEncoderBpo().getDefaultLocation().get("lat");
			}
			ImsGisInfo imsGisInfo = new ImsGisInfo();
			imsGisInfo.setChannelId(encoderIdandChannelId[0].toString());
			imsGisInfo.setLongitude(lng + x);
			imsGisInfo.setLatitude(lat + y);
			y = y + 0.01;
			getChannelBpo().saveImsGisInfo(imsGisInfo);
		}
	}

	public String findServerIp(Integer serverType) {
		return serverBpo.findServerIp(serverType);
	}

	public void findMduIpAndPort() {
		String mduIp = serverBpo.findOutServerIp(ServerType.MDU);
		if (StringUtils.isNotBlank(mduIp)&& mduIp.split(":").length > 1){
			ServletActionContext.getRequest().setAttribute("mduIp", mduIp.split(":")[0]);
			ServletActionContext.getRequest().setAttribute("mduPort", mduIp.split(":")[1]);
		} else {
			ServletActionContext.getRequest().setAttribute("mduIp", serverBpo.findServerIp(ServerType.MDU));
			ServletActionContext.getRequest().setAttribute("mduPort", ResourceUtil.getServerConf("mdu.server.port"));
		}
	}
}
