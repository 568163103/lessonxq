package com.beyeon.nvss.bussiness.control.action;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

import com.beyeon.bean.xml.cInterface.AlarmResSubscribeReqBean;
import com.beyeon.bean.xml.cInterface.AlarmResSubscribeResBean;
import com.beyeon.common.config.ResourceUtil;
import com.beyeon.common.util.DateUtil;
import com.beyeon.common.util.ExcelFileGeneratorPage;
import com.beyeon.common.util.HttpClientUtil;
import com.beyeon.common.util.JaxbUtils;
import com.beyeon.common.web.control.action.BaseAction;
import com.beyeon.common.web.control.util.SpringUtil;
import com.beyeon.common.web.model.dto.AutoCompleteDto;
import com.beyeon.common.web.model.dto.TreeNode;
import com.beyeon.common.web.model.util.PageObject;
import com.beyeon.constants.CommandConstant;
import com.beyeon.general.baseinfo.model.bpo.DictBpo;
import com.beyeon.hibernate.fivsdb.AlarmType;
import com.beyeon.hibernate.fivsdb.Encoder;
import com.beyeon.hibernate.fivsdb.PositionCode;
import com.beyeon.hibernate.fivsdb.Server;
import com.beyeon.hibernate.fivsdb.ServerType;
import com.beyeon.hibernate.fivsdb.TDict;
import com.beyeon.hibernate.fivsdb.TbAlarmType;
import com.beyeon.hibernate.fivsdb.UserGroup;
import com.beyeon.hibernate.fivsdb.UserTree;
import com.beyeon.nvss.bussiness.model.BussinessFacade;
import com.beyeon.nvss.device.model.DeviceFacade;
import com.opensymphony.xwork2.ActionContext;

