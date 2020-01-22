package com.bot.service;

import com.bot.model.entities.BotUser;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BotService {

    private List<BotUser> accessBotUserList;
    private BotUser botAdminUser = new BotUser(728739455, null, true);

    public BotService() {
        accessBotUserList = new ArrayList<>();
        accessBotUserList.add(botAdminUser);
    }

    public boolean hasAccessToCommands(int id) {
        return getUserById(id) != null;
    }

    public boolean hasAdminAccess(int id) {
        return hasAccessToCommands(id) && getUserById(id).hasAdminAccess();
    }

    public void addUser(BotUser botUser){
        accessBotUserList.add(botUser);
    }

    public BotUser getUserById(int id){
        return accessBotUserList.stream().filter(x -> x.getId() == id).findFirst().orElse(null);
    }

    public BotUser getAdminUser(){
        return botAdminUser;
    }

    public List<BotUser> getAccessBotUserList() {
        return accessBotUserList;
    }
}