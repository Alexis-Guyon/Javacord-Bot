package fr.javabot.main;

import fr.javabot.listener.CommandMute;
import fr.javabot.listener.CommandPing;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class Main {
    public static String prefix = "$";

    public static void main(String[] args) {
        DiscordApi api = new DiscordApiBuilder()
                .addServerBecomesAvailableListener(event -> {
                    System.out.println("Loaded " + event.getServer().getName());
                })
                // A listener in their own class
                .addListener(new CommandPing())
                .addListener(new CommandMute())
                .setToken("ODUzNjY3MzgzMTcxNjEyNjcy.YMYttA.7SiNIrsCM5d6HrXy4hi3DHSI3KM")
                .setWaitForServersOnStartup(false)
                .login()
                .join();
        System.out.println(api.createBotInvite());
    }
}
