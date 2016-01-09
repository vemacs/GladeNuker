package com.vikkcraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class GladeNuker extends JavaPlugin implements Listener {
    private static final Pattern stripPattern = Pattern.compile("[^A-Za-z0-9]");
    private static final List<String> blocked = new ArrayList<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(this, this);
        for (String s : getConfig().getStringList("blocked")) {
            blocked.add(s.toLowerCase());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {
        if (event.getPlayer().hasPermission("gladenuker.bypass")) return;
        String stripped = stripPattern.matcher(event.getMessage()).replaceAll("").toLowerCase();
        for (String entry : blocked) {
            if (stripped.contains(entry)) {
                event.getRecipients().clear();
                event.getRecipients().add(event.getPlayer());
                return;
            }
        }
    }
}
