package com.bot.model.enumeration.stats;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum TimeStatisticType implements Type {
    PAST("За прошедший период", "past"),
    NOW("За текущий период", "now");

    TimeStatisticType(String ruName, String name) {
        this.ruName = ruName;
        this.name = name;
    }

    private String ruName;
    private String name;

    public TimeStatisticType getByRuName(String ruName) {
        return Arrays.stream(TimeStatisticType.values())
                .filter(dataAction -> dataAction.getRuName().toLowerCase().equals(ruName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public TimeStatisticType getByName(String name) {
        return Arrays.stream(TimeStatisticType.values())
                .filter(dataAction -> dataAction.getRuName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
