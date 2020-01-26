package com.bot.commands.commonCommands;

import com.bot.commands.PlannerBaseCommand;
import com.bot.repositories.ProductRepository;
import com.bot.service.util.ParseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

@Component
public class DeleteCommand extends PlannerBaseCommand {

    @Autowired
    ProductRepository productRepository;

    public DeleteCommand() {
        super("delete", "attributes:\n &lt;category&gt; &lt;price&gt; ");
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder addMessage = new StringBuilder();

        if(botService.hasAccessToCommands(user.getId())){
            if (arguments.length < 2) {
                addMessage.append("You need to use this format:\n /add &lt;category&gt; &lt;price&gt;");
            } else {
                addMessage.append(String.format("Purchase %s with price %s successfully deleted.", arguments[0], arguments[1]));
                productRepository.deleteAllByCategoryAndPrice(arguments[0], ParseUtil.getIntFromString(arguments[1]));
            }
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(addMessage.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
