package com.bot.model.entities;


import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

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

    @Column(name = "spended_by")
    private String spendedBy;

    @Column(name = "deleted")
    private boolean deleted;

    @Override
    public String toString() {
        return description == null ?
          String.join(" - ", Arrays.asList(data.format(DateTimeFormatter.ISO_LOCAL_DATE), spendedBy, category, price + "\n"))
                : String.join(" - ", Arrays.asList(data.format(DateTimeFormatter.ISO_LOCAL_DATE), spendedBy, category, description, price + "\n"));
    }
}
