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
import service.util.ParseUtil;

public class AddUserCommand extends PlannerBaseCommand {

    private static final Logger LOG = LoggerFactory.getLogger(HelpCommand.class);
    private final BotService botService;

    public AddUserCommand(BotService botService) {
        super("userAdd", "attributes:\n &lt;id&gt; &lt;hasAdminAccess&gt; ", botService);
        this.botService = botService;
    }
    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        LOG.info("BotUser {}, id: {}, chat: {} is trying to execute '{}'.", user.getUserName(), user.getId(), chat.getId(), getCommandIdentifier());

        StringBuilder addMessage = new StringBuilder();

        if(botService.hasAdminAccess(user.getId())){
            Integer id;
            switch (arguments.length) {
                case 1:
                    id = ParseUtil.getIntFromString(arguments[0]);
                    if (id != null) {
                        botService.addUser(new BotUser(id, null, false));
                        break;
                    }
                case 2:
                    id = ParseUtil.getIntFromString(arguments[0]);
                    if (id != null) {
                        botService.addUser(new BotUser(id, null, (arguments[1].equals("true"))));
                        break;
                    }
                default:
                    addMessage.append("You need to use this format:\n /add &lt;id&gt; &lt;hasAdminAccess&gt;");
            }
        }

        SendMessage helpMessage = new SendMessage();
        helpMessage.setChatId(chat.getId().toString());
        helpMessage.enableHtml(true);
        helpMessage.setText(addMessage.toString());

        execute(absSender, helpMessage, chat, user);
    }
}
