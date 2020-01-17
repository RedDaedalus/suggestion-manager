package com.redstonedaedalus.suggestionmanager;

import com.redstonedaedalus.suggestionmanager.commands.Ping;
import com.redstonedaedalus.suggestionmanager.commands.base.CommandClient;
import com.redstonedaedalus.suggestionmanager.database.SQLProvider;
import com.redstonedaedalus.suggestionmanager.events.GuildMessageReceivedListener;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Scanner;

public class SuggestionManager {
    private static Logger logger = LoggerFactory.getLogger("SuggestionManager");
    private static SQLProvider databaseProvider;

    public static ArrayList<String> readConfig(String path) throws FileNotFoundException {
        File file = new File(path);
        Scanner scanner = new Scanner(file);

        ArrayList<String> lines = new ArrayList<>();
        while (scanner.hasNext()) lines.add(scanner.nextLine());

        return lines;
    }

    public static void main(String[] args) throws LoginException, FileNotFoundException, SQLException {
        logger.info("SuggestionManager instance initialized. Building JDA instance...");

        // Read from config file
        ArrayList<String> config = readConfig("C:\\projects\\java\\suggmanager\\src\\main\\resources\\credentials.txt");
        // Create JDABuilder
        JDABuilder jda = new JDABuilder(config.get(1));

        // Enable lazy loading
        jda.setChunkingFilter(ChunkingFilter.NONE);
        // Disable caching for activities, statuses, and voice states to reduce memory footprint
        jda.setDisabledCacheFlags(EnumSet.of(
                CacheFlag.ACTIVITY,
                CacheFlag.CLIENT_STATUS,
                CacheFlag.VOICE_STATE
        ));

        databaseProvider = new SQLProvider.Builder()
                .setDriverClassName("com.mysql.jdbc.Driver")
                .setUrl("jdbc:mysql://localhost:3306/" + config.get(3))
                .setUsername(config.get(4))
                .setPassword(config.get(5))
                .setInitialSize(5)
                .setMax(5)
                .build();

        // Set status & activity
        jda.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jda.setActivity(Activity.watching("console for errors"));

        // Create command client to handle user input
        CommandClient client = new CommandClient();

        // Register command
        client.registerCommand(new Ping());

        // Add events
        jda.addEventListeners(client.createEventHandler(), new GuildMessageReceivedListener());

        // Build JDA instance
        jda.build();

        databaseProvider.performTest();
    }

    public static SQLProvider getDatabaseProvider() {
        return SuggestionManager.databaseProvider;
    }
}
