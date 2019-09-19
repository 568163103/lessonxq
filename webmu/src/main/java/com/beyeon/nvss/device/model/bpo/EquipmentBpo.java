package com.beyeon.nvss.device.model.bpo;

import java.util.*;

import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.*;
import org.springframework.stereotype.Component;

import com.beyeon.common.aop.annotation.Cache;
import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.nvss.device.model.dao.EquipmentDao;
import com.beyeon.nvss.device.model.dto.EquipmentDto;
import com.beyeon.nvss.dmu.model.dao.DmuEquipmentDao;
@Cache(cacheName = "其他设备")
@Component("equipmentBpo")
public class EquipmentBpo extends BaseBpo {
	private EquipmentDao equipmentDao;
	private DmuEquipmentDao dmuEquipmentDao;
	public void setEquipmentDao(EquipmentDao equipmentDao) {
		this.equipmentDao = equipmentDao;
	}
	public void setDmuEquipmentDao(DmuEquipmentDao dmuEquipmentDao) {
		this.dmuEquipmentDao = dmuEquipmentDao;
	}
	public EquipmentDto get(String mid) {
		EquipmentDto equipmentDto = new EquipmentDto();
		equipmentDto.setEquipment(this.equipmentDao.getFivs(Equipment.class, mid));
		return equipmentDto;
	}
	
	public void save(EquipmentDto equipmentDto) {
		
		String sid = this.equipmentDao.createId(equipmentDto.getEquipment().getTypeName(),"","0",equipmentDto.getEquipment().getPosition());
		equipmentDto.getEquipment().setId(sid);
		equipmentDto.getEquipment().setStatus(1);
		Equipment.getObjectMap(equipmentDto.getEquipment().getType().toString()).put(equipmentDto.getEquipment().getId(),equipmentDto.getEquipment().getName());
        this.equipmentDao.saveFivs(equipmentDto.getEquipment());
        
        DmuEquipment dmuEquipment = new DmuEquipment();
		dmuEquipment.setId(equipmentDto.getEquipment().getId());
		dmuEquipment.setName(equipmentDto.getEquipment().getName());
		dmuEquipment.setCorp(equipmentDto.getEquipment().getCorp());
		dmuEquipment.setType(equipmentDto.getEquipment().getTypeName());		
		dmuEquipment.setVersion(equipmentDto.getEquipment().getVersion());
		dmuEquipment.setPos(equipmentDto.getEquipment().getPos());
		dmuEquipment.setIp(equipmentDto.getEquipment().getIp());
		dmuEquipment.setMac(equipmentDto.getEquipment().getMac());
		dmuEquipment.setPort(equipmentDto.getEquipment().getPort());
		dmuEquipment.setRemark(equipmentDto.getEquipment().getRemark());
		dmuEquipment.setStatus(1);
		this.equipmentDao.saveFivs(dmuEquipment);

		Collection<AutoCompleteDto> types = AlarmType.getTypes();
		Iterator<AutoCompleteDto> it = types.iterator();
		while (it.hasNext()) {
			AutoCompleteDto autoCompleteDto = it.next();
			String alarmType = autoCompleteDto.getValue();

			if("29".equals(alarmType)||"30".equals(alarmType)
					||"31".equals(alarmType)||"32".equals(alarmType)
					||"33".equals(alarmType) ||"35".equals(alarmType)){
				TbAlarmRes alarmRes = new TbAlarmRes(equipmentDto.getEquipment().getId(), alarmType, equipmentDto.getEquipment().getName()+"("+autoCompleteDto.getLabel()+")",equipmentDto.getEquipment().getId());
				this.equipmentDao.saveFivs(alarmRes);
			}else if ("27".equals(alarmType)||"28".equals(alarmType)){
				String tempId = null;
				tempId = equipmentDto.getEquipment().getId();
				StringBuffer alremIdBuffer= new StringBuffer(tempId);
				alremIdBuffer.replace(13,14,"2");
				alremIdBuffer.replace(15,16,"1");
				TbAlarmRes alarmRes = new TbAlarmRes(alremIdBuffer.toString(), alarmType, equipmentDto.getEquipment().getName()+"("+autoCompleteDto.getLabel()+")",equipmentDto.getEquipment().getId());
				this.equipmentDao.saveFivs(alarmRes);
			}else if ("34".equals(alarmType)){
				String tempId = null;
				tempId = equipmentDto.getEquipment().getId();
				StringBuffer alremIdBuffer= new StringBuffer(tempId);
				alremIdBuffer.replace(13,14,"1");
				alremIdBuffer.replace(15,16,"1");
				TbAlarmRes alarmRes = new TbAlarmRes(alremIdBuffer.toString(), alarmType, equipmentDto.getEquipment().getName()+"("+autoCompleteDto.getLabel()+")",equipmentDto.getEquipment().getId());
				this.equipmentDao.saveFivs(alarmRes);
			}
		}
        
	}
	
