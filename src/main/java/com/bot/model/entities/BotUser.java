package com.model.entities;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;

public class BotUser {

    private Integer id;
    private Chat chat;
    private boolean hasAdminAccess;
    private User tlgUser;

    public User getTlgUser() {
        return tlgUser;
    }

    public void setTlgUser(User tlgUser) {
        this.tlgUser = tlgUser;
    }

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
