package com.alexis.javacordbot.commands;

import com.alexis.javacordbot.Embeds;
import com.alexis.javacordbot.util.Arguments;
import com.alexis.javacordbot.util.Command;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.entity.user.User;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.listener.server.member.ServerMemberBanListener;
import org.javacord.api.util.logging.ExceptionLogger;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class BanCommand extends Command {
    public static LinkedList<User> bans = new LinkedList<>();

    public BanCommand() {
        super("ban", "syntax", "description");
    }

    @Override
    public void execute(MessageCreateEvent event, Arguments args) {

        event.getServer().ifPresent(server -> event.getMessageAuthor().asUser().ifPresent(user -> {
            if (!server.hasPermission(user, PermissionType.MOVE_MEMBERS)) {
                event.getChannel()
                        .sendMessage(Embeds.errorEmbed()
                                .setTitle("Invalid Permissions")
                                .setDescription("You require the **Move Members** permission."))
                        .exceptionally(ExceptionLogger.get());
                return;
            }
            if (args.size() > 1) {
                args.getUser(event.getApi(), 1).ifPresent(banUser -> {
                    server.banUser(banUser).exceptionally(ExceptionLogger.get());
                    if (args.size() > 2) {
                        event.getChannel()
                                .sendMessage(Embeds.defaultEmbed()
                                        .setTitle("User Banned")
                                        .setDescription("**" + banUser.getDiscriminatedName() + "** was banned.")
                                        .addField("Reason", args.getTrailing(2)))
                                .exceptionally(ExceptionLogger.get());
                        this.bans.add(banUser);
                    } else {
                        event.getChannel()
                                .sendMessage(Embeds.defaultEmbed()
                                        .setTitle("User Banned")
                                        .setDescription("**" + banUser.getDiscriminatedName() + "** was banned.")
                                        .addField("Reason", "No Reason Provided."))
                                .exceptionally(ExceptionLogger.get());
                        this.bans.add(banUser);
                    }
                });
            } else {
                event.getChannel()
                        .sendMessage(Embeds.errorEmbed()
                                .setTitle("Invalid Syntax")
                                .setDescription("You need to specify a user."))
                        .exceptionally(ExceptionLogger.get());
            }
        }));

    }
}
