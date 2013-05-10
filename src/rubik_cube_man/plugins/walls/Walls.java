package rubik_cube_man.plugins.walls;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import rubik_cube_man.plugins.walls.blockFileData.ArenaFileManager;
import rubik_cube_man.plugins.walls.listeners.*;
import rubik_cube_man.plugins.walls.utils.UpdateChecker;

/*
 * The Walls Minigame Copyright (C) 2013 rubik_cube_man This program comes with
 * ABSOLUTELY NO WARRANTY; for details type `show w'. This is free software, and
 * you are welcome to redistribute it under certain conditions; type `show c'
 * for details.
 * 
* The hypothetical commands `show w' and `show c' should show the appropriate
 * parts of the General Public License. Of course, your program's commands might
 * be different; for a GUI interface, you would use an "about box".
 * 
* You should also get your employer (if you work as a programmer) or school, if
 * any, to sign a "copyright disclaimer" for the program, if necessary. For more
 * information on this, and how to apply and follow the GNU GPL, see
 * <http://www.gnu.org/licenses/>.
 * 
* The GNU General Public License does not permit incorporating your program
 * into proprietary programs. If your program is a subroutine library, you may
 * consider it more useful to permit linking proprietary applications with the
 * library. If this is what you want to do, use the GNU Lesser General Public
 * License instead of this License. But first, please read
 * <http://www.gnu.org/philosophy/why-not-lgpl.html>.
 */
public class Walls extends JavaPlugin implements Serializable {

    private static final long serialVersionUID = 19204106755649487L;
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

