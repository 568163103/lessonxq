package com.socket.sip.process.query;

import java.util.Date;

import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.EncoderStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.ServerStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.SwitchStateRecordBpo;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;	

public class CleanHistoryState extends SIPSocketUtil{
	/**
	 * 轮询采集本级的设备状态
	 */
	public void init (){
		DiskStateRecordBpo diskStateRecordBpo = (DiskStateRecordBpo)SpringInit.getApplicationContext().getBean("diskStateRecordBpo");
		EncoderStateRecordBpo encoderStateRecordBpo =(EncoderStateRecordBpo)SpringInit.getApplicationContext().getBean("encoderStateRecordBpo");
		SwitchStateRecordBpo switchStateRecordBpo =(SwitchStateRecordBpo)SpringInit.getApplicationContext().getBean("switchStateRecordBpo");
		ServerStateRecordBpo serverStateRecordBpo =(ServerStateRecordBpo)SpringInit.getApplicationContext().getBean("serverStateRecordBpo");
		System.out.println(DateUtil.getTime()+"-------------start clean history state");
		String date = DateUtil.format(DateUtil.subDays(new Date(), ResourceUtil.getZabbixCleanTime()),DateUtil.yyyyMMddHHmmssSpt);
		try {
			serverStateRecordBpo.delete(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			switchStateRecordBpo.delete(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			encoderStateRecordBpo.delete(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			diskStateRecordBpo.delete(date);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
