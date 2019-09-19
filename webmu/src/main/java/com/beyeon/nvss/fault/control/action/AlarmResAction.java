package com.beyeon.nvss.fault.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.fault.model.dto.AlarmResDto;

@Component("alarmResAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class AlarmResAction extends BaseAction {
    private static Logger logger = LoggerFactory.getLogger(AlarmResAction.class);
    private DeviceFacade deviceFacade;
    private AlarmResDto alarmResDto;

    public void setDeviceFacade(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    public AlarmResDto getAlarmRes() {
        return alarmResDto;
    }

    public void setAlarmRes(AlarmResDto alarmResDto) {
        this.alarmResDto = alarmResDto;
    }

    public Object getModel() {
        if (null == alarmResDto) {
            String id = this.getReqParam("id");
            if (StringUtils.isNotBlank(id)) {

                alarmResDto = deviceFacade.getAlarmResBpo().get(Integer.parseInt(id));
            } else {
                alarmResDto = new AlarmResDto();
            }
        }
        return alarmResDto;
    }

    public String beforeUpdate() {
        return "beforeUpdate";
    }

    public String update() {
        deviceFacade.getAlarmResBpo().update(alarmResDto);
        return findPage();
    }

    public String beforeSave() {
        return "beforeSave";
    }

    public String save() {
        String[] ids = this.getReqParams("items");
        for (String id : ids) {
            String[] temp = id.split("\\|");
            if (temp != null ) {
                String resId = temp[0];
                String alarmType = temp[1];
                String name = temp[2];
                String sid = "";
                String description= "";
                if (temp.length > 3)
                    sid = temp[3];
                if (temp.length > 4)
                    description = temp[4];
                TbAlarmRes tbAlarmRes = new TbAlarmRes();
                tbAlarmRes.setResId(resId);
                tbAlarmRes.setAlarmType(alarmType);
                tbAlarmRes.setName(name);
                tbAlarmRes.setSid(sid);
                tbAlarmRes.setDescription(description);
                alarmResDto.setAlarmRes(tbAlarmRes);
                deviceFacade.getAlarmResBpo().save(alarmResDto);
            }
        }
        return findPage();
    }

    public String delete() {
        String[] ids = this.getReqParams("items");
        deviceFacade.getAlarmResBpo().delete(ids);
        return findPage();
    }

    public String findPage() {
        this.setReqAttr("alarmTypes", AlarmType.getTypes());
        deviceFacade.getAlarmResBpo().find(getPageObject());
        return "findPage";
    }

    public String findRes() {
        this.setReqAttr("alarmTypes", AlarmType.getTypes());
        Map<String, String> types = Equipment.getTypeMap();
        Map<String, String> type = new HashMap<>();
        type.putAll(types);
        type.put("7", "服务器");
        type.put("8", "本级编码器");
        type.put("9", "下级编码器");
        this.setReqAttr("types", type);
        deviceFacade.getAlarmResBpo().findRes(getPageObject());
        return "findRes";
    }

    /**
     * 报表导出
     *
     * @return
     * @author
     */
    public String exportList() {
        /** 构造excel的标题数据 */
        ArrayList<String> fieldName = findExcelFieldName();
        /** 构造excel的数据内容 */
        ArrayList<ArrayList<String>> fieldData = deviceFacade.getAlarmResBpo().findExcelFieldData(getPageObject());
        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
        try {
            /** 获取字节输出流*/
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            /** 设置excel的响应头部信息，（内联和附件）*/
            String filename = "告警资源导出(" + DateUtil.format("yyyyMMddHHmmss") + ")";
            /** 处理中文 */
            filename = new String(filename.getBytes("gbk"), "iso-8859-1");
            ServletActionContext.getRequest().setAttribute("name", filename);
            excelFileGenerator.expordExcel(os);
            /**从缓存区中读出字节数组 */
            byte[] bytes = os.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            /** 将inputStream放置到模型驱动的对象中 */
            alarmResDto.setInputStream(inputStream);
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
        fieldName.add("设备ID");
        fieldName.add("告警类型");
        fieldName.add("告警资源名称");
        fieldName.add("所属平台ID");
        return fieldName;
    }


}
