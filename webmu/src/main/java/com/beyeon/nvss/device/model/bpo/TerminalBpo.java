package com.beyeon.nvss.device.model.bpo;

import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.Equipment;
import com.beyeon.nvss.device.model.dto.TerminalDto;
import com.beyeon.nvss.dmu.model.dao.DmuEquipmentDao;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.model.bpo.BaseBpo;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.hibernate.fivsdb.Terminal;
import com.beyeon.nvss.device.model.dao.TerminalDao;

import java.util.ArrayList;
import java.util.List;

@Component("terminalBpo")
public class TerminalBpo extends BaseBpo {
	private TerminalDao terminalDao;
	private DmuEquipmentDao dmuEquipmentDao;
	public void setTerminalDao(TerminalDao terminalDao) {
		this.terminalDao = terminalDao;
	}
	public void setDmuEquipmentDao(DmuEquipmentDao dmuEquipmentDao) {
		this.dmuEquipmentDao = dmuEquipmentDao;
	}
	public TerminalDto get(String mid) {
		TerminalDto terminalDto = new TerminalDto();
		terminalDto.setTerminal(this.terminalDao.getFivs(Terminal.class, mid));
		return terminalDto;
	}
	
	public void save(TerminalDto terminalDto) {
		String tid = this.terminalDao.createId("terminal","","0",terminalDto.getTerminal().getPosition());
		terminalDto.getTerminal().setId(tid);
        this.terminalDao.saveFivs(terminalDto.getTerminal());

		DmuEquipment dmuEquipment = new DmuEquipment();
		dmuEquipment.setId(terminalDto.getTerminal().getId());
		dmuEquipment.setName(terminalDto.getTerminal().getName());
		dmuEquipment.setType("终端");
		dmuEquipment.setPos(terminalDto.getTerminal().getAddress());
		dmuEquipment.setIp(terminalDto.getTerminal().getIp());
		dmuEquipment.setRemark(terminalDto.getTerminal().getDescription());
		dmuEquipment.setStatus(0);
		this.terminalDao.saveFivs(dmuEquipment);
	}
	
	public void update(TerminalDto terminalDto) {
        this.terminalDao.updateFivs(terminalDto.getTerminal());
		DmuEquipment dmuEquipment = new DmuEquipment();
		dmuEquipment.setId(terminalDto.getTerminal().getId());
		dmuEquipment.setName(terminalDto.getTerminal().getName());
		dmuEquipment.setType("终端");
		dmuEquipment.setPos(terminalDto.getTerminal().getAddress());
		dmuEquipment.setIp(terminalDto.getTerminal().getIp());
		dmuEquipment.setRemark(terminalDto.getTerminal().getDescription());
		dmuEquipment.setStatus(0);
		DmuEquipment equpiment = dmuEquipmentDao.findById(terminalDto.getTerminal().getId());
		if (equpiment!=null){
			this.terminalDao.updateFivs(dmuEquipment);
		}else{
			this.terminalDao.saveFivs(dmuEquipment);
		}
	}

	public void delete(String[] ids) {
		this.terminalDao.delete(ids);
	}

	public void find(PageObject pageObject) {
		this.terminalDao.find(pageObject);
	}

	public boolean checkUnique(String attrName,String value,String id) {
		return terminalDao.checkTerminalUnique(id, value);
	}


	public ArrayList<ArrayList<String>> findExcelFieldData(
			PageObject pageObject) {
		ArrayList<ArrayList<String>> fieldData = new ArrayList<ArrayList<String>>();
		// 存放excel的内容的所有数据
		List<Terminal> list = terminalDao
				.findByNoPageWithExcel(pageObject);

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {

				// 存放每一行的数据
				Terminal terminal = (Terminal) list.get(i);
				// 将每一行的数据，放置到ArrayList<WmuOperationLog>
				if (terminal != null) {
					ArrayList<String> data = new ArrayList<String>();
					data.add(String.valueOf(i + 1));
					data.add(terminal.getId());
					data.add(terminal.getName());
					data.add(terminal.getPositionZH());
					data.add(terminal.getIp());
					data.add(terminal.getAddress());
					data.add(terminal.getEnabledZh());
					data.add(terminal.getDescription());
					fieldData.add(data);
				}
			}
		}
		return fieldData;
	}

}
