package rubik_cube_man.plugins.walls.blockFileData;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import rubik_cube_man.plugins.walls.Walls;

public class CheckForFallingItems implements Runnable {

	private Walls plugin;
	private Location loc;
	
	public CheckForFallingItems(Walls plugin, Location loc){
		this.plugin = plugin;
		this.loc = loc;
	}
	
	@Override
	public void run() {
		if (loc.getBlock().getType() != Material.AIR){
			loc.getBlock().setType(Material.AIR);
			Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new CheckForFallingItems(plugin, loc), 63L);
		}
	}
}
