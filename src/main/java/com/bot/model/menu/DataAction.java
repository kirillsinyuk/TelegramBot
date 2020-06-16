package com.bot.model.menu;

import com.bot.model.menu.stats.Type;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter
public enum DataAction implements Type {
    DATA("Трату", "data", Arrays.asList(CommonAction.ADD, CommonAction.DELETE), false),
    CATEGORIES("Категорию", "cat", Arrays.asList(CommonAction.ADD, CommonAction.DELETE), true),
    USER("Пользователя", "user", Arrays.asList(CommonAction.ADD, CommonAction.DELETE), true),
    CAT_RENAME("Переименовать категорию", "cat_r", Collections.singletonList(CommonAction.ADD), true),
    DATA_DELETE("Удалить все данные", "all", Collections.singletonList(CommonAction.DELETE), false);

    DataAction(String ruName, String name, List<CommonAction> actions, boolean onlyForAdmin) {
        this.ruName = ruName;
        this.name = name;
        this.actions = actions;
        this.onlyForAdmin = onlyForAdmin;
    }

    private String ruName;
    private String name;
    private List<CommonAction> actions;
    private boolean onlyForAdmin;

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
