package com.planner.model.enumeration;

import com.planner.model.enumeration.stats.CurrentTimePeriod;

import java.util.Arrays;

public enum CommonAction {
    ADD("/add", "Добавить"),
    DELETE("/del", "Удалить"),
    PURCHASES("/getspend", "Список трат"),
    STATISTIC("/stat", "Статистика");
   // MEMBERS("/members", "Участники группы");

    CommonAction(String name, String ruName) {
        this.name = name;
        this.ruName = ruName;
    }

    private String name;
    private String ruName;

    public CommonAction getByName(String commandName){
        return Arrays.stream(CommonAction.values())
                .filter(act -> act.getName().equals(commandName))
                .findFirst()
                .orElse(null);
    }

    public CurrentTimePeriod getByRuName(String ruName){
        return Arrays.stream(CurrentTimePeriod.values())
                .filter(act -> act.getRuName().toLowerCase().equals(ruName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
