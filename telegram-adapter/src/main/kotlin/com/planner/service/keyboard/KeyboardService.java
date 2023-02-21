package com.planner.service.keyboard;

import com.planner.model.enumeration.*;
import java.util.*;
import java.util.stream.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.*;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.*;
import org.telegram.telegrambots.meta.bots.*;
import org.telegram.telegrambots.meta.exceptions.*;

@Service
@Slf4j
public class KeyboardService {

    public InlineKeyboardMarkup basicKeyboardMarkup(){

        return new InlineKeyboardMarkup()
                .setKeyboard(getDataRangeButtons());
    }

    private List<List<InlineKeyboardButton>> getDataRangeButtons() {

        return Arrays.stream(CommonAction.values())
                .map(this::getInlineKeyboardButton)
                .map(Collections::singletonList)
                .collect(Collectors.toList());
    }


    private InlineKeyboardButton getInlineKeyboardButton(CommonAction commonAction) {

        return new InlineKeyboardButton()
                .setText(commonAction.getRuName())
                .setCallbackData(commonAction.getName());
    }

    public void hideKeyboard(AbsSender absSender, Update update){
        try {
            absSender.execute(
                    editPreviousReplyMarkup(update));
        } catch (TelegramApiException e) {
            log.error("Error while trying hide keyboard for user " + update.getCallbackQuery().getFrom().getId(), e);
        }
    }

    private EditMessageReplyMarkup editPreviousReplyMarkup(Update update) {

        return new EditMessageReplyMarkup()
                .setReplyMarkup(null)
                .setChatId(getChatId(update))
                .setInlineMessageId(getInlineMessageId(update))
                .setMessageId(getMessageId(update));
    }

    private Integer getMessageId(Update update) {
        return update.getCallbackQuery().getMessage().getMessageId();
    }

    private String getInlineMessageId(Update update) {
        return update.getCallbackQuery().getInlineMessageId();
    }

    private Long getChatId(Update update) {
        return update.getCallbackQuery().getMessage().getChatId();
    }
}
