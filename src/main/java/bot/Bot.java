package bot;


import commands.HelpCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    // имя бота, которое мы указали при создании аккаунта у BotFather
    // и токен, который получили в результате
    private static final String BOT_NAME = "budget_planner_bot";
    private static final String BOT_TOKEN = "991064577:AAHtI9JsNg8mgR5IRNiveJMLxN9JpZMw4v8";
    private static Bot bot;

    public static Bot getInstance(DefaultBotOptions botOptions) {
        if (bot == null) {
            bot = new Bot(botOptions);
        }
        return bot;
    }

    private Bot(DefaultBotOptions botOptions) {
        super(botOptions);

        LOG.info("Initializing Planner Bot...");
        register(new HelpCommand(this));
        LOG.info("/help command initializing...");

    }


    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    public String getBotUsername() {
        return BOT_NAME;
    }


    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void processNonCommandUpdate(Update update) {

    }

}
