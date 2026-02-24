package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass  // 이게 없으면 상속받은 컬럼들이 DB에 매핑 안 됨
public abstract class BaseEntity {

    @CreatedDate
    @Column(name = "SYS_REG_DTM", updatable = false)
    private LocalDateTime sysRegDtm;

    @LastModifiedDate
    @Column(name = "SYS_UPD_DTM")
    private LocalDateTime sysUpdDtm;

    @Column(name = "SYS_DEL_DTM")
    private LocalDateTime sysDelDtm;

    @Column(name = "SYS_DEL_YN")
    private Boolean sysDelYn = false;

    public void softDelete() {
        this.sysDelYn = true;
        this.sysDelDtm = LocalDateTime.now();
    }
}
