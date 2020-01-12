package commands.commonCommands;

import commands.PlannerBaseCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.BotService;

public class HelpCommand extends PlannerBaseCommand {

    private final ICommandRegistry commandRegistry;
    private static final Logger LOG = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand(ICommandRegistry commandRegistry, BotService botService) {
        super("help", "list all known commands\n", botService);
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder helpMessageBuilder = new StringBuilder();

        helpMessageBuilder.append("<b>Available commands:</b>\n");
        commandRegistry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append(cmd.toString()));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
