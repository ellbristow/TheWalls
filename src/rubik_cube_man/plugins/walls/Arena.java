package rubik_cube_man.plugins.walls;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.TagAPI;

import rubik_cube_man.plugins.walls.blockFileData.ArenaFileManager;
import rubik_cube_man.plugins.walls.signs.SignWall;

public class Arena implements Serializable{

	private static final long serialVersionUID = -5169004947980681473L;

	public Arena(String name, Location loc1, Location loc2, Integer blocknumber, Boolean started, Location lose, Location win, Location playerLoc, Integer reds, Integer greens, Integer blues, Integer yellows, Integer min, Integer redplayers, Integer blueplayers, Integer greenplayers, Integer yellowplayers, Integer counter, Player random, Integer drops, Integer total, Location tempLoc, Location tempLoc1, String temp, Location lobby, Integer saveTotal, boolean showing, Integer totalDrops, Walls walls){
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.blocknumber = blocknumber;
		this.started = started;
		this.win = win;
		this.lose = lose;
		this.playerLoc = playerLoc;
		this.greens = greens;
		this.yellows = yellows;
		this.blues = blues;
		this.reds = reds;
		this.min = min;
		this.redplayers = redplayers;
		this.blueplayers = blueplayers;
		this.yellowplayers = yellowplayers;
		this.greenplayers = greenplayers;
		this.counter = counter;
		this.random = random;
		this.drops = drops;
		this.total = total;
		this.tempLoc = tempLoc;
		this.tempLoc1 = tempLoc1;
		this.temp = temp;
		this.lobby = lobby;
		this.saveTotal = saveTotal;
		this.showing = showing;
		this.totalDrops = totalDrops;
		this.resetting = false;
	}
	
	private boolean resetting;
	private Integer files;
	private String name;
	private Integer countToEnd;
	private Location loc1;
	private Location loc2;
	private Map<Player,Integer> invinsibleTimer = new HashMap<Player, Integer>();
	private Map<Integer, Location> blocksLocation = new HashMap<Integer, Location>();
	private Map<Integer, BlockState> blockstype = new HashMap<Integer, BlockState>();
	private Map<Integer, Location> redspawns = new HashMap<Integer, Location>();
	private Map<Integer, Location> bluespawns = new HashMap<Integer, Location>();
	private Map<Integer, Location> yellowspawns = new HashMap<Integer, Location>();
	private Map<Integer, Location> greenspawns = new HashMap<Integer, Location>();
	private Set<Player> lobbyPlayers = new HashSet<Player>();
	private Integer blocknumber;
	private Boolean started;
	private Location win;
	private Location lose;
	private Integer reds;
	private Integer blues;
	private Integer yellows;
	private Integer greens;
	private Location playerLoc;
	private Integer min;
	private Map<Player, String> playerList = new HashMap<Player, String>();
	private Integer redplayers;
	private Integer blueplayers;
	private Integer greenplayers;
	private Integer yellowplayers;
	private Integer counter;
	private Player random;
	private Map<Integer, Location> drop = new HashMap<Integer, Location>();
	private Integer drops;
	private Integer total;
	private Location tempLoc;
	private Location tempLoc1;
	private String temp;
	private Location lobby;
	private Map<Player, Location> playerLocation = new HashMap<Player , Location>();
	private Map<Player, String> deadplayers = new HashMap<Player, String>();
	private Map<Location, ItemStack[]> chests = new HashMap<Location, ItemStack[]>();
	private Map<Player, ItemStack[]> playerInventories = new HashMap<Player, ItemStack[]>();
	private Map<Integer, Location> droploc1 = new HashMap<Integer, Location>();
	private Map<Integer, Location> droploc2 = new HashMap<Integer, Location>();
	private Map<Player, ItemStack[]> aurmor = new HashMap<Player, ItemStack[]>();
	private Map<Player, Integer> exp = new HashMap<Player, Integer>();
	private Map<Location, String> signLine0 = new HashMap<Location, String>();
	private Map<Location, String> signLine1 = new HashMap<Location, String>();
	private Map<Location, String> signLine2 = new HashMap<Location, String>();
	private Map<Location, String> signLine3 = new HashMap<Location, String>();
	private Map<Integer, Location> saveregion = new HashMap<Integer, Location>();
	private Map<Integer, Location> saveregion1 = new HashMap<Integer, Location>();
	private Integer saveTotal;
	private Integer num;
	private boolean showing;
	private Integer showcount;
	private Map<Location , BlockState> showStore = new HashMap<Location, BlockState>();
	private Player seeing;
	private Integer totalDrops;
	private Integer dropTime;
	private Map<Integer, String> messages = new HashMap<Integer, String>();
	private Map<Integer, SignWall> signWalls = new HashMap<Integer, SignWall>();

