package rubik_cube_man.plugins.walls.blockFileData;

import rubik_cube_man.plugins.walls.Walls;
import lucariatias.plugins.walls.ObjectFileLib;

public class SaveDataRunnable implements Runnable {

	private ArenaBlocksAndInfo arenaData;
	private Walls plugin;
	private String arenaName;
	private Integer fileNumber;
	private Integer minX;
	private Integer minY;
	private Integer minZ;
	private Integer maxX;
	private Integer maxY;
	private Integer maxZ;
	
	public SaveDataRunnable(ArenaBlocksAndInfo arena, Walls plugin, String arenaName, Integer fileNumber, Integer minX, Integer minY, Integer minZ, Integer maxX, Integer maxY, Integer maxZ){
		this.arenaData = arena;
		this.plugin = plugin;
		this.arenaName = arenaName;
		this.fileNumber = fileNumber;
		this.maxX = maxX;
		this.maxY = maxY;
		this.maxZ = maxZ;
		this.minX = minX;
		this.minY = minY;
		this.minZ = minZ;
	}
	
	@Override
	public void run() {
		ObjectFileLib.saveObject(plugin, new ArenaData(arenaData.getX(), arenaData.getY(), arenaData.getZ(), arenaData.getWorld(), arenaData.getBlockId(), arenaData.getBlockData(), arenaData.getLines(), arenaData.getChests(), minX, minY, minZ, maxX, maxY, maxZ), arenaName + " Blocks" + fileNumber);
	}
}
