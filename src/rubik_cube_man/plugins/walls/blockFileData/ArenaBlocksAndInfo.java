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
import rubik_cube_man.plugins.walls.Walls;

public class ArenaBlocksAndInfo implements Serializable{
	
	private static final long serialVersionUID = -3896531915356967448L;
	private Walls plugin;
	private Map<Integer, Integer> blockId = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> blockData = new HashMap<Integer, Integer>();
	private Map<Integer, String[]> lines = new HashMap<Integer, String[]>();
	private Map<Integer, ChestData> chests = new HashMap<Integer, ChestData>();
	private String world;
	private Integer startX;
	private Integer startY;
	private Integer startZ;
	private Integer count;
	private String name;
	private Integer fileNumber;
	private Map<Integer, Integer> x = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> y = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> z = new HashMap<Integer, Integer>();
	
	public ArenaBlocksAndInfo(Integer loc1x, Integer loc1y, Integer loc1z, Integer loc2x, Integer loc2y, Integer loc2z, String world, String arenaName, Walls plugin, Integer x, Integer y, Integer z, Integer fileNumber){
		this.world = world;
		this.plugin = plugin;
		this.startX = x;
		this.startY = y;
		this.startZ = z;
		this.count = -1;
		this.name = arenaName;
		this.fileNumber = fileNumber;
		if (fileNumber != -1){
			this.updateInfo(loc1x, loc1y, loc1z, loc2x, loc2y, loc2z);
		}
	}
	
	public void updateInfo(Integer loc1x, Integer loc1y, Integer loc1z, Integer loc2x, Integer loc2y, Integer loc2z){
		Integer num = 0;
		Integer minX = (int) Math.min(loc1x , loc2x);
		Integer maxX = (int) Math.max(loc1x , loc2x);
		Integer minY = (int) Math.min(loc1y , loc2y);
		Integer maxY = (int) Math.max(loc1y , loc2y);
		Integer minZ = (int) Math.min(loc1z , loc2z);
		Integer maxZ = (int) Math.max(loc1z , loc2z);
		for (int y = minY; y <= maxY; ++y) {
			for (int x = minX; x <= maxX; ++x) {
				for (int z = minZ; z <= maxZ; ++z) {
					if (Bukkit.getWorld(this.world).getBlockAt(x,y,z).getLocation().getBlockX() == Bukkit.getWorld(this.world).getBlockAt(startX, startY, startZ).getLocation().getBlockX() && Bukkit.getWorld(this.world).getBlockAt(x,y,z).getLocation().getBlockY() == Bukkit.getWorld(this.world).getBlockAt(startX, startY, startZ).getLocation().getBlockY() && Bukkit.getWorld(this.world).getBlockAt(x,y,z).getLocation().getBlockZ() == Bukkit.getWorld(this.world).getBlockAt(startX, startY, startZ).getLocation().getBlockZ()){
						count = 0;
					}
					if (count != -1){
						if (count <= 20000){
							this.x.put(num, x);
							this.y.put(num, y);
							this.z.put(num, z);
							count++;
							num++;
							this.blockId.put(num, Bukkit.getWorld(this.world).getBlockTypeIdAt(x,y,z));
							this.blockData.put(num, (int)Bukkit.getWorld(this.world).getBlockAt(x,y,z).getData());
							if (Bukkit.getWorld(this.world).getBlockAt(x,y,z).getType() == Material.CHEST){
								Chest chest = (Chest) Bukkit.getWorld(this.world).getBlockAt(x,y,z).getState();
								ItemStack[] is = chest.getBlockInventory().getContents();
								ChestData cd = new ChestData(is);
								chests.put(num, cd);
								
							}
							else if (Bukkit.getWorld(this.world).getBlockAt(x,y,z).getType() == Material.SIGN_POST || Bukkit.getWorld(this.world).getBlockAt(x,y,z).getType() == Material.WALL_SIGN){
								Sign sign = (Sign) Bukkit.getWorld(this.world).getBlockAt(x,y,z).getState();
								this.lines.put(num, sign.getLines());
							}
							if (x == maxX && y == maxY && z == maxZ){
								plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new SaveDataRunnable(this, plugin, name, this.fileNumber, minX, minY, minZ, maxX, maxY, maxZ));
								this.plugin = null;
								this.count = -1;
								ArenaFileManager.setFilesForAnArena(this.fileNumber, this.name);
								return;
							}
						}
						else{
							plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new SaveDataRunnable(this, plugin, name, this.fileNumber, minX, minY, minZ, maxX, maxY, maxZ));
							new ArenaBlocksAndInfo(loc1x, loc1y, loc1z, loc2x, loc2y, loc2z, world, name, plugin, x, y, z, this.fileNumber + 1);
							return;
						}
					}
				}
			}
		}
	}
	
	public void restoreBlocks(){
		for (Integer num : this.x.keySet()){
			World world = Bukkit.getWorld(this.world);
			Location loc = world.getBlockAt(this.x.get(num), this.y.get(num), this.z.get(num)).getLocation();
			loc.getBlock().setType(Material.getMaterial(this.blockId.get(num + 1)));
			int data =  this.blockData.get(num + 1);
			loc.getBlock().setData((byte) data);
			if (loc.getBlock().getType() == Material.CHEST){
				Chest chest = (Chest) loc.getBlock().getState();
				chest.getBlockInventory().setContents(this.chests.get(num + 1).getChestData());
			}
			if (loc.getBlock().getType() == Material.SIGN_POST || loc.getBlock().getType() == Material.WALL_SIGN){
				Sign sign = (Sign) loc.getBlock().getState();
				sign.setLine(0, this.lines.get(num + 1)[0]);
				sign.setLine(1, this.lines.get(num + 1)[1]);
				sign.setLine(2, this.lines.get(num + 1)[2]);
				sign.setLine(3, this.lines.get(num + 1)[3]);
				sign.update();
			}
			if (loc.getBlock().getType() == Material.AIR){
				Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new CheckForFallingItems(plugin, loc), 15L);
			}
		}
	}
	
	public void setX(Map<Integer, Integer> x){
		this.x = x;
	}
	
	public void setY(Map<Integer, Integer> y){
		this.y = y;
	}
	
	public void setZ(Map<Integer, Integer> z){
		this.z = z;
	}
	
	public void setChests (Map<Integer, ChestData> chests){
		this.chests = chests;
	}
	
	public void setBlockIds(Map<Integer, Integer> blockIds){
		this.blockId = blockIds;
	}
	
	public void setBlockData(Map<Integer, Integer> blockData){
		this.blockData = blockData;
	}
	
	public void setLines(Map<Integer, String[]> lines){
		this.lines = lines;
	}
	
	public String getWorld(){
		return this.world;
	}
	
	public Map<Integer, Integer> getBlockId(){
		return this.blockId;
	}
	
	public Map<Integer, Integer> getBlockData(){
		return this.blockData;
	}
	
	public Map<Integer, String[]> getLines(){
		return this.lines;
	}
	
	public Map<Integer, ChestData> getChests(){
		return this.chests;
	}
	
	public Map<Integer, Integer> getX(){
		return this.x;
	}
	
	public Map<Integer, Integer> getY(){
		return this.y;
	}
	
	public Map<Integer, Integer> getZ(){
		return this.z;
	}
}
