package rubik_cube_man.plugins.walls;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
	
	private Walls plugin;

	public PlayerJoinListener(Walls walls) {
		this.plugin = walls;
	}
	
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent event){
		Player player = event.getPlayer();
		if (player.hasPermission("walls.updates")){
			if (plugin.checkForUpdates == true){
				plugin.CheckUpdates();
				if (plugin.update == true){
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------");
					player.sendMessage(ChatColor.BLUE + "" + ChatColor.BOLD + "The walls version " + plugin.updateChecker.getVersion() + " is now out!");
					player.sendMessage(ChatColor.BLUE + "" +  ChatColor.BOLD + "Get it here:");
					player.sendMessage(ChatColor.BLUE + plugin.updateChecker.getLink());
					player.sendMessage(ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "---------------------------");
				}
			}
		}
	}
}
