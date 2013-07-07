package rubik_cube_man.plugins.walls;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rubik_cube_man.plugins.walls.blockFileData.ArenaFileManager;
import rubik_cube_man.plugins.walls.kits.KitFile;
import rubik_cube_man.plugins.walls.signs.SignChangeListener;
import rubik_cube_man.plugins.walls.signs.SignWall;
import rubik_cube_man.plugins.walls.updatechecking.UpdateChecker;

/*
*	The Walls Minigame Copyright (C) 2013 rubik_cube_man
*	This program comes with ABSOLUTELY NO WARRANTY; for details type `show w'.
*	This is free software, and you are welcome to redistribute it
*	under certain conditions; type `show c' for details.
*	
*	The hypothetical commands `show w' and `show c' should show the appropriate
*	parts of the General Public License.  Of course, your program's commands
*	might be different; for a GUI interface, you would use an "about box".
*	
*	You should also get your employer (if you work as a programmer) or school,
*	if any, to sign a "copyright disclaimer" for the program, if necessary.
*	For more information on this, and how to apply and follow the GNU GPL, see
*	<http://www.gnu.org/licenses/>.
*	
*	The GNU General Public License does not permit incorporating your program
*	into proprietary programs.  If your program is a subroutine library, you
*	may consider it more useful to permit linking proprietary applications with
*	the library.  If this is what you want to do, use the GNU Lesser General
*	Public License instead of this License.  But first, please read
*	 <http://www.gnu.org/philosophy/why-not-lgpl.html>.
*/
public class Walls extends JavaPlugin implements Serializable{

