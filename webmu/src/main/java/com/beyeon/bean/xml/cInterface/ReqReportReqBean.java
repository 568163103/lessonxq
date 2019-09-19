package com.beyeon.bean.xml.cInterface;

import com.beyeon.bean.xml.common.RequestBaseBean;
import com.beyeon.common.util.JaxbUtils;

import javax.xml.bind.annotation.*;

/**
 * 类名称：ReqChangeReqBean
 * 类描述：摄像机资源变更请求Bean
 * 创建人：CP
 * 日期：2017年4月11日 下午5:36:31
 */
@XmlRootElement(name = "request")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = {"parameters"})
public class ReqReportReqBean extends RequestBaseBean {

    @XmlElement
    private Parameter parameters = new Parameter();

    public Parameter getParameters() {
        return parameters;
    }

    public void setParameters(Parameter parameters) {
        this.parameters = parameters;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(propOrder = {"saId", "totalPacketNum","curPacketNum", "index", "resources"})
    public static class Parameter {

        private String saId = "";

        private int  totalPacketNum =1;

        private int curPacketNum =1;

        private String index = "0";

        private Resources resources;

        public Parameter() {
            super();
        }

        public String getSaId() {
            return saId;
        }

        public void setSaId(String saId) {
            this.saId = saId;
        }

        public int getTotalPacketNum() {
            return totalPacketNum;
        }

        public void setTotalPacketNum(int totalPacketNum) {
            this.totalPacketNum = totalPacketNum;
        }

        public int getCurPacketNum() {
            return curPacketNum;
        }

        public void setCurPacketNum(int curPacketNum) {
            this.curPacketNum = curPacketNum;
        }

        public Resources getResources() {
            return resources;
        }

        public void setResources(Resources resources) {
            this.resources = resources;
        }

        public String getIndex() {
            Integer i = Integer.parseInt(index) + 1;
            String str = index;
            index = String.valueOf(i);
            return str;
        }

        public void setIndex() {
        }

    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType
    public static class Resources {

        private String resId;
        private String name;
        private String location;
        private String purpose;
        private String information;

        public String getResId() {
            return resId;
        }

        public void setResId(String resId) {
            this.resId = resId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getPurpose() {
            return purpose;
        }

        public void setPurpose(String purpose) {
            this.purpose = purpose;
        }

        public String getInformation() {
            return information;
        }

        public void setInformation(String information) {
            this.information = information;
        }
    }


    public static void main(String[] args) {
        ReqReportReqBean bean = new ReqReportReqBean();
        bean.setCommand("ResReport");
        ReqReportReqBean.Resources resources = new ReqReportReqBean.Resources();

        resources.setResId("1");
        resources.setName("1");
        resources.setInformation("1");
        resources.setLocation("1");
        resources.setPurpose("1");

        bean.getParameters().setResources(resources);
        bean.getParameters().setSaId("XXXXXX");
        System.out.println(JaxbUtils.marshaller(bean));
    }

}
