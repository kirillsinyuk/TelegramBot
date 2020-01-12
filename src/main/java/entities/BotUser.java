package entities;

import org.telegram.telegrambots.meta.api.objects.Chat;

public class BotUser {

    private Integer id;
    private Chat chat;
    private boolean hasAdminAccess;

    public BotUser(Integer id, Chat chat, boolean hasAdminAccess) {
        this.id = id;
        this.chat = chat;
        this.hasAdminAccess = hasAdminAccess;
    }

    public Integer getId() {
        return id;
    }

    public Chat getChat() {
        return chat;
    }

    public boolean hasAdminAccess() {
        return hasAdminAccess;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public void setHasAdminAccess(boolean hasAdminAccess) {
        this.hasAdminAccess = hasAdminAccess;
    }
}
