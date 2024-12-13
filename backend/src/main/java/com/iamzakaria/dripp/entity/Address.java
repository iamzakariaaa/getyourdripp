package com.iamzakaria.dripp.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    @Column(name = "line_address")
    private String lineAddress;
    @Column(name = "zip_code")
    private String zipCode;
    @OneToOne
    @PrimaryKeyJoinColumn
    private Order order;
}
