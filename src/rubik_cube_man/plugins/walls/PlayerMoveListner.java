package rubik_cube_man.plugins.walls;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListner implements Listener {
	
	private Walls plugin;
	
	public PlayerMoveListner(Walls walls) {
		this.plugin = walls;
	}
	
	@EventHandler (priority=EventPriority.HIGHEST)
	public void onPlayerMove(PlayerMoveEvent event) {
		Player player = (Player)event.getPlayer();
		if (plugin.arenas.containsKey(plugin.playerarena.get(player))){
			if (plugin.arenas.get(plugin.playerarena.get(player)).getCounter() != null){
				if (plugin.arenas.get(plugin.playerarena.get(player)).getCounter() <= 9 && plugin.arenas.get(plugin.playerarena.get(player)).getCounter() >= 0){
				   if ((plugin.arenas.get(plugin.playerarena.get(player)).getPlayerLocation().get(player)).getX() != player.getLocation().getX() || (plugin.arenas.get(plugin.playerarena.get(player)).getPlayerLocation().get(player)).getZ() != player.getLocation().getZ()){
					   plugin.teleportable.put(player , true);
					   player.teleport(plugin.arenas.get(plugin.playerarena.get(player)).getPlayerLocation().get(player));
					   plugin.teleportable.put(player , false);
				   	}   
				}
			}
		}
	}
}