@Component("userTreeAction")
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class UserTreeAction extends BaseAction {
	private static Logger logger =LoggerFactory.getLogger(UserTreeAction.class);
	private DeviceFacade deviceFacade;
	private BussinessFacade bussinessFacade;
	private UserTree userTree;

	public UserTree getUserTree() {
		return userTree;
	}

	public void setUserTree(UserTree userTree) {
		this.userTree = userTree;
	}

	public void setDeviceFacade(DeviceFacade deviceFacade) {
		this.deviceFacade = deviceFacade;
	}

	public void setBussinessFacade(BussinessFacade bussinessFacade) {
		this.bussinessFacade = bussinessFacade;
	}
	
	public Object getModel() {
		if(null == userTree){
			userTree = new UserTree();
		}
		return userTree;
	}
	
	public String findPage(){
		this.setReqAttr("platforms",Encoder.getTypeMap());
		Map<String, String> map = this.bussinessFacade.getUserTreeBpo().findUserList();
		this.setReqAttr("users",map);
		PageObject pageObject = getPageObject();
		this.bussinessFacade.getUserTreeBpo().findPage(pageObject);
		return "findPage";
	}
	
	public String findPageForUserAlarmRes(){
		this.setReqAttr("platforms",Encoder.getTypeMap());
		Map<String, String> map = this.bussinessFacade.getUserTreeBpo().findUserList();
		this.setReqAttr("users",map);
		PageObject pageObject = getPageObject();
		this.bussinessFacade.getUserTreeBpo().findPageForUserAlarmRes(pageObject);
		return "findPageForUserAlarmRes";
	}
	public String findPageUserForAuthGroups(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.bussinessFacade.getUserTreeBpo().findUser(this.getPageObject());
		TreeNode treeNode = this.bussinessFacade.getGroupsBpo().findGroupsTreeAndSelect(null);
		this.setReqAttr("treeNode", toJSON(treeNode));
		this.setReqAttr("groupRights", DictBpo.find(TDict.USER_GROUPS_RIGHT_TYPE));
		return "findPageUserForAuthGroups";
	}
	
	public String findPageUserGroupForAuthGroups(){
		this.bussinessFacade.getUserGroupBpo().findUserGroup(this.getPageObject());
		TreeNode treeNode = this.bussinessFacade.getGroupsBpo().findGroupsTreeAndSelect(null);
		this.setReqAttr("treeNode", toJSON(treeNode));
		this.setReqAttr("groupRights", DictBpo.find(TDict.USER_GROUPS_RIGHT_TYPE));
		return "findPageUserGroupForAuthGroups";
	}

	public String findPageUserForAuthAlarmRes(){
		this.setReqAttr("positions",PositionCode.getTypeMap());
		this.bussinessFacade.getUserTreeBpo().findUser(this.getPageObject());
		TreeNode treeNode = new TreeNode();
		this.setReqAttr("treeNode", toJSON(treeNode));
		return "findPageUserForAuthAlarmRes";
	}
	
	public String beforeAuthAlarmResForUser(){
		String uid = this.getReqParam("uid");
		List<Map<String,Object>> alarmRess = bussinessFacade.getUserTreeBpo().getUserAlarmRes(uid);
		Map<String,String> alarmResMap = new HashMap<String, String>();
		for (Map<String,Object> alarmRes : alarmRess) {
			alarmResMap.put(String.valueOf((Integer)alarmRes.get("alarmResId")),(String)alarmRes.get("userId"));
		}
	    List<TreeNode>	userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(uid,"","0",alarmResMap);
		List<TreeNode>	userResourceTree1 = bussinessFacade.getUserTreeBpo().findUserDmuResourceTree(uid,"","0",alarmResMap);
		userResourceTree.addAll(userResourceTree1);
		this.responseJSON(userResourceTree);
		return null;
	}

	public String beforeAuthGroupsForUser(){
		String uid = this.getReqParam("uid");
		List<Map<String,Object>> groupRights = bussinessFacade.getUserTreeBpo().getUserGroupIds(uid);
		Map<String,Integer> groupRightMap = new HashMap<String, Integer>();
		for (Map<String,Object> groupRight : groupRights) {
			groupRightMap.put((String)groupRight.get("groupId"),(Integer)groupRight.get("groupRight"));
		}
		TreeNode treeNode = this.bussinessFacade.getGroupsBpo().findGroupsTreeAndSelect(groupRightMap);
		this.responseJSON(treeNode);
		return null;
	}
	
	public String beforeAuthGroupsForUserGroup(){
		String uid = this.getReqParam("uid");
		List<Map<String,Object>> groupRights = bussinessFacade.getUserTreeBpo().getUserGroupGroupIds(uid);
		Map<String,Integer> groupRightMap = new HashMap<String, Integer>();
		for (Map<String,Object> groupRight : groupRights) {
			groupRightMap.put((String)groupRight.get("groupId"),(Integer)groupRight.get("groupRight"));
		}
		TreeNode treeNode = this.bussinessFacade.getGroupsBpo().findGroupsTreeAndSelect(groupRightMap);
		this.responseJSON(treeNode);
		return null;
	}

	public String authGroupsForUser(){
		String uids = this.getReqParam("uids");
		String gids = this.getReqParam("gids");
		this.bussinessFacade.getUserTreeBpo().authGroupsForUser(uids, gids);
		this.responseHTML("ok");
		return null;
	}
	
	public String authGroupsForUserGroup(){
		String uids = this.getReqParam("uids");
		String gids = this.getReqParam("gids");
		this.bussinessFacade.getUserTreeBpo().authGroupsForUserGroup(uids, gids);
		this.responseHTML("ok");
		return null;
	}

	public String authAlarmResForUser(){
		String uid = this.getReqParam("uid");
		String gids = this.getReqParam("gids");
		this.bussinessFacade.getUserTreeBpo().authAlarmResForUser(uid, gids);
		this.responseHTML("ok");
		return null;
	}

	public String beforeAuthResourceForUser(){
		String uid = this.getReqParam("uid");
		String cmuId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
		List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(uid,cmuId);
		this.setReqAttr("userResourceTree", toJSON(userResourceTree));
		List<TreeNode> resourceTree = bussinessFacade.getUserTreeBpo().getUserGroupResourceTrees(uid);
		this.setReqAttr("resourceTree", toJSON(resourceTree));
		this.setReqAttr("channelValue", "120");
		this.setReqAttr("cmuId", cmuId);
		this.setReqAttr("users", bussinessFacade.getUserTreeBpo().findUser(uid));
		return "beforeAuthResourceForUser";
	}

	public String findUserResourceTree(){
		String uid = this.getReqParam("uid");
		String pid = this.getReqParam("pid");
		List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(uid,pid);
		this.responseJSON(userResourceTree);
		return null;
	}
	
	public String findResourceTree(){
		String uid = this.getReqParam("uid");
		String name = this.getReqParam("name");
		List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().getUserGroupResourceTrees(uid,name);
		this.responseJSON(userResourceTree);
		return null;
	}
	

	/**
	 * 报表导出
	 * @author
	 * @return
	 */
	public String exportList() {
		String uid = this.getReqParam("uid");
		/** 构造excel的标题数据 */
		ArrayList<String> fieldName1 = findExcelFieldName1();
		ArrayList<String> fieldName2 = findExcelFieldName2();
		/** 构造excel的数据内容 */
		ArrayList<ArrayList<String>> fieldData1 = bussinessFacade.getUserTreeBpo().findExcelFieldData1(uid);
		ArrayList<ArrayList<String>> fieldData2 = bussinessFacade.getUserTreeBpo().findExcelFieldData2(uid);
		ExcelFileGeneratorPage excelFileGenerator = new ExcelFileGeneratorPage(fieldName1,fieldName2,fieldData1,fieldData2);
		try {
			/** 获取字节输出流*/
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			/** 设置excel的响应头部信息，（内联和附件）*/
			String filename = "用户目录资源导出("+ DateUtil.format("yyyyMMddHHmmss")+ ")";
			/** 处理中文 */
			filename = new String(filename.getBytes("gbk"), "iso-8859-1");
			ServletActionContext.getRequest().setAttribute("name", filename);
			excelFileGenerator.expordExcel(os);
			/**从缓存区中读出字节数组 */
			byte[] bytes = os.toByteArray();
			InputStream inputStream = new ByteArrayInputStream(bytes);
			/** 将inputStream放置到模型驱动的对象中 */
			userTree.setInputStream(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "exportList";
	}
	
	/**
	 * EXCEL表头名称
	 * @author 
	 * @return
	 */
	public ArrayList<String> findExcelFieldName1() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("序号");
		fieldName.add("用户ID");
		fieldName.add("用户名称");
		fieldName.add("目录序号");
		fieldName.add("目录名称");
		fieldName.add("上级目录");
		return fieldName;
	}
	
	public ArrayList<String> findExcelFieldName2() {
		ArrayList<String> fieldName = new ArrayList<String>();
		fieldName.add("序号");
		fieldName.add("用户ID");
		fieldName.add("目录序号");
		fieldName.add("摄像机编码");
		fieldName.add("摄像机名称");
		return fieldName;
	}

	public String saveUserTree() {
		List list = new ArrayList();
		String[] resIds = userTree.getResId().split(",");
		String[] resNames = userTree.getName().split(",");
		for (int i= 0; i < resIds.length; i++) {
			if (i !=0 ){
				userTree.setPreviousId(userTree.getResId());
			}
			userTree.setResId(resIds[i]);userTree.setName(resNames[i]);
			this.bussinessFacade.getUserTreeBpo().saveUserTree(userTree);
			String parentId = userTree.getParentId();
			if (null == parentId) {
				parentId = "";
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setTitle(userTree.getName());
			treeNode.setIcon("");
			StringBuilder treeNodeId = new StringBuilder(userTree.getUserId());
			if (userTree.getResId().substring(6, 9).equals("120")) {
				treeNodeId.append("---").append(parentId);
				treeNode.setIcon("tree_camera_online.png");
			} else {
				treeNode.setIcon("tree_folder.png");
			}
			treeNodeId.append("---").append(userTree.getResId());
			treeNode.setKey(treeNodeId.toString());
			list.add(treeNode);
		}
		responseJSON(list,false);
		return null;
	}

	public String updateUserTree(){
		this.bussinessFacade.getUserTreeBpo().updateUserTree(userTree);
		String parentId = userTree.getParentId();if (null==parentId){parentId="";}
		StringBuilder treeNodeId = new StringBuilder(userTree.getUserId());
		if (userTree.getResId().substring(6, 9).equals("120")){
			treeNodeId.append("---").append(parentId);
		}
		treeNodeId.append("---").append(userTree.getResId());
		List list = new ArrayList();
		list.add(new TreeNode(treeNodeId.toString(),userTree.getName(),(short)0));
		responseJSON(list,true);
		return null;
	}

	public String deleteUserTree(){
		this.bussinessFacade.getUserTreeBpo().deleteUserTree(userTree);
		responseHTML("ok");
		return null;
	}

	public String moveUserTree(){
		String flag = this.getReqParam("flag");
		String[] keys = this.getReqParams("siblingIds");
		this.bussinessFacade.getUserTreeBpo().moveUserTree(flag, keys);
		responseHTML("ok");
		return null;
	}

	public String beforeCopyUserTreeToUser(){
		String uid = this.getReqParam("uid");
		List<TreeNode> userResourceTree = bussinessFacade.getUserTreeBpo().findUserResourceTree(uid);
		this.setReqAttr("userResourceTree", toJSON(userResourceTree));
		this.setReqAttr("channelValue", "120");
		this.setReqAttr("users", bussinessFacade.getUserTreeBpo().findUser(uid));
		this.setReqAttr("cmuId", Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next());
		return "beforeCopyUserTreeToUser";
	}

	public String copyUserTreeToUser(){
		String resIds = this.getReqParam("resIds");
		String[] uids = this.getReqParams("uids");
		this.bussinessFacade.getUserTreeBpo().copyUserTreeToUser(resIds, uids);
		return "copyUserTreeToUser";
	}
	
	/**
	 * 外域设备begin
	 */
	//告警资源订阅
	public String beforeSubExternalAlarmRes(){
		String[] items = this.getReqParams("items");
		String serverId = this.getReqParam("serverId");
		if(serverId!=null){
			this.setReqAttr("serverId",serverId);
		}else if(items!=null&&items.length==1){
			this.setReqAttr("serverId",items[0]);
		}
	    List<TreeNode>	userResourceTree = bussinessFacade.getUserTreeBpo().findExtenalChannelTree(this.getReqAttr("serverId").toString());
		this.setReqAttr("treeNode", toJSON(userResourceTree));
		Collection<AutoCompleteDto> types = AlarmType.getTypes();
		Iterator<AutoCompleteDto> it = types.iterator();
		List<AutoCompleteDto> list = new ArrayList<AutoCompleteDto>();
		while (it.hasNext()) {
			AutoCompleteDto i = it.next();
			String alarmType = i.getValue();
			if("1".equals(alarmType)||"3".equals(alarmType)
					||"5".equals(alarmType)||"10".equals(alarmType)
					||"12".equals(alarmType)||"16".equals(alarmType)
					||"17".equals(alarmType)||"17".equals(alarmType)
					||"19".equals(alarmType)){
				list.add(i); 
			}
		}
		this.setReqAttr("alarmTypes", list);
		return "beforeSubExternalAlarmRes";
	}
	
	public String reportSubExternalAlarmRes(){
		AlarmResSubscribeResBean resBean = null;
		try {
			String serverId = this.getReqParam("serverId");
			String ids = this.getReqParam("gids");
			String action = this.getReqParam("actiontype");
			Encoder encoder = deviceFacade.getEncoderBpo().findById(serverId);
			AlarmResSubscribeReqBean bean = new AlarmResSubscribeReqBean();
			bean.setCommand(CommandConstant.ALARMRESSUBSCRIBE);
			
			bean.getParameters().setSaId(serverId);
			bean.getParameters().setSaName(encoder.getName());
			bean.getParameters().setAction(action);
			
			AlarmResSubscribeReqBean.Group group = new AlarmResSubscribeReqBean.Group();
			
			List<AlarmResSubscribeReqBean.Url> urls = new ArrayList<AlarmResSubscribeReqBean.Url>();
			if("2".equals(action)||"3".equals(action)){
				for(String s :ids.split(",")){
					String[] str = s.split("---");
					String type = TbAlarmType.getObjectName(str[1]);
					String id = str[0]+type;
					AlarmResSubscribeReqBean.Url url = new AlarmResSubscribeReqBean.Url();
					url.setId(id);
					url.setType(type);
					urls.add(url);
				}
			}else{
				AlarmResSubscribeReqBean.Url url = new AlarmResSubscribeReqBean.Url();
				urls.add(url);
			}
			group.setUrls(urls);
			bean.getParameters().setGroup(group);
			System.out.println(JaxbUtils.marshaller(bean));
			
			StringBuilder cmuUrl = new StringBuilder();
			cmuUrl.append("http://").append(this.findServerIp(ServerType.CMU)).append(":")
					.append(ResourceUtil.getServerConf("cmu.server.port")).append("/TBMessageReq");
			String result = HttpClientUtil.post(cmuUrl.toString()+"?said="+serverId, JaxbUtils.marshaller(bean));
			logger.debug(result);
			resBean = JaxbUtils.unmarshal(result, AlarmResSubscribeResBean.class);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		
		Map<String,String> map = new HashMap<String,String>();
		if (null == resBean || !resBean.getResult().getResultCode().equals("0")) {
			map.put("code", "201");
		}else{
			map.put("code", "200");
		}
		this.responseJSON(map);
		return null;
	}
	
	/**
	 * 外域设备end
	 */
	public String findMenu(){
		String userid = this.getReqParam("userid");
		if (StringUtils.isBlank(userid)){
			userid = SpringUtil.getCurrUser().getId();
		}
		List<UserTree> list = this.bussinessFacade.getUserTreeBpo().findMenuByUserid(userid);
		this.responseJSON(list);
		return null;
	}
	
	public String updateMenu(){
		String[] ids = this.getReqParams("items");
		String userid = this.getPageObject().getParams().get("userid");
		if (StringUtils.isBlank(userid)){
			userid = SpringUtil.getCurrUser().getId();
		}
		String parentid = this.getPageObject().getParams().get("menu");
		this.bussinessFacade.getUserTreeBpo().updateMenu(ids,userid,parentid);
		return findPage();
	}
	
	public String beforeUpdateUserGroup(){
		String id = this.getReqParam("amid");
		String groupId = this.bussinessFacade.getUserTreeBpo().findUserGroupResourceByUserId(id);
		List<UserGroup> groupList = this.bussinessFacade.getUserTreeBpo().findUserGroupResource();
		this.setReqAttr("groupId",groupId);
		this.setReqAttr("amid",id);
		this.setReqAttr("groupList",groupList);
		return "beforeUpdateUserGroup";
	}
	
	public void updateUserGroup(){
		String id = this.getReqParam("amid");
		String oldId = this.getReqParam("groupId");
		String newId = this.getReqParam("groupIdd");
		System.out.println(id);
		this.bussinessFacade.getUserTreeBpo().authGroupsForUserGroup2(id, newId,oldId);
		String flag = "true";
		this.responseHTML(flag);;
	}
	
	
	public String impFromExcel(){		
		File[] file = (File[]) ActionContext.getContext().getParameters().get("fileSource");
		if (null == file || file.length == 0){
			this.responseHTML("上传文件无效！");
			return null;
		}
		int i = 1;
		int m = 1;
		try {
			List<UserTree> userMenuTree = new ArrayList<UserTree>();
			List<UserTree> userResourceTree = new ArrayList<UserTree>();
			Workbook wb = WorkbookFactory.create(file[0]);
			Sheet sheet = wb.getSheetAt(0);
			int rowNum = sheet.getLastRowNum();
			Row row = null;
			for (; i <= rowNum; i++) {
				int j = 1;
				row = sheet.getRow(i);
				UserTree menu = new UserTree();
				menu.setUserId(getCellValue(row.getCell(j)));j++;
				j++;
				menu.setResId(getCellValue(row.getCell(j)));j++;
				menu.setName(getCellValue(row.getCell(j)));j++;
				menu.setParentId(getCellValue(row.getCell(j)));
				userMenuTree.add(menu);
			}
			
			
			Sheet sheet2 = wb.getSheetAt(1);
			int rowNum2 = sheet2.getLastRowNum();
			Row row2 = null;
			for (; m <= rowNum2; m++) {
				int j = 1;
				row2 = sheet2.getRow(m);
				UserTree resource = new UserTree();
				resource.setUserId(getCellValue(row2.getCell(j)));j++;
				resource.setParentId(getCellValue(row2.getCell(j)));j++;
				resource.setResId(getCellValue(row2.getCell(j)));j++;
				resource.setName(getCellValue(row2.getCell(j)));j++;
				
				userResourceTree.add(resource);
			}
			List<UserTree> handleResult = this.bussinessFacade.getUserTreeBpo().handleWithExcelBeforeSave(userMenuTree, userResourceTree);
			this.bussinessFacade.getUserTreeBpo().handleWithExcel(handleResult);
//			String topParentId = Server.getObjectMap(ServerType.CMU.toString()).keySet().iterator().next();
//			String rootName = this.bussinessFacade.getUserTreeBpo().findTopUserTree(topParentId);
//			String uid = this.bussinessFacade.getUserTreeBpo().findAdminUid();
//			this.bussinessFacade.getUserTreeBpo().initUserResourceTree(uid, rootName, encoderImpDtos);
			this.responseHTML("ok");
		} catch (Exception e) {
			logger.debug("导入数据失败，第"+i+"行:",e);
			this.responseHTML("导入数据失败，请查看第"+i+"行数据有误");
		}
		return null;
	}
	
	
	public String getCellValue(Cell c) {
		String string = "";
		if(c == null){
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
				if(string.endsWith(".0")){
					string = Integer.toString(((Double)d).intValue());
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



	public String synUserTree(){
		String uid = this.getReqParam("uid");
		List<UserTree>	allList = this.bussinessFacade.getUserTreeBpo().sysUserTree(uid);
		responseHTML("ok");
		return null;
	}
}
