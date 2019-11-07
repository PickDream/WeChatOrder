package com.pickdream.wechatorder.beans;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@Entity
@DynamicUpdate
public class ShippingInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String tag;

    private String address;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createDate;

    @UpdateTimestamp
    private Date updateDate;
}
