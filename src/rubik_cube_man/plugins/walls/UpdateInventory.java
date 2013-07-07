package rubik_cube_man.plugins.walls;

import org.bukkit.entity.Player;

public class UpdateInventory implements Runnable {

	private Player player;
	
	public UpdateInventory(Player player){
		this.player = player;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		player.updateInventory();
	}
}
