package com.customscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.*;

public class CustomScoreboard extends JavaPlugin {

    private ScoreboardManager manager;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        manager = new ScoreboardManager(this);
        Bukkit.getPluginManager().registerEvents(manager, this);
        getLogger().info("CustomScoreboard enabled!");
    }

    public String translate(String text, Player player) {
        return ChatColor.translateAlternateColorCodes('&', text
                .replace("{player}", player.getName())
                .replace("{online}", String.valueOf(Bukkit.getOnlinePlayers().size()))
                .replace("{maxonline}", String.valueOf(Bukkit.getMaxPlayers()))
                .replace("{servername}", getConfig().getString("title", "Server").replace("&6&l", "").replace("&", "")) // Simplified
                .replace("{ip}", "yourserver.com") // Change this to your server IP
                .replace("{rank}", "Player")); // Add rank logic here if needed
    }
}