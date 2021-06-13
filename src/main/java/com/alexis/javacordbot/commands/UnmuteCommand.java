package com.alexis.javacordbot.commands;

import com.alexis.javacordbot.Embeds;
import com.alexis.javacordbot.util.Arguments;
import com.alexis.javacordbot.util.Command;
import org.javacord.api.entity.permission.PermissionType;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

public class UnmuteCommand extends Command {

    public UnmuteCommand() {
        super("unmute", "syntax", "description");
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
                args.getUser(event.getApi(), 1).ifPresent(targetUser -> {
                    if (targetUser.getRoles(server).contains(server.getRolesByName("Muted").get(0))) {
                        server.removeRoleFromUser(targetUser, server.getRolesByName("Muted").get(0)).exceptionally(ExceptionLogger.get());
                        event.getChannel()
                                .sendMessage(Embeds.defaultEmbed()
                                        .setTitle("User Unmuted")
                                        .setDescription("**" + targetUser.getDiscriminatedName() + "** was unmuted."))
                                .exceptionally(ExceptionLogger.get());
                    } else {
                        event.getChannel()
                                .sendMessage(Embeds.errorEmbed()
                                        .setTitle("User is not Muted")
                                        .setDescription("**" + targetUser.getDiscriminatedName() + "** is not muted."))
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
