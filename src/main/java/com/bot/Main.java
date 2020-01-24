package com.bot;

import com.bot.model.Bot;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class Main {

    public static void main(String[] args) {
        ApplicationContext ctx = new AnnotationConfigApplicationContext("com.bot");
        ctx.getBean(Bot.class).registerCommands();
        //SpringApplication.run(Main.class, args);
    }
}
