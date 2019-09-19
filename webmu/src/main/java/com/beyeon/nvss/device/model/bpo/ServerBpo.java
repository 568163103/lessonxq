package com.beyeon.nvss.device.model.bpo;

import java.util.*;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.*;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.device.model.dao.ServerDao;
import com.beyeon.nvss.device.model.dto.ServerDto;
import com.beyeon.nvss.dmu.model.dao.DmuEquipmentDao;
@Cache(cacheName = "服务器")
@Component("serverBpo")
public class ServerBpo extends BaseBpo {
	private ServerDao serverDao;
	private DmuEquipmentDao dmuEquipmentDao;
	
	public void setServerDao(ServerDao serverDao) {
		this.serverDao = serverDao;
	}
	public void setDmuEquipmentDao(DmuEquipmentDao dmuEquipmentDao) {
		this.dmuEquipmentDao = dmuEquipmentDao;
	}
	public ServerDto get(String mid) {
		ServerDto serverDto = new ServerDto();
		serverDto.setServer(this.serverDao.getFivs(Server.class, mid));
		serverDto.setServerExtra(this.serverDao.getEncoderExtra(mid));
		return serverDto;
	}
	
	public void save(ServerDto serverDto) {

		String sid = this.serverDao.createId(serverDto.getServer().getTypeName(),"","0",serverDto.getServer().getPosition());
		serverDto.getServer().setId(sid);
		serverDto.getServer().setProhibited_status(1);
		serverDto.getServerExtra().setId(sid);
		boolean isVmu = (serverDto.getServer().getType() == 7);
		if (isVmu){
			serverDto.getServer().setStatus(true);
		}
		Server.getObjectMap(serverDto.getServer().getType().toString()).put(serverDto.getServer().getId(),serverDto.getServer().getName());
        this.serverDao.saveFivs(serverDto.getServer());
        this.serverDao.saveFivs(serverDto.getServerExtra());
		PositionServer positionServer = new PositionServer();
		positionServer.setPositionId(serverDto.getServer().getPosition());
		positionServer.setServerId(sid);
        this.serverDao.saveFivs(positionServer);
        
        boolean isDmu =  (serverDto.getServer().getType() == 8 || serverDto.getServer().getType() == 7);
        if (!isDmu){
        	 DmuEquipment dmuEquipment = new DmuEquipment();
     		dmuEquipment.setId(serverDto.getServer().getId());
     		dmuEquipment.setName(serverDto.getServer().getName());
     		dmuEquipment.setCorp(serverDto.getServerExtra().getCorp());
     		dmuEquipment.setType("服务器");		
     		dmuEquipment.setVersion(serverDto.getServerExtra().getVersion());
     		dmuEquipment.setPos(serverDto.getServer().getAddress());
     		dmuEquipment.setIp(serverDto.getServer().getIp());
     		dmuEquipment.setMac(serverDto.getServerExtra().getMac());
     		dmuEquipment.setPort(serverDto.getServer().getPort());
     		dmuEquipment.setRemark(serverDto.getServer().getDescription());
     		dmuEquipment.setStatus(0);
     		this.serverDao.saveFivs(dmuEquipment);
        }
		Collection<AutoCompleteDto> types = AlarmType.getTypes();
		Iterator<AutoCompleteDto> it = types.iterator();
		while (it.hasNext()) {
			AutoCompleteDto autoCompleteDto = it.next();
			String alarmType = autoCompleteDto.getValue();
			if("29".equals(alarmType)||"31".equals(alarmType)
					||"35".equals(alarmType)){
				TbAlarmRes alarmRes = new TbAlarmRes(serverDto.getServer().getId(), alarmType, serverDto.getServer().getName()+"("+autoCompleteDto.getLabel()+")",serverDto.getServer().getId());
				this.serverDao.saveFivs(alarmRes);
			}else if ("27".equals(alarmType)||"28".equals(alarmType)){
				String tempId = null;
				tempId = serverDto.getServer().getId();
				StringBuffer alremIdBuffer= new StringBuffer(tempId);
				alremIdBuffer.replace(13,14,"2");
				alremIdBuffer.replace(15,16,"1");
				TbAlarmRes alarmRes = new TbAlarmRes(alremIdBuffer.toString(), alarmType, serverDto.getServer().getName()+"("+autoCompleteDto.getLabel()+")",serverDto.getServer().getId());
				this.serverDao.saveFivs(alarmRes);
			}else if ("34".equals(alarmType)){
				String tempId = null;
				tempId = serverDto.getServer().getId();
				StringBuffer alremIdBuffer= new StringBuffer(tempId);
				alremIdBuffer.replace(13,14,"1");
				alremIdBuffer.replace(15,16,"1");
				TbAlarmRes alarmRes = new TbAlarmRes(alremIdBuffer.toString(), alarmType, serverDto.getServer().getName()+"("+autoCompleteDto.getLabel()+")",serverDto.getServer().getId());
				this.serverDao.saveFivs(alarmRes);
			}
		}
       
        
	}
	
