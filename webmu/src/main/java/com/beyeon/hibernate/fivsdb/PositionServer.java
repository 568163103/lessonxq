package com.beyeon.hibernate.fivsdb;

import com.beyeon.common.web.model.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * User: Administrator
 * Date: 2015/9/8
 * Time: 11:41
 */
@Entity
@Table(name = "position_server")
public class PositionServer extends BaseEntity {
    private String serverId;
    private String positionId;

    @Id
    @Column(name = "server_id")
    public String getServerId() {
        return serverId;
    }
    public void setServerId(String serverId) {
        this.serverId = serverId;
    }
    @Id
    @Column(name = "position_id")
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
}