    @Override
    public void onEnable() {
        update = false;
        if (checkForUpdates == true) {
            if (UpdateChecker.UpdateNeeded()) {
                getLogger().info(ChatColor.RED + "There is an update for the walls, get it now");
                getLogger().info(ChatColor.RED + UpdateChecker.getVersion());
                update = true;
            }
        }
        Walls.plugin = this;
        ArenaFileManager.setPlugin(this);
        saveDefaultConfig();
        LoadarenaConfig();
        UpdatearenaConfig();
        getCommand("walls").setExecutor(new WallsCommand(this));
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTagEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerPlaceBlock(this), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListner(this), this);
        getServer().getPluginManager().registerEvents(new PlayerBreakBlockEvent(this), this);
        getServer().getPluginManager().registerEvents(new PlayerDisconnectListner(this), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleportListner(this), this);
        getServer().getPluginManager().registerEvents(new PlayerFoodLevelChangeListner(this), this);
        getServer().getPluginManager().registerEvents(new SignChangeListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerPVPListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerCommandListner(this), this);
        for (String arena : arenas.keySet()) {
            ArenaFileManager.loadArenaFiles(arena);
            getLogger().info("Loaded the arena " + arena);
        }
        getLogger().info("Loaded all the arenas into RAM!");
        ArenaFileManager.restoreAllArenas();
        getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            @Override
            public void run() {
                Iterator<String> list = arenas.keySet().iterator();
                while (list.hasNext()) {
                    String name = list.next();
                    arenas.get(name).counter();
                    arenas.get(name).saveRegionRestore();
                    arenas.get(name).countToTheEnd();
                }
            }
        }, 0L, 20L);
    }

    @Override
    public void onDisable() {
        SaveConfig();
        endGames();
    }

    public void LoadarenaConfig() {
        if (getConfig().getConfigurationSection("arenas") != null) {
            Iterator<String> iterator = getConfig().getConfigurationSection("arenas").getKeys(false).iterator();
            while (iterator.hasNext()) {
                String arena = iterator.next();
                arenaworld = getConfig().getString("arenas." + arena + ".location1.World");
                ponex = getConfig().getInt("arenas." + arena + ".location1.X");
                poney = getConfig().getInt("arenas." + arena + ".location1.Y");
                ponez = getConfig().getInt("arenas." + arena + ".location1.Z");
                ptwox = getConfig().getInt("arenas." + arena + ".location2.X");
                ptwoy = getConfig().getInt("arenas." + arena + ".location2.Y");
                ptwoz = getConfig().getInt("arenas." + arena + ".location2.Z");
                Arena arenaname = new Arena(arena, Bukkit.getServer().getWorld(arenaworld).getBlockAt(ponex, poney, ponez).getLocation(), Bukkit.getServer().getWorld(arenaworld).getBlockAt(ptwox, ptwoy, ptwoz).getLocation(), null, false, null, null, null, 0, 0, 0, 0, 0, 0, 0, 0, 0, null, null, 0, 0, null, null, null, null, 0, false, 0, this);
                arenas.put(arena, arenaname);
            }
        }
        try {
            getConfig().save(getDataFolder() + System.getProperty("file.separator") + "config.yml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void UpdatearenaConfig() {
        checkForUpdates = getConfig().getBoolean("CheckForUpdates");
        if (getConfig().getList("AllowedCommands") != null) {
            allowedCommands = getConfig().getStringList("AllowedCommands");
        }
        if (getConfig().getConfigurationSection("arenas") != null) {
            Iterator<String> iterator = arenas.keySet().iterator();
            while (iterator.hasNext()) {
                String arena = iterator.next();
                if (getConfig().getConfigurationSection("arenas." + arena + ".broadcasts") != null) {
                    Iterator<String> itr = getConfig().getConfigurationSection("arenas." + arena + ".broadcasts").getKeys(false).iterator();
                    while (itr.hasNext()) {
                        try {
                            arenas.get(arena).getMessages().put(Integer.parseInt(itr.next()), "");
                        } catch (Exception e) {
                            getConfig().set("arenas." + arena + ".broadcasts", null);
                            arenas.get(arena).getMessages().clear();
                        }
                    }
                }
                arenas.get(arena).setLoc1(Bukkit.getServer().getWorld(arenaworld).getBlockAt(ponex, poney, ponez).getLocation());
                arenas.get(arena).setLoc2(Bukkit.getServer().getWorld(arenaworld).getBlockAt(ptwox, ptwoy, ptwoz).getLocation());
                if (getConfig().getConfigurationSection("arenas." + arena + ".lobby") != null) {
                    lobbyx = getConfig().getInt("arenas." + arena + ".lobby.X");
                    lobbyy = getConfig().getInt("arenas." + arena + ".lobby.Y");
                    lobbyz = getConfig().getInt("arenas." + arena + ".lobby.Z");
                    lobbyWorld = getConfig().getString("arenas." + arena + ".lobby.World");
                    arenas.get(arena).setLobby(Bukkit.getServer().getWorld(lobbyWorld).getBlockAt(lobbyx, lobbyy, lobbyz).getLocation());
                    arenas.get(arena).getLobby().setPitch(getConfig().getInt("arenas." + arena + ".lobby.Pitch"));
                    arenas.get(arena).getLobby().setYaw(getConfig().getInt("arenas." + arena + ".lobby.Yaw"));
                }
                if (getConfig().getConfigurationSection("arenas." + arena + ".win") != null) {
                    winx = getConfig().getInt("arenas." + arena + ".win.X");
                    winy = getConfig().getInt("arenas." + arena + ".win.Y");
                    winz = getConfig().getInt("arenas." + arena + ".win.Z");
                    winWorld = getConfig().getString("arenas." + arena + ".win.World");
                    arenas.get(arena).setWin(Bukkit.getServer().getWorld(winWorld).getBlockAt(winx, winy, winz).getLocation());
                    arenas.get(arena).getWin().setPitch(getConfig().getInt("arenas." + arena + ".win.Pitch"));
                    arenas.get(arena).getWin().setYaw(getConfig().getInt("arenas." + arena + ".win.Yaw"));
                }
                if (getConfig().getConfigurationSection("arenas." + arena + ".lose") != null) {
                    losex = getConfig().getInt("arenas." + arena + ".lose.X");
                    losey = getConfig().getInt("arenas." + arena + ".lose.Y");
                    losez = getConfig().getInt("arenas." + arena + ".lose.Z");
                    loseWorld = getConfig().getString("arenas." + arena + ".lose.World");
                    arenas.get(arena).setLose(Bukkit.getServer().getWorld(loseWorld).getBlockAt(losex, losey, losez).getLocation());
                    arenas.get(arena).getLose().setPitch(getConfig().getInt("arenas." + arena + ".lose.Pitch"));
                    arenas.get(arena).getLose().setYaw(getConfig().getInt("arenas." + arena + ".lose.Yaw"));
                }
                Integer countred = 0;
                if (getConfig().getConfigurationSection("arenas." + arena + ".redspawns") != null) {
                    Iterator<String> red = getConfig().getConfigurationSection("arenas." + arena + ".redspawns").getKeys(false).iterator();
                    while (red.hasNext()) {
                        String num = red.next();
                        countred++;
                        arenas.get(arena).setReds(arenas.get(arena).getReds() + 1);
                        redx = getConfig().getInt("arenas." + arena + ".redspawns." + num + ".X");
                        redy = getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Y");
                        redz = getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Z");
                        redWorld = getConfig().getString("arenas." + arena + ".redspawns." + num + ".World");
                        Location tempLoc = Bukkit.getServer().getWorld(redWorld).getBlockAt(redx, redy, redz).getLocation();
                        tempLoc.setPitch(getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Pitch"));
                        tempLoc.setYaw(getConfig().getInt("arenas." + arena + ".redspawns." + num + ".Yaw"));
                        arenas.get(arena).getRedspawns().put(countred, tempLoc);
                    }
                }
                if (getConfig().getConfigurationSection("arenas." + arena + ".bluespawns") != null) {
                    Integer countblue = 0;
                    Iterator<String> blue = getConfig().getConfigurationSection("arenas." + arena + ".bluespawns").getKeys(false).iterator();
                    while (blue.hasNext()) {
                        String num = blue.next();
                        countblue++;
                        arenas.get(arena).setBlues(arenas.get(arena).getBlues() + 1);
                        bluex = getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".X");
                        bluey = getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Y");
                        bluez = getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Z");
                        blueWorld = getConfig().getString("arenas." + arena + ".bluespawns." + num + ".World");
                        Location tempLoc = Bukkit.getServer().getWorld(blueWorld).getBlockAt(bluex, bluey, bluez).getLocation();
                        tempLoc.setPitch(getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Pitch"));
                        tempLoc.setYaw(getConfig().getInt("arenas." + arena + ".bluespawns." + num + ".Yaw"));
                        arenas.get(arena).getBluespawns().put(countblue, tempLoc);
                    }
                }
                Integer countgreen = 0;
                if (getConfig().getConfigurationSection("arenas." + arena + ".greenspawns") != null) {
                    Iterator<String> green = getConfig().getConfigurationSection("arenas." + arena + ".greenspawns").getKeys(false).iterator();
                    while (green.hasNext()) {
                        String num = green.next();
                        countgreen++;
                        arenas.get(arena).setGreens(arenas.get(arena).getGreens() + 1);
                        greenx = getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".X");
                        greeny = getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Y");
                        greenz = getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Z");
                        greenWorld = getConfig().getString("arenas." + arena + ".greenspawns." + num + ".World");
                        Location tempLoc = Bukkit.getServer().getWorld(greenWorld).getBlockAt(greenx, greeny, greenz).getLocation();
                        tempLoc.setPitch(getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Pitch"));
                        tempLoc.setYaw(getConfig().getInt("arenas." + arena + ".greenspawns." + num + ".Yaw"));
                        arenas.get(arena).getGreenspawns().put(countgreen, tempLoc);
                    }
                }
                Integer countyellow = 0;
                if (getConfig().getConfigurationSection("arenas." + arena + ".yellowspawns") != null) {
                    Iterator<String> yellow = getConfig().getConfigurationSection("arenas." + arena + ".yellowspawns").getKeys(false).iterator();
                    while (yellow.hasNext()) {
                        String num = yellow.next();
                        countyellow++;
                        arenas.get(arena).setYellows(arenas.get(arena).getYellows() + 1);
                        yellowx = getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".X");
                        yellowy = getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Y");
                        yellowz = getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Z");
                        yellowWorld = getConfig().getString("arenas." + arena + ".yellowspawns." + num + ".World");
                        Location tempLoc = Bukkit.getServer().getWorld(yellowWorld).getBlockAt(yellowx, yellowy, yellowz).getLocation();
                        tempLoc.setPitch(getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Pitch"));
                        tempLoc.setYaw(getConfig().getInt("arenas." + arena + ".yellowspawns." + num + ".Yaw"));
                        arenas.get(arena).getYellowspawns().put(countyellow, tempLoc);
                    }
                }
                if (getConfig().getConfigurationSection("arenas." + arena + ".droplocations") != null) {
                    Iterator<String> droplocations = getConfig().getConfigurationSection("arenas." + arena + ".droplocations").getKeys(false).iterator();
                    while (droplocations.hasNext()) {
                        String droplocation = droplocations.next();
                        loc11 = getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location1.X");
                        loc12 = getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location1.Y");
                        loc13 = getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location1.Z");
                        loc21 = getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location2.X");
                        loc22 = getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location2.Y");
                        loc23 = getConfig().getInt("arenas." + arena + ".droplocations." + droplocation + ".location2.Z");
                        locWorld = Bukkit.getServer().getWorld(getConfig().getString("arenas." + arena + ".droplocations." + droplocation + ".World"));
                        arenas.get(arena).setTempLoc(locWorld.getBlockAt(loc11, loc12, loc13).getLocation());
                        arenas.get(arena).setTempLoc1(locWorld.getBlockAt(loc21, loc22, loc23).getLocation());
                        arenas.get(arena).CreateDropLocation();
                    }
                }
                if (getConfig().getConfigurationSection("arenas." + arena + ".buildlocations") != null) {
                    Integer count = 0;
                    arenas.get(arena).setDrops(0);
                    Iterator<String> buildlocations = getConfig().getConfigurationSection("arenas." + arena + ".buildlocations").getKeys(false).iterator();
                    while (buildlocations.hasNext()) {
                        String num = buildlocations.next();
                        count++;
                        save11 = getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location1.X");
                        save12 = getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location1.Y");
                        save13 = getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location1.Z");
                        save21 = getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location2.X");
                        save22 = getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location2.Y");
                        save23 = getConfig().getInt("arenas." + arena + ".buildlocations." + num + ".location2.Z");
                        saveWorld = Bukkit.getServer().getWorld(getConfig().getString("arenas." + arena + ".buildlocations." + num + ".locationWorld"));
                        arenas.get(arena).getSaveregion().put(count, saveWorld.getBlockAt(save11, save12, save13).getLocation());
                        arenas.get(arena).getSaveregion1().put(count, saveWorld.getBlockAt(save21, save22, save23).getLocation());
                        arenas.get(arena).setSaveTotal(arenas.get(arena).getSaveTotal() + 1);
                    }
                }
                arenas.get(arena).setDropTime(getConfig().getInt("arenas." + arena + ".time"));
                arenas.get(arena).min();
                if (getConfig().getConfigurationSection("arenas." + arena + ".SignWalls") != null) {
                    Iterator<String> itr = getConfig().getConfigurationSection("arenas." + arena + ".SignWalls").getKeys(false).iterator();
                    while (itr.hasNext()) {
                        String configNum = itr.next();
                        Integer num = Integer.parseInt(configNum);
                        String arenaName = getConfig().getString("arenas." + arena + ".SignWalls." + configNum + ".Arena");
                        int X = getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".X");
                        int Y = getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".Y");
                        int Z = getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".Z");
                        byte Rotation = (byte) getConfig().getInt("arenas." + arena + ".SignWalls." + configNum + ".Rotation");
                        World world = Bukkit.getServer().getWorld(getConfig().getString("arenas." + arena + ".SignWalls." + configNum + ".World"));
                        SignWall signwall = new SignWall(world.getBlockAt(X, Y, Z).getLocation(), Rotation, arenaName, arenas.get(arena).getMin(), "Waiting");
                        arenas.get(arena).getSignWalls().put(num, signwall);
                        arenas.get(arena).getSignWalls().get(num).Create();
                    }
                }
            }
            try {
                getConfig().save(getDataFolder() + System.getProperty("file.separator") + "config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void SaveConfig() {
        getConfig().set("AllowedCommands", allowedCommands);
        Iterator<String> iterator = arenas.keySet().iterator();
        while (iterator.hasNext()) {
            String arena = iterator.next();
            if (arenas.get(arena).getDropTime() != null) {
                getConfig().set("arenas." + arena + ".time", arenas.get(arena).getDropTime());
            }
            if (!arenas.get(arena).getMessages().isEmpty()) {
                Iterator<Integer> itr = arenas.get(arena).getMessages().keySet().iterator();
                while (itr.hasNext()) {
                    getConfig().set("arenas." + arena + ".broadcasts." + itr.next(), "");
                }
            }
            getConfig().set("arenas." + arena + ".location1.X", arenas.get(arena).getLoc1().getX());
            getConfig().set("arenas." + arena + ".location1.Y", arenas.get(arena).getLoc1().getY());
            getConfig().set("arenas." + arena + ".location1.Z", arenas.get(arena).getLoc1().getZ());
            getConfig().set("arenas." + arena + ".location1.World", arenas.get(arena).getLoc1().getWorld().getName());
            getConfig().set("arenas." + arena + ".location2.X", arenas.get(arena).getLoc2().getX());
            getConfig().set("arenas." + arena + ".location2.Y", arenas.get(arena).getLoc2().getY());
            getConfig().set("arenas." + arena + ".location2.Z", arenas.get(arena).getLoc2().getZ());
            if (arenas.get(arena).getLobby() != null) {
                getConfig().set("arenas." + arena + ".lobby.X", arenas.get(arena).getLobby().getX());
                getConfig().set("arenas." + arena + ".lobby.Y", arenas.get(arena).getLobby().getY());
                getConfig().set("arenas." + arena + ".lobby.Z", arenas.get(arena).getLobby().getZ());
                getConfig().set("arenas." + arena + ".lobby.Pitch", arenas.get(arena).getLobby().getPitch());
                getConfig().set("arenas." + arena + ".lobby.Yaw", arenas.get(arena).getLobby().getYaw());
                getConfig().set("arenas." + arena + ".lobby.World", arenas.get(arena).getLobby().getWorld().getName());
            }
            if (arenas.get(arena).getWin() != null) {
                getConfig().set("arenas." + arena + ".win.X", arenas.get(arena).getWin().getX());
                getConfig().set("arenas." + arena + ".win.Y", arenas.get(arena).getWin().getY());
                getConfig().set("arenas." + arena + ".win.Z", arenas.get(arena).getWin().getZ());
                getConfig().set("arenas." + arena + ".win.Pitch", arenas.get(arena).getWin().getPitch());
                getConfig().set("arenas." + arena + ".win.Yaw", arenas.get(arena).getWin().getYaw());
                getConfig().set("arenas." + arena + ".win.World", arenas.get(arena).getWin().getWorld().getName());
            }
            if (arenas.get(arena).getLose() != null) {
                getConfig().set("arenas." + arena + ".lose.X", arenas.get(arena).getLose().getX());
                getConfig().set("arenas." + arena + ".lose.Y", arenas.get(arena).getLose().getY());
                getConfig().set("arenas." + arena + ".lose.Z", arenas.get(arena).getLose().getZ());
                getConfig().set("arenas." + arena + ".lose.Pitch", arenas.get(arena).getLose().getPitch());
                getConfig().set("arenas." + arena + ".lose.Yaw", arenas.get(arena).getLose().getYaw());
                getConfig().set("arenas." + arena + ".lose.World", arenas.get(arena).getLose().getWorld().getName());
            }
            Iterator<Integer> red = arenas.get(arena).getRedspawns().keySet().iterator();
            while (red.hasNext()) {
                Integer num = red.next();
                getConfig().set("arenas." + arena + ".redspawns." + num + ".X", ((Location) arenas.get(arena).getRedspawns().get(num)).getX());
                getConfig().set("arenas." + arena + ".redspawns." + num + ".Y", ((Location) arenas.get(arena).getRedspawns().get(num)).getY());
                getConfig().set("arenas." + arena + ".redspawns." + num + ".Z", ((Location) arenas.get(arena).getRedspawns().get(num)).getZ());
                getConfig().set("arenas." + arena + ".redspawns." + num + ".Pitch", arenas.get(arena).getRedspawns().get(num).getPitch());
                getConfig().set("arenas." + arena + ".redspawns." + num + ".Yaw", arenas.get(arena).getRedspawns().get(num).getYaw());
                getConfig().set("arenas." + arena + ".redspawns." + num + ".World", ((Location) arenas.get(arena).getRedspawns().get(num)).getWorld().getName());
            }
            Iterator<Integer> blue = arenas.get(arena).getBluespawns().keySet().iterator();
            while (blue.hasNext()) {
                Integer num = blue.next();
                getConfig().set("arenas." + arena + ".bluespawns." + num + ".X", ((Location) arenas.get(arena).getBluespawns().get(num)).getX());
                getConfig().set("arenas." + arena + ".bluespawns." + num + ".Y", ((Location) arenas.get(arena).getBluespawns().get(num)).getY());
                getConfig().set("arenas." + arena + ".bluespawns." + num + ".Z", ((Location) arenas.get(arena).getBluespawns().get(num)).getZ());
                getConfig().set("arenas." + arena + ".bluespawns." + num + ".Pitch", arenas.get(arena).getBluespawns().get(num).getPitch());
                getConfig().set("arenas." + arena + ".bluespawns." + num + ".Yaw", arenas.get(arena).getBluespawns().get(num).getYaw());
                getConfig().set("arenas." + arena + ".bluespawns." + num + ".World", ((Location) arenas.get(arena).getBluespawns().get(num)).getWorld().getName());
            }
            Iterator<Integer> green = arenas.get(arena).getGreenspawns().keySet().iterator();
            while (green.hasNext()) {
                Integer num = green.next();
                getConfig().set("arenas." + arena + ".greenspawns." + num + ".X", ((Location) arenas.get(arena).getGreenspawns().get(num)).getX());
                getConfig().set("arenas." + arena + ".greenspawns." + num + ".Y", ((Location) arenas.get(arena).getGreenspawns().get(num)).getY());
                getConfig().set("arenas." + arena + ".greenspawns." + num + ".Z", ((Location) arenas.get(arena).getGreenspawns().get(num)).getZ());
                getConfig().set("arenas." + arena + ".greenspawns." + num + ".Pitch", arenas.get(arena).getGreenspawns().get(num).getPitch());
                getConfig().set("arenas." + arena + ".greenspawns." + num + ".Yaw", arenas.get(arena).getGreenspawns().get(num).getYaw());
                getConfig().set("arenas." + arena + ".greenspawns." + num + ".World", ((Location) arenas.get(arena).getGreenspawns().get(num)).getWorld().getName());
            }
            Iterator<Integer> yellow = arenas.get(arena).getYellowspawns().keySet().iterator();
            while (yellow.hasNext()) {
                Integer num = yellow.next();
                getConfig().set("arenas." + arena + ".yellowspawns." + num + ".X", ((Location) arenas.get(arena).getYellowspawns().get(num)).getX());
                getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Y", ((Location) arenas.get(arena).getYellowspawns().get(num)).getY());
                getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Z", ((Location) arenas.get(arena).getYellowspawns().get(num)).getZ());
                getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Pitch", arenas.get(arena).getYellowspawns().get(num).getPitch());
                getConfig().set("arenas." + arena + ".yellowspawns." + num + ".Yaw", arenas.get(arena).getYellowspawns().get(num).getYaw());
                getConfig().set("arenas." + arena + ".yellowspawns." + num + ".World", ((Location) arenas.get(arena).getYellowspawns().get(num)).getWorld().getName());
            }
            Iterator<Integer> drops = arenas.get(arena).getDroploc1().keySet().iterator();
            while (drops.hasNext()) {
                Integer num = drops.next();
                if (num != null) {
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".location1.X", arenas.get(arena).getDroploc1().get(num).getX());
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".location1.Y", arenas.get(arena).getDroploc1().get(num).getY());
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".location1.Z", arenas.get(arena).getDroploc1().get(num).getZ());
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".location2.X", arenas.get(arena).getDroploc2().get(num).getX());
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".location2.Y", arenas.get(arena).getDroploc2().get(num).getY());
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".location2.Z", arenas.get(arena).getDroploc2().get(num).getZ());
                    getConfig().set("arenas." + arena + ".droplocations." + num + ".World", arenas.get(arena).getDroploc1().get(num).getWorld().getName());
                }
            }
            Iterator<Integer> location1i = arenas.get(arena).getSaveregion().keySet().iterator();
            while (location1i.hasNext()) {
                Integer num = location1i.next();
                if (num != null) {
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".location1.X", arenas.get(arena).getSaveregion().get(num).getX());
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".location1.Y", arenas.get(arena).getSaveregion().get(num).getY());
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".location1.Z", arenas.get(arena).getSaveregion().get(num).getZ());
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".location2.X", arenas.get(arena).getSaveregion1().get(num).getX());
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".location2.Y", arenas.get(arena).getSaveregion1().get(num).getY());
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".location2.Z", arenas.get(arena).getSaveregion1().get(num).getZ());
                    getConfig().set("arenas." + arena + ".buildlocations." + num + ".locationWorld", arenas.get(arena).getSaveregion().get(num).getWorld().getName());
                }
            }
            Iterator<Integer> arenasi = arenas.get(arena).getSignWalls().keySet().iterator();
            while (arenasi.hasNext()) {
                Integer num = arenasi.next();
                getConfig().set("arenas." + arena + ".SignWalls." + num + ".X", arenas.get(arena).getSignWalls().get(num).getStartSign().getX());
                getConfig().set("arenas." + arena + ".SignWalls." + num + ".Y", arenas.get(arena).getSignWalls().get(num).getStartSign().getY());
                getConfig().set("arenas." + arena + ".SignWalls." + num + ".Z", arenas.get(arena).getSignWalls().get(num).getStartSign().getZ());
                getConfig().set("arenas." + arena + ".SignWalls." + num + ".Rotation", arenas.get(arena).getSignWalls().get(num).getRotation());
                getConfig().set("arenas." + arena + ".SignWalls." + num + ".World", arenas.get(arena).getSignWalls().get(num).getStartSign().getWorld().getName());
                getConfig().set("arenas." + arena + ".SignWalls." + num + ".Arena", arenas.get(arena).getSignWalls().get(num).getArena());
            }
            try {
                getConfig().save(getDataFolder() + System.getProperty("file.separator") + "config.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        getConfig().set("CheckForUpdates", checkForUpdates);
    }

    public void DeleteArena() {
        if (getConfig().getConfigurationSection("").contains("arenas")) {
            if (getConfig().getConfigurationSection("arenas").contains(DeleteArena)) {
                Iterator<String> iterator = getConfig().getConfigurationSection("arenas").getKeys(false).iterator();
                int chocolate = 0;
                while (iterator.hasNext()) {
                    chocolate++;
                    iterator.next();
                }
                if (chocolate != 1) {
                    getConfig().set("arenas." + DeleteArena, null);
                    try {
                        getConfig().save(getDataFolder() + System.getProperty("file.separator") + "config.yml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    getConfig().set("arenas", null);
                    try {
                        getConfig().save(getDataFolder() + System.getProperty("file.separator") + "config.yml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void endGames() {
        Iterator<String> list = arenas.keySet().iterator();
        while (list.hasNext()) {
            String name = list.next();
            if (!arenas.get(name).getPlayerList().isEmpty()) {
                Iterator<Player> players = arenas.get(name).getPlayerList().keySet().iterator();
                while (players.hasNext()) {
                    Player player = players.next();
                    arenas.get(name).setRandom(player);
                    arenas.get(name).Leave();
                    player.teleport(arenas.get(name).getLose());
                }
            }
            arenas.get(name).RestoreBlocks();
        }
    }

    public void setUpdatesOn() {
        getConfig().set("CheckForUpdates", true);
    }

    public void setUpdatesOff() {
        getConfig().set("CheckForUpdates", false);
    }
}
