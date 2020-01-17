package com.redstonedaedalus.suggestionmanager;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.TextChannel;

public enum SuggestionChannel {
    DISCUSSION("658074065692786720");

    private final String id;

    SuggestionChannel(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public TextChannel getChannelInstance(JDA jda) {
        return jda.getTextChannelById(this.id);
    }
}
