package commands.adminCommands;

import commands.commonCommands.HelpCommand;
import commands.PlannerBaseCommand;
import entities.BotUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import service.BotService;

public class AddUserCommand extends PlannerBaseCommand {

    private static final Logger LOG = LoggerFactory.getLogger(HelpCommand.class);
    private final BotService botService;

    public AddUserCommand(BotService botService) {
        super("addUser", "attributes:\n <id> <hasAdminAccess> ", botService);
        this.botService = botService;
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder addMessage = new StringBuilder();

        if(botService.hasAdminAccess(user.getId())){
            String[] id;
            switch (arguments.length) {
                case 2:
                    id = arguments[0].split("\\b^[0-9]+\\b$");
                    if (id.length == 1) {
                        botService.addUser(new BotUser(Integer.parseInt(id[0]), null, (arguments[1].equals("true"))));
                    }
                    break;
                case 1:
                    id = arguments[0].split("\\b^[0-9]+\\b$");
                    if (id.length == 1) {
                        botService.addUser(new BotUser(Integer.parseInt(id[0]), null, false));
                    }
                    break;
                default:
                    addMessage.append("You need to use this format: '/add <id> <hasAdminAccess>'");
            }
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(addMessage.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
