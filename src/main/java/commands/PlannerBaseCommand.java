package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public abstract class PlannerBaseCommand extends BotCommand {

    final Logger LOG = LoggerFactory.getLogger(PlannerBaseCommand.class);

    PlannerBaseCommand(String commandIdentifier, String description) {
        super(commandIdentifier, description);
    }

    void execute(AbsSender sender, SendMessage message, User user) {
        try {
            sender.execute(message);
            LOG.info(user.getId() + getCommandIdentifier());
        } catch (TelegramApiException e) {
            LOG.error(user.getId() + getCommandIdentifier(), e);
        }
    }

}
