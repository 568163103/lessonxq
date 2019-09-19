package com.beyeon.common.web.filter;
 
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;

import org.apache.commons.lang3.StringUtils;

import com.beyeon.common.util.DateUtil;
import com.beyeon.hibernate.fivsdb.DiskStateRecord;
import com.beyeon.hibernate.fivsdb.DmuEquipment;
import com.beyeon.hibernate.fivsdb.EncoderStateRecord;
import com.beyeon.nvss.dmu.model.bpo.DiskStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.DmuEquipmentBpo;
import com.beyeon.nvss.dmu.model.bpo.EncoderStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.ServerStateRecordBpo;
import com.beyeon.nvss.dmu.model.bpo.SwitchStateRecordBpo;
import com.beyeon.nvss.dmu.model.dto.DiskStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.DmuEquipmentDto;
import com.beyeon.nvss.dmu.model.dto.EncoderStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.ServerStateRecordDto;
import com.beyeon.nvss.dmu.model.dto.SwitchStateRecordDto;
import com.socket.sip.SIPSocketUtil;
import com.socket.sip.SpringInit;


 
/**
 * FlyLoggerThread
 * Created by sff on 2017/5/6.
 */
public class FlyLoggerThread extends Observable implements Runnable {
 
    /** 任务队列 */
    /** 是将线程挂起和激活用的 */
    private Object object;
    private static int time =1000 * 1 ; //30S
    private static boolean status = false;
    private static HashMap<String,String> map = new HashMap<String,String>();
   
	
	public static HashMap<String, String> getMap() {
		return map;
	}

	public static void setMap(HashMap<String, String> map2) {
		Map<String,String> oldObjectMap = map;
		map = map2;
		oldObjectMap.clear();
	}

	

	public static int getTime() {
		return time;
	}

	public static void setTime(int time) {
		FlyLoggerThread.time = time;
	}

	public static boolean isStatus() {
		return status;
	}

	public static void setStatus(boolean status) {
		FlyLoggerThread.status = status;
	}

	public void doBusiness(){
        //出现异常时，通知一个别的线程。
        if(true){
            super.setChanged();
        }
//        notifyObservers(this);
        notifyObservers();
    }
 
    public FlyLoggerThread(){
        super();
        object = 1;
    }
 
    @Override
    public void run(){
       try{
           while(true){
        	   if (status){
        		   processLocation();
        	   }
        	   Thread.sleep(time);
           }
       } catch (Exception e) {
           // 被中断。应该停止
            e.printStackTrace();
            doBusiness();
        }
    }
 
    public void threadWait(){
        try{
            object.wait();
        } catch (InterruptedException e){
 
        }
    }
 
    public void threadNotify(){
        object.notify();
    }
 
    /**
	 * 每小时处理一次数据
	 */
	private void processLocation() {
		DmuEquipmentBpo dmuEquipmentBpo =(DmuEquipmentBpo)SpringInit.getApplicationContext().getBean("dmuEquipmentBpo");
		DiskStateRecordBpo  diskStateRecordBpo =(DiskStateRecordBpo)SpringInit.getApplicationContext().getBean("diskStateRecordBpo");
		ServerStateRecordBpo serverStateRecordBpo =(ServerStateRecordBpo)SpringInit.getApplicationContext().getBean("serverStateRecordBpo");
		SwitchStateRecordBpo switchStateRecordBpo =(SwitchStateRecordBpo)SpringInit.getApplicationContext().getBean("switchStateRecordBpo");
		EncoderStateRecordBpo encoderStateRecordBpo =(EncoderStateRecordBpo)SpringInit.getApplicationContext().getBean("encoderStateRecordBpo");
		List<DmuEquipment> self = dmuEquipmentBpo.findEquipment(map, true);
		for (DmuEquipment d : self){
			DmuEquipmentDto dto = dmuEquipmentBpo.get(d.getId());
			if (dto.getDiskStateRecord()!=null && StringUtils.isNotBlank(dto.getDiskStateRecord().getDiskid())){
				DiskStateRecord disk =new DiskStateRecord(dto.getDiskStateRecord());
				disk.setRecordTime(DateUtil.getTime());				
				DiskStateRecordDto diskStateRecordDto = new DiskStateRecordDto();
				diskStateRecordDto.setDiskStateRecord(disk);
				diskStateRecordBpo.save(diskStateRecordDto);
			}else if (dto.getEncoderStateRecord()!=null && StringUtils.isNotBlank(dto.getEncoderStateRecord().getEncoderid()) ){
				EncoderStateRecord encoder = new EncoderStateRecord (dto.getEncoderStateRecord());
				encoder.setRecordTime(DateUtil.getTime());
				EncoderStateRecordDto encoderStateRecordDto = new EncoderStateRecordDto();
				encoderStateRecordDto.setEncoderStateRecord(encoder);
				encoderStateRecordBpo.save(encoderStateRecordDto);
			}else if (dto.getServerStateRecord()!=null && dto.getServerStateRecord().getServerStateRecord()!=null ){
				ServerStateRecordDto server = dto.getServerStateRecord();
//				ServerStateRecord record = new ServerStateRecord(server.getServerStateRecord());
//				List<ServerStateRecordCpu> cpuList = server.getServerStateRecordCpu();
//				List<ServerStateRecordDisk> diskList = server.getServerStateRecordDisk();
//				List<ServerStateRecordNetcard> netList = server.getServerStateRecordNetcard();							
				ServerStateRecordDto serverStateRecordDto = new ServerStateRecordDto(server);
//				serverStateRecordDto.setServerStateRecord(record);
//				serverStateRecordDto.setServerStateRecordCpu(cpuList);
//				serverStateRecordDto.setServerStateRecordDisk(diskList);
//				serverStateRecordDto.setServerStateRecordNetcard(netList);
				serverStateRecordBpo.save2(serverStateRecordDto);
			}else if (dto.getSwitchStateRecord()!=null && dto.getSwitchStateRecord().getSwitchStateRecord()!=null){
				SwitchStateRecordDto switchState = dto.getSwitchStateRecord(); 
//				SwitchStateRecord record = switchState.getSwitchStateRecord();
//				record.setId(null);
//				record.setRecordTime(DateUtil.getTime());				
				SwitchStateRecordDto switchStateRecordDto = new SwitchStateRecordDto(switchState);
//				switchStateRecordDto.setSwitchStateRecord(record);
//				switchStateRecordDto.setSwitchStateRecordPort(switchState.getSwitchStateRecordPort());
				switchStateRecordBpo.save2(switchStateRecordDto);
				
			}
			List<DmuEquipment> other = dmuEquipmentBpo.findEquipment(map, false);
			for (DmuEquipment f : other){
				 SIPSocketUtil.queryState(f.getId(), f.getType(), f.getMaster());
			}
		}
		
	}
}
