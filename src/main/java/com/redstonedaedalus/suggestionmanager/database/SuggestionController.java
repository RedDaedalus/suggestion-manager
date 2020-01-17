package com.redstonedaedalus.suggestionmanager.database;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SuggestionController {
    private SQLProvider provider;

    public SuggestionController(SQLProvider provider) {
        this.provider = provider;
    }

    public void createSuggestion(Message message, TextChannel channel, User author) throws SQLException {
        Connection conn = this.provider.grabConnection();
        PreparedStatement query = conn.prepareStatement("INSERT INTO suggestions (message_id, channel_id, author_id, reactions) VALUES (?, ?, ?, '[]');");

        query.setLong(1, message.getIdLong());
        query.setLong(2, channel.getIdLong());
        query.setLong(3, author.getIdLong());

        query.execute();
    }
}
