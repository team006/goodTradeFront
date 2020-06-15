package com.goodstrade.goodstrade.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
public class Image extends BaseEntity {

    @NotNull
    private String path;

    public Image() {
    }
}
