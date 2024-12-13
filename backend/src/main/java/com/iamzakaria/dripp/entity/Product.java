package com.iamzakaria.dripp.entity;

import com.iamzakaria.dripp.enums.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @Column(scale = 2)
    private BigDecimal price;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Lob
    @Column(name = "product_image", columnDefinition = "MEDIUMBLOB")
    private byte[] imageUrl;
    private int units;
}
