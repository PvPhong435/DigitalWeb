package com.webdigital.Model;

import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartID;

    @ManyToOne
    @JoinColumn(name = "userID", foreignKey = @ForeignKey(name = "FK_Cart_User"))
    private User user;

    @ManyToOne
    @JoinColumn(name = "productID", foreignKey = @ForeignKey(name = "FK_Cart_Product"))
    private Product product;

    @Column(nullable = false)
    private Integer quantity;
}