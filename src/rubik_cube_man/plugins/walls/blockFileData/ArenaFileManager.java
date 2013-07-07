package rubik_cube_man.plugins.walls.blockFileData;

import java.io.Serializable;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import rubik_cube_man.plugins.walls.Arena;
import rubik_cube_man.plugins.walls.Walls;

public class ArenaFileManager implements Serializable{

	private static final long serialVersionUID = -3280620778831356769L;
	private static Walls plugin;
	
	public static void setPlugin(Walls plugin){
		ArenaFileManager.plugin = plugin;
	}
	
	public static void saveToAFile(String arenaName, Location loc1, Location loc2){
		Integer minX = (int) Math.min(loc1.getX() , loc2.getX());
		Integer minY = (int) Math.min(loc1.getY() , loc2.getY());
		Integer minZ = (int) Math.min(loc1.getZ() , loc2.getZ());
		new ArenaBlocksAndInfo(loc1.getBlockX(), loc1.getBlockY(), loc1.getBlockZ(), loc2.getBlockX(), loc2.getBlockY(), loc2.getBlockZ(), loc1.getWorld().getName(), arenaName.toLowerCase(), plugin, (int) minX, (int) minY, (int) minZ, 1);
	}
	
	public static void loadArenaFiles(String arenas, Integer num){
		Arena arena = plugin.arenas.get(arenas);
		arena.setResetting(true);
		for (Integer number : arena.getSignWalls().keySet()){
			arena.getSignWalls().get(number).setProgress("Resetting");
			arena.getSignWalls().get(number).setTime(null);
			arena.getSignWalls().get(number).updateSigns(arena.getMin(), arena.getTotal(), arena.getPlayerList());
		}
		Bukkit.getServer().getScheduler().runTaskAsynchronously(plugin, new LoadDataRunnable(plugin, arenas, num));
	}
	
	public static void setFilesForAnArena(Integer files, String arena){
		plugin.arenas.get(arena).setFiles(files);
	}
}
