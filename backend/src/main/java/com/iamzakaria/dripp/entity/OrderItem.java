package com.iamzakaria.dripp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_items")
public class OrderItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "product_id")
    private Long productId;
    private int quantity;
    @Column(scale = 2)
    private BigDecimal totalPrice;
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
