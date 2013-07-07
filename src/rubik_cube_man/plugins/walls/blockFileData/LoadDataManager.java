package rubik_cube_man.plugins.walls.blockFileData;

import rubik_cube_man.plugins.walls.Walls;

public class LoadDataManager implements Runnable {

	private ArenaData ad;
	private Walls plugin;
	private String arena;
	
	public LoadDataManager(ArenaData ad, Walls plugin, String arena){
		this.ad = ad;
		this.plugin = plugin;
		this.arena = arena;
	}
	
	@Override
	public void run() {
		ArenaBlocksAndInfo abai = new ArenaBlocksAndInfo(ad.getStartx(), ad.getStartY(), ad.getStartZ(), ad.getEndX(), ad.getEndY(), ad.getEndZ(), ad.getWorld(), this.arena, plugin, ad.getStartx(), ad.getStartY(), ad.getStartZ(), -1);
		abai.setX(ad.getX());
		abai.setY(ad.getY());
		abai.setZ(ad.getZ());
		abai.setLines(ad.getLines());
		abai.setBlockData(ad.getBlockData());
		abai.setBlockIds(ad.getBlockId());
		abai.setChests(ad.getChests());
		abai.restoreBlocks();
	}
}
