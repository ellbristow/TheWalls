package rubik_cube_man.plugins.walls.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import rubik_cube_man.plugins.walls.Walls;

public class PlayerCommandListner implements Listener {

    private Walls plugin;

    public PlayerCommandListner(Walls walls) {
        plugin = walls;
    }
    boolean blocked = false;

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerSendCommandEvent(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (plugin.playerarena.get(player) != null) {
            String message[] = event.getMessage().split(" ", 2);
            String command = message[0].toLowerCase();
            plugin.allowedCommands.add("/walls");
            if (!plugin.allowedCommands.contains(command)) {
                event.setCancelled(true);
                player.sendMessage(ChatColor.RED + "That command is blocked in the walls");
            }
            plugin.allowedCommands.remove("/walls");
        }
    }
}