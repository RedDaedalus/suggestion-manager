package com.redstonedaedalus.suggestionmanager.events;

import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.Presence;

import javax.annotation.Nonnull;

public class ReadyListener extends ListenerAdapter {
    @Override
    public void onReady(@Nonnull ReadyEvent event) {
        System.out.println("Connected to Discord gateway! Serving as user " + event.getJDA().getSelfUser().getAsTag() +" (ID " + event.getJDA().getSelfUser().getId() + ")");

        Presence presence = event.getJDA().getPresence();
        presence.setActivity(Activity.watching("for 18 upvotes"));
        presence.setStatus(OnlineStatus.ONLINE);
    }
}
