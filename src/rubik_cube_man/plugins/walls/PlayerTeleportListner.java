package rubik_cube_man.plugins.walls;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListner implements Listener {
	private Walls plugin;

	public PlayerTeleportListner(Walls walls) {
		this.plugin = walls;
	}
	@EventHandler (priority=EventPriority.HIGHEST)
	public void playerTeleport(PlayerTeleportEvent event){
		Player player = event.getPlayer();
		if (plugin.playerarena.get(player) != null && plugin.arenas.get(plugin.playerarena.get(player)).getCounter() != null && plugin.arenas.get(plugin.playerarena.get(player)).getCounter() != 10){
			if (plugin.teleportable.get(player) == false){
				event.setCancelled(true);
			}
		}
	}
}