	public void playerJoin(){
		Iterator<Integer> signs = signWalls.keySet().iterator();
		while(signs.hasNext()){
			signWalls.get(signs.next()).updateSigns(this.min, this.total, this.playerList);
		}
	}
	public void playerAdd(String addedPlayer){
		Iterator<Integer> signs = signWalls.keySet().iterator();
		while(signs.hasNext()){
			signWalls.get(signs.next()).updateSigns(this.min, this.total, this.playerList);
		}
	}
	
	public void countToTheEnd(){
		if (this.countToEnd != null){
			countToEnd++;
			if (countToEnd == 5){
				this.End();
				this.countToEnd = null;
			}
		}
	}
	
	public void RestoreBlocks(){
		this.started = false;
		ArenaFileManager.loadArenaFiles(this.name, 1);
	}
	public void counter(){
		if (this.counter != null){
			if (this.getCounter() == 10){
				this.onGameStart();
				Iterator<Integer> itr = this.signWalls.keySet().iterator();
				while (itr.hasNext()){
					Integer num = itr.next();
					this.signWalls.get(num).setProgress("Starting");
					this.signWalls.get(num).setStarted(true);
				}
			}
			if (this.getCounter() == 60){
				Iterator<Player> list = this.playerList.keySet().iterator();
				while(list.hasNext()){
					Player name = list.next();
					if (this.getPlayerList().get(name) != null){
						name.sendMessage(ChatColor.BLUE + "Game starting in 1 min!");
					}
				}
			}
			if (this.getCounter() == 30){
				Iterator<Player> list = this.playerList.keySet().iterator();
				while(list.hasNext()){
					Player name = list.next();
					if (this.getPlayerList().get(name) != null){
						name.sendMessage(ChatColor.BLUE + "Game starting in 30 seconds!");
					}
				}
			}
			else if (this.getCounter() <= 10 && this.getCounter() >= 1){
				Iterator<Player> list = this.playerList.keySet().iterator();
				while(list.hasNext()){
					Player name = list.next();
					if (this.getPlayerList().get(name) != null){
						name.sendMessage(ChatColor.BLUE + "Game starting in " + counter + " seconds!");
					}
				}
			}
			else if (this.getCounter() == 0){
				this.started = true;
				Iterator<Player> list = this.playerList.keySet().iterator();
				while(list.hasNext()){
					Player name = list.next();
					if (this.getPlayerList().get(name) != null){
						name.sendMessage(ChatColor.BLUE + "Goooo!");
						name.setGameMode(GameMode.SURVIVAL);
					}
				}
				for (Integer num : this.signWalls.keySet()){
					this.signWalls.get(num).setProgress("Dropping in:");
				}
			}
			if (this.counter == -this.getDropTime()){
				Iterator<Integer> itr = this.signWalls.keySet().iterator();
				while (itr.hasNext()){
					Integer num = itr.next();
					this.signWalls.get(num).setProgress("Walls Dropped");
					this.signWalls.get(num).setTime(null);
				}
				this.Dropwalls();
				Iterator<Player> list = this.playerList.keySet().iterator();
				while(list.hasNext()){
					Player name = list.next();
					if (this.getPlayerList().get(name) != null){
						name.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Let Those Walls Drop!!!!!");
					}
				}
			}
			if (this.messages.containsKey(dropTime + counter) && this.getCounter() < 0 && this.getCounter() > -this.getDropTime()){
				Integer seconds = dropTime + counter;
				Integer mins = 0;
				while (seconds >= 60){
					seconds = seconds - 60;
					mins++;
				}
				if (mins != 0 && seconds != 0){
					for (Player name : this.playerList.keySet()){
						if (this.getPlayerList().get(name) != null){
							name.sendMessage(ChatColor.RED + "Walls dropping in " + mins + " minutes and " + seconds + " seconds");
						}
					}
				}
				else if (mins == 0 && seconds != 0){
					for (Player name : this.playerList.keySet()){
						if (this.getPlayerList().get(name) != null){
							name.sendMessage(ChatColor.RED + "Walls dropping in " + seconds + " seconds");
						}
					}
				}
				else if (mins != 0 && seconds == 0){
					for (Player name : this.playerList.keySet()){
						if (this.getPlayerList().get(name) != null){
							name.sendMessage(ChatColor.RED + "Walls dropping in " + mins + " minutes");
						}
					}
				}
			}
			counter--;
				for (Integer num : this.signWalls.keySet()){
					if (this.counter > -this.dropTime && this.counter < 0){
						this.signWalls.get(num).setTime(this.dropTime + this.counter);
						this.signWalls.get(num).updateSigns(this.min, this.total, this.playerList);
					}
					else {
						this.signWalls.get(num).setTime(null);
						this.signWalls.get(num).updateSigns(this.min, this.total, this.playerList);
					}
				}
			}
		}
	
