package com.bot.model.enumeration.stats;

import com.bot.model.enumeration.DataAction;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum CommonStatisticType implements Type {
    CATEGORY("По категориям", "cat"),
    DYNAMICS("Динамика", "dyn");

    CommonStatisticType(String ruName, String name) {
        this.ruName = ruName;
        this.name = name;
    }

    private String ruName;
    private String name;

    public DataAction getByRuName(String ruName){
        return Arrays.stream(DataAction.values())
                .filter(dataAction -> dataAction.getRuName().toLowerCase().equals(ruName.toLowerCase()))
                .findFirst()
                .orElse(null);
    }

    public DataAction getByName(String name){
        return Arrays.stream(DataAction.values())
                .filter(dataAction -> dataAction.getRuName().toLowerCase().equals(name.toLowerCase()))
                .findFirst()
                .orElse(null);
    }
}
