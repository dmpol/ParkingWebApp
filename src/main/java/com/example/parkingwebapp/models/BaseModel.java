package com.example.parkingwebapp.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseModel {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreatedDate
    private Date date;
    @LastModifiedDate
    private Date lastModifiedTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseModel baseModel = (BaseModel) o;
        return id != null && Objects.equals(id, baseModel.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
    @Override
    public String toString() {
        return "BaseModel{" +
                "id=" + id +
                ", date=" + date +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }
}