	public void update(EquipmentDto equipmentDto) {
		
		Equipment.getObjectMap(equipmentDto.getEquipment().getType().toString()).put(equipmentDto.getEquipment().getId(),equipmentDto.getEquipment().getName());
        this.equipmentDao.updateFivs(equipmentDto.getEquipment());
        
        
        DmuEquipment dmuEquipment = new DmuEquipment();
		dmuEquipment.setId(equipmentDto.getEquipment().getId());
		dmuEquipment.setName(equipmentDto.getEquipment().getName());
		dmuEquipment.setCorp(equipmentDto.getEquipment().getCorp());
		dmuEquipment.setType(equipmentDto.getEquipment().getTypeName());		
		dmuEquipment.setVersion(equipmentDto.getEquipment().getVersion());
		dmuEquipment.setPos(equipmentDto.getEquipment().getPos());
		dmuEquipment.setIp(equipmentDto.getEquipment().getIp());
		dmuEquipment.setMac(equipmentDto.getEquipment().getMac());
		dmuEquipment.setPort(equipmentDto.getEquipment().getPort());
		dmuEquipment.setRemark(equipmentDto.getEquipment().getRemark());
		dmuEquipment.setStatus(equipmentDto.getEquipment().getStatus());
		DmuEquipment equipment = dmuEquipmentDao.findById(equipmentDto.getEquipment().getId());
		if (equipment !=null){
			this.equipmentDao.updateFivs(dmuEquipment);
		}else{
			this.equipmentDao.saveFivs(dmuEquipment);
		}
		
	}

	public void delete(String[] ids) {
		this.equipmentDao.delete(ids);
	}

	public void find(PageObject pageObject) {
		this.equipmentDao.find(pageObject);
	}
	
	public List findAllEquipment() {
		return this.equipmentDao.findAllEquipment();
	}
	public List<Equipment> findByParam(Map<String, String> paramMap){
		return this.equipmentDao.findByParam(paramMap);
	}
	@Cache(cacheName = "其他设备")
	public void findEquipment() {
		List<Map<String,Object>> list = this.equipmentDao.findEquipment(new Integer[]{EquipmentType.DISK,EquipmentType.SWITCH,EquipmentType.VOOP});
		Map<String,Map<String,String>> currEquipmentMap = new HashMap<String, Map<String, String>>();
		for (Map<String,Object> o : list) {
			Map<String,String> equipmentMap = Equipment.getObjectMap(currEquipmentMap, o.get("type").toString());
			equipmentMap.put((String)o.get("id"), (String)o.get("name"));
		}
		Equipment.setObjectMap(currEquipmentMap);
	}

	@Cache(cacheName = "其他设备类型")
	public void findEquipmentType() {
		List<EquipmentType> list = this.equipmentDao.findEquipmentType();
		for (EquipmentType o : list) {
			Equipment.getTypeMap().put(o.getId().toString(),o.getName());
		}
	}
	
	
	@Cache(cacheName = "其他设备厂家")
	public void findEquipmentCorp() {
		List<EquipmentCorp> list = this.equipmentDao.findEquipmentCorp();
		for (EquipmentCorp o : list) {
			Equipment.getCorpMap().put(o.getId().toString(),o.getName());
		}
	}
	
	
	public List<EquipmentCorp> findCorpByType(String type) {
		return equipmentDao.findCorpByType(type);
	}
	public boolean checkUnique(String attrName,String value,String id) {
		return equipmentDao.checkEquipmentUnique(id, value);
	}

	public String findEquipmentIp(Integer equipmentType) {
		return equipmentDao.findEquipmentIp(equipmentType);
	}


	public String findOutEquipmentIp(Integer equipmentType) {
		return equipmentDao.findOutEquipmentIp(equipmentType);
	}
	
	
	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<Equipment> list = equipmentDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				Equipment equipment = (Equipment) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (equipment != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(equipment.getId());
					data.add(equipment.getName());
					data.add(equipment.getPositionZH());
					data.add(equipment.getTypeName());
					data.add(equipment.getIp());
					data.add(equipment.getEnabledZh());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}
}
