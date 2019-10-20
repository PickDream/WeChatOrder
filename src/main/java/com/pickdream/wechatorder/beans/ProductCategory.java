package com.pickdream.wechatorder.beans;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicUpdate
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
public class ProductCategory {

    /** 类目id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryId;

    /** 类目名字. */
    private String categoryName;

    /** 类目编号. */
    private Long categoryType;

    @Column(updatable = false)
    @CreationTimestamp
    private Date createTime;

    @UpdateTimestamp
    private Date updateTime;

}
