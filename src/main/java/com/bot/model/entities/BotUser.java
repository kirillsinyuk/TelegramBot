package com.bot.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Bot_user")
@NoArgsConstructor
public class BotUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="band_id")
    private Band band;

    @Column
    private Long chatId;

    @Column
    private Integer userId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column
    private String username;

    @Column
    private String laguageCode;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean isBot;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Product> products;

}
