package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.BotService;

public class StartCommand extends PlannerBaseCommand {

    private static final Logger LOG = LoggerFactory.getLogger(StartCommand.class);
    private final BotService botService;

    public StartCommand(BotService botService) {
        super("start", "new user connected to bot", botService);
        this.botService = botService;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        StringBuilder sb = new StringBuilder();

        SendMessage message = new SendMessage();
        message.setChatId(chat.getId().toString());

        LOG.info("User {}, id: {} is trying to execute '{}'.", user.getUserName(), user.getId(), getCommandIdentifier());
        sb.append("Hi, ").append(user.getUserName()).append("! You've been added to bot users' list!\n")
                .append("Please enter '/help' for more information.");

        message.setText(sb.toString());
        execute(absSender, message, chat, user);
    }
}
