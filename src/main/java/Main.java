import bot.Bot;
import com.sun.istack.internal.logging.Logger;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;

import java.util.logging.Level;

public class Main {

    private static final Logger LOG = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        ApiContextInitializer.init();
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(Bot.getInstance());
        } catch (TelegramApiRequestException e) {
            LOG.log(Level.SEVERE, "Ошибка регистрации бота:", e.fillInStackTrace());
        }
    }
}
