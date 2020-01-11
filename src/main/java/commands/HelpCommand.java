package commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.ICommandRegistry;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class HelpCommand extends PlannerBaseCommand {

    private final ICommandRegistry commandRegistry;
    private static final Logger LOG = LoggerFactory.getLogger(HelpCommand.class);

    public HelpCommand(ICommandRegistry commandRegistry) {
        super("help", "list all known commands\n");
        this.commandRegistry = commandRegistry;
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {

        LOG.info(user.getId() + " " + getCommandIdentifier() + " " + getDescription());

        StringBuilder helpMessageBuilder = new StringBuilder("<b>Available commands:</b>");

        commandRegistry.getRegisteredCommands().forEach(cmd -> helpMessageBuilder.append(cmd.toString()).append("\n"));

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(helpMessageBuilder.toString());

        execute(absSender, helpMessage, user);
    }
}
