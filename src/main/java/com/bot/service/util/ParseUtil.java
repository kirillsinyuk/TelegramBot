package com.bot.service.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseUtil {

    private ParseUtil(){}

    public static Integer getIntFromString(String s){
        List<Integer> numbers = new ArrayList<>(2);
        Pattern p = Pattern.compile("\\b^[0-9]+\\b$");
        Matcher m = p.matcher(s);
        while (m.find()) {
            numbers.add(Integer.parseInt(m.group()));
        }
        return numbers.size() != 0 ? numbers.get(0) : null;
    }

    public static LocalDateTime getLocalDateTimeFromString(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return LocalDateTime.parse(date + " 00:00", formatter);
    }
}
