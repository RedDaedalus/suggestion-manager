package com.redstonedaedalus.suggestionmanager.commands.base;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.List;

public abstract class Command {
    private CommandClient client;
    public abstract String getName();
    public abstract String getDescription();
    public abstract PermLevel getAccessLevel();
    public abstract boolean respondInDM();

    public abstract void execute(MessageReceivedEvent event, List<String> args);

    public CommandClient getClient() {
        return this.client;
    }

    public Command setClient(CommandClient client) {
        this.client = client;

        return this;
    }
}