	private static final long serialVersionUID = 19204106755649487L;
	protected Logger log;
	protected UpdateChecker updateChecker;
	public static Plugin plugin;
	public Map<String, Arena> arenas = new HashMap<String, Arena>();
	public Map<Player, Location> location1 = new HashMap<Player, Location>();
	public Map<Player, Location> location2 = new HashMap<Player, Location>();
	public Map<Player, String> playerarena = new HashMap<Player, String>();
	public Map<Integer, Location> tempredspawnsx = new HashMap<Integer, Location>();
	public Map<Player, Boolean> teleportable = new HashMap<Player, Boolean>();
	public List<String> allowedCommands = new ArrayList<String>();
	public String arenaworld;
	public int ponex;
	public int poney;
	public int ponez;
	public int ptwox;
	public int ptwoy;
	public int ptwoz;
	public String lobbyWorld;
	public int lobbyx;
	public int lobbyy;
	public int lobbyz;
	public int winx;
	public int winy;
	public int winz;
	public String winWorld;
	public int losex;
	public int losey;
	public int losez;
	public String loseWorld;
	public int redx;
	public int redy;
	public int redz;
	public String redWorld;
	public int bluex;
	public int bluey;
	public int bluez;
	public String blueWorld;
	public int greenx;
	public int greeny;
	public int greenz;
	public String greenWorld;
	public int yellowx;
	public int yellowy;
	public int yellowz;
	public String yellowWorld;
	public int loc11;
	public int loc12;
	public int loc13;
	public int loc21;
	public int loc22;
	public int loc23;
	public int save11;
	public int save12;
	public int save13;
	public int save21;
	public int save22;
	public int save23;
	public boolean update;
	public World saveWorld;
	public World locWorld;
	public String DeleteArena;
	public boolean checkForUpdates;
	public void onEnable(){
		this.update = false;
		this.log = this.getLogger();
		this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/server-mods/the-walls-minigame/files.rss");
		if (this.checkForUpdates == true){
			if (updateChecker.UpdateNeeded()){
				this.log.info(ChatColor.RED + "There is an update for the walls, get it now");
				this.log.info(ChatColor.RED + updateChecker.getVersion());
				update = true;
			}
		}
		Walls.plugin = this;
		ArenaFileManager.setPlugin(this);
		this.saveDefaultConfig();
		this.LoadarenaConfig();
		this.UpdatearenaConfig();
		this.getCommand("walls").setExecutor(new WallsCommand(this));
		this.getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerTagEvent(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDropItemListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerPlaceBlock(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerMoveListner(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerBreakBlockEvent(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerDisconnectListner(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerTeleportListner(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerFoodLevelChangeListner(this), this);
		this.getServer().getPluginManager().registerEvents(new SignChangeListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerPVPListener(this), this);
		this.getServer().getPluginManager().registerEvents(new PlayerCommandListner(this), this);
		this.getServer().getPluginManager().registerEvents(new TntChecker(this), this);
		this.getServer().getPluginManager().registerEvents(new StructureGrowListener(), this);
		KitFile.saveDefaultKits(new File(this.getDataFolder() + File.separator + "kits.yml"));
		KitFile.loadKit(new File(this.getDataFolder() + File.separator + "kits.yml"));
		for (String arena : this.arenas.keySet()){
			ArenaFileManager.loadArenaFiles(arena, 1);
		}
		this.getServer().getScheduler().scheduleSyncRepeatingTask(this , new Runnable(){
			@Override
			public void run() {
				Iterator<String> list = arenas.keySet().iterator();
				while(list.hasNext()){
					String name = list.next();
					arenas.get(name).counter();
					arenas.get(name).saveRegionRestore();
					arenas.get(name).countToTheEnd();
				}
			}
		}, 0L, 20L);
	}
	public void onDisable(){
		this.SaveConfig();
		this.endGames();
	}
	public void LoadarenaConfig(){
		if (this.getConfig().getConfigurationSection("arenas") != null){
			Iterator<String> iterator = this.getConfig().getConfigurationSection("arenas").getKeys(false).iterator();
			while (iterator.hasNext()){
				String arena = iterator.next();
				arenaworld = this.getConfig().getString("arenas." + arena + ".location1.World");
				ponex = this.getConfig().getInt("arenas." + arena + ".location1.X");
				poney = this.getConfig().getInt("arenas." + arena + ".location1.Y");
				ponez = this.getConfig().getInt("arenas." + arena + ".location1.Z");
				ptwox = this.getConfig().getInt("arenas." + arena + ".location2.X");
				ptwoy = this.getConfig().getInt("arenas." + arena + ".location2.Y");
				ptwoz = this.getConfig().getInt("arenas." + arena + ".location2.Z");
				Arena arenaname = new Arena(arena, Bukkit.getServer().getWorld(arenaworld).getBlockAt(ponex,poney,ponez).getLocation(), Bukkit.getServer().getWorld(arenaworld).getBlockAt(ptwox,ptwoy,ptwoz).getLocation(), null , false, null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, null, null, null, null, 0, false, 0, this);
				this.arenas.put(arena , arenaname);
			}
		}
		try {
			this.getConfig().save(this.getDataFolder() + System.getProperty("file.separator") + "config.yml");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void UpdatearenaConfig(){
		this.checkForUpdates = this.getConfig().getBoolean("CheckForUpdates");
		if (this.getConfig().getList("AllowedCommands") != null){
			allowedCommands = this.getConfig().getStringList("AllowedCommands");
		}
		if (this.getConfig().getConfigurationSection("arenas") != null){
			Iterator<String> iterator = this.arenas.keySet().iterator();
			while (iterator.hasNext()){
				String arena = iterator.next();
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".broadcasts") != null){
					Iterator<String> itr = this.getConfig().getConfigurationSection("arenas." + arena + ".broadcasts").getKeys(false).iterator();
					while (itr.hasNext()){
						try{
							arenas.get(arena).getMessages().put(Integer.parseInt(itr.next()), "");
						}catch(Exception e){
							this.getConfig().set("arenas." + arena + ".broadcasts", null);
							arenas.get(arena).getMessages().clear();
						}
					}
				}
				arenas.get(arena).setLoc1(Bukkit.getServer().getWorld(arenaworld).getBlockAt(ponex,poney,ponez).getLocation());
				arenas.get(arena).setLoc2(Bukkit.getServer().getWorld(arenaworld).getBlockAt(ptwox,ptwoy,ptwoz).getLocation());
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".lobby") != null){
					lobbyx = this.getConfig().getInt("arenas." + arena + ".lobby.X");
					lobbyy = this.getConfig().getInt("arenas." + arena + ".lobby.Y");
					lobbyz = this.getConfig().getInt("arenas." + arena + ".lobby.Z");
					lobbyWorld = this.getConfig().getString("arenas." + arena + ".lobby.World");
					arenas.get(arena).setLobby(Bukkit.getServer().getWorld(lobbyWorld).getBlockAt(lobbyx,lobbyy,lobbyz).getLocation());
					arenas.get(arena).getLobby().setPitch(this.getConfig().getInt("arenas." + arena + ".lobby.Pitch"));
					arenas.get(arena).getLobby().setYaw(this.getConfig().getInt("arenas." + arena + ".lobby.Yaw"));
				}
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".win") != null){
					winx = this.getConfig().getInt("arenas." + arena + ".win.X");
					winy = this.getConfig().getInt("arenas." + arena + ".win.Y");
					winz = this.getConfig().getInt("arenas." + arena + ".win.Z");
					winWorld = this.getConfig().getString("arenas." + arena + ".win.World");
					arenas.get(arena).setWin(Bukkit.getServer().getWorld(winWorld).getBlockAt(winx,winy,winz).getLocation());
					arenas.get(arena).getWin().setPitch(this.getConfig().getInt("arenas." + arena + ".win.Pitch"));
					arenas.get(arena).getWin().setYaw(this.getConfig().getInt("arenas." + arena + ".win.Yaw"));
				}
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".lose") != null){
					losex = this.getConfig().getInt("arenas." + arena + ".lose.X");
					losey = this.getConfig().getInt("arenas." + arena + ".lose.Y");
					losez = this.getConfig().getInt("arenas." + arena + ".lose.Z");
					loseWorld = this.getConfig().getString("arenas." + arena + ".lose.World");
					arenas.get(arena).setLose(Bukkit.getServer().getWorld(loseWorld).getBlockAt(losex,losey,losez).getLocation());
					arenas.get(arena).getLose().setPitch(this.getConfig().getInt("arenas." + arena + ".lose.Pitch"));
					arenas.get(arena).getLose().setYaw(this.getConfig().getInt("arenas." + arena + ".lose.Yaw"));
				}
				Integer countred = 0;
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".redspawns") != null){
					Iterator<String> red = this.getConfig().getConfigurationSection("arenas." + arena + ".redspawns").getKeys(false).iterator();
					while (red.hasNext()){
						String num = red.next();
						countred++;
						arenas.get(arena).setReds(arenas.get(arena).getReds() + 1);
						redx = this.getConfig().getInt("arenas." + arena + ".redspawns." + num + ".X");
						redy = this.getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Y");
						redz = this.getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Z");
						redWorld = this.getConfig().getString("arenas." + arena + ".redspawns." + num + ".World");
						Location tempLoc = Bukkit.getServer().getWorld(redWorld).getBlockAt(redx, redy, redz).getLocation();
						tempLoc.setPitch(this.getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Pitch"));
						tempLoc.setYaw(this.getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Yaw"));
						arenas.get(arena).getRedspawns().put(countred, tempLoc);
					}
				}
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".bluespawns") != null){
					Integer countblue = 0;
					Iterator<String> blue = this.getConfig().getConfigurationSection("arenas." + arena + ".bluespawns").getKeys(false).iterator();
					while (blue.hasNext()){
						String num = blue.next();
						countblue++;
						arenas.get(arena).setBlues(arenas.get(arena).getBlues() + 1);
						bluex = this.getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".X");
						bluey = this.getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Y");
						bluez = this.getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Z");
						blueWorld = this.getConfig().getString("arenas." + arena + ".bluespawns." + num + ".World");
						Location tempLoc = Bukkit.getServer().getWorld(blueWorld).getBlockAt(bluex, bluey, bluez).getLocation();
						tempLoc.setPitch(this.getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Pitch"));
						tempLoc.setYaw(this.getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Yaw"));
						arenas.get(arena).getBluespawns().put(countblue, tempLoc);
					}
				}
				Integer countgreen = 0;
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".greenspawns") != null){
					Iterator<String> green = this.getConfig().getConfigurationSection("arenas." + arena + ".greenspawns").getKeys(false).iterator();
					while (green.hasNext()){
						String num = green.next();
						countgreen++;
						arenas.get(arena).setGreens(arenas.get(arena).getGreens() + 1);
						greenx = this.getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".X");
						greeny = this.getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Y");
						greenz = this.getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Z");
						greenWorld = this.getConfig().getString("arenas." + arena + ".greenspawns." + num + ".World");
						Location tempLoc = Bukkit.getServer().getWorld(greenWorld).getBlockAt(greenx, greeny, greenz).getLocation();
						tempLoc.setPitch(this.getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Pitch"));
						tempLoc.setYaw(this.getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Yaw"));
						arenas.get(arena).getGreenspawns().put(countgreen, tempLoc);
					}
				}
				Integer countyellow = 0;
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".yellowspawns") != null){
					Iterator<String> yellow = this.getConfig().getConfigurationSection("arenas." + arena + ".yellowspawns").getKeys(false).iterator();
					while (yellow.hasNext()){
						String num = yellow.next();
						countyellow++;
						arenas.get(arena).setYellows(arenas.get(arena).getYellows() + 1);
						yellowx = this.getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".X");
						yellowy = this.getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Y");
						yellowz = this.getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Z");
						yellowWorld = this.getConfig().getString("arenas." + arena + ".yellowspawns." + num + ".World");
						Location tempLoc = Bukkit.getServer().getWorld(yellowWorld).getBlockAt(yellowx, yellowy, yellowz).getLocation();
						tempLoc.setPitch(this.getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Pitch"));
						tempLoc.setYaw(this.getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Yaw"));
						arenas.get(arena).getYellowspawns().put(countyellow, tempLoc);
					}
				}
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".droplocations") != null){
					Iterator<String> droplocations = this.getConfig().getConfigurationSection("arenas." + arena + ".droplocations").getKeys(false).iterator();
					while (droplocations.hasNext()){
						String droplocation = droplocations.next();
						loc11 = this.getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location1.X");
						loc12 = this.getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location1.Y");
						loc13 = this.getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location1.Z");
						loc21 = this.getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location2.X");
						loc22 = this.getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location2.Y");
						loc23 = this.getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location2.Z");
						locWorld = Bukkit.getServer().getWorld(this.getConfig().getString("arenas." + arena + ".droplocations." + droplocation + ".World"));
						arenas.get(arena).setTempLoc(locWorld.getBlockAt(loc11,loc12,loc13).getLocation());
						arenas.get(arena).setTempLoc1(locWorld.getBlockAt(loc21,loc22,loc23).getLocation());
						arenas.get(arena).CreateDropLocation();
					}
				}
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".buildlocations") != null){
					Integer count = 0;
					arenas.get(arena).setDrops(0);
					Iterator<String> buildlocations = this.getConfig().getConfigurationSection("arenas." + arena + ".buildlocations").getKeys(false).iterator();
					while (buildlocations.hasNext()){
						String num = buildlocations.next();
						count++;
						save11 = this.getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location1.X");
						save12 = this.getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location1.Y");
						save13 = this.getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location1.Z");
						save21 = this.getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location2.X");
						save22 = this.getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location2.Y");
						save23 = this.getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location2.Z");
						saveWorld = Bukkit.getServer().getWorld(this.getConfig().getString("arenas." + arena + ".buildlocations." + num + ".locationWorld"));
						arenas.get(arena).getSaveregion().put(count, saveWorld.getBlockAt(save11,save12,save13).getLocation());
						arenas.get(arena).getSaveregion1().put(count, saveWorld.getBlockAt(save21,save22,save23).getLocation());
						arenas.get(arena).setSaveTotal(arenas.get(arena).getSaveTotal() + 1);
					}
				}
				arenas.get(arena).setDropTime(this.getConfig().getInt("arenas." + arena + ".time"));
				arenas.get(arena).min();
				if (this.getConfig().getConfigurationSection("arenas." + arena + ".SignWalls") != null){
					Iterator<String> itr = this.getConfig().getConfigurationSection("arenas." + arena + ".SignWalls").getKeys(false).iterator();
					while (itr.hasNext()){
						String configNum = itr.next();
						Integer num = Integer.parseInt(configNum);
						String arenaName = this.getConfig().getString("arenas." + arena + ".SignWalls." + configNum + ".Arena");
						int X = this.getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".X");
						int Y = this.getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".Y");
						int Z = this.getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".Z");
						byte Rotation = (byte) this.getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".Rotation");
						World world = Bukkit.getServer().getWorld(this.getConfig().getString("arenas." + arena + ".SignWalls." + configNum + ".World"));
						SignWall signwall = new SignWall(world.getBlockAt(X,Y,Z).getLocation(), Rotation, arenaName,arenas.get(arena).getMin() ,"Waiting");
						arenas.get(arena).getSignWalls().put(num, signwall);
						arenas.get(arena).getSignWalls().get(num).Create();
					}
				}
				arenas.get(arena).setFiles(this.getConfig().getInt("arenas." + arena + ".Files"));
			}
			try {
				this.getConfig().save(this.getDataFolder() + System.getProperty("file.separator") + "config.yml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void SaveConfig(){
		this.getConfig().set("AllowedCommands", allowedCommands);
		Iterator<String> iterator = arenas.keySet().iterator();
		while (iterator.hasNext()){
			String arena = iterator.next();
			if (arenas.get(arena).getDropTime() != null){
				this.getConfig().set("arenas." + arena + ".time", arenas.get(arena).getDropTime());
			}
			if (!arenas.get(arena).getMessages().isEmpty()){
				Iterator<Integer> itr = arenas.get(arena).getMessages().keySet().iterator();
				while (itr.hasNext()){
					this.getConfig().set("arenas." + arena + ".broadcasts." + itr.next(), "");
				}
			}
			this.getConfig().set("arenas." + arena + ".location1.X", this.arenas.get(arena).getLoc1().getX());
			this.getConfig().set("arenas." + arena + ".location1.Y", this.arenas.get(arena).getLoc1().getY());
			this.getConfig().set("arenas." + arena + ".location1.Z", this.arenas.get(arena).getLoc1().getZ());
			this.getConfig().set("arenas." + arena + ".location1.World", this.arenas.get(arena).getLoc1().getWorld().getName());
			this.getConfig().set("arenas." + arena + ".location2.X", this.arenas.get(arena).getLoc2().getX());
			this.getConfig().set("arenas." + arena + ".location2.Y", this.arenas.get(arena).getLoc2().getY());
			this.getConfig().set("arenas." + arena + ".location2.Z", this.arenas.get(arena).getLoc2().getZ());
			if (arenas.get(arena).getLobby() != null){
				this.getConfig().set("arenas." + arena + ".lobby.X", this.arenas.get(arena).getLobby().getX());
				this.getConfig().set("arenas." + arena + ".lobby.Y", this.arenas.get(arena).getLobby().getY());
				this.getConfig().set("arenas." + arena + ".lobby.Z", this.arenas.get(arena).getLobby().getZ());
				this.getConfig().set("arenas." + arena + ".lobby.Pitch", this.arenas.get(arena).getLobby().getPitch());
				this.getConfig().set("arenas." + arena + ".lobby.Yaw", this.arenas.get(arena).getLobby().getYaw());
				this.getConfig().set("arenas." + arena + ".lobby.World", this.arenas.get(arena).getLobby().getWorld().getName());
			}
			if (arenas.get(arena).getWin() != null){
				this.getConfig().set("arenas." + arena + ".win.X", this.arenas.get(arena).getWin().getX());
				this.getConfig().set("arenas." + arena + ".win.Y", this.arenas.get(arena).getWin().getY());
				this.getConfig().set("arenas." + arena + ".win.Z", this.arenas.get(arena).getWin().getZ());
				this.getConfig().set("arenas." + arena + ".win.Pitch", this.arenas.get(arena).getWin().getPitch());
				this.getConfig().set("arenas." + arena + ".win.Yaw", this.arenas.get(arena).getWin().getYaw());
				this.getConfig().set("arenas." + arena + ".win.World", this.arenas.get(arena).getWin().getWorld().getName());
			}
			if (arenas.get(arena).getLose() != null){
				this.getConfig().set("arenas." + arena + ".lose.X", this.arenas.get(arena).getLose().getX());
				this.getConfig().set("arenas." + arena + ".lose.Y", this.arenas.get(arena).getLose().getY());
				this.getConfig().set("arenas." + arena + ".lose.Z", this.arenas.get(arena).getLose().getZ());
				this.getConfig().set("arenas." + arena + ".lose.Pitch", this.arenas.get(arena).getLose().getPitch());
				this.getConfig().set("arenas." + arena + ".lose.Yaw", this.arenas.get(arena).getLose().getYaw());
				this.getConfig().set("arenas." + arena + ".lose.World", this.arenas.get(arena).getLose().getWorld().getName());
			}
			Iterator<Integer> red = arenas.get(arena).getRedspawns().keySet().iterator();
			while (red.hasNext()){
				Integer num = red.next();
				this.getConfig().set("arenas." + arena + ".redspawns." + num + ".X" ,((Location) arenas.get(arena).getRedspawns().get(num)).getX());
				this.getConfig().set("arenas." + arena + ".redspawns." + num + ".Y" ,((Location) arenas.get(arena).getRedspawns().get(num)).getY());
				this.getConfig().set("arenas." + arena + ".redspawns." + num + ".Z" ,((Location) arenas.get(arena).getRedspawns().get(num)).getZ());
				this.getConfig().set("arenas." + arena + ".redspawns." + num + ".Pitch", this.arenas.get(arena).getRedspawns().get(num).getPitch());
				this.getConfig().set("arenas." + arena + ".redspawns." + num + ".Yaw", this.arenas.get(arena).getRedspawns().get(num).getYaw());
				this.getConfig().set("arenas." + arena + ".redspawns." + num + ".World" ,((Location) arenas.get(arena).getRedspawns().get(num)).getWorld().getName());
			}
			Iterator<Integer> blue = arenas.get(arena).getBluespawns().keySet().iterator();
			while (blue.hasNext()){
				Integer num = blue.next();
				this.getConfig().set("arenas." + arena + ".bluespawns." + num + ".X" ,((Location) arenas.get(arena).getBluespawns().get(num)).getX());
				this.getConfig().set("arenas." + arena + ".bluespawns." + num + ".Y" ,((Location) arenas.get(arena).getBluespawns().get(num)).getY());
				this.getConfig().set("arenas." + arena + ".bluespawns." + num + ".Z" ,((Location) arenas.get(arena).getBluespawns().get(num)).getZ());
				this.getConfig().set("arenas." + arena + ".bluespawns." + num + ".Pitch", this.arenas.get(arena).getBluespawns().get(num).getPitch());
				this.getConfig().set("arenas." + arena + ".bluespawns." + num + ".Yaw", this.arenas.get(arena).getBluespawns().get(num).getYaw());
				this.getConfig().set("arenas." + arena + ".bluespawns." + num + ".World" ,((Location) arenas.get(arena).getBluespawns().get(num)).getWorld().getName());
			}
			Iterator<Integer> green = arenas.get(arena).getGreenspawns().keySet().iterator();
			while (green.hasNext()){
				Integer num = green.next();
				this.getConfig().set("arenas." + arena + ".greenspawns." + num + ".X" ,((Location) arenas.get(arena).getGreenspawns().get(num)).getX());
				this.getConfig().set("arenas." + arena + ".greenspawns." + num + ".Y" ,((Location) arenas.get(arena).getGreenspawns().get(num)).getY());
				this.getConfig().set("arenas." + arena + ".greenspawns." + num + ".Z" ,((Location) arenas.get(arena).getGreenspawns().get(num)).getZ());
				this.getConfig().set("arenas." + arena + ".greenspawns." + num + ".Pitch", this.arenas.get(arena).getGreenspawns().get(num).getPitch());
				this.getConfig().set("arenas." + arena + ".greenspawns." + num + ".Yaw", this.arenas.get(arena).getGreenspawns().get(num).getYaw());
				this.getConfig().set("arenas." + arena + ".greenspawns." + num + ".World" ,((Location) arenas.get(arena).getGreenspawns().get(num)).getWorld().getName());
			}
			Iterator<Integer> yellow = arenas.get(arena).getYellowspawns().keySet().iterator();
			while (yellow.hasNext()){
				Integer num = yellow.next();
				this.getConfig().set("arenas." + arena + ".yellowspawns." + num + ".X" ,((Location) arenas.get(arena).getYellowspawns().get(num)).getX());
				this.getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Y" ,((Location) arenas.get(arena).getYellowspawns().get(num)).getY());
				this.getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Z" ,((Location) arenas.get(arena).getYellowspawns().get(num)).getZ());
				this.getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Pitch", this.arenas.get(arena).getYellowspawns().get(num).getPitch());
				this.getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Yaw", this.arenas.get(arena).getYellowspawns().get(num).getYaw());
				this.getConfig().set("arenas." + arena + ".yellowspawns." + num + ".World" ,((Location) arenas.get(arena).getYellowspawns().get(num)).getWorld().getName());
			}
			Iterator<Integer> drops = arenas.get(arena).getDroploc1().keySet().iterator();
			while (drops.hasNext()){
				Integer num = drops.next();
				if (num != null){
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".location1.X", arenas.get(arena).getDroploc1().get(num).getX());
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".location1.Y", arenas.get(arena).getDroploc1().get(num).getY());
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".location1.Z", arenas.get(arena).getDroploc1().get(num).getZ());
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".location2.X", arenas.get(arena).getDroploc2().get(num).getX());
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".location2.Y", arenas.get(arena).getDroploc2().get(num).getY());
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".location2.Z", arenas.get(arena).getDroploc2().get(num).getZ());
					this.getConfig().set("arenas." + arena + ".droplocations." + num + ".World", arenas.get(arena).getDroploc1().get(num).getWorld().getName());
				}
			}
			Iterator<Integer> location1 = arenas.get(arena).getSaveregion().keySet().iterator();
			while (location1.hasNext()){
				Integer num = location1.next();
				if (num != null){
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".location1.X", arenas.get(arena).getSaveregion().get(num).getX());
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".location1.Y", arenas.get(arena).getSaveregion().get(num).getY());
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".location1.Z", arenas.get(arena).getSaveregion().get(num).getZ());
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".location2.X", arenas.get(arena).getSaveregion1().get(num).getX());
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".location2.Y", arenas.get(arena).getSaveregion1().get(num).getY());
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".location2.Z", arenas.get(arena).getSaveregion1().get(num).getZ());
					this.getConfig().set("arenas." + arena + ".buildlocations." + num + ".locationWorld", arenas.get(arena).getSaveregion().get(num).getWorld().getName());
				}
			}
			Iterator<Integer> arenas = this.arenas.get(arena).getSignWalls().keySet().iterator();
			while (arenas.hasNext()){
				Integer num = arenas.next();
				this.getConfig().set("arenas." + arena + ".SignWalls." + num + ".X", this.arenas.get(arena).getSignWalls().get(num).getStartSign().getX());
				this.getConfig().set("arenas." + arena + ".SignWalls." + num + ".Y", this.arenas.get(arena).getSignWalls().get(num).getStartSign().getY());
				this.getConfig().set("arenas." + arena + ".SignWalls." + num + ".Z", this.arenas.get(arena).getSignWalls().get(num).getStartSign().getZ());
				this.getConfig().set("arenas." + arena + ".SignWalls." + num + ".Rotation", this.arenas.get(arena).getSignWalls().get(num).getRotation());
				this.getConfig().set("arenas." + arena + ".SignWalls." + num + ".World", this.arenas.get(arena).getSignWalls().get(num).getStartSign().getWorld().getName());
				this.getConfig().set("arenas." + arena + ".SignWalls." + num + ".Arena", this.arenas.get(arena).getSignWalls().get(num).getArena());
			}
			if (this.arenas.get(arena).getFiles() != null){
				this.getConfig().set("arenas." + arena + ".Files", this.arenas.get(arena).getFiles());
			}
			try {
				this.getConfig().save(this.getDataFolder() + System.getProperty("file.separator") + "config.yml");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.getConfig().set("CheckForUpdates", this.checkForUpdates);
	}
	public void DeleteArena(){
		if (this.getConfig().getConfigurationSection("").contains("arenas")){
			if (this.getConfig().getConfigurationSection("arenas").contains(this.DeleteArena)){
				Iterator<String> iterator = this.getConfig().getConfigurationSection("arenas").getKeys(false).iterator();
				int chocolate = 0;
				while (iterator.hasNext()){
					chocolate++;
					iterator.next();
				}
				if (chocolate != 1){
					this.getConfig().set("arenas." + this.DeleteArena , null);
					try {
						this.getConfig().save(this.getDataFolder() + System.getProperty("file.separator") + "config.yml");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				else{
					this.getConfig().set("arenas", null);
					try {
						this.getConfig().save(this.getDataFolder() + System.getProperty("file.separator") + "config.yml");
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	public void endGames(){
		Iterator<String> list = arenas.keySet().iterator();
		while(list.hasNext()){
			String name = list.next();
			if (!arenas.get(name).getPlayerList().isEmpty()){
				Iterator<Player> players = arenas.get(name).getPlayerList().keySet().iterator();
				while(players.hasNext()){
					Player player = players.next();
					arenas.get(name).setRandom(player);
					arenas.get(name).Leave();
					player.teleport(arenas.get(name).getLose());
				}
			}
		}
	}
	public void CheckUpdates(){
		if (updateChecker.UpdateNeeded()){
			this.update = true;
		}
		else{
			this.update = false;
		}
	}
	
	public void setUpdatesOn(){
		this.getConfig().set("CheckForUpdates", true);
	}
	public void setUpdatesOff(){
		this.getConfig().set("CheckForUpdates" , false);
	}
}
