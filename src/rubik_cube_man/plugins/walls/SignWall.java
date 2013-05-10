package rubik_cube_man.plugins.walls;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;

public class SignWall {

	private Map<Integer, Location> signLocations = new HashMap<Integer, Location>();
	private Location startSign;
	private byte rotation;
	private String arena;
	private Integer min;
	private Location clickSign;
	private boolean started;
	private String progress;
	private Integer time;
	
	public SignWall(Location startSign, byte rotation, String arena, Integer min, String progress){
		this.startSign = startSign;
		this.rotation = rotation;
		this.arena = arena;
		this.min = min;
		this.progress = progress;
	}
	
	public void Create(){
		Integer loops = this.min + 2;
		Location location = this.startSign;
		for (int i = 0; i <= loops; ++i){
			if (this.rotation == 4){
				location.setZ(location.getZ() + i);
			}
			else if (this.rotation == 2){
				location.setX(location.getX() - i);
			}
			else if (this.rotation == 3){
				location.setX(location.getX() + i);
			}
			else if (this.rotation == 5){
				location.setZ(location.getZ() - i);
			}
			int X = (int) location.getX();
			int Y = (int) location.getY();
			int Z = (int) location.getZ();
			World world = location.getWorld();
			if (this.rotation == 4){
				X++;
			}
			else if (this.rotation == 2){
				Z++;
			}
			else if (this.rotation == 3){
				Z--;
			}
			else if (this.rotation == 5){
				X--;
			}
			if (world.getBlockAt(X,Y,Z).getType() == Material.AIR || world.getBlockAt(X, Y, Z).isLiquid()){
				world.getBlockAt(X,Y,Z).setType(Material.BRICK);
			}
			location.getBlock().setType(Material.WALL_SIGN);
			Sign signs = (Sign) location.getBlock().getState();
			signs.setRawData(this.rotation);
			if (i == 0){
				signs.setLine(0, ChatColor.BOLD + "The Walls");
				signs.setLine(1, ChatColor.DARK_GRAY + "Click to Join");
				signs.setLine(2, ChatColor.UNDERLINE + "" + this.arena);
				Integer maxPlayers = min * 4;
				signs.setLine(3, ChatColor.DARK_GRAY + "0/" + maxPlayers);
				this.clickSign = location;
			}
			if (i == 1){
				signs.setLine(0, ChatColor.RED + "Status");
				signs.setLine(1, ChatColor.BOLD + "Waiting");
				signs.setLine(2, "");
				signs.setLine(3, "");
			}
			if (i == 2){
				signs.setLine(0, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Red" + ChatColor.WHITE + ":");
				signs.setLine(1, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Blue" + ChatColor.WHITE + ":");
				signs.setLine(2, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Green" + ChatColor.WHITE + ":");
				signs.setLine(3, ChatColor.YELLOW + "" + ChatColor.BOLD + "Yellow" + ChatColor.WHITE + ":");
			}
			signs.update();
			if (this.rotation == 4){
				location.setZ(location.getZ() - i);
			}
			else if (this.rotation == 2){
				location.setX(location.getX() + i);
			}
			else if (this.rotation == 3){
				location.setX(location.getX() - i);
			}
			else if (this.rotation == 5){
				location.setZ(location.getZ() + i);
			}
		}
		Location loc = location;
		World world = loc.getWorld();
		int X = (int) loc.getX();
		int Y = (int) loc.getY();
		int Z = (int) loc.getZ();
		int i = 0;
		if (this.rotation == 3){
			int loopForXPlus = (int) (this.startSign.getX() + loops);
				for (X = (int) this.startSign.getX(); X <= loopForXPlus; ++ X){
					this.signLocations.put(i, world.getBlockAt(X,Y,Z).getLocation());
					i++;
				}
			}
		else if (this.rotation == 2){
			int loopForXMinus = (int) (this.startSign.getX() - loops);
				for (X = (int) this.startSign.getX(); X >= loopForXMinus; -- X){
					this.signLocations.put(i, world.getBlockAt(X,Y,Z).getLocation());
					i++;
				}
			}
		else if (this.rotation == 4){
			int loopForZPlus = (int) (this.startSign.getZ() + loops);
				for (Z = (int) this.startSign.getZ(); Z <= loopForZPlus; ++ Z){
					this.signLocations.put(i, world.getBlockAt(X,Y,Z).getLocation());
					i++;
				}
			}
		else if (this.rotation == 5){
			int loopForZMinus = (int) (this.startSign.getZ() - loops);
				for (Z = (int) this.startSign.getZ(); Z >= loopForZMinus; -- Z){
					this.signLocations.put(i, world.getBlockAt(X,Y,Z).getLocation());
					i++;
				}
			}
		}
	public void updateSigns(Integer min, Integer total, Map<Player, String> players){
		Iterator<Integer> itr = this.signLocations.keySet().iterator();
		while (itr.hasNext()){
			Integer num = itr.next();
			int X = (int) this.signLocations.get(num).getX();
			int Y = (int) this.signLocations.get(num).getY();
			int Z = (int) this.signLocations.get(num).getZ();
			World world = this.signLocations.get(num).getWorld();
			if (this.rotation == 4){
				X++;
			}
			else if (this.rotation == 2){
				Z++;
			}
			else if (this.rotation == 3){
				Z--;
			}
			else if (this.rotation == 5){
				X--;
			}
			if (world.getBlockAt(X,Y,Z).getType() == Material.AIR){
				world.getBlockAt(X,Y,Z).setType(Material.BRICK);
			}
			this.signLocations.get(num).getBlock().setType(Material.WALL_SIGN);
			Sign sign = (Sign) this.signLocations.get(num).getBlock().getState();
			sign.setRawData(this.rotation);
			sign.update();
			if (num == 2){
				sign.setLine(0, ChatColor.DARK_RED + "" + ChatColor.BOLD + "Red" + ChatColor.WHITE + ":");
				sign.setLine(1, ChatColor.DARK_BLUE + "" + ChatColor.BOLD + "Blue" + ChatColor.WHITE + ":");
				sign.setLine(2, ChatColor.DARK_GREEN + "" + ChatColor.BOLD + "Green" + ChatColor.WHITE + ":");
				sign.setLine(3, ChatColor.YELLOW + "" + ChatColor.BOLD + "Yellow" + ChatColor.WHITE + ":");
			}
			else if (num == 1){
				if (this.time != null){
					if (this.started == true){
						Integer minu = 0;
						Integer sec = this.time;
						while (sec >= 60){
							minu++;
							sec = sec - 60;
						}
						sign.setLine(2, minu + ":" + String.format("%02d", sec));
					}
				}
				else{
					sign.setLine(2, "");
					sign.update();
				}
				sign.setLine(0, ChatColor.RED + "Status");
				sign.setLine(1, ChatColor.BOLD + "" + this.progress);
			}
			else if (num == 0){
				sign.setLine(0, ChatColor.BOLD + "The Walls");
				sign.setLine(1, ChatColor.DARK_GRAY + "Click to Join");
				sign.setLine(2, ChatColor.UNDERLINE + "" + this.arena);
				Integer maxPlayers = min * 4;
				sign.setLine(3, ChatColor.DARK_GRAY + "" + total + "/" + maxPlayers);
			}
			if (num > 2){
				sign.setLine(0, "");
				sign.setLine(1, "");
				sign.setLine(2, "");
				sign.setLine(3, "");
			}
			sign.update();
		}
		Iterator<Player> itr2 = players.keySet().iterator();
		int red = 0;
		int blue = 0;
		int green = 0;
		int yellow = 0;
		while (itr2.hasNext()){
			Player player = itr2.next();
			if (started == true){
				if (players.get(player) == "red"){
					Sign sign  = (Sign) this.signLocations.get(red + 3).getBlock().getState();
					sign.setLine(0, ChatColor.DARK_RED + player.getName());
					sign.update();
					red++;
				}
				else if (players.get(player) == "blue"){
					Sign sign  = (Sign) this.signLocations.get(blue + 3).getBlock().getState();
					sign.setLine(1, ChatColor.DARK_BLUE + player.getName());
					sign.update();
					blue++;
				}
				else if (players.get(player) == "green"){
					Sign sign  = (Sign) this.signLocations.get(green + 3).getBlock().getState();
					sign.setLine(2, ChatColor.DARK_GREEN + player.getName());
					sign.update();
					green++;
				}
				else if (players.get(player) == "yellow"){
					Sign sign  = (Sign) this.signLocations.get(yellow + 3).getBlock().getState();
					sign.setLine(3, ChatColor.YELLOW + player.getName());
					sign.update();
					yellow++;
				}
			}
			else {
				Sign sign = (Sign) this.signLocations.get(red + 3).getBlock().getState();
				sign.setLine(blue, player.getName() + "");
				if (blue == 3){
					blue = 0;
					red++;
				}
				else {
					blue++;
				}
				sign.update();
			}
		}
	}
	
	public void addPlayerSign(Integer min){
		if (this.min < min){
			int X = (int) this.signLocations.get(this.min + 2).getX();
			int Y = (int) this.signLocations.get(this.min + 2).getY();
			int Z = (int) this.signLocations.get(this.min + 2).getZ();
			World world = this.signLocations.get(this.min + 2).getWorld();
			if (this.rotation == 4){
				Z++;
			}
			else if (this.rotation == 2){
				X--;
			}
			else if (this.rotation == 3){
				X++;
			}
			else if (this.rotation == 5){
				Z--;
			}
			this.min++;
			Location loc = world.getBlockAt(X, Y, Z).getLocation();
			this.signLocations.put(this.min + 2, loc);
			loc.getBlock().setType(Material.WALL_SIGN);
			Sign sign = (Sign) loc.getBlock().getState();
			sign.setRawData(this.rotation);
			sign.update();
		}
	}
	
	public Location getStartSign() {
		return startSign;
	}

	public void setStartSign(Location startSign) {
		this.startSign = startSign;
	}

	public byte getRotation() {
		return rotation;
	}

	public void setRotation(byte rotation) {
		this.rotation = rotation;
	}

	public Location getClickSign() {
		return clickSign;
	}

	public void setClickSign(Location clickSign) {
		this.clickSign = clickSign;
	}
	
	public Map<Integer, Location> getSignLocations(){
		return signLocations;
	}

	public boolean isStarted() {
		return started;
	}

	public void setStarted(boolean started) {
		this.started = started;
		if (started = true){
			Sign sign = (Sign) this.signLocations.get(1).getBlock().getState();
			sign.setLine(1, ChatColor.BOLD + "Starting");
			sign.update();
		}
		else {
			Sign sign = (Sign) this.signLocations.get(1).getBlock().getState();
			sign.setLine(1, ChatColor.BOLD + "Waiting");
			sign.update();
		}
	}

	public Integer getTime() {
		return time;
	}

	public void setTime(Integer time) {
		this.time = time;
	}

	public String getProgress() {
		return progress;
	}

	public void setProgress(String progress) {
		this.progress = progress;
	}
	
	public String getArena(){
		return arena;
	}
}
