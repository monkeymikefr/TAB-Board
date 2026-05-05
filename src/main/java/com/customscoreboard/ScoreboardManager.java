package com.customscoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.*;

import java.util.List;

public class ScoreboardManager implements Listener {
    private final CustomScoreboard plugin;
    private final Scoreboard scoreboard;

    public ScoreboardManager(CustomScoreboard plugin) {
        this.plugin = plugin;
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        startUpdater();
    }

    private void startUpdater() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::updateAll, 20L, 20L); // Every second
    }

    public void updateAll() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            updatePlayer(p);
        }
    }

    public void updatePlayer(Player player) {
        Objective objective = scoreboard.getObjective("customsb");
        if (objective == null) {
            objective = scoreboard.registerNewObjective("customsb", "dummy");
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        }

        String title = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("title", "&6&lServer"));
        objective.setDisplayName(title);

        // Clear old scores
        for (String entry : scoreboard.getEntries()) {
            scoreboard.resetScores(entry);
        }

        List<String> lines = plugin.getConfig().getStringList("lines");
        int score = lines.size();

        for (String line : lines) {
            String formatted = plugin.translate(line, player);
            if (formatted.isEmpty()) formatted = " ";
            objective.getScore(formatted).setScore(score--);
        }

        player.setScoreboard(scoreboard);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> updatePlayer(e.getPlayer()), 10L);
    }
}