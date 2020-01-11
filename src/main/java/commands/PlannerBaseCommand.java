package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import service.BotService;

public abstract class PlannerBaseCommand extends BotCommand {

    final Logger LOG = LoggerFactory.getLogger(PlannerBaseCommand.class);
    private final BotService botService;

    PlannerBaseCommand(String commandIdentifier, String description, BotService botService) {
        super(commandIdentifier, description);
        this.botService = botService;
    }

    void execute(AbsSender sender, SendMessage message, Chat chat, User user) {
        try {
            if (!botService.hasAccessToCommands(user.getId())) {
                LOG.warn("User {} has no access.", user.getId());
                message.setText("Access denied for you. Try to connect @KirillSinyuk for more info.");
            }
            sender.execute(message);
        } catch (TelegramApiException e) {
            LOG.error(user.getId() + getCommandIdentifier(), e);
        }
    }

}