	public void update(ServerDto serverDto) {
		
		Server.getObjectMap(serverDto.getServer().getType().toString()).put(serverDto.getServer().getId(),serverDto.getServer().getName());
        this.serverDao.updateFivs(serverDto.getServer());
        this.serverDao.updateServerExtra(serverDto.getServerExtra());
        boolean isDmu =  (serverDto.getServer().getType() == 8 || serverDto.getServer().getType() == 7);
        if (!isDmu){
	        DmuEquipment dmuEquipment = new DmuEquipment();
			dmuEquipment.setId(serverDto.getServer().getId());
			dmuEquipment.setName(serverDto.getServer().getName());
			dmuEquipment.setCorp(serverDto.getServerExtra().getCorp());
			dmuEquipment.setType("服务器");		
			dmuEquipment.setVersion(serverDto.getServerExtra().getVersion());
			dmuEquipment.setPos(serverDto.getServer().getAddress());
			dmuEquipment.setIp(serverDto.getServer().getIp());
			dmuEquipment.setMac(serverDto.getServerExtra().getMac());
			dmuEquipment.setPort(serverDto.getServer().getPort());
			dmuEquipment.setRemark(serverDto.getServer().getDescription());
			dmuEquipment.setStatus(serverDto.getServer().getStatus() ? 1 : 0);
			DmuEquipment equpiment = dmuEquipmentDao.findById(serverDto.getServer().getId());
			if (equpiment!=null){
				this.serverDao.updateFivs(dmuEquipment);
			}else{
				this.serverDao.saveFivs(dmuEquipment);
			}
        }
		
	}

	public void delete(String[] ids) {
		this.serverDao.delete(ids);
	}

    public  void  updateProhibitedStatus(String id ,int prohibitedStatus){
		this.serverDao.updateProhibitedStatus(id,prohibitedStatus);
	}
	public void find(PageObject pageObject) {
		this.serverDao.find(pageObject);
	}
	
	public List findAllServer() {
		return this.serverDao.findAllServer();
	}
	
	public List findServerByParamName() {
		return this.serverDao.findServerByParamName();
	}
	
	public Server getServerById(String id ) {
		return this.serverDao.getServerById(id);
	}
	
	public ServerExtra getEncoderExtra(String id ) {
		return this.serverDao.getEncoderExtra(id);
	}

	@Cache(cacheName = "服务器")
	public void findServer() {
		List<Map<String,Object>> list = this.serverDao.findServer(new Integer[]{ServerType.CMU,ServerType.MDU,ServerType.MSU});
		Map<String,Map<String,String>> currServerMap = new HashMap<String, Map<String, String>>();
		for (Map<String,Object> o : list) {
			Map<String,String> serverMap = Server.getObjectMap(currServerMap, o.get("type").toString());
			serverMap.put((String)o.get("id"), (String)o.get("name"));
		}
		Server.setObjectMap(currServerMap);
	}

	@Cache(cacheName = "服务器类型")
	public void findServerType() {
		List<ServerType> list = this.serverDao.findServerType();
		for (ServerType o : list) {
			Server.getTypeMap().put(o.getId().toString(),o.getName());
		}
	}
	
	public void updateStatus(String id,String status){
		this.serverDao.updateStautus(id, status);
	}
	public boolean checkUnique(String attrName,String value,String id) {
		return serverDao.checkServerUnique(id, value);
	}

	public String findServerIp(Integer serverType) {
		return serverDao.findServerIp(serverType);
	}


	public String findOutServerIp(Integer serverType) {
		return serverDao.findOutServerIp(serverType);
	}	
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<Server> list = serverDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				Server server = (Server) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (server != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(server.getId());
					data.add(server.getName());
					data.add(server.getPositionZH());
					data.add(server.getTypeName());
					data.add(server.getIp());
					data.add(server.getEnabledZh());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}
	
	
	@SuppressWarnings("unchecked")
	@Cache(cacheName = "位置编码")
	public void findPositionCodeList() {
		List<PositionCode> list = this.serverDao.findPositionList();
		for (PositionCode o : list) {
			PositionCode.getTypeMap().put(o.getCode(),o.getName());
		}
	}


}
