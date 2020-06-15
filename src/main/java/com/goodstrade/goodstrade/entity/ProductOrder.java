package com.goodstrade.goodstrade.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

import static javax.persistence.TemporalType.TIMESTAMP;

@Getter
@Setter
@Entity
@Table(name = "order")
public class ProductOrder extends BaseEntity {

    private String detail;

    @NotNull
    private long quantity;

    private String track;

    @CreatedDate
    @Temporal(TIMESTAMP)
    private Date date;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User buyer;

    @ManyToOne(cascade = CascadeType.MERGE)
    private User seller;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Product product;

    public ProductOrder() {
    }
}
