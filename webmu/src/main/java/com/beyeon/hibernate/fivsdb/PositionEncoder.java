package com.beyeon.hibernate.fivsdb;

import com.beyeon.common.web.model.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName
 * @Description
 * @Auror
 * @Date
 * @Version
 **/
@Entity
@Table(name = "position_encoder")
public class PositionEncoder extends BaseEntity {
    private String  positionId;
    private String  encoderId;
    @Id
    @Column(name = "position_id")
    public String getPositionId() {
        return positionId;
    }

    public void setPositionId(String positionId) {
        this.positionId = positionId;
    }
    @Id
    @Column(name = "encoder_id")
    public String getEncoderId() {
        return encoderId;
    }

    public void setEncoderId(String encoderId) {
        this.encoderId = encoderId;
    }
}
