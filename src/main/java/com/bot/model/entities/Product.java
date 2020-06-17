package com.bot.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

@Entity
@Getter
@Setter
@Table(name = "product")
@NoArgsConstructor
public class Product {

    public final static int MAX_COMMENT_LENGTH = 64;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable=false)
    private Category category;

    @Column(name = "price")
    private int price;

    @Column(name = "add_data")
    private LocalDateTime data;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable=false)
    private BotUser user;

    @Column(name = "deleted")
    private boolean deleted;

    public Product(Category category, int price, LocalDateTime data, String description, BotUser user) {
        this.category = category;
        this.price = price;
        this.data = data;
        this.description = description;
        this.user = user;
    }

    @Override
    public String toString() {
        return description == null ?
          String.join(" - ", Arrays.asList(data.format(DateTimeFormatter.ISO_LOCAL_DATE), user.getUsername(), category.getName(), price + "\n"))
                : String.join(" - ", Arrays.asList(data.format(DateTimeFormatter.ISO_LOCAL_DATE), user.getUsername(), category.getName(), description, price + "\n"));
    }
}