	public void Dropwalls(){
		for (Integer name : this.drop.keySet()){
			World world = this.drop.get(name).getWorld();
			world.getBlockAt(this.drop.get(name)).setTypeId(0);
		}
	}
	@SuppressWarnings("deprecation")
	public void Leave(){
		if (this.playerList.get(this.random) == "red"){
			this.redplayers--;
		}
		else if (this.playerList.get(this.random) == "blue"){
			this.blueplayers--;
		}
		else if (this.playerList.get(this.random) == "green"){
			this.greenplayers--;
		}
		else if (this.playerList.get(this.random) == "yellow"){
			this.yellowplayers--;
		}
		this.total--;
		this.random.setGameMode(GameMode.SURVIVAL);
		for (Integer num : this.signWalls.keySet()){
			this.signWalls.get(num).updateSigns(this.min, this.total, this.playerList);
		}
		this.random.getInventory().setContents(this.playerInventories.get(this.random));
		this.random.getInventory().setArmorContents(this.aurmor.get(this.random));
		this.random.updateInventory();
		this.random.teleport(this.lose);
		this.random.setLevel(this.exp.get(this.random));
		this.random.setFallDistance(0);
		this.random.setFireTicks(0);
	}
	public void onGameStart(){
		this.lobbyPlayers.clear();
		for (Player name : this.playerList.keySet()){
			Integer temp = Math.min(Math.min(this.redplayers , this.greenplayers), Math.min(this.blueplayers , this.yellowplayers));
			if (temp == this.redplayers){
				this.redplayers++;
				TagAPI.refreshPlayer(name);
				name.teleport(this.redspawns.get(this.redplayers));
				this.playerLocation.put(name , this.redspawns.get(this.redplayers));
				this.playerList.put(name, "red");
				name.sendMessage(ChatColor.RED + "You are on team red!");
				for (Integer num : this.signWalls.keySet()){
					Sign sign = (Sign) this.signWalls.get(num).getSignLocations().get(this.redplayers + 2).getBlock().getState();
					sign.setLine(0, "" + ChatColor.DARK_RED + name.getName());
					sign.update();
				}
			}
			else if (temp == this.blueplayers){
				this.blueplayers++;
				TagAPI.refreshPlayer(name);
				name.teleport(this.bluespawns.get(this.blueplayers));
				this.playerLocation.put(name , this.bluespawns.get(this.blueplayers));
				this.playerList.put(name, "blue");
				name.sendMessage(ChatColor.DARK_BLUE + "You are on team blue!");
				Iterator<Integer> itr = this.signWalls.keySet().iterator();
				while (itr.hasNext()){
					Integer num = itr.next();
					Sign sign = (Sign) this.signWalls.get(num).getSignLocations().get(this.blueplayers + 2).getBlock().getState();
					sign.setLine(1, "" + ChatColor.DARK_BLUE + name.getName());
					sign.update();
				}
			}
			else if (temp == this.greenplayers){
				this.greenplayers++;
				TagAPI.refreshPlayer(name);
				name.teleport(this.greenspawns.get(this.greenplayers));
				this.playerLocation.put(name , this.greenspawns.get(this.greenplayers));
				this.playerList.put(name, "green");
				name.sendMessage(ChatColor.GREEN + "You are on team green!");
				Iterator<Integer> itr = this.signWalls.keySet().iterator();
				while (itr.hasNext()){
					Integer num = itr.next();
					Sign sign = (Sign) this.signWalls.get(num).getSignLocations().get(this.greenplayers + 2).getBlock().getState();
					sign.setLine(2, "" + ChatColor.DARK_GREEN + name.getName());
					sign.update();
				}
			}
			else if (temp == yellowplayers){
				this.yellowplayers++;
				TagAPI.refreshPlayer(name);
				name.teleport(this.yellowspawns.get(this.yellowplayers));
				this.playerLocation.put(name , this.yellowspawns.get(this.yellowplayers));
				this.playerList.put(name, "yellow");
				name.sendMessage(ChatColor.YELLOW + "You are on team yellow!");
				Iterator<Integer> itr = this.signWalls.keySet().iterator();
				while (itr.hasNext()){
					Integer num = itr.next();
					Sign sign = (Sign) this.signWalls.get(num).getSignLocations().get(this.yellowplayers + 2).getBlock().getState();
					sign.setLine(3, "" + ChatColor.YELLOW + name.getName());
					sign.update();
				}
			}
			name.setHealth(20);
			name.setFoodLevel(20);
			name.setFireTicks(0);
		}
	}
	public void CreateDropLocation(){
		Location start = this.tempLoc;
		Location end = this.tempLoc1;
		World world = this.tempLoc1.getWorld();
		Integer minX = (int) Math.min(start.getX() , end.getX());
		Integer maxX = (int) Math.max(start.getX() , end.getX());
		Integer minY = (int) Math.min(start.getY() , end.getY());
		Integer maxY = (int) Math.max(start.getY() , end.getY());
		Integer minZ = (int) Math.min(start.getZ() , end.getZ());
		Integer maxZ = (int) Math.max(start.getZ() , end.getZ());
		this.totalDrops++;
		this.droploc1.put(this.totalDrops, this.tempLoc);
		this.droploc2.put(this.totalDrops, this.tempLoc1);
		for (int x = minX; x <= maxX; ++x) {
			for (int y = minY; y <= maxY; ++y) {
				for (int z = minZ; z <= maxZ; ++z) {
					this.drops++;
					this.drop.put(this.drops , world.getBlockAt(x, y, z).getLocation());
				}
			}
		}
	}
	public void broadcastWinner(){
		if (this.redplayers == this.total){
			Bukkit.broadcastMessage(ChatColor.RED + "Red team has won the arena on arena " + this.getName());
		}
		else if (this.blueplayers == this.total){
			Bukkit.broadcastMessage(ChatColor.BLUE + "Blue team has won the arena on arena " + this.getName());
		}
		else if (this.greenplayers == this.total){
			Bukkit.broadcastMessage(ChatColor.GREEN + "Green team has won the arena on arena " + this.getName());
		}
		else if (this.yellowplayers == this.total){
			Bukkit.broadcastMessage(ChatColor.YELLOW + "Yellow team has won the arena on arena " + this.getName());	
		}
	}
	
