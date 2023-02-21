package com.planner.service.keyboard;

import com.planner.model.enumeration.stats.*;
import java.util.*;
import java.util.stream.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;


@Service
public class StatsKeyboardService<T extends Type> extends KeyboardService {

    public InlineKeyboardMarkup getKeyboard(Class<T> enumType, String command){

        return new InlineKeyboardMarkup()
                .setKeyboard(getRows(enumType, command));
    }

    private List<List<InlineKeyboardButton>> getRows(Class<T> enumType, String command) {

        return Arrays.stream(enumValues(enumType))
                .map(type -> createButton(command, type))
                .map(Collections::singletonList)
                .collect(Collectors.toList());
    }

    private InlineKeyboardButton createButton(String command, T enumType) {

        return new InlineKeyboardButton()
                .setText(enumType.getRuName())
                .setCallbackData(command + enumType.getLabel());
    }

    private T[] enumValues(Class<T> enumType) {
        return enumType.getEnumConstants();
    }


    public InlineKeyboardMarkup getDataRangeKeyboard(Class<T> enumType, String command){

        return new InlineKeyboardMarkup()
                .setKeyboard(getDataRangeButtons(enumType, command));
    }

    private List<List<InlineKeyboardButton>> getDataRangeButtons(Class<T> enumType, String command) {

        return Arrays.stream(enumValues(enumType))
                .map(period -> getInlineKeyboardButton(command, period))
                .map(Collections::singletonList)
                .collect(Collectors.toList());
    }

    private InlineKeyboardButton getInlineKeyboardButton(String command, T period) {

        return new InlineKeyboardButton()
                .setText(period.getRuName())
                .setCallbackData(command + " " + period.getLabel());
    }
}
