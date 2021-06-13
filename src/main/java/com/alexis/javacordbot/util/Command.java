package com.alexis.javacordbot.util;

import org.javacord.api.event.message.MessageCreateEvent;

public abstract class Command {

    public final String identifier;
    public final String syntax;
    public final String description;

    public Command(String identifier, String syntax, String description) {
        this.identifier = identifier;
        this.syntax = syntax;
        this.description = description;
    }

    public abstract void execute(MessageCreateEvent event, Arguments args);


}
