package com.iamzakaria.dripp.entity;


import com.iamzakaria.dripp.enums.Status;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "order_created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_address_id", referencedColumnName = "id")
    private Address shippingAddress;
    @Column(name = "phone_number")
    private String phoneNumber;
    @Column(name = "total_amount",scale = 2)
    private BigDecimal totalAmount;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "tracking_number")
    private String orderTrackingNumber;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private User user;
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<OrderItem> orderItems = new HashSet<>();
    public void add(OrderItem item) {
        if (item != null) {
            if (orderItems == null) {
                orderItems = new HashSet<>();
            }
            orderItems.add(item);
            item.setOrder(this);
        }
    }
}
