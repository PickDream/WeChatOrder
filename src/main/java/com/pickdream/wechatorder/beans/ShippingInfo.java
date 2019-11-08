package com.pickdream.wechatorder.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@DynamicUpdate
@AllArgsConstructor
@NoArgsConstructor
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
