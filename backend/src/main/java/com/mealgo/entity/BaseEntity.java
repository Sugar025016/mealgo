package com.mealgo.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@MappedSuperclass
public abstract class BaseEntity {

    // @CreatedDate
    // @JsonIgnore
    @CreationTimestamp
    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    // @LastModifiedDate
    // @JsonIgnore
    @UpdateTimestamp
    @Column(name = "update_time")
    private LocalDateTime updateTime;

    public BaseEntity() {

        // 自定義日期時間格式
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

}
