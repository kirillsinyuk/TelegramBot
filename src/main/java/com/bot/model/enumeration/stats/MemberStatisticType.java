package com.bot.model.enumeration.stats;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum MemberStatisticType implements Type {
    EACH("По каждому", "each"),
    ALL("Общая", "all");

    MemberStatisticType(String ruName, String name) {
        this.ruName = ruName;
        this.name = name;
    }

    private String ruName;
    private String name;

    public MemberStatisticType getByRuName(String ruName) {
        return Arrays.stream(MemberStatisticType.values())
                .filter(dataAction -> dataAction.getRuName().toLowerCase().equals(ruName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public MemberStatisticType getByName(String name) {
        return Arrays.stream(MemberStatisticType.values())
                .filter(dataAction -> dataAction.getRuName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
