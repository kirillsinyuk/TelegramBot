package com.bot.model.menu;

import com.bot.model.menu.stats.CurrentTimePeriod;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CommonAction {
    ADD("/add", "Добавить"),
    DELETE("/del", "Удалить"),
    PURCHASES("/data", "Список трат"),
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
