package com.alexis.javacordbot.commands;

import com.alexis.javacordbot.util.Arguments;
import com.alexis.javacordbot.util.Command;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

public class PingCommand extends Command {

    public PingCommand() {
        super("ping", "ping", "Sends a ping-pong message.");
    }

    @Override
    public void execute(MessageCreateEvent event, Arguments args) {
        event.getChannel()
                .sendMessage("Pong!")
                .exceptionally(ExceptionLogger.get());
    }
}
