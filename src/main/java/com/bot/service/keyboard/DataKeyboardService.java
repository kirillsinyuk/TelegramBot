package com.bot.service.keyboard;

import com.bot.model.entities.Category;
import com.bot.model.entities.Band;
import com.bot.model.menu.CommonAction;
import com.bot.model.menu.DataAction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
                .setCallbackData(type.getName() + dataAction.getName());
    }

    @Transactional
    public InlineKeyboardMarkup categoriesKeyboard(CommonAction commonAction, DataAction dataAction, Band band){

        return new InlineKeyboardMarkup()
                .setKeyboard(getCategoriesKeyboard(commonAction, dataAction, band));
    }

    @Transactional
    List<List<InlineKeyboardButton>> getCategoriesKeyboard(CommonAction commonAction, DataAction dataAction, Band band) {
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
        return commonAction.getName() + dataAction.getName() + " ";
    }
}
