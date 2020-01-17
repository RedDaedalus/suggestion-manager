package com.redstonedaedalus.suggestionmanager.events;

import com.redstonedaedalus.suggestionmanager.SuggestionChannel;
import com.redstonedaedalus.suggestionmanager.SuggestionManager;
import com.redstonedaedalus.suggestionmanager.commands.base.PermLevel;
import com.redstonedaedalus.suggestionmanager.database.SQLProvider;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class GuildMessageReceivedListener extends ListenerAdapter {
    private final List<String> suggestionChannels = Arrays.asList("660991928351064094", "658014733097500672");
    private final List<String> issueChannels = Arrays.asList("658014745537806358", "658014760083914762");

    @Override
    public void onGuildMessageReceived(@Nonnull GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        String id = event.getChannel().getId();

        if (suggestionChannels.contains(id)) {
            try {
                this.handleSuggestion(event);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        else if (issueChannels.contains(id)) this.handleIssue(event);
    }

    private void handleSuggestion(@Nonnull GuildMessageReceivedEvent event) throws SQLException {
        Message message = event.getMessage();
        Member member = Objects.requireNonNull(event.getMember());
        User author = event.getAuthor();
        TextChannel channel = event.getChannel();

        String trimmed = message.getContentRaw();
        if (trimmed.length() > 1024) trimmed = trimmed.substring(0, 1021) + "...";

        MessageEmbed embed = new EmbedBuilder()
                .setColor(PermLevel.MOD.getColor())
                .setDescription("(" + message.getJumpUrl() + ")[\uD83D\uDCE8 New Suggestion Posted]")
                .setAuthor(member.getEffectiveName() + " (" +author.getAsTag() + ")", null, author.getEffectiveAvatarUrl())
                .addField(" ", trimmed, false)
                .build();

        SQLProvider provider = SuggestionManager.getDatabaseProvider();
        provider.getSuggestionController().createSuggestion(message, channel, author);

        SuggestionChannel.DISCUSSION.getChannelInstance(event.getJDA()).sendMessage(embed).queue();
    }

    private void handleIssue(@Nonnull GuildMessageReceivedEvent event) {

    }
}
