package com.xq.expansion;

import com.xq.domain.Events;
import org.springframework.data.annotation.Transient;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
public class EventsExpansion extends Events {

    /**
     * 告警开始时间
     */
    @Transient
    private String begin_time;

    /**
     * 告警结束时间
     */
    @Transient
    private String end_time;

    @Transient
    private String status;

    /**
     * 服务器端口
     */
    @Transient
    private String host;

    /**
     * 告警描述
     */
    @Transient
    private String description;

    /**
     * 告警类型
     */
    @Transient
    private String comments;

    /**
     * 服务器告警类型
     */
    @Transient
    private String name;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
