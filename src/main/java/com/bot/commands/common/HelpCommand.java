package com.bot.commands.common;

import com.bot.commands.PlannerBaseCommand;
import com.bot.service.keyboard.KeyboardService;
import com.bot.service.util.CalculateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class HelpCommand extends PlannerBaseCommand {

    @Autowired
    private KeyboardService keyboardService;

    private static final List<String> MESSAGES =
            Arrays.asList(
                    "Здесь могла бы быть ваша реклама С:",
                    "Я напишу своего бота! С блэкэжеком и багами.",
                    "НЛО прилетело и оставило тут это сообщение.",
                    "Астрологи объявили неделю дедлайнов.",
                    "Дописал наконец-то бота. Работает без багов, код идеальный. И тут я проснулся.",
                    "Не понимаю, почему я написал этого бота таким кривым образом. Наверняка на это была причина, просто я о ней был.",
                    "Все предложения, замечания и баги направляйте в адрес @KirillSinyuk. Заранее благодарен.",
                    "Работает? Не ожидал.",
                    "Любимый вид танца: с бубном.",
                    "00101110001011010010110100110011",
                    "Если нашёл баг - приглядись. Возможно это фича. Но в любом случае напиши @KirillSinyuk об этом.",
                    "Зачем ты вообще вызываешь эту команду? Всё же интуитивно понятно! Не согласен? Напиши @KirillSinyuk.",
                    "Судя по отступам, Маяковский писал на одной из ранних версий Python.",
                    "Ненавижу, когда мои программы не работают. А когда они работают, пытаюсь их улучшить и начинаю ненавидеть.",
                    "Я просто хотел узнать, куда пропадают деньги с моей карты. И тут понеслась...",
                    "В следуюшей версии будет функция раздачи денег(чтобы было что тратить и вносить эти траты в бота). Но это не точно.",
                    "В моих программах нет багов. Есть лишь неожиданные фичи. Если найдёшь такую, напиши о ней @KirillSinyuk."
            );

    public HelpCommand() {
        super("help", "Список доступных команд.");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        log.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        //TODO написать что-нибудь вразумительное
        String msg = MESSAGES.get(CalculateUtils.getRandomInt(MESSAGES.size()));

        sendMsg(absSender, user, chat, msg, keyboardService.basicKeyboardMarkup());

    }



}