	public void End(){
		this.playerList.clear();
		this.redplayers = 0;
		this.blueplayers = 0;
		this.greenplayers = 0;
		this.yellowplayers = 0;
		this.started = false;
		this.total = 0;
		this.RestoreBlocks();
		this.counter = null;
		this.blocksLocation.clear();
		this.blockstype.clear();
		this.playerList.clear();
		for (Integer num : this.signWalls.keySet()){
			this.signWalls.get(num).setTime(null);
			this.signWalls.get(num).updateSigns(this.min, this.total, this.playerList);
		}
		for (Entity entity : this.loc1.getWorld().getEntities()){
			Integer minX = (int) Math.min(this.loc1.getX() , this.loc2.getX());
			Integer maxX = (int) Math.max(this.loc1.getX() , this.loc2.getX());
			Integer minY = (int) Math.min(this.loc1.getY() , this.loc2.getY());
			Integer maxY = (int) Math.max(this.loc1.getY() , this.loc2.getY());
			Integer minZ = (int) Math.min(this.loc1.getZ() , this.loc2.getZ());
			Integer maxZ = (int) Math.max(this.loc1.getZ() , this.loc2.getZ());
			if (entity.getLocation().getBlockX() < maxX || entity.getLocation().getBlockX() > minX){
				if (entity.getLocation().getBlockY() < maxY || entity.getLocation().getBlockY() > minY){
					if (entity.getLocation().getBlockZ() < maxZ || entity.getLocation().getBlockZ() > minZ){
						if (entity.getType() != EntityType.PLAYER){
							entity.remove();
						}
					}
				}
			}
		}
	}
	
