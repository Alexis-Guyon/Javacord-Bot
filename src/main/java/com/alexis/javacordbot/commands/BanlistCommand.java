package com.alexis.javacordbot.commands;

import com.alexis.javacordbot.Embeds;
import com.alexis.javacordbot.util.Arguments;
import com.alexis.javacordbot.util.Command;
import org.javacord.api.entity.DiscordEntity;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.server.Ban;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class BanlistCommand extends Command {
    public BanlistCommand() {
        super("banlist", "syntax", "description");
    }

    @Override
    public void execute(MessageCreateEvent event, Arguments args) {
        event.getServer().flatMap(server -> event.getMessageAuthor().asUser()).ifPresent(user -> {
            if(BanCommand.bans.isEmpty()) {
                event.getChannel()
                        .sendMessage(Embeds.defaultEmbed()
                                .setTitle("Empty")
                                .setDescription("** User list ** is empty.")
                                .addField("banned : ", "No one are banned"))
                        .exceptionally(ExceptionLogger.get());
            }
            for (User u : BanCommand.bans) {
                event.getChannel()
                        .sendMessage(Embeds.defaultEmbed()
                                .setTitle("Ban List")
                                .setDescription("** User list ** was banned.")
                                .addField("", u.getDiscriminatedName()))
                        .exceptionally(ExceptionLogger.get());
            }
        });
    }
}
