package bot;


import commands.HelpCommand;
import commands.StartCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.objects.Update;
import service.BotService;

public class Bot extends TelegramLongPollingCommandBot {

    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    // имя бота, которое мы указали при создании аккаунта у BotFather
    // и токен, который получили в результате
    private static final String BOT_NAME = "budget_planner_bot";
    private static final String BOT_TOKEN = "991064577:AAHtI9JsNg8mgR5IRNiveJMLxN9JpZMw4v8";
    private static Bot bot;
    private final BotService botService;

    public static Bot getInstance(DefaultBotOptions botOptions) {
        if (bot == null) {
            bot = new Bot(botOptions);
        }
        return bot;
    }

    private Bot(DefaultBotOptions botOptions) {
        super(botOptions);

        botService = new BotService();

        LOG.info("Initializing Planner Bot...");
        register(new HelpCommand(this, botService));
        LOG.info("/help command initializing...");
        register(new StartCommand(botService));
        LOG.info("/start command initializing...");

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
