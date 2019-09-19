package com.beyeon.nvss.device.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.beyeon.common.scheduling.SLSchedulerFactoryBean;
import com.beyeon.hibernate.fivsdb.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.struts2.ServletActionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.thread.ThreadPoolManager;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGenerator;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.device.model.dto.EncoderDto;
import com.beyeon.nvss.device.model.dto.EncoderImpDto;
import com.beyeon.nvss.device.model.dto.TreeEncoderImpDto;
import com.beyeon.security.util.SystemSnUtils;
import com.opensymphony.xwork2.ActionContext;

@Component("encoderAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class EncoderAction extends BaseAction {
    private static Logger logger = LoggerFactory.getLogger(EncoderAction.class);
    private DeviceFacade deviceFacade;
    private BussinessFacade bussinessFacade;
    private EncoderDto encoderDto;

    public void setDeviceFacade(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    public void setBussinessFacade(BussinessFacade bussinessFacade) {
        this.bussinessFacade = bussinessFacade;
    }

    public EncoderDto getEncoder() {
        return encoderDto;
    }

    public void setEncoder(EncoderDto encoderDto) {
        this.encoderDto = encoderDto;
    }

    public Object getModel() {
        if (null == encoderDto) {
            String id = this.getReqParam("id");
            if (StringUtils.isNotBlank(id)) {
                encoderDto = deviceFacade.getEncoderBpo().get(id);
            } else {
                encoderDto = new EncoderDto();
            }
        }
        return encoderDto;
    }

    public String beforeUpdate() {
        this.setReqAttr("encoderModels", Encoder.getEncoderModelMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        return "beforeUpdate";
    }

    public String update() {
        int newChannel = encoderDto.getEncoder().getChannelCount() - encoderDto.getChannels().size();
        if (newChannel < 0) {
            List<String> deleteChannelIds = this.deviceFacade.getEncoderBpo().findOutChannel(encoderDto.getEncoder().getId(), encoderDto.getEncoder().getChannelCount());
            if (deleteChannelIds.size() > 0) {
                this.bussinessFacade.getUserTreeBpo().deleteUserTreeByChannelId(deleteChannelIds.toArray(new String[0]));
            }
        }
        this.deviceFacade.getEncoderBpo().update(encoderDto);
		/*try {
			String topParentId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
			String rootName = this.bussinessFacade.getUserTreeBpo().findTopUserTree(topParentId);
			String uid = this.bussinessFacade.getUserTreeBpo().findAdminUid();
			List encoderImpDtos = new ArrayList();
			EncoderImpDto encoderImpDto = new TreeEncoderImpDto();
			encoderImpDto.setModel(encoderDto.getEncoder().getModel());
			encoderImpDto.setEncoderName(encoderDto.getEncoder().getName());
			encoderImpDto.setIp(encoderDto.getEncoder().getIp());
			encoderImpDto.setPort(encoderDto.getEncoder().getPort());
			encoderImpDto.setUsername(encoderDto.getEncoder().getUsername());
			encoderImpDto.setPassword(encoderDto.getEncoder().getPassword());
			encoderImpDto.setOutputCount(encoderDto.getEncoder().getOutputCount());
			encoderImpDtos.add(encoderImpDto);
			this.bussinessFacade.getUserTreeBpo().initUserResourceTree(uid, rootName, encoderImpDtos);
		} catch (Exception e) {
			logger.error("生成用户目录树失败", e);
			this.addActionMessage("摄像头添加到管理员目录树失败，请随后在（用户管理||自定义目录树）手动添加");
		}*/
        return findPage();
    }

    public String beforeSave() {
        this.setReqAttr("encoderModels", Encoder.getEncoderModelMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        return "beforeSave";
    }

    public String createChannel() {
        int existSize = 0;
        if (null != encoderDto.getChannels()) {
            existSize = encoderDto.getChannels().size();
        }
        int newChannel = encoderDto.getEncoder().getChannelCount() - existSize;
        if (newChannel > 0) {
            for (int i = existSize; i < encoderDto.getEncoder().getChannelCount(); i++) {
                encoderDto.getChannels().add(new Channel("", encoderDto.getEncoder().getName() + "-channel " + i, encoderDto.getEncoder().getId(), i));
            }
        } else if (newChannel < 0) {
            for (int i = existSize - 1; i > encoderDto.getEncoder().getChannelCount() - 1; i--) {
                encoderDto.getChannels().remove(i);
            }
        }
        if (null != encoderDto.getEncoder().getUpdateChannel() && encoderDto.getEncoder().getUpdateChannel().equals("1")) {
            for (int i = 0; i < encoderDto.getChannels().size(); i++) {
                encoderDto.getChannels().get(i).setName(encoderDto.getEncoder().getName() + "-channel " + i);
            }
        }
        this.setReqAttr("cameraTypes", DictBpo.find(TDict.CAMERA_TYPE));
        this.setReqAttr("type", "1204");
        return "createChannel";
    }

    public String save() {
        int existSize = 0;
        if (null != encoderDto.getChannels()) {
            existSize = encoderDto.getChannels().size();
        }
        int newChannel = encoderDto.getEncoder().getChannelCount() - existSize;
        long existTotal = this.deviceFacade.getEncoderBpo().findEncoderNum();
        if (SystemSnUtils.getSystemSn().getDeviceNum() < existTotal + newChannel) {
            this.addActionMessage("超出许可最大通道数！");
            return findPage();
        }
        deviceFacade.getEncoderBpo().save(encoderDto);
        try {
            String topParentId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
            System.out.println("topParentId" + topParentId);
            String rootName = this.bussinessFacade.getUserTreeBpo().findTopUserTree(topParentId);
            String uid = this.bussinessFacade.getUserTreeBpo().findAdminUid();
            List encoderImpDtos = new ArrayList();
            EncoderImpDto encoderImpDto = new TreeEncoderImpDto();
            encoderImpDto.setModel(encoderDto.getEncoder().getModel());
            encoderImpDto.setEncoderName(encoderDto.getEncoder().getName());
            encoderImpDto.setIp(encoderDto.getEncoder().getIp());
            encoderImpDto.setPort(encoderDto.getEncoder().getPort());
            encoderImpDto.setUsername(encoderDto.getEncoder().getUsername());
            encoderImpDto.setPassword(encoderDto.getEncoder().getPassword());
            encoderImpDto.setOutputCount(encoderDto.getEncoder().getOutputCount());
            encoderImpDto.setPosition(encoderDto.getEncoder().getPosition());
            encoderImpDtos.add(encoderImpDto);
            this.bussinessFacade.getUserTreeBpo().initUserResourceTree(uid, encoderDto.getEncoder().getModel().equals("platform") ? "" : rootName, encoderImpDtos);
        } catch (Exception e) {
            logger.error("生成用户目录树失败", e);
            this.addActionMessage("编码器添加成功，将其添加到管理员目录树失败，请随后在（用户管理||自定义目录树）手动添加");
        }
        ThreadPoolManager.excuteDefault(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuilder cmuUrl = new StringBuilder();
                    cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
                            .append(ResourceUtil.getServerConf("cmu.server.port")).append("/AddEncoder");
                    String result = HttpClientUtil.post(cmuUrl.toString(), "{\"encoderId\":\"" + encoderDto.getEncoder().getId() + "\"}");
                    logger.debug(result);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
        return findPage();
    }

    public String syncChannelName() {
        ServerStatus serverStatus = null;
        try {
            String encoerId = this.getReqParam("encoderId");
            StringBuilder cmuUrl = new StringBuilder();
            cmuUrl.append("http://").append(deviceFacade.findServerIp(ServerType.CMU)).append(":")
                    .append(ResourceUtil.getServerConf("cmu.server.port")).append("/SyncChannelName");
            String result = HttpClientUtil.post(cmuUrl.toString(), "{\"encoderId\":\"" + encoerId + "\"}");
            logger.debug(result);
            serverStatus = JSON.parseObject(result, ServerStatus.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        if (null == serverStatus || serverStatus.getResult() != 0) {
            this.addActionMessage("同步摄像头名称失败！");
        }
        return findPage();
    }

    public String delete() {
        String[] ids = this.getReqParams("items");
        List<String> channelIds = this.deviceFacade.getEncoderBpo().findChannelByEncoderIds(ids);
        this.bussinessFacade.getUserTreeBpo().deleteUserTreeByChannelId(channelIds.toArray(new String[0]));
        this.bussinessFacade.getUserTreeBpo().deleteUserTreeByEncoderId(ids);
        this.deviceFacade.getEncoderBpo().delete(ids);
        return findPage();
    }

    public String findPage() {
        SLSchedulerFactoryBean.runJob("EncoderBpo.findEncoderModel");
        this.setReqAttr("encoderModels", Encoder.getEncoderModelMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        deviceFacade.getEncoderBpo().find(getPageObject());
        List<ServerEncoder> encoderList = deviceFacade.getEncoderBpo().findServerEncoderAll();
        List<Encoder> encoders = deviceFacade.getEncoderBpo().findAllEncoder();
        List<JSONObject> resultList = new ArrayList<>();
        for (int i = 0; i < encoders.size(); i++) {

            for (int j = 0; j < encoderList.size(); j++) {
                if (encoders.get(i).getId().equals(encoderList.get(j).getEncoderId())) {
                    JSONObject serverEncoderMap = new JSONObject();
                    serverEncoderMap.put("id", encoders.get(i).getId());
                    serverEncoderMap.put("name", encoders.get(i).getName());
                    serverEncoderMap.put("positionZH", encoders.get(i).getPositionZH());
                    serverEncoderMap.put("modelName", encoders.get(i).getModelName());
                    serverEncoderMap.put("ip", encoders.get(i).getIp());
                    serverEncoderMap.put("port", String.valueOf(encoders.get(i).getPort()));
                    serverEncoderMap.put("username", encoders.get(i).getUsername());
                    serverEncoderMap.put("password", encoders.get(i).getPassword());
                    serverEncoderMap.put("serverName", encoderList.get(j).getServerName());
                    serverEncoderMap.put("channelCount", String.valueOf(encoders.get(i).getChannelCount()));
                    serverEncoderMap.put("inputCount", String.valueOf(encoders.get(i).getInputCount()));
                    serverEncoderMap.put("outputCount", String.valueOf(encoders.get(i).getOutputCount()));
                    serverEncoderMap.put("address", encoders.get(i).getAddress());
                    serverEncoderMap.put("enabledZh", encoders.get(i).getEnabledZh());
                    serverEncoderMap.put("statusZh", encoders.get(i).getStatusZh());
                    serverEncoderMap.put("status", encoders.get(i).getStatus());
                    serverEncoderMap.put("model", encoders.get(i).getModel());
                    resultList.add(serverEncoderMap);
                }
            }


        }

        for (int i = 0; i < encoders.size(); i++) {
            if (deviceFacade.getEncoderBpo().findByServerEncoderId(encoders.get(i).getId()).size() == 0) {
                Encoder encoder = deviceFacade.getEncoderBpo().findById(encoders.get(i).getId());
                JSONObject serverEncoderMap = new JSONObject();
                serverEncoderMap.put("id", encoder.getId());
                serverEncoderMap.put("name", encoder.getName());
                serverEncoderMap.put("positionZH", encoder.getPositionZH());
                serverEncoderMap.put("modelName", encoder.getModelName());
                serverEncoderMap.put("ip", encoder.getIp());
                serverEncoderMap.put("port", String.valueOf(encoder.getPort()));
                serverEncoderMap.put("username", encoder.getUsername());
                serverEncoderMap.put("password", encoder.getPassword());
                serverEncoderMap.put("serverName", "");
                serverEncoderMap.put("channelCount", String.valueOf(encoder.getChannelCount()));
                serverEncoderMap.put("inputCount", String.valueOf(encoder.getInputCount()));
                serverEncoderMap.put("outputCount", String.valueOf(encoder.getOutputCount()));
                serverEncoderMap.put("address", encoder.getAddress());
                serverEncoderMap.put("enabledZh", encoder.getEnabledZh());
                serverEncoderMap.put("statusZh", encoder.getStatusZh());
                serverEncoderMap.put("status", encoder.getStatus());
                serverEncoderMap.put("model", encoder.getModel());
                resultList.add(serverEncoderMap);
            }
        }

        this.setReqAttr("resultList", resultList);
        return "findPage";
    }

    public String downExcelTempl() {
        InputStream in = null;
        OutputStream out = null;
        try {
            /** 响应头 */
            ServletActionContext.getResponse().setContentType("application/octet-stream;charset=UTF-8");
            ServletActionContext.getResponse().setHeader("Content-Disposition", "attachment;filename=" + new String("编码器模版.xlsx".getBytes(), "iso8859-1"));
            in = ServletActionContext.getServletContext().getResourceAsStream("/WEB-INF/doc/ipc.xlsx");
            out = ServletActionContext.getResponse().getOutputStream();
            IOUtils.copy(in, out);
            out.flush();
            ServletActionContext.getResponse().flushBuffer();
            return null;
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (IOException e) {
            }
            try {
                if (null != out) {
                    out.close();
                }
            } catch (IOException e) {
            }
        }
        return findPage();
    }

    public String impFromExcel() {
        if (null == Server.getObjectMap(ServerType.CMU.toString()) || null == Server.getObjectMap(ServerType.MDU.toString())) {
            this.responseHTML("请先确认是否添加CMU、MDU服务器！");
            return null;
        }
        File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
        if (null == file || file.length == 0) {
            this.responseHTML("上传文件无效！");
            return null;
        }
        int i = 1;
        try {
            List<EncoderImpDto> encoderImpDtos = new ArrayList<EncoderImpDto>();
            Workbook wb = WorkbookFactory.create(file[0]);
            System.out.println("file[0].getName()=====" + file[0].getName());
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            Row row = null;
            for (; i <= rowNum; i++) {
                int j = 0;
                row = sheet.getRow(i);
                EncoderImpDto encoderImpDto = new TreeEncoderImpDto();
                encoderImpDto.setModel(getCellValue(row.getCell(j)));
                j++;
                encoderImpDto.setEncoderName(getCellValue(row.getCell(j)));
                j++;
                String position = getCellValue(row.getCell(j));
                j++;
                if (StringUtils.isNotBlank(PositionCode.getTypeName(position))) {
                    encoderImpDto.setPosition(position);
                } else if (StringUtils.isNotBlank(PositionCode.getCode(position))) {
                    encoderImpDto.setPosition(PositionCode.getCode(position));
                }
                encoderImpDto.setIp(getCellValue(row.getCell(j)));
                j++;
                if (StringUtils.isNotBlank(getCellValue(row.getCell(j)))) {
                    encoderImpDto.setPort(Integer.valueOf(getCellValue(row.getCell(j))));
                    j++;
                }
                encoderImpDto.setUsername(getCellValue(row.getCell(j)));
                j++;
                encoderImpDto.setPassword(getCellValue(row.getCell(j)));
                j++;
                encoderImpDto.setMduId(getCellValue(row.getCell(j)));
                j++;
                encoderImpDto.setMsuId(getCellValue(row.getCell(j)));
                j++;
                if (StringUtils.isNotBlank(getCellValue(row.getCell(j)))) {
                    encoderImpDto.setOutputCount(Integer.valueOf(getCellValue(row.getCell(j))));
                    j++;
                }
                encoderImpDto.setDescription(getCellValue(row.getCell(j)));
                j++;
                if (StringUtils.isNotBlank(getCellValue(row.getCell(j)))) {
                    encoderImpDto.setIndexCount(Integer.valueOf(getCellValue(row.getCell(j))));
                    j++;
                }
                encoderImpDto.setChannelName(getCellValue(row.getCell(j)));
                j++;
                encoderImpDto.setChannelType(getCellValue(row.getCell(j)));
                j++;
                if (StringUtils.isNotBlank(encoderImpDto.getPosition()))
                    encoderImpDtos.add(encoderImpDto);
            }
            Object[] handleResult = this.deviceFacade.getEncoderBpo().handleEncoderNoExistAndNew2(encoderImpDtos, encoderImpDtos);
            Collection<EncoderImpDto> newEncoderImpDtos = (Collection<EncoderImpDto>) handleResult[1];
            this.deviceFacade.getEncoderBpo().initEncoderAndGroupAndUserGroupsAndChannel2(newEncoderImpDtos, null);
//			String topParentId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
//			String rootName = this.bussinessFacade.getUserTreeBpo().findTopUserTree(topParentId);
//			String uid = this.bussinessFacade.getUserTreeBpo().findAdminUid();
//			this.bussinessFacade.getUserTreeBpo().initUserResourceTree(uid, rootName, encoderImpDtos);
            this.responseHTML("ok");
        } catch (Exception e) {
            logger.debug("导入数据失败，第" + i + "行:", e);
            this.responseHTML("导入数据失败，请查看第" + i + "行数据有误");
        }
        return null;
    }

    public String impFromExcelToUpdate() {
        if (null == Server.getObjectMap(ServerType.CMU.toString()) || null == Server.getObjectMap(ServerType.MDU.toString())) {
            this.responseHTML("请先确认是否添加CMU、MDU服务器！");
            return null;
        }
        File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
        if (null == file || file.length == 0) {
            this.responseHTML("上传文件无效！");
            return null;
        }
        int i = 1;
        try {
            List<EncoderImpDto> encoderImpDtos = new ArrayList<EncoderImpDto>();
            Workbook wb = WorkbookFactory.create(file[0]);
            Sheet sheet = wb.getSheetAt(0);
            int rowNum = sheet.getLastRowNum();
            Row row = null;
            for (; i <= rowNum; i++) {
                int j = 1;
                row = sheet.getRow(i);
                EncoderImpDto encoderImpDto = new TreeEncoderImpDto();
                encoderImpDto.setEncoderNo(getCellValue(row.getCell(j)));
                j++;
                encoderImpDto.setEncoderName(getCellValue(row.getCell(j)));
                j++;
                encoderImpDtos.add(encoderImpDto);
            }
            this.deviceFacade.getEncoderBpo().updateEncoderName(encoderImpDtos);
            this.responseHTML("ok");
        } catch (Exception e) {
            logger.debug("导入数据失败，第" + i + "行:", e);
            this.responseHTML("导入数据失败，请查看第" + i + "行数据有误");
        }
        return null;
    }

    public String getCellValue(Cell c) {
        String string = "";
        if (c == null) {
            return string;
        }
        if (c != null) {
            if (c.getCellType() == Cell.CELL_TYPE_BLANK) {
                string = "";
            } else if (c.getCellType() == Cell.CELL_TYPE_BOOLEAN) {
                string = Boolean.toString(c.getBooleanCellValue());
            } else if (c.getCellType() == Cell.CELL_TYPE_ERROR) {
                string = "";
            } else if (c.getCellType() == Cell.CELL_TYPE_FORMULA) {
                double d = c.getNumericCellValue();
                string = Double.toString(d);
                if (string.endsWith(".0")) {
                    string = Integer.toString(((Double) d).intValue());
                }
            } else if (c.getCellType() == Cell.CELL_TYPE_NUMERIC) {
                double d = c.getNumericCellValue();
                BigDecimal bd = new BigDecimal(d);
                string = bd.toPlainString();
            } else {
                string = c.getStringCellValue();
            }
        }
        return string;
    }

    public String beforeInitUserResourceTree() {
        Map cmuMap = Server.getObjectMap(ServerType.CMU.toString());
        Map mduMap = Server.getObjectMap(ServerType.MDU.toString());
        if (null == cmuMap || cmuMap.size() == 0 || null == mduMap || mduMap.size() == 0) {
            this.addActionMessage("请先确认是否添加CMU、MDU服务器");
            return "addServer";
        }
        this.setReqAttr("encoderModels", Encoder.getEncoderModelMap());
        String topParentId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
        String rootName = this.bussinessFacade.getUserTreeBpo().findTopUserTree(topParentId);
        this.setReqAttr("rootName", rootName);
        return "beforeInitUserResourceTree";
    }

//	public String initUserResourceTree(){
//		String rootName = this.getReqParam("rootName");
//		String uid = this.bussinessFacade.getUserTreeBpo().findAdminUid();
//
//		String ver = this.getReqParam("ver");
//		String model = this.getReqParam("model");
//		String username = this.getReqParam("username");
//		String password = this.getReqParam("password");
//		String port = this.getReqParam("port");
//		String outputCount = this.getReqParam("outputCount");
//
//		List<EncoderImpDto> encoderImpDtos = null,allEncoderImpDtos = null;
//		try {
//			encoderImpDtos = this.deviceFacade.getEncoderBpo().findFilaLineBus(ver);
//			allEncoderImpDtos = this.deviceFacade.getEncoderBpo().findFilaLineBus(null);
//		} catch (Exception e) {
//			this.addActionMessage("连接外部数据库异常！");
//			return "initUserResourceTree";
//		}
//		if (null != encoderImpDtos && encoderImpDtos.size() > 0 ) {
//			EncoderImpDto encoderImpDto = encoderImpDtos.get(0);
//			encoderImpDto.setModel(model);
//			encoderImpDto.setUsername(username);
//			encoderImpDto.setPassword(password);
//			if (StringUtils.isNotBlank(port)) {
//				encoderImpDto.setPort(Integer.valueOf(port));
//			}
//			if (StringUtils.isNotBlank(outputCount)) {
//				encoderImpDto.setOutputCount(Integer.valueOf(outputCount));
//			}
//			Object[] handleResult = this.deviceFacade.getEncoderBpo().handleEncoderNoExistAndNew(encoderImpDtos, allEncoderImpDtos);
//			List<String> noExistEncoderNos = (List<String>) handleResult[0];
//			Collection<EncoderImpDto> newEncoderImpDtos = (Collection<EncoderImpDto>) handleResult[1];
//			if (noExistEncoderNos.size() > 0) {
//				this.bussinessFacade.getUserTreeBpo().deleteUserTreeByEncoderId(noExistEncoderNos.toArray(new String[0]));
//				this.deviceFacade.getEncoderBpo().delete(noExistEncoderNos.toArray(new String[0]));
//			}
//			this.deviceFacade.getEncoderBpo().initEncoderAndGroupAndUserGroupsAndChannel(newEncoderImpDtos,encoderImpDto);
//			this.bussinessFacade.getUserTreeBpo().initUserResourceTree(uid, rootName, encoderImpDtos);
//		}
//		this.addActionMessage("初始化成功！");
//		return "initUserResourceTree";
//	}

    public String deleteAllPlatformRes() {
        String encoderId = this.getReqParam("id");
        List<String> channelIds = this.deviceFacade.getEncoderBpo().findChannelByEncoderIds(new String[]{encoderId});
        this.bussinessFacade.getUserTreeBpo().deleteUserTreeByChannelId(channelIds.toArray(new String[0]));
        this.deviceFacade.getEncoderBpo().deleteAllPlatformRes(encoderId);
        return beforeUpdate();
    }

    public String syncAllPlatformRes() {
        return beforeUpdate();
    }

    public String deleteInitUserTree() {
        this.deviceFacade.getEncoderBpo().deleteInitUserTree();
        return findPage();
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
        ArrayList<ArrayList<String>> fieldData = deviceFacade.getEncoderBpo().findExcelFieldData(getPageObject());
        ExcelFileGenerator excelFileGenerator = new ExcelFileGenerator(fieldName, fieldData);
        try {
            /** 获取字节输出流*/
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            /** 设置excel的响应头部信息，（内联和附件）*/
            String filename = "编码器列表导出(" + DateUtil.format("yyyyMMddHHmmss") + ")";
            /** 处理中文 */
            filename = new String(filename.getBytes("gbk"), "iso-8859-1");
            ServletActionContext.getRequest().setAttribute("name", filename);
            excelFileGenerator.expordExcel(os);
            /**从缓存区中读出字节数组 */
            byte[] bytes = os.toByteArray();
            InputStream inputStream = new ByteArrayInputStream(bytes);
            /** 将inputStream放置到模型驱动的对象中 */
            encoderDto.setInputStream(inputStream);
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
        fieldName.add("设备名称");
        fieldName.add("位置");
        fieldName.add("设备类型");
        fieldName.add("服务器IP");
        fieldName.add("是否启用");
        fieldName.add("在线状态");
        return fieldName;
    }

    /**
     * 外域设备begin
     */
    public String findPlatformPage() {
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        this.setReqAttr("platforms", Encoder.getTypeMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        PageObject object = getPageObject();
        Map<String, String> params = object.getParams();
        params.put("model", "platform");
        object.setParams(params);
        deviceFacade.getEncoderBpo().find(object);
        return "findPlatformPage";
    }

    /**
     *
     *外域设备end
     */

}
