package com.webdigital.Model;

import java.math.BigDecimal;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "OrderDetails")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderDetailID;

    @ManyToOne
    @JoinColumn(name = "orderID", foreignKey = @ForeignKey(name = "FK_OrderDetail_Order"))
    private Order order;

    @ManyToOne
    @JoinColumn(name = "productID", foreignKey = @ForeignKey(name = "FK_OrderDetail_Product"))
    private Product product;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;
}