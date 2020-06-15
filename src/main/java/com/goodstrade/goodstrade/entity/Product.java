package com.goodstrade.goodstrade.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
public class Product extends BaseEntity {

    @NotNull
    private String name;

    @NotNull
    private String detail;

    @NotNull
    private long quantity;

    private Date startDate;
    private Date endDate;

    @ManyToOne
    private User seller;

    @OneToMany
    private List<Image> image;


    @Transient
    private String startDateStr;

    @Transient
    private String endDateStr;
    @Transient
    private String ImageStr;

    public Product() {
    }
}