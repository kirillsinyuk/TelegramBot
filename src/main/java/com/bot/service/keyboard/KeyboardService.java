package com.bot.service.keyboard;

import com.bot.model.enumeration.CommonAction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
