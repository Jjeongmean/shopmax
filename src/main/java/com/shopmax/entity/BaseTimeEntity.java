package com.shopmax.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(value = {AuditingEntityListener.class}) //Auditing를 적용하기(audit 기능을 사용하기 위해 작성)
@MappedSuperclass //다른 엔티티에서 부모클래스로 사용하기 위해/ 부모 클래스를 상속받는 자식 클래스한테 매핑정보를 제공하기 위해
@Getter
@Setter
public abstract class BaseTimeEntity {

    @CreatedDate //게시물을 최초로 등록한 날짜를 저장 및 감지. 엔티티가 생성되서 저장될때 시간을 자동으로 저장한다
    @Column(updatable = false) //해당 컬럼에 대한 값은 업데이트 X. 컬럼의 값을 수정하지 못하게 막음
    private LocalDateTime regTime; //등록일

    @LastModifiedDate //게시물을 수정한 날짜를 저장 및 감지(수정될때 시간을 자동으로 저장한다)
    private LocalDateTime updateTime; //수정일
}
