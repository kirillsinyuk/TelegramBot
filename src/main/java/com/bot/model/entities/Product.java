package com.bot.model.entities;


import com.bot.model.Category;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Entity
@Data
@Table(name = "product")
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

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

    public Product(Category category, int price, LocalDateTime data, String description, String spendedBy) {
        this.category = category;
        this.price = price;
        this.data = data;
        this.description = description;
        this.spendedBy = spendedBy;
    }

    @Override
    public String toString() {
        return description == null ?
          String.join(" - ", Arrays.asList(data.format(DateTimeFormatter.ISO_LOCAL_DATE), spendedBy, Category.getNameByCategory(category), price + "\n"))
                : String.join(" - ", Arrays.asList(data.format(DateTimeFormatter.ISO_LOCAL_DATE), spendedBy, Category.getNameByCategory(category), description, price + "\n"));
    }
}
