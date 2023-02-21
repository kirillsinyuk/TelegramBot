package com.planner.service.keyboard;

import com.planner.model.entities.*;
import com.planner.model.enumeration.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;

@Service
public class DataKeyboardService extends KeyboardService {

    public InlineKeyboardMarkup getDataKeyboard(CommonAction type, boolean isAdmin){

        return new InlineKeyboardMarkup()
                .setKeyboard(getDataButtons(type, isAdmin));
    }

    private List<List<InlineKeyboardButton>> getDataButtons(CommonAction type, boolean isAdmin) {

        return Arrays.stream(DataAction.values())
                .filter(act -> act.getActions().contains(type))
                .filter(act -> isAdmin || !act.isOnlyForAdmin())
                .map(action -> getInlineKeyboardButton(type, action))
                .map(Collections::singletonList)
                .collect(Collectors.toList());
    }

    private InlineKeyboardButton getInlineKeyboardButton(CommonAction type, DataAction dataAction) {

        return new InlineKeyboardButton()
                .setText(dataAction.getRuName())
                .setCallbackData(type.getName() + dataAction.getLabel());
    }

    public InlineKeyboardMarkup categoriesKeyboard(CommonAction commonAction, DataAction dataAction, UserGroup band){

        return new InlineKeyboardMarkup()
                .setKeyboard(getCategoriesKeyboard(commonAction, dataAction, band));
    }

    List<List<InlineKeyboardButton>> getCategoriesKeyboard(CommonAction commonAction, DataAction dataAction, UserGroup band) {
        List<Category> list = new ArrayList<>(band.getCategories());
        int rowSize = 3;

        return keyboardRowsList(commonAction, dataAction, list, rowSize);
    }

    private List<List<InlineKeyboardButton>> keyboardRowsList(CommonAction commonAction, DataAction dataAction, List<Category> list, int rowSize) {
        return IntStream.range(0, (list.size() + rowSize - 1) / rowSize)
                .mapToObj(i -> list.subList(i * rowSize, Math.min(rowSize * (i + 1), list.size())))
                .map(catList -> getButtonsRow(commonAction, dataAction, catList))
                .collect(Collectors.toList());
    }

    private List<InlineKeyboardButton> getButtonsRow(CommonAction commonAction, DataAction dataAction, List<Category> catList) {
        return catList.stream()
                .map(c -> new InlineKeyboardButton()
                        .setText(c.getName())
                        .setCallbackData(getCommand(commonAction, dataAction) + c.getName())
                )
                .collect(Collectors.toList());
    }

    private String getCommand(CommonAction commonAction, DataAction dataAction) {
        return commonAction.getName() + dataAction.getLabel() + " ";
    }
}
