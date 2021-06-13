package com.alexis.javacordbot.commands;

import com.alexis.javacordbot.Embeds;
import com.alexis.javacordbot.util.Arguments;
import com.alexis.javacordbot.util.Command;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

public class MuteCommand extends Command {

    public MuteCommand() {
        super("mute", "mute <User> [Reason]", "Mutes a user for a reason.");
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
                args.getUser(event.getApi(), 1).ifPresent(mutedUser -> {
                    server.addRoleToUser(mutedUser, server.getRolesByName("Muted").get(0)).exceptionally(ExceptionLogger.get());
                    if (args.size() > 2) {
                        event.getChannel()
                                .sendMessage(Embeds.defaultEmbed()
                                        .setTitle("User Muted")
                                        .setDescription("**" + mutedUser.getDiscriminatedName() + "** was muted.")
                                        .addField("Reason", args.getTrailing(2)))
                                .exceptionally(ExceptionLogger.get());
                    } else {
                        event.getChannel()
                                .sendMessage(Embeds.defaultEmbed()
                                        .setTitle("User Muted")
                                        .setDescription("**" + mutedUser.getDiscriminatedName() + "** was muted.")
                                        .addField("Reason", "No Reason Provided."))
                                .exceptionally(ExceptionLogger.get());
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
