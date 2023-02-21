package com.planner.model.enumeration.stats;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum PastTimePeriod implements Type {
    PAST_WEEK("Прошлая неделя", "week"),
    PAST_FORTNIGHT("Прошлые 2 недели", "fortnight"),
    PAST_MONTH("Прошлый месяц", "month"),
    PAST_THREE_MONTH("Прошлые 3 месяца", "quarter");

    PastTimePeriod(String ruName, String name) {
        this.ruName = ruName;
        this.name = name;
    }

    private String ruName;
    private String name;

    public PastTimePeriod getByRuName(String ruName){
        return Arrays.stream(PastTimePeriod.values())
                .filter(period -> period.getRuName().toLowerCase().equals(ruName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public PastTimePeriod getByName(String name){
        return Arrays.stream(PastTimePeriod.values())
                .filter(period -> period.getRuName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

}
