import bot.Bot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.ApiContext;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Bot.class);

    private static final String PROXY_HOST = "181.101.22.239";
    private static final int PROXY_PORT =1080;


    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {

            LOG.info("Configuring bot options...");
            DefaultBotOptions botOptions = ApiContext.getInstance(DefaultBotOptions.class);

            botOptions.setProxyHost(PROXY_HOST);
            botOptions.setProxyPort(PROXY_PORT);
            botOptions.setProxyType(DefaultBotOptions.ProxyType.SOCKS4);

            telegramBotsApi.registerBot(Bot.getInstance(botOptions));
        } catch (TelegramApiRequestException e) {
            LOG.error( "Ошибка регистрации бота:", e.fillInStackTrace());
        }
    }
}
