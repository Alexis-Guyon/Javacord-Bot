package com.alexis.javacordbot.util;

import com.alexis.javacordbot.*;
import com.alexis.javacordbot.Embeds;
import com.alexis.javacordbot.commands.MuteCommand;
import com.alexis.javacordbot.commands.PingCommand;
import com.alexis.javacordbot.commands.UnmuteCommand;
import org.javacord.api.DiscordApi;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.message.MessageCreateListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements MessageCreateListener {

    private final DiscordApi api;

    List<Command> commands = new ArrayList<>();

    public CommandHandler(DiscordApi api) {
        this.api = api;
        api.addMessageCreateListener(this);

        commands.add(new PingCommand());
        commands.add(new MuteCommand());
        commands.add(new UnmuteCommand());
    }

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        if (!event.getMessageAuthor().isRegularUser()) {
            return;
        }

        String message = event.getMessageContent();

        if (message.startsWith(Constants.PREFIX)) {
            Arguments args = new Arguments(message.substring(Constants.PREFIX.length()));

            if (args.size() >= 1) {
                for (Command command : commands) {
                    if (args.get(0).equals(command.identifier)) {
                        try {
                            command.execute(event, args);
                        } catch (Exception e) {
                            e.printStackTrace();
                            api.getOwner().thenAcceptAsync(user -> {
                                String ownerName = user.getName();
                                event.getChannel()
                                        .sendMessage(Embeds.errorEmbed()
                                                .setTitle("Error")
                                                .setDescription("Please DM " + ownerName + " if this continues."))
                                        .exceptionally(ExceptionLogger.get());
                            }).exceptionally(ExceptionLogger.get());
                        }
                    }
                }
            }
        }
    }

}