	public void showRegion(){
		this.showing = true;
		this.showcount = 5;
		Player player = this.seeing;
		World world = saveregion.get(num).getWorld();
		for (int x = (int)this.saveregion1.get(num).getX(); x <= (int)this.saveregion.get(num).getX(); ++x) {
			this.showStore.put(world.getBlockAt(x, (int)saveregion.get(num).getY(), (int)saveregion.get(num).getZ()).getLocation(), world.getBlockAt(x, (int)saveregion.get(num).getY(), (int)saveregion.get(num).getZ()).getState());
			this.showStore.put(world.getBlockAt(x, (int)saveregion.get(num).getY(), (int)saveregion1.get(num).getZ()).getLocation(), world.getBlockAt(x, (int)saveregion.get(num).getY(), (int)saveregion1.get(num).getZ()).getState());
			this.showStore.put(world.getBlockAt(x, (int)saveregion1.get(num).getY(), (int)saveregion.get(num).getZ()).getLocation(), world.getBlockAt(x, (int)saveregion1.get(num).getY(), (int)saveregion.get(num).getZ()).getState());
			this.showStore.put(world.getBlockAt(x, (int)saveregion1.get(num).getY(), (int)saveregion1.get(num).getZ()).getLocation(), world.getBlockAt(x, (int)saveregion1.get(num).getY(), (int)saveregion1.get(num).getZ()).getState());
			player.sendBlockChange(world.getBlockAt(x, (int)saveregion1.get(num).getY(), (int)saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt(x, (int)saveregion1.get(num).getY(), (int)saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt(x, (int)saveregion.get(num).getY(), (int)saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt(x, (int)saveregion.get(num).getY(), (int)saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
		}
		for (int y = (int)this.saveregion1.get(num).getY(); y <= (int)this.saveregion.get(num).getY(); ++y) {
			if (world.getBlockAt((int)saveregion.get(num).getX() , y,(int)saveregion.get(num).getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion.get(num).getX(), y,(int)saveregion.get(num).getZ()).getLocation(), world.getBlockAt((int)saveregion.get(num).getX(), y, (int)saveregion.get(num).getZ()).getState());
			}
			if (world.getBlockAt((int)saveregion.get(num).getX(), y,(int)saveregion1.get(num).getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion.get(num).getX(), y,(int)saveregion1.get(num).getZ()).getLocation(), world.getBlockAt((int)saveregion.get(num).getX(), y, (int)saveregion1.get(num).getZ()).getState());
			}
			if (world.getBlockAt((int)saveregion1.get(num).getX(), y,(int)saveregion.get(num).getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion1.get(num).getX(), y,(int)saveregion.get(num).getZ()).getLocation(), world.getBlockAt((int)saveregion1.get(num).getX(), y, (int)saveregion.get(num).getZ()).getState());
			}
			if (world.getBlockAt((int)saveregion1.get(num).getX(), y,(int)saveregion1.get(num).getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion1.get(num).getX(), y,(int)saveregion1.get(num).getZ()).getLocation(), world.getBlockAt((int)saveregion1.get(num).getX(), y, (int)saveregion1.get(num).getZ()).getState());
			}
			player.sendBlockChange(world.getBlockAt((int)saveregion1.get(num).getX(), y,(int)saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)saveregion1.get(num).getX(), y,(int)saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)saveregion.get(num).getX(), y,(int)saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)saveregion.get(num).getX(), y,(int)saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
		}
		for (int z = (int)this.saveregion1.get(num).getZ(); z <= (int)this.saveregion.get(num).getZ(); ++z) {
			if (world.getBlockAt((int)saveregion.get(num).getX(), (int)saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion.get(num).getX(), (int)saveregion.get(num).getY(), z).getLocation(), world.getBlockAt((int)saveregion.get(num).getX(), (int)saveregion.get(num).getY(), z).getState());
			}
			if (world.getBlockAt((int)saveregion.get(num).getX(), (int)saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion.get(num).getX(), (int)saveregion1.get(num).getY(), z).getLocation(), world.getBlockAt((int)saveregion.get(num).getX(), (int)saveregion1.get(num).getY(), z).getState());
			}
			if (world.getBlockAt((int)saveregion1.get(num).getX(), (int)saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion1.get(num).getX(), (int)saveregion.get(num).getY(), z).getLocation(), world.getBlockAt((int)saveregion1.get(num).getX(), (int)saveregion.get(num).getY(), z).getState());
			}
			if (world.getBlockAt((int)saveregion1.get(num).getX(), (int)saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)saveregion1.get(num).getX(), (int)saveregion1.get(num).getY(), z).getLocation(), world.getBlockAt((int)saveregion1.get(num).getX(), (int)saveregion1.get(num).getY(), z).getState());
			}
			player.sendBlockChange(world.getBlockAt((int)saveregion1.get(num).getX(),(int)saveregion1.get(num).getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)saveregion1.get(num).getX(),(int)saveregion.get(num).getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)saveregion.get(num).getX(),(int)saveregion1.get(num).getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)saveregion.get(num).getX(),(int)saveregion.get(num).getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
		}
	}
	
	public void saveRegionRestore(){
		if (this.showcount != null){
			this.showcount--;
			if (this.showcount == 0){
				this.showcount = null;
				this.showing = false;
				Iterator<Location> location = this.showStore.keySet().iterator();
				while (location.hasNext()){
					Location loc = location.next();
					Player player = this.seeing;
					player.sendBlockChange(loc, this.showStore.get(loc).getType(), (byte)0);
				}
				this.showStore.clear();
				this.seeing = null;
			}
		}
	}
	public void ShowArena(){
		World world = loc2.getWorld();
		Integer maxX = Math.max((int)loc1.getX(), (int)loc2.getX());
		Integer minX = Math.min((int)loc1.getX(), (int)loc2.getX());
		Integer maxY = Math.max((int)loc1.getY(), (int)loc2.getY());
		Integer minY = Math.min((int)loc1.getY(), (int)loc2.getY());
		Integer maxZ = Math.max((int)loc1.getZ(), (int)loc2.getZ());
		Integer minZ = Math.min((int)loc1.getZ(), (int)loc2.getZ());
		Location loc3 = world.getBlockAt(maxX, maxY, maxZ).getLocation();
		Location loc4 = world.getBlockAt(minX, minY, minZ).getLocation();
		this.showing = true;
		this.showcount = 5;
		Player player = this.seeing;
		for (int x = (int)loc4.getX(); x <= (int)loc3.getX(); ++x) {
			this.showStore.put(world.getBlockAt(x, (int)loc3.getY(), (int)loc3.getZ()).getLocation(), world.getBlockAt(x, (int)loc3.getY(), (int)loc3.getZ()).getState());
			this.showStore.put(world.getBlockAt(x, (int)loc3.getY(), (int)loc4.getZ()).getLocation(), world.getBlockAt(x, (int)loc3.getY(), (int)loc4.getZ()).getState());
			this.showStore.put(world.getBlockAt(x, (int)loc4.getY(), (int)loc3.getZ()).getLocation(), world.getBlockAt(x, (int)loc4.getY(), (int)loc3.getZ()).getState());
			this.showStore.put(world.getBlockAt(x, (int)loc4.getY(), (int)loc4.getZ()).getLocation(), world.getBlockAt(x, (int)loc4.getY(), (int)loc4.getZ()).getState());
			player.sendBlockChange(world.getBlockAt(x, (int)loc3.getY(), (int)loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt(x, (int)loc3.getY(), (int)loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt(x, (int)loc4.getY(), (int)loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt(x, (int)loc4.getY(), (int)loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
		}
	
		for (int y = (int)loc4.getY(); y <= (int)loc3.getY(); ++y) {
			if (world.getBlockAt((int)loc3.getX(), y,(int)loc3.getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc3.getX(), y,(int)loc3.getZ()).getLocation(), world.getBlockAt((int)loc3.getX(), y, (int)loc3.getZ()).getState());
			}
			if (world.getBlockAt((int)loc3.getX(), y,(int)loc4.getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc3.getX(), y,(int)loc4.getZ()).getLocation(), world.getBlockAt((int)loc3.getX(), y, (int)loc4.getZ()).getState());
			}
			if (world.getBlockAt((int)loc4.getX(), y,(int)loc3.getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc4.getX(), y,(int)loc3.getZ()).getLocation(), world.getBlockAt((int)loc4.getX(), y, (int)loc3.getZ()).getState());
			}
			if (world.getBlockAt((int)loc4.getX(), y,(int)loc4.getZ()).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc4.getX(), y,(int)loc4.getZ()).getLocation(), world.getBlockAt((int)loc4.getX(), y, (int)loc4.getZ()).getState());
			}
			player.sendBlockChange(world.getBlockAt((int)loc4.getX(), y, (int)loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)loc4.getX(), y, (int)loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)loc3.getX(), y, (int)loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)loc3.getX(), y, (int)loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte)0);
		}
		for (int z = (int)loc4.getZ(); z <= (int)loc3.getZ(); ++z) {
			if (world.getBlockAt((int)loc3.getX(), (int)loc3.getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc3.getX(), (int)loc3.getY(), z).getLocation(), world.getBlockAt((int)loc3.getX(), (int)loc3.getY(), z).getState());
			}
			if (world.getBlockAt((int)loc3.getX(), (int)loc4.getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc3.getX(), (int)loc4.getY(), z).getLocation(), world.getBlockAt((int)loc3.getX(), (int)loc4.getY(), z).getState());
			}
			if (world.getBlockAt((int)loc4.getX(), (int)loc3.getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc4.getX(), (int)loc3.getY(), z).getLocation(), world.getBlockAt((int)loc4.getX(), (int)loc3.getY(), z).getState());
			}
			if (world.getBlockAt((int)loc4.getX(), (int)loc4.getY(), z).getType() != Material.GLOWSTONE){
				this.showStore.put(world.getBlockAt((int)loc4.getX(), (int)loc4.getY(), z).getLocation(), world.getBlockAt((int)loc4.getX(), (int)loc4.getY(), z).getState());
			}
			player.sendBlockChange(world.getBlockAt((int)loc3.getX(),(int)loc3.getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)loc3.getX(),(int)loc4.getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)loc4.getX(),(int)loc3.getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
			player.sendBlockChange(world.getBlockAt((int)loc4.getX(),(int)loc4.getY(),z).getLocation(), Material.GLOWSTONE, (byte)0);
		}
	}
	
	public Integer getTotalDrops() {
		return totalDrops;
	}
	public void setTotalDrops(Integer totalDrops) {
		this.totalDrops = totalDrops;
	}
	public Integer getDropTime() {
		return dropTime;
	}
	public void setDropTime(Integer dropTime) {
		this.dropTime = dropTime;
	}
	public Map<Integer, String> getMessages() {
		return messages;
	}
	public void setMessages(Map<Integer, String> messages) {
		this.messages = messages;
	}
	public Map<Integer, SignWall> getSignWalls() {
		return signWalls;
	}
	public void setSignWalls(Map<Integer , SignWall> signWalls) {
		this.signWalls = signWalls;
	}
	public Set<Player> getLobbyPlayers() {
		return lobbyPlayers;
	}
	public void setLobbyPlayers(Set<Player> lobbyPlayers) {
		this.lobbyPlayers = lobbyPlayers;
	}
	public Integer getCountToEnd() {
		return countToEnd;
	}
	public void setCountToEnd(Integer countToEnd) {
		this.countToEnd = countToEnd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Location getLoc1() {
		return loc1;
	}
	public void setLoc1(Location loc1) {
		this.loc1 = loc1;
	}
	public Location getLoc2() {
		return loc2;
	}
	public void setLoc2(Location loc2) {
		this.loc2 = loc2;
	}
	public Map<Integer, Location> getBlocksLocation() {
		return blocksLocation;
	}
	public void setBlocksLocation(Map<Integer, Location> blocksLocation) {
		this.blocksLocation = blocksLocation;
	}
	public Map<Integer, BlockState> getBlockstype() {
		return blockstype;
	}
	public void setBlockstype(Map<Integer, BlockState> blockstype) {
		this.blockstype = blockstype;
	}
	public Integer getBlocknumber() {
		return blocknumber;
	}
	public void setBlocknumber(Integer blocknumber) {
		this.blocknumber = blocknumber;
	}
	public Location getTempLoc() {
		return tempLoc;
	}
	public void setTempLoc(Location tempLoc) {
		this.tempLoc = tempLoc;
	}
	public Location getTempLoc1() {
		return tempLoc1;
	}
	public void setTempLoc1(Location tempLoc1) {
		this.tempLoc1 = tempLoc1;
	}
	public String getTemp() {
		return temp;
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public Location getLobby() {
		return lobby;
	}
	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}
	public void addRedSpawn(){
		this.reds++;
		this.redspawns.put(this.reds , this.playerLoc);
	}
	public void addBlueSpawn(){
		this.blues++;
		this.bluespawns.put(this.blues , this.playerLoc);
	}
	public void addGreenSpawn(){
		this.greens++;
		this.greenspawns.put(this.greens , this.playerLoc);
	}
	public void addYellowSpawn(){
		this.yellows++;
		this.yellowspawns.put(this.yellows , this.playerLoc);
	}
	public void min(){
		this.min = (int) Math.min(Math.min(this.reds, this.blues), Math.min(this.yellows, this.greens));
	}
	public Boolean getStarted() {
		return started;
	}
	public void setStarted(Boolean started) {
		this.started = started;
	}
	public Location getWin() {
		return win;
	}
	public void setWin(Location win) {
		this.win = win;
	}
	public Location getLose() {
		return lose;
	}
	public void setLose(Location lose) {
		this.lose = lose;
	}
	public Map<Integer, Location> getRedspawns() {
		return redspawns;
	}
	public void setRedspawns(Map<Integer, Location> redspawns) {
		this.redspawns = redspawns;
	}
	public Map<Integer, Location> getBluespawns() {
		return bluespawns;
	}
	public void setBluespawns(Map<Integer, Location> bluespawns) {
		this.bluespawns = bluespawns;
	}
	public Map<Integer, Location> getYellowspawns() {
		return yellowspawns;
	}
	public void setYellowspawns(Map<Integer, Location> yellowspawns) {
		this.yellowspawns = yellowspawns;
	}
	public Map<Integer, Location> getGreenspawns() {
		return greenspawns;
	}
	public void setGreenspawns(Map<Integer, Location> greenspawns) {
		this.greenspawns = greenspawns;
	}
	public Integer getReds() {
		return reds;
	}
	public void setReds(Integer reds) {
		this.reds = reds;
	}
	public Integer getBlues() {
		return blues;
	}
	public void setBlues(Integer blues) {
		this.blues = blues;
	}
	public Integer getYellows() {
		return yellows;
	}
	public void setYellows(Integer yellows) {
		this.yellows = yellows;
	}
	public Integer getGreens() {
		return greens;
	}
	public void setGreens(Integer greens) {
		this.greens = greens;
	}
	public Location getPlayerLoc() {
		return playerLoc;
	}
	public void setPlayerLoc(Location playerLoc) {
		this.playerLoc = playerLoc;
	}
	public Integer getMin() {
		return min;
	}
	public void setMin(Integer min) {
		this.min = min;
	}
	public Map<Player, String> getPlayerList() {
		return playerList;
	}
	public void setPlayerList(Map<Player, String> playerList) {
		this.playerList = playerList;
	}
	public Integer getRedplayers() {
		return redplayers;
	}
	public void setRedplayers(Integer redplayers) {
		this.redplayers = redplayers;
	}
	public Integer getBlueplayers() {
		return blueplayers;
	}
	public void setBlueplayers(Integer blueplayers) {
		this.blueplayers = blueplayers;
	}
	public Integer getGreenplayers() {
		return greenplayers;
	}
	public void setGreenplayers(Integer greenplayers) {
		this.greenplayers = greenplayers;
	}
	public Integer getYellowplayers() {
		return yellowplayers;
	}
	public void setYellowplayers(Integer yellowplayers) {
		this.yellowplayers = yellowplayers;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public boolean isShowing() {
		return showing;
	}
	public void setShowing(boolean showing) {
		this.showing = showing;
	}
	public Integer getShowcount() {
		return showcount;
	}
	public void setShowcount(Integer showcount) {
		this.showcount = showcount;
	}
	public Map<Location , BlockState> getShowStore() {
		return showStore;
	}
	public void setShowStore(Map<Location , BlockState> showStore) {
		this.showStore = showStore;
	}
	
	public void setSeeing(Player seeing) {
		this.seeing = seeing;
	}
	
	public Player getSeeing(){
		return seeing;
	}
	public Player getRandom() {
		return random;
	}
	public void setRandom(Player random) {
		this.random = random;
	}
	public Map<Integer, Location> getDrop() {
		return drop;
	}
	public void setDrop(Map<Integer, Location> drop) {
		this.drop = drop;
	}
	public Integer getDrops() {
		return drops;
	}
	public void setDrops(Integer drops) {
		this.drops = drops;
	}
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Map<Player, Location> getPlayerLocation() {
		return playerLocation;
	}
	public void setPlayerLocation(Map<Player, Location> playerLocation) {
		this.playerLocation = playerLocation;
	}
	public Map<Player, String> getDeadplayers() {
		return deadplayers;
	}
	public void setDeadplayers(Map<Player, String> deadplayers) {
		this.deadplayers = deadplayers;
	}

	public Map<Location, ItemStack[]> getChests() {
		return chests;
	}
	public void setChests(Map<Location, ItemStack[]> chests) {
		this.chests = chests;
	}
	public Map<Player, ItemStack[]> getPlayerInventories() {
		return playerInventories;
	}
	public void setPlayerInventories(Map<Player, ItemStack[]> playerInventories) {
		this.playerInventories = playerInventories;
	}
	public Map<Integer, Location> getDroploc1() {
		return droploc1;
	}
	public void setDroploc1(Map<Integer, Location> droploc1) {
		this.droploc1 = droploc1;
	}
	public Map<Integer, Location> getDroploc2() {
		return droploc2;
	}
	public void setDroploc2(Map<Integer, Location> droploc2) {
		this.droploc2 = droploc2;
	}
	public Map<Player, ItemStack[]> getAurmor() {
		return aurmor;
	}
	public void setAurmor(Map<Player, ItemStack[]> aurmor) {
		this.aurmor = aurmor;
	}
	public Map<Player, Integer> getExp() {
		return exp;
	}
	public void setExp(Map<Player, Integer> exp) {
		this.exp = exp;
	}
	public Map<Location, String> getSignLine0() {
		return signLine0;
	}
	public void setSignLine0(Map<Location, String> signLine0) {
		this.signLine0 = signLine0;
	}
	public Map<Location, String> getSignLine1() {
		return signLine1;
	}
	public void setSignLine1(Map<Location, String> signLine1) {
		this.signLine1 = signLine1;
	}
	public Map<Location, String> getSignLine2() {
		return signLine2;
	}
	public void setSignLine2(Map<Location, String> signLine2) {
		this.signLine2 = signLine2;
	}
	public Map<Location, String> getSignLine3() {
		return signLine3;
	}
	public void setSignLine3(Map<Location, String> signLine3) {
		this.signLine3 = signLine3;
	}
	public Map<Integer, Location> getSaveregion() {
		return saveregion;
	}
	public void setSaveregion(Map<Integer, Location> saveregion) {
		this.saveregion = saveregion;
	}
	public Map<Integer, Location> getSaveregion1() {
		return saveregion1;
	}
	public void setSaveregion1(Map<Integer, Location> saveregion1) {
		this.saveregion1 = saveregion1;
	}
	public Integer getSaveTotal() {
		return saveTotal;
	}
	public void setSaveTotal(Integer saveTotal) {
		this.saveTotal = saveTotal;
	}
	public Integer getCounter() {
		return counter;
	}
	public void setCounter(Integer counter) {
		this.counter = counter;
	}
	public void JoinRed(){
		this.playerList.put(this.random , "Red");
	}
	public void JoinBlue(){
		this.playerList.put(this.random , "Blue");
	}
	public void JoinGreen(){
		this.playerList.put(this.random , "Green");
	}
	public void JoinYellow(){
		this.playerList.put(this.random , "Yellow");
	}
	public Integer getFiles() {
		return files;
	}
	public void setFiles(Integer files) {
		this.files = files;
	}
	public boolean isResetting() {
		return resetting;
	}
	public void setResetting(boolean resetting) {
		this.resetting = resetting;
	}
	public Map<Player,Integer> getInvinsibleTimer() {
		return invinsibleTimer;
	}
	public void setInvinsibleTimer(Map<Player,Integer> invinsibleTimer) {
		this.invinsibleTimer = invinsibleTimer;
	}
}