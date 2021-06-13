package fr.javabot.listener;

import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import fr.javabot.main.Main;
public class CommandPing implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if(event.getMessageContent().equalsIgnoreCase(Main.prefix + "ping")) {
            event.getChannel().sendMessage("pong!");
        }
    }
}
