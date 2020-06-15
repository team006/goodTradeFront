package com.goodstrade.goodstrade.entity;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue
    private Long id;
}