package rubik_cube_man.plugins.walls.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import rubik_cube_man.plugins.walls.Walls;
import rubik_cube_man.plugins.walls.utils.UpdateChecker;

public class PlayerJoinListener implements Listener {

    private Walls plugin;

    public PlayerJoinListener(Walls walls) {
        plugin = walls;
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("walls.updates")) {
            if (plugin.checkForUpdates == true) {
                if (UpdateChecker.UpdateNeeded()) {
                    player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------");
                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "The walls version " + UpdateChecker.getVersion() + " is now out!");
                    player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "Get it here:");
                    player.sendMessage(ChatColor.BLUE + UpdateChecker.getLink());
                    player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------");
                }
            }
        }
    }
}
