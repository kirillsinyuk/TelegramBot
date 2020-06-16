package com.bot.model.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "Band")
@NoArgsConstructor
public class Band {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "BIT", length = 1)
    private boolean isSingle;

    @Column(nullable = false, name = "admin_id")
    private Integer adminId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "band")
    private Set<BotUser> bandUsers = new HashSet<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "band", cascade = CascadeType.REMOVE)
    private Set<Category> categories = new HashSet<>();

}
