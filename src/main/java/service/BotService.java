package service;

import java.util.Arrays;
import java.util.List;

public class BotService {

    private List<Integer> accessUserList;

    public BotService(){
        accessUserList = Arrays.asList(728739455);
    }

    public boolean hasAccessToCommands(int a) {
        return accessUserList.contains(a);
    }

    public void addUser(int id){
        accessUserList.add(id);
    }
}