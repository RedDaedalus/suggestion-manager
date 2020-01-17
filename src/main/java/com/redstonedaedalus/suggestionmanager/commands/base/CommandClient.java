package com.redstonedaedalus.suggestionmanager.commands.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

public class CommandClient {
    private static Logger logger = LoggerFactory.getLogger("CommandClient");
    private HashMap<String, Command> commands = new HashMap<>();
    private final String prefix = "sm!";

    public void registerCommand(Command command) {
        CommandClient.logger.info("Command" + command.getName() + " was registered successfully.");
        commands.put(command.getName().toLowerCase(), command);
    }

    public CommandEventHandler createEventHandler() {
        return new CommandEventHandler(this);
    }

    public String getPrefix() {
        return this.prefix;
    }

    public HashMap<String, Command> getCommands() {
        return this.commands;
    }
}
