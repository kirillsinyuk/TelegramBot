package com.planner.model.enumeration.stats;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CurrentTimePeriod implements Type {
    TODAY("Сегодня", "today"),
    WEEK("Неделя", "week"),
    FORTNIGHT("2 недели", "fortnight"),
    MONTH("Месяц", "month"),
    THREE_MONTH("3 месяца", "quarter"),
    SIX_MONTH("Полгода", "half-year"),
    ALL_TIME("Всё время", "all-time");

    CurrentTimePeriod(String ruName, String name) {
        this.ruName = ruName;
        this.name = name;
    }

    private String ruName;
    private String name;

    public CurrentTimePeriod getByRuName(String ruName){
        return Arrays.stream(CurrentTimePeriod.values())
                .filter(period -> period.getRuName().toLowerCase().equals(ruName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public CurrentTimePeriod getByName(String name){
        return Arrays.stream(CurrentTimePeriod.values())
                .filter(period -> period.getRuName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

}
