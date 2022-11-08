package com.quochuy.banhangcoban.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "carts")
public class Cart extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(precision = 12, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    private Long tableId;

    public Bill toBill() {
        return new Bill()
                .setId(0L)
                .setTotalAmount(totalAmount);
    }

}
