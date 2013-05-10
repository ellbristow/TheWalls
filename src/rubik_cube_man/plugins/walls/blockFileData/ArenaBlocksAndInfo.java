package rubik_cube_man.plugins.walls.blockFileData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.inventory.ItemStack;

public class ArenaBlocksAndInfo implements Serializable{

	private static final long serialVersionUID = 1436755451917501905L;
	
	private Map<Integer, Integer> blockId = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> blockData = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> x = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> y = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> z = new HashMap<Integer, Integer>();
	private Map<Integer, String[]> lines = new HashMap<Integer, String[]>();
	private Map<Integer, ChestData> chests = new HashMap<Integer, ChestData>();
	private String world;
	
	public ArenaBlocksAndInfo(Location loc1, Location loc2, String arenaName){
		this.world = loc1.getWorld().getName();
		this.updateInfo(loc1, loc2);
	}
	
	public void updateInfo(Location loc1, Location loc2){
		Integer num = 0;
		Location start = loc1;
		Location end = loc2;
		World world = Bukkit.getWorld(this.world);
		Integer minX = (int) Math.min(start.getX() , end.getX());
		Integer maxX = (int) Math.max(start.getX() , end.getX());
		Integer minY = (int) Math.min(start.getY() , end.getY());
		Integer maxY = (int) Math.max(start.getY() , end.getY());
		Integer minZ = (int) Math.min(start.getZ() , end.getZ());
		Integer maxZ = (int) Math.max(start.getZ() , end.getZ());
		for (int x = minX; x <= maxX; ++x) {
			for (int y = minY; y <= maxY; ++y) {
				for (int z = minZ; z <= maxZ; ++z) {
					num++;
					this.x.put(num, x);
					this.y.put(num, y);
					this.z.put(num, z);
					this.blockId.put(num, world.getBlockTypeIdAt(x,y,z));
					this.blockData.put(num, (int)world.getBlockAt(x,y,z).getData());
					if (world.getBlockAt(x,y,z).getType() == Material.CHEST){
						Chest chest = (Chest) world.getBlockAt(x,y,z).getState();
						ItemStack[] is = chest.getBlockInventory().getContents();
						ChestData cd = new ChestData(is);
						chests.put(num, cd);
						
					}
					else if (world.getBlockAt(x,y,z).getType() == Material.WALL_SIGN || world.getBlockAt(x,y,z).getType() == Material.WALL_SIGN){
						Sign sign = (Sign) world.getBlockAt(x,y,z).getState();
						this.lines.put(num, sign.getLines());
					}
				}
			}
		}
	}
	
	public void restoreBlocks(){
		World world = Bukkit.getWorld(this.world);
		for (Integer num : this.blockId.keySet()){
			Location loc = world.getBlockAt(x.get(num), y.get(num), z.get(num)).getLocation();
			loc.getBlock().setType(Material.getMaterial(blockId.get(num)));
			int data = this.blockData.get(num);
			loc.getBlock().setData((byte)data);
			if (lines.containsKey(num)){
				Sign sign = (Sign) loc.getBlock().getState();
				sign.setLine(0, this.lines.get(num)[0]);
				sign.setLine(1, this.lines.get(num)[1]);
				sign.setLine(2, this.lines.get(num)[2]);
				sign.setLine(3, this.lines.get(num)[3]);
				sign.update();
			}
			if (chests.containsKey(num)){
				Chest chest = (Chest) loc.getBlock().getState();
				chest.getBlockInventory().setContents(chests.get(num).getChestData());
			}
		}
	}
}
