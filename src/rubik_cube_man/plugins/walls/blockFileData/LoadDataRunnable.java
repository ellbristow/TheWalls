package rubik_cube_man.plugins.walls.blockFileData;

import org.bukkit.Bukkit;
import lucariatias.plugins.walls.ObjectFileLib;
import rubik_cube_man.plugins.walls.Arena;
import rubik_cube_man.plugins.walls.Walls;

public class LoadDataRunnable implements Runnable {

	private Walls plugin;
	private String arena;
	private Integer fileNumber;
	
	public LoadDataRunnable(Walls plugin, String arena, Integer fileNumber){
		this.arena = arena;
		this.plugin = plugin;
		this.fileNumber = fileNumber;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void run() {
		if (plugin.arenas.get(arena).getFiles() >= fileNumber){
			ArenaData ad = (ArenaData) ObjectFileLib.loadObject(plugin, arena + " Blocks" + this.fileNumber);
			try{
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new LoadDataManager(ad, plugin, this.arena));
				Bukkit.getServer().getScheduler().scheduleAsyncDelayedTask(plugin, new LoadDataRunnable(plugin, arena, fileNumber + 1), 20L);
			}
			catch (Exception e){}
		}
		else{
			Arena arena = plugin.arenas.get(this.arena);
			arena.setResetting(false);
			for (Integer number : arena.getSignWalls().keySet()){
				arena.getSignWalls().get(number).setProgress("Waiting");
				arena.getSignWalls().get(number).setTime(null);
				arena.getSignWalls().get(number).updateSigns(arena.getMin(), arena.getTotal(), arena.getPlayerList());
			}
		}
	}
}
