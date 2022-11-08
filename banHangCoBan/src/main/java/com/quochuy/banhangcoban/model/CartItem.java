package com.quochuy.banhangcoban.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "cart_items")
@Accessors(chain = true)
public class CartItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_price", precision = 12, scale = 0, nullable = false)
    private BigDecimal productPrice;

    private Long quantity;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_id", referencedColumnName = "id", nullable = false)
    private Cart cart;

    public BillDetail toBillDetail(Bill bill) {
        return new BillDetail()
                .setId(id)
                .setProductName(productName)
                .setProductPrice(productPrice)
                .setQuantity(quantity)
                .setAmount(amount)
                .setBill(bill);
    }
}
