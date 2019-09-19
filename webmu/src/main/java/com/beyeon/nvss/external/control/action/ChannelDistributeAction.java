package com.beyeon.nvss.external.control.action;

import com.beyeon.bean.xml.cInterface.ReqChangeReqBean;
import com.beyeon.bean.xml.cInterface.ReqChangeResBean;
import com.beyeon.bean.xml.cInterface.ReqReportReqBean;
import com.beyeon.bean.xml.cInterface.ReqReportResBean;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.util.JaxbUtils;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.constants.CommandConstant;
import com.beyeon.hibernate.fivsdb.*;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.beyeon.nvss.external.model.dto.ChannelDistributeDto;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("channelDistributeAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class ChannelDistributeAction extends BaseAction {
    private static Logger logger = LoggerFactory.getLogger(ChannelDistributeAction.class);
    private DeviceFacade deviceFacade;
    private BussinessFacade bussinessFacade;
    private ChannelDistributeDto channelDistributeDto;

    public DeviceFacade getDeviceFacade() {
        return deviceFacade;
    }

    public void setDeviceFacade(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    public BussinessFacade getBussinessFacade() {
        return bussinessFacade;
    }

    public void setBussinessFacade(BussinessFacade bussinessFacade) {
        this.bussinessFacade = bussinessFacade;
    }

    public ChannelDistributeDto getChannelDistributeDto() {
        return channelDistributeDto;
    }

    public void setChannelDistributeDto(ChannelDistributeDto channelDistributeDto) {
        this.channelDistributeDto = channelDistributeDto;
    }

    public Object getModel() {
        if (null == channelDistributeDto) {
            String id = this.getReqParam("id");
            if (StringUtils.isNotBlank(id)) {
                channelDistributeDto = bussinessFacade.getChannelDistributeBpo().get(id);
            } else {
                channelDistributeDto = new ChannelDistributeDto();
            }
        }
        return channelDistributeDto;
    }

    public String save() {
        String[] ids = this.getReqParams("items");
        String serverId = this.getReqParam("serverId");
        bussinessFacade.getChannelDistributeBpo().delete(ids);
        String userid = SpringUtil.getCurrUser().getId();
        if (StringUtils.isNotBlank(serverId)) {
            for (int i = 0; i < ids.length; i++) {
                ChannelDistribute channelDistribute = new ChannelDistribute();
                channelDistribute.setCreateTime(DateUtil.getTime());
                channelDistribute.setChannelId(ids[i]);
                channelDistribute.setCreateUserId(userid);
                channelDistribute.setServerId(serverId);
                ReqReportResBean resBean = null;
                try {
                    PlatformRes platformRes = deviceFacade.getChannelBpo().findPlatformRes(ids[i]);
                    if (platformRes != null ) {
                        ReqReportReqBean bean = new ReqReportReqBean();
                        bean.setCommand(CommandConstant.RESREPORT);
                        ReqReportReqBean.Resources resources = new ReqReportReqBean.Resources();

                        resources.setResId(platformRes.getId());
                        resources.setName(platformRes.getName());
                        resources.setInformation("");
                        resources.setLocation(platformRes.getLocation());
                        resources.setPurpose(platformRes.getPurpose());

                        bean.getParameters().setResources(resources);
                        bean.getParameters().setSaId(serverId);

                        System.out.println(JaxbUtils.marshaller(bean));

                        StringBuilder cmuUrl = new StringBuilder();
                        cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
                                .append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
                        String result = HttpClientUtil.post(cmuUrl.toString() + "?said=" + serverId, JaxbUtils.marshaller(bean));
                        System.out.println(JaxbUtils.marshaller(bean));
                        logger.debug(result);
                        System.out.println(result);

                        resBean = JaxbUtils.unmarshal(result, ReqReportResBean.class);
                    }
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
                if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
                    this.addActionMessage("资源下发失败！");
                } else if (resBean.getResult().getResultCode().equals("0")) {
                    bussinessFacade.getChannelDistributeBpo().save(channelDistribute);
                    this.addActionMessage("资源下发成功！");
                }
            }
        }

        this.setReqAttr("serverId", serverId);
        PageObject pageObject = getPageObject();
        Map<String, String> params = pageObject.getParams();
        params.put("sid", serverId);
        pageObject.setParams(params);
        this.setReqAttr("platforms", Encoder.getTypeMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        bussinessFacade.getChannelDistributeBpo().find(pageObject);
        return "findPage";
    }

    public String delete() {
        String[] ids = this.getReqParams("items");
        String serverId = this.getReqParam("serverId");
        this.setReqAttr("serverId", serverId);
        if (StringUtils.isNotBlank(serverId)) {
            for (int i = 0; i < ids.length; i++) {
                ChannelDistribute channelDistribute = bussinessFacade.getChannelDistributeBpo().findById(Integer.parseInt(ids[i]));
                if (channelDistribute != null) {
                    ReqChangeResBean resBean = null;
                    try {
                        PlatformRes platformRes = deviceFacade.getChannelBpo().findPlatformRes(channelDistribute.getChannelId());
                        if (platformRes != null ) {
                            ReqChangeReqBean bean = new ReqChangeReqBean();
                            bean.setCommand(CommandConstant.RESCHANGE);
                            ReqChangeReqBean.Resources resources = new ReqChangeReqBean.Resources();

                            resources.setResId(platformRes.getId());
                            resources.setName(platformRes.getName());
                            resources.setInformation("");
                            resources.setLocation(platformRes.getLocation());
                            resources.setPurpose(platformRes.getPurpose());

                            bean.getParameters().setResources(resources);
                            bean.getParameters().setCmd(2);
                            bean.getParameters().setSaId(serverId);

                            System.out.println(JaxbUtils.marshaller(bean));

                            StringBuilder cmuUrl = new StringBuilder();
                            cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
                                    .append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
                            String result = HttpClientUtil.post(cmuUrl.toString() + "?said=" + serverId, JaxbUtils.marshaller(bean));
                            logger.debug(result);
                            System.out.println(result);

                            resBean = JaxbUtils.unmarshal(result, ReqChangeResBean.class);
                        }

                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }

                    if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
                        this.addActionMessage("资源删除失败！");
                    } else if (resBean.getResult().getResultCode().equals("0")) {
                        bussinessFacade.getChannelDistributeBpo().delete(ids[i]);
                        this.addActionMessage("资源删除成功！");
                    }
                }
            }
        }
        PageObject pageObject = getPageObject();
        Map<String, String> params = pageObject.getParams();
        params.put("sid", serverId);
        pageObject.setParams(params);
        this.setReqAttr("platforms", Encoder.getTypeMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        bussinessFacade.getChannelDistributeBpo().find(pageObject);
        return "findPage";
    }

    public String findPage() {
        String[] items = this.getReqParams("items");
        String serverId = this.getReqParam("serverId");
        if (items != null && items.length == 1) {
            serverId = items[0];
        }
        this.setReqAttr("serverId", serverId);
        PageObject pageObject = getPageObject();
        Map<String, String> params = pageObject.getParams();
        params.put("sid", serverId);
        pageObject.setParams(params);
        this.setReqAttr("platforms", Encoder.getTypeMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        bussinessFacade.getChannelDistributeBpo().find(pageObject);
        return "findPage";
    }

    public String findResPage() {
        String[] items = this.getReqParams("items");
        String serverId = this.getReqParam("serverId");
        if (items != null && items.length == 1) {
            serverId = items[0];
        }
        this.setReqAttr("serverId", serverId);
        PageObject pageObject = getPageObject();
        Map<String, String> params = pageObject.getParams();
        params.put("sid", serverId);
        pageObject.setParams(params);
        this.setReqAttr("platforms", Encoder.getTypeMap());
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("msus", Server.getObjectMap(ServerType.MSU.toString()));
        this.setReqAttr("mdus", Server.getObjectMap(ServerType.MDU.toString()));
        bussinessFacade.getChannelDistributeBpo().findRes(pageObject);
        return "findResPage";
    }


}
