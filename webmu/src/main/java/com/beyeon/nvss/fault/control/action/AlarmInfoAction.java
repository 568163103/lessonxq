package com.beyeon.nvss.fault.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.hibernate.fivsdb.AlarmInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.DmuAlarmType;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.dmu.model.dto.DmuAlarmInfoDto;
import com.beyeon.nvss.fault.model.bpo.AlarmInfoBpo;
import com.beyeon.nvss.fault.model.dto.AlarmInfoDto;

@Component("alarmInfoAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AlarmInfoAction extends BaseAction {
    private AlarmInfoDto alarmInfoDto;
    private DeviceFacade deviceFacade;

    public void setDeviceFacade(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    public void setAlarmInfoDto(AlarmInfoDto alarmInfoDto) {
        this.alarmInfoDto = alarmInfoDto;
    }

    public Object getModel() {
        if (null == alarmInfoDto) {
            String id = this.getReqParam("id");
            if (StringUtils.isNotBlank(id)) {

                alarmInfoDto = deviceFacade.getAlarmInfoBpo().get(id);
            } else {
                alarmInfoDto = new AlarmInfoDto();
            }
        }
        return alarmInfoDto;
    }

    public String findPage() {
        deviceFacade.getAlarmInfoBpo().findAlarmInfo(this.getPageObject());
        this.setReqAttr("alarmTypes", AlarmType.getTypes());
        this.setReqAttr("levels", AlarmType.getLevels());
        this.setReqAttr("alarmLevels", TbAlarmType.getLevelMap());
        this.setReqAttr("dealStateMap", AlarmInfo.getDealStateMap());

        return "findPage";
    }

    public String handleAlarm() {
        String status = this.getReqParam("status");
        String id = this.getReqParam("id");
        String memo = this.getReqParam("memo");
        String type = this.getReqParam("type");
        String uid = this.getCurrentUser().getId();
        deviceFacade.getAlarmInfoBpo().handleAlarm(status, uid, memo, id, type);
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "200");
        this.responseJSON(map);
        return null;
    }

    public String alarmInfoStatistics() {
        this.setReqAttr("levels", AlarmType.getLevels());
        this.setReqAttr("alarmTypes2", AlarmType.getTypes());
        String statisticsTime = this.getReqParam("statisticsTime");
        String sourceId = this.getReqParam("sourceId");
        String alarmLevel = this.getReqParam("alarmLevel");
        String status = this.getReqParam("status");
        String alarmType = this.getReqParam("alarmType");
        String beginTime = this.getReqParam("beginTime");
        String endTime = this.getReqParam("endTime");
        int i = 1;
        if (StringUtils.isNotBlank(statisticsTime)) {
            i = Integer.valueOf(statisticsTime);
        }
        switch (i) {
            case 2: {
                statisticsTime = DateUtil.format(DateUtil.addWeeks(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
                break;
            }
            case 3: {
                statisticsTime = DateUtil.format(DateUtil.addMonths(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
                break;
            }
            default:
                statisticsTime = DateUtil.format(DateUtil.addDays(new Date(), -1), "yyyy-MM-dd HH:mm:ss");
        }

        Map result = new HashMap();
        for (AutoCompleteDto o : AlarmType.getTypes()) {
            result.put(o.getLabel(), "");
        }
        Map<String, String> params = new HashMap<String, String>();
//		params.put("statisticsTime", statisticsTime);
        params.put("sourceId", sourceId);
        params.put("alarmLevel", alarmLevel);
        params.put("status", status);
        params.put("alarmType", alarmType);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        List<Object[]> alarmInfoStatistics = deviceFacade.getAlarmInfoBpo().findAlarmInfoStatistics(params);
        for (Object[] alarmInfoStatistic : alarmInfoStatistics) {
            result.put(AlarmType.getTypeName(alarmInfoStatistic[0].toString()), alarmInfoStatistic[1].toString());
        }
        this.setReqAttr("alarmTypes", StringUtils.join(result.keySet(), "','"));
        this.setReqAttr("alarmDatas", StringUtils.join(result.values(), ","));
        this.setReqAttr("statisticsTime", i);
        this.setReqAttr("sourceId", sourceId);
        this.setReqAttr("alarmLevel", alarmLevel);
        this.setReqAttr("status", status);
        this.setReqAttr("alarmType", alarmType);
        this.setReqAttr("beginTime", params.get("beginTime"));
        this.setReqAttr("endTime", params.get("endTime"));
        return "alarmInfoStatistics";
    }


    public String exportList() {
        /** 构造excel的标题数据 */
        ArrayList<String> fieldName = findExcelFieldName();
        /** 构造excel的数据内容 */
        ArrayList<ArrayList<String>> fieldData = deviceFacade.getAlarmInfoBpo().findExcelFieldData(getPageObject());
        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
        try {
            /** 获取字节输出流*/
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            /** 设置excel的响应头部信息，（内联和附件）*/
            String filename = "告警(" + DateUtil.format("yyyyMMddHHmmss") + ")";
            /** 处理中文 */
            filename = new String(filename.getBytes("gbk"), "iso-8859-1");
            ServletActionContext.getRequest().setAttribute("name", filename);
            excelFileGenerator.expordExcel(os);
            /**从缓存区中读出字节数组 */
            byte[] bytes = os.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            /** 将inputStream放置到模型驱动的对象中 */
            alarmInfoDto.setInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "exportList";
    }

    public String exportList2() {
        /** 构造excel的标题数据 */
        ArrayList<String> fieldName = findExcelFieldName2();
        /** 构造excel的数据内容 */
        String statisticsTime = this.getReqParam("statisticsTime");
        String sourceId = this.getReqParam("sourceId");
        String alarmLevel = this.getReqParam("alarmLevel");
        String status = this.getReqParam("status");
        String alarmType = this.getReqParam("alarmType");
        String beginTime = this.getReqParam("beginTime");
        String endTime = this.getReqParam("endTime");
        Map<String, String> params = new HashMap<String, String>();
//		params.put("statisticsTime", statisticsTime);
        params.put("sourceId", sourceId);
        params.put("alarmLevel", alarmLevel);
        params.put("status", status);
        params.put("alarmType", alarmType);
        params.put("beginTime", beginTime);
        params.put("endTime", endTime);
        ArrayList<ArrayList<String>> fieldData = deviceFacade.getAlarmInfoBpo().findExcelFieldData2(params);
        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
        try {
            /** 获取字节输出流*/
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            /** 设置excel的响应头部信息，（内联和附件）*/
            String filename = "告警统计(" + DateUtil.format("yyyyMMddHHmmss") + ")";
            /** 处理中文 */
            filename = new String(filename.getBytes("gbk"), "iso-8859-1");
            ServletActionContext.getRequest().setAttribute("name", filename);
            excelFileGenerator.expordExcel(os);
            /**从缓存区中读出字节数组 */
            byte[] bytes = os.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            /** 将inputStream放置到模型驱动的对象中 */
            alarmInfoDto.setInputStream(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "exportList";
    }

    /**
     * EXCEL表头名称
     *
     * @return
     * @author
     */
    public ArrayList<String> findExcelFieldName() {
        ArrayList<String> fieldName = new ArrayList<String>();
        fieldName.add("序号");
        fieldName.add("设备编号");
        fieldName.add("设备名称");
        fieldName.add("告警类型");
        fieldName.add("开始时间");
        fieldName.add("结束时间");
        fieldName.add("告警状态");
        fieldName.add("处理人员");
        fieldName.add("处理时间");
        fieldName.add("处理备注");
        fieldName.add("备注");
        return fieldName;
    }

    public ArrayList<String> findExcelFieldName2() {
        ArrayList<String> fieldName = new ArrayList<String>();
        fieldName.add("序号");
        fieldName.add("告警类型");
        fieldName.add("数量");
        return fieldName;
    }

}
