package rubik_cube_man.plugins.walls;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;

public class PlayerFoodLevelChangeListner implements Listener {
	
	private Walls plugin;

	public PlayerFoodLevelChangeListner(Walls walls) {
		this.plugin = walls;
	}
	@EventHandler
	public void onPlayerFoodChangeEvent(FoodLevelChangeEvent event){
		if (event.getEntity() instanceof Player){
			Player player = (Player) event.getEntity();
			if (plugin.playerarena.get(player) != null){
				if (plugin.arenas.get(plugin.playerarena.get(player)).getCounter() == null || plugin.arenas.get(plugin.playerarena.get(player)).getCounter() >= 0){
					event.setCancelled(true);
				}
			}
		}
	}
}
