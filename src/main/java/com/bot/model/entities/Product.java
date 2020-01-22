package com.bot.model.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "category")
    private String category;

    @Column(name = "price")
    private int price;

    @Column(name = "add_data")
    private LocalDateTime data;

    @Column(name = "description")
    private String description;


}
