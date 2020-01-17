package com.redstonedaedalus.suggestionmanager.commands;

import com.redstonedaedalus.suggestionmanager.commands.base.Command;
import com.redstonedaedalus.suggestionmanager.commands.base.PermLevel;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public class Ping extends Command {
    @Override
    public void execute(MessageReceivedEvent event, List<String> args) {
        EmbedBuilder embed = new EmbedBuilder()
                .setColor(PermLevel.MOD.getColor())
                .setTitle("Pinging...")
                .setDescription("» Please wait.");

        long time = System.currentTimeMillis();
        event.getChannel().sendMessage(embed.build()).queue(message -> {
            long ping = System.currentTimeMillis() - time;
            message.editMessage(embed
                    .setTitle("Pong!")
                    .setDescription("")
                    .addField("» Bot Latency", Math.round(ping) + "ms", true)
                    .addField("» Discord API Latency", Math.round(event.getJDA().getGatewayPing()) + "ms", true)
                    .setThumbnail(event.getJDA().getSelfUser().getAvatarUrl())
                    .setFooter("Suggestion Manager by Daedalus#1111")
                    .build()).queue();
        });
    }

    @Override
    public String getName() { return "ping"; }

    @Override
    public String getDescription() { return "Pings the bot, making sure it is online."; }

    @Override
    public PermLevel getAccessLevel() { return PermLevel.EVERYONE; }

    @Override
    public boolean respondInDM() { return false; }
}
