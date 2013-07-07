package rubik_cube_man.plugins.walls;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

public class PlayerDropItemListener implements Listener {

	private Walls plugin;
	
	public PlayerDropItemListener(Walls plugin){
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerDropItemEvent(PlayerDropItemEvent event){
		if (plugin.playerarena.get(event.getPlayer()) != null){
			if (plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getCounter() == null || plugin.arenas.get(plugin.playerarena.get(event.getPlayer())).getCounter() >= 0){
				event.setCancelled(true);
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new UpdateInventory(event.getPlayer()), 2L);
			}
		}
	}
}
