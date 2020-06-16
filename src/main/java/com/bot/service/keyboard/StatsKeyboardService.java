package com.bot.service.keyboard;

import com.bot.model.menu.CommonAction;
import com.bot.model.menu.stats.Type;
import org.jfree.data.time.TimePeriod;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//TODO много шаблонного кода. подумать над рефакторингом. возможно подойдут дженерики.
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
                .setCallbackData(command + enumType.getName());
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
                .setCallbackData(command + " " + period.getName());
    }

//    public InlineKeyboardMarkup statisticCommonKeyboard(String command){
//        return new InlineKeyboardMarkup()
//                .setKeyboard(statisticTypes(command));
//    }
//
//    private List<List<InlineKeyboardButton>> statisticTypes(String command) {
//
//        return Arrays.stream(CommonStatisticType.values())
//                .map(type -> createButton(command, type))
//                .map(Collections::singletonList)
//                .collect(Collectors.toList());
//    }
//
//    private InlineKeyboardButton createButton(String command, CommonStatisticType type) {
//
//        return new InlineKeyboardButton()
//                .setText(type.getRuName())
//                .setCallbackData(command + type.getName());
//    }



//    public InlineKeyboardMarkup memberTypesKeyboard(String command){
//        return new InlineKeyboardMarkup()
//                .setKeyboard(memberTypes(command));
//    }
//
//    private List<List<InlineKeyboardButton>> memberTypes(String command) {
//
//        return Arrays.stream(MemberStatisticType.values())
//                .map(type -> createButton(command, type))
//                .map(Collections::singletonList)
//                .collect(Collectors.toList());
//    }
//
//    private InlineKeyboardButton createButton(String command, MemberStatisticType type) {
//
//        return new InlineKeyboardButton()
//                .setText(type.getRuName())
//                .setCallbackData(command + type.getName());
//    }
//
//
//
//    public InlineKeyboardMarkup timeTypesKeyboard(String command){
//        return new InlineKeyboardMarkup()
//                .setKeyboard(timeTypes(command));
//    }
//
//    private List<List<InlineKeyboardButton>> timeTypes(String command) {
//
//        return Arrays.stream(TimeStatisticType.values())
//                .map(type -> createButton(command, type))
//                .map(Collections::singletonList)
//                .collect(Collectors.toList());
//    }
//
//    private InlineKeyboardButton createButton(String command, TimeStatisticType type) {
//
//        return new InlineKeyboardButton()
//                .setText(type.getRuName())
//                .setCallbackData(command + type.getName());
//    }
//
//
//    public InlineKeyboardMarkup dataRangeKeyboard(String command) {
//
//        return new InlineKeyboardMarkup()
//                .setKeyboard(dataRangeRows(command));
//    }
//
//    private List<List<InlineKeyboardButton>> dataRangeRows(String command) {
//
//        return Arrays.stream(CurrentTimePeriod.values())
//                .map(period -> createButton(command, period))
//                .map(Collections::singletonList)
//                .collect(Collectors.toList());
//    }
//
//    private InlineKeyboardButton createButton(String command, CurrentTimePeriod period) {
//
//        return new InlineKeyboardButton()
//                .setText(period.getRuName())
//                .setCallbackData(command + period.getName());
//    }
}
