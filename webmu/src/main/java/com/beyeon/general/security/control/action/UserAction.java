package com.beyeon.general.security.control.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.beyeon.nvss.device.model.DeviceFacade;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.beyeon.bean.xml.cInterface.GetUserCurStateReqBean;
import com.beyeon.bean.xml.cInterface.GetUserCurStateResBean;
import com.beyeon.bean.xml.cInterface.GetUserCurStateResBean.Url;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.util.JaxbUtils;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.constants.CommandConstant;
import com.beyeon.general.baseinfo.model.bpo.CorpBpo;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.general.security.model.SecurityFacade;
import com.beyeon.general.security.model.dto.UserDto;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TPrivData;
import com.beyeon.hibernate.fivsdb.TRole;
import com.beyeon.hibernate.fivsdb.User;
import com.beyeon.security.util.SystemSnUtils;

import javax.persistence.Id;

@Component("userAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserAction extends BaseAction {
    private static final long serialVersionUID = 7058627953498672317L;
    private static Logger logger = LoggerFactory.getLogger(UserAction.class);
    private SecurityFacade securityFacade;
    private DeviceFacade deviceFacade;
    protected UserDto userDto;

    public void setSecurityFacade(SecurityFacade securityFacade) {
        this.securityFacade = securityFacade;
    }

    public void setDeviceFacade(DeviceFacade deviceFacade) {
        this.deviceFacade = deviceFacade;
    }

    public UserDto getUserDto() {
        return userDto;
    }

    public void setUserDto(UserDto userDto) {
        this.userDto = userDto;
    }

    public Object getModel() {
        if (null == this.userDto) {
            String amid = this.getReqParam("amid");
            if (StringUtils.isNotBlank(amid)) {
                this.userDto = securityFacade.getUserBpo().get(amid);
            } else {
                this.userDto = new UserDto();
            }
        }
        return this.userDto;
    }

    public String beforeUpdate() {
//		this.setReqAttr("ptzLevels", DictBpo.find(TDict.USER_PTZ_LEVEL));
        this.setReqAttr("avLevels", DictBpo.find(TDict.USER_AV_LEVEL));
        this.setReqAttr("tbDepts", DictBpo.find(TDict.TB_DEPARTMENT));
        if (this.getCurrentUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).size() == 0) {
            this.setReqAttr("corps", CorpBpo.getCorps());
        }
        this.beforeAuthForUser();
        return "beforeUpdate";
    }

    public String beforeSave() {
//		this.setReqAttr("ptzLevels", DictBpo.find(TDict.USER_PTZ_LEVEL));
        this.setReqAttr("avLevels", DictBpo.find(TDict.USER_AV_LEVEL));
        this.setReqAttr("tbDepts", DictBpo.find(TDict.TB_DEPARTMENT));
        this.setReqAttr("tbUtypes", DictBpo.find(TDict.TB_USER_TYPE));
        this.setReqAttr("positions", PositionCode.getTypeMap());
        this.setReqAttr("roles", securityFacade.getUserBpo().getRoles());
        if (this.getCurrentUser().getPrivDatas(TPrivData.PRIV_TYPE_CORP).size() == 0) {
            this.setReqAttr("corps", CorpBpo.getCorps());
        }
        return "beforeSave";
    }

    public String update() {
        if (!"abc222".equals(this.userDto.getUserInfo().getPassword()))
            this.userDto.getUserInfo().setPasswd(this.userDto.getUserInfo().getPassword());
        this.securityFacade.getUserBpo().update(this.userDto);
        return this.authForUserUP();
    }

    public String save() {
        if (SystemSnUtils.getSystemSn().getUserNum() <= this.securityFacade.getUserBpo().findUserNum()) {
            this.addActionMessage("超出许可最大用户数！");
            return findPage();
        }
        this.securityFacade.getUserBpo().save(this.userDto);
        return this.authForUserUP();
    }

    public String delete() {
        String[] usernames = this.getReqParams("items");
        securityFacade.getUserBpo().delete(usernames);
        return findPage();
    }

    public String findPage() {
        this.setReqAttr("positions", PositionCode.getTypeMap());
        securityFacade.getUserBpo().find(this.getPageObject());
        return "findPage";
    }

    public String updateProStatus() {
        String userInfoId = this.getReqParam("userInfo_id");
        int prohibited_status = Integer.parseInt(this.getReqParam("user_userInfo.prohibited_status"));
        StringBuilder cmuUrl = new StringBuilder();
        cmuUrl.append("http://").append(deviceFacade.getServerBpo().findServerIp(ServerType.CMU)).append(":")
                .append(ResourceUtil.getServerConf("cmu.server.port")).append("/UsbManageAction");
        String result = HttpClientUtil.post(cmuUrl.toString(), "{\"serverId\":\""+userInfoId+"\",\"prohibited_status\":\""+prohibited_status+"\"}");
        logger.debug(result);
        securityFacade.getUserBpo().updateProStatus(userInfoId, prohibited_status);
        return findPage();
    }

    public String getProStatus() {
        JSONObject proJson = new JSONObject();
        String userInfoId = this.getReqParam("userInfo_id");
        int proStatus = Integer.parseInt(this.getReqParam("user_userInfo.prohibited_status"));

        proJson.put("userInfoId", userInfoId);
        proJson.put("proStatus", proStatus);
        this.responseJSON(proJson);

        return null;
    }

    public String beforeAuthForUser() {
        String amid = this.getReqParam("amid");
        List<TRole> roles = securityFacade.getUserBpo().getRoles();
        List<Integer> userRoleIds = securityFacade.getUserBpo().getUserRoleIds(amid);
        this.setReqAttr("roles", roles);
        this.setReqAttr("userRoleIds", "[\"" + StringUtils.join(userRoleIds, "\",\"") + "\"]");
        return "beforeAuthForUser";
    }

    public String authForUserUP() {
        String[] rids = this.getReqParams("rids");
        String amid = this.getReqParam("amid");
        if (StringUtils.isBlank(amid)) {
            if (this.userDto != null && this.userDto.getUserInfo() != null) {
                amid = this.userDto.getUserInfo().getId();
            }
        }
        this.securityFacade.getUserBpo().authForUser(amid, rids);
        return findPage();
    }

    public String findByDeptId() {
        String deptId = this.getReqParam("deptId");
        List users = this.securityFacade.getUserBpo().findByDeptId(deptId);
        this.responseJSON(users);
        return null;
    }

    public void unfreezeUser() throws Exception {
        String flag = "false";
        try {
            String username = this.getReqParam("username");
            this.securityFacade.getUserBpo().unfreezeUser(username);
            flag = "true";
        } catch (Exception e) {
            logger.error("刷新网络视频监控系统私钥失败", e);
        } finally {
            this.responseHTML(flag);
        }
    }

    public String checkPsw() {
        Map<String, String> map = new HashMap<String, String>();
        String password = this.getReqParam("password");
        User userInfo = this.securityFacade.getUserBpo().getUserByUsername(this.getCurrentUser().getUsername());
        if (!userInfo.getPasswd().equals(password)) {
            map.put("code", "201");
        } else {
            map.put("code", "200");
        }
        this.responseJSON(map);
        return null;
    }

    /**
     * 外域用户begin
     */
    public String findExternalPage() {
        String[] items = this.getReqParams("items");
        String serverId = this.getReqParam("serverId");
        if (serverId != null) {
            this.setReqAttr("serverId", serverId);
        } else if (items != null && items.length == 1) {
            this.setReqAttr("serverId", items[0]);
        }
        PageObject pageObject = this.getPageObject();
        Map<String, String> params = new HashMap<String, String>();
        params.put("sid", this.getReqAttr("serverId").toString());
        pageObject.setParams(params);
        securityFacade.getUserBpo().findExternal(pageObject);
        return "findExternalPage";
    }

    public String deleteExternalUser() {
        String[] usernames = this.getReqParams("items");
        securityFacade.getUserBpo().deleteExternalUser(usernames);
        return findExternalPage();
    }

    public String getUserCurState() {
        GetUserCurStateResBean resBean = null;
        try {
            String serverId = this.getReqParam("serverId");
            String ids = this.getReqParam("item");

            GetUserCurStateReqBean bean = new GetUserCurStateReqBean();
            bean.setCommand(CommandConstant.GETUSERCURSTATE);

            bean.getParameters().setSaId(serverId);
            bean.getParameters().setCurUserId(ids);

            System.out.println(JaxbUtils.marshaller(bean));

            StringBuilder cmuUrl = new StringBuilder();
            cmuUrl.append("http://").append(this.findServerIp(ServerType.CMU)).append(":")
                    .append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
            String result = HttpClientUtil.post(cmuUrl.toString() + "?said=" + serverId, JaxbUtils.marshaller(bean));
            logger.debug(result);
            resBean = JaxbUtils.unmarshal(result, GetUserCurStateResBean.class);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        Map<String, String> map = new HashMap<String, String>();
        if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
            map.put("code", "201");
        } else {
            map.put("code", "200");
            String userstate = resBean.getParameters().getUserState();
            String msg = "";
            if ("1".equals(userstate)) {
                msg = "用户当前已离线";
            } else {
                msg = "用户当前在线\n";
                String ip = resBean.getParameters().getUserIp();
                if (StringUtils.isNoneBlank(ip)) {
                    msg += "用户当前登录ip：" + ip + "\n";
                }
                List<Url> urls = resBean.getParameters().getGroup().getUrls();
                if (urls != null) {
                    for (int i = 0; i < urls.size(); i++) {
                        Url url = urls.get(i);
                        msg += "正在浏览:" + url.getCamId() + "|" + url.getCamName() + "\n";
                    }
                }
            }
            map.put("data", msg);
        }
        this.responseJSON(map);
        return null;
    }

    /**
     * 外域用户end
     *
     */
}
