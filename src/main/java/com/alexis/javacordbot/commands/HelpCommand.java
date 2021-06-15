package com.alexis.javacordbot.commands;

import com.alexis.javacordbot.Embeds;
import com.alexis.javacordbot.util.Arguments;
import com.alexis.javacordbot.util.Command;
import com.alexis.javacordbot.util.CommandHandler;
import com.alexis.javacordbot.util.Constants;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;
import org.javacord.api.util.logging.ExceptionLogger;

public class HelpCommand extends Command {

    private final CommandHandler bot;

    public HelpCommand(CommandHandler bot) {
        super("help", "help", "Help Command.");
        this.bot = bot;
    }

    @Override
    public void execute(MessageCreateEvent event, Arguments args) {
        EmbedBuilder helpEmbed = Embeds.defaultEmbed()
                .setTitle("Help Command")
                .setDescription("These are the commands currently offered by the bot.");

        for (int i = 1; i < bot.commands.size(); i++) {
            Command command = bot.commands.get(i);
            helpEmbed.addField(Constants.PREFIX + command.identifier, command.description);
        }

        event.getChannel().sendMessage(helpEmbed).exceptionally(ExceptionLogger.get());
    }
}
