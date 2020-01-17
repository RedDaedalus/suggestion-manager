package com.redstonedaedalus.suggestionmanager.commands.base;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class CommandEventHandler extends ListenerAdapter {
    private CommandClient client;

    public CommandEventHandler(CommandClient client) {
        this.client = client;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        String prefix = this.client.getPrefix();
        Message message = event.getMessage();

        if (!message.getContentRaw().toLowerCase().startsWith(prefix)) return;

        List<String> args = Arrays.asList(message.getContentRaw().split("\\s+"));
        String commandName = args.get(0).substring(prefix.length()).toLowerCase();

        Command command = this.client.getCommands().get(commandName);
        if (command == null) {
            MessageEmbed embed = new EmbedBuilder()
                    .setColor(0xf52929)
                    .setTitle("Unknown Command")
                    .setDescription("» Command `" + prefix + commandName + "` was not recognized. Type `" + prefix + "help` for a list of commands.")
                    .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                    .setFooter("Suggestion Manager by Daedalus#1111")
                    .build();
            event.getChannel().sendMessage(embed).queue();
            return;
        }

        command.execute(event, args);
    }
}
