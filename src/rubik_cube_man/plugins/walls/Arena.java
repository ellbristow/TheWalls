package rubik_cube_man.plugins.walls;

import java.io.Serializable;
import java.util.*;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.kitteh.tag.TagAPI;
import rubik_cube_man.plugins.walls.blockFileData.ArenaFileManager;

public class Arena implements Serializable {

    private static final long serialVersionUID = -5169004947980681473L;

    public Arena(String name, Location loc1, Location loc2, Integer blocknumber, Boolean started, Location lose, Location win, Location playerLoc, Integer reds, Integer greens, Integer blues, Integer yellows, Integer min, Integer redplayers, Integer blueplayers, Integer greenplayers, Integer yellowplayers, Integer counter, Player random, Integer drops, Integer total, Location tempLoc, Location tempLoc1, String temp, Location lobby, Integer saveTotal, boolean showing, Integer totalDrops, Walls walls) {
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
    }
    private String name;
    private Integer countToEnd;
    private Location loc1;
    private Location loc2;
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
    private Map<Player, Location> playerLocation = new HashMap<Player, Location>();
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
    private Map<Location, BlockState> showStore = new HashMap<Location, BlockState>();
    private Player seeing;
    private Integer totalDrops;
    private Integer dropTime;
    private Map<Integer, String> messages = new HashMap<Integer, String>();
    private Map<Integer, SignWall> signWalls = new HashMap<Integer, SignWall>();

    public void playerJoin() {
        Iterator<Integer> signs = signWalls.keySet().iterator();
        while (signs.hasNext()) {
            signWalls.get(signs.next()).updateSigns(min, total, playerList);
        }
    }

    public void playerAdd(String addedPlayer) {
        Iterator<Integer> signs = signWalls.keySet().iterator();
        while (signs.hasNext()) {
            signWalls.get(signs.next()).updateSigns(min, total, playerList);
        }
    }

    public void countToTheEnd() {
        if (countToEnd != null) {
            countToEnd++;
            if (countToEnd == 5) {
                End();
                countToEnd = null;
                RestoreBlocks();
            }
        }
    }

    public void RestoreBlocks() {
        started = false;
        ArenaFileManager.restoreArena(name.toLowerCase());
        for (Entity e : loc1.getWorld().getEntities()) {
            Location start = loc1;
            Location end = loc2;
            Integer minX = (int) Math.min(start.getX(), end.getX());
            Integer maxX = (int) Math.max(start.getX(), end.getX());
            Integer minY = (int) Math.min(start.getY(), end.getY());
            Integer maxY = (int) Math.max(start.getY(), end.getY());
            Integer minZ = (int) Math.min(start.getZ(), end.getZ());
            Integer maxZ = (int) Math.max(start.getZ(), end.getZ());
            if (e.getLocation().getX() < maxX && e.getLocation().getX() > minX) {
                if (e.getLocation().getY() < maxY && e.getLocation().getY() > minY) {
                    if (e.getLocation().getZ() < maxZ && e.getLocation().getZ() > minZ) {
                    }
                }
            }
        }
    }

    public void counter() {
        if (counter != null) {
            if (getCounter() == 10) {
                onGameStart();
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    signWalls.get(numi).setProgress("Starting");
                    signWalls.get(numi).setStarted(true);
                }
            }
            if (getCounter() == 60) {
                Iterator<Player> list = playerList.keySet().iterator();
                while (list.hasNext()) {
                    Player namei = list.next();
                    if (getPlayerList().get(namei) != null) {
                        namei.sendMessage(ChatColor.BLUE + "Game starting in 1 min!");
                    }
                }
            }
            if (getCounter() == 30) {
                Iterator<Player> list = playerList.keySet().iterator();
                while (list.hasNext()) {
                    Player namei = list.next();
                    if (getPlayerList().get(namei) != null) {
                        namei.sendMessage(ChatColor.BLUE + "Game starting in 30 seconds!");
                    }
                }
            } else if (getCounter() <= 10 && getCounter() >= 1) {
                Iterator<Player> list = playerList.keySet().iterator();
                while (list.hasNext()) {
                    Player namei = list.next();
                    if (getPlayerList().get(namei) != null) {
                        namei.sendMessage(ChatColor.BLUE + "Game starting in " + counter + " seconds!");
                    }
                }
            } else if (getCounter() == 0) {
                started = true;
                Iterator<Player> list = playerList.keySet().iterator();
                while (list.hasNext()) {
                    Player namei = list.next();
                    if (getPlayerList().get(namei) != null) {
                        namei.sendMessage(ChatColor.BLUE + "Goooo!");
                        namei.setGameMode(GameMode.SURVIVAL);
                    }
                }
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    signWalls.get(numi).setProgress("Dropping in:");
                }
            }
            if (counter == -getDropTime()) {
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    signWalls.get(numi).setProgress("Walls Dropped");
                    signWalls.get(numi).setTime(null);
                }
                Dropwalls();
                Iterator<Player> list = playerList.keySet().iterator();
                while (list.hasNext()) {
                    Player namei = list.next();
                    if (getPlayerList().get(namei) != null) {
                        namei.sendMessage(ChatColor.RED + "" + ChatColor.BOLD + "Let Those Walls Drop!!!!!");
                    }
                }
            }
            if (messages.containsKey(dropTime + counter) && getCounter() < 0 && getCounter() > -getDropTime()) {
                Integer seconds = dropTime + counter;
                Integer mins = 0;
                while (seconds >= 60) {
                    seconds = seconds - 60;
                    mins++;
                }
                if (mins != 0 && seconds != 0) {
                    Iterator<Player> list = playerList.keySet().iterator();
                    while (list.hasNext()) {
                        Player namei = list.next();
                        if (getPlayerList().get(namei) != null) {
                            namei.sendMessage(ChatColor.RED + "Walls dropping in " + mins + " minutes and " + seconds + " seconds");
                        }
                    }
                } else if (mins == 0 && seconds != 0) {
                    Iterator<Player> list = playerList.keySet().iterator();
                    while (list.hasNext()) {
                        Player namei = list.next();
                        if (getPlayerList().get(namei) != null) {
                            namei.sendMessage(ChatColor.RED + "Walls dropping in " + seconds + " seconds");
                        }
                    }
                } else if (mins != 0 && seconds == 0) {
                    Iterator<Player> list = playerList.keySet().iterator();
                    while (list.hasNext()) {
                        Player namei = list.next();
                        if (getPlayerList().get(namei) != null) {
                            namei.sendMessage(ChatColor.RED + "Walls dropping in " + mins + " minutes");
                        }
                    }
                }
            }
            counter--;
            Iterator<Integer> itr = signWalls.keySet().iterator();
            while (itr.hasNext()) {
                Integer numi = itr.next();
                if (counter > -dropTime && counter < 0) {
                    signWalls.get(numi).setTime(dropTime + counter);
                    signWalls.get(numi).updateSigns(min, total, playerList);
                } else {
                    signWalls.get(numi).setTime(null);
                    signWalls.get(numi).updateSigns(min, total, playerList);
                }
            }
        }
    }

    public void Dropwalls() {
        Iterator<Integer> list = drop.keySet().iterator();
        while (list.hasNext()) {
            Integer namei = list.next();
            World world = drop.get(namei).getWorld();
            world.getBlockAt(drop.get(namei)).setTypeId(0);
        }
    }

    @SuppressWarnings("deprecation")
    public void Leave() {
        if ("red".equals(playerList.get(random))) {
            redplayers--;
        } else if ("blue".equals(playerList.get(random))) {
            blueplayers--;
        } else if ("green".equals(playerList.get(random))) {
            greenplayers--;
        } else if ("yellow".equals(playerList.get(random))) {
            yellowplayers--;
        }
        total--;
        random.setGameMode(GameMode.SURVIVAL);
        Iterator<Integer> itr = signWalls.keySet().iterator();
        while (itr.hasNext()) {
            Integer numi = itr.next();
            signWalls.get(numi).updateSigns(min, total, playerList);
        }
        random.getInventory().setContents(playerInventories.get(random));
        random.getInventory().setArmorContents(aurmor.get(random));
        random.updateInventory();
        random.teleport(lose);
        random.setLevel(exp.get(random));
        random.setFallDistance(0);
        random.setFireTicks(0);
    }

    public void onGameStart() {
        lobbyPlayers.clear();
        Iterator<Player> list = playerList.keySet().iterator();
        while (list.hasNext()) {
            Player namei = list.next();
            Integer tempi = Math.min(Math.min(redplayers, greenplayers), Math.min(blueplayers, yellowplayers));
            if (tempi == redplayers) {
                redplayers++;
                TagAPI.refreshPlayer(namei);
                namei.teleport(redspawns.get(redplayers));
                playerLocation.put(namei, redspawns.get(redplayers));
                playerList.put(namei, "red");
                namei.sendMessage(ChatColor.RED + "You are on team red!");
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    Sign sign = (Sign) signWalls.get(numi).getSignLocations().get(redplayers + 2).getBlock().getState();
                    sign.setLine(0, "" + ChatColor.DARK_RED + namei.getName());
                    sign.update();
                }
            } else if (tempi == blueplayers) {
                blueplayers++;
                TagAPI.refreshPlayer(namei);
                namei.teleport(bluespawns.get(blueplayers));
                playerLocation.put(namei, bluespawns.get(blueplayers));
                playerList.put(namei, "blue");
                namei.sendMessage(ChatColor.DARK_BLUE + "You are on team blue!");
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    Sign sign = (Sign) signWalls.get(numi).getSignLocations().get(blueplayers + 2).getBlock().getState();
                    sign.setLine(1, "" + ChatColor.DARK_BLUE + namei.getName());
                    sign.update();
                }
            } else if (tempi == greenplayers) {
                greenplayers++;
                TagAPI.refreshPlayer(namei);
                namei.teleport(greenspawns.get(greenplayers));
                playerLocation.put(namei, greenspawns.get(greenplayers));
                playerList.put(namei, "green");
                namei.sendMessage(ChatColor.GREEN + "You are on team green!");
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    Sign sign = (Sign) signWalls.get(numi).getSignLocations().get(greenplayers + 2).getBlock().getState();
                    sign.setLine(2, "" + ChatColor.DARK_GREEN + namei.getName());
                    sign.update();
                }
            } else if (tempi == yellowplayers) {
                yellowplayers++;
                TagAPI.refreshPlayer(namei);
                namei.teleport(yellowspawns.get(yellowplayers));
                playerLocation.put(namei, yellowspawns.get(yellowplayers));
                playerList.put(namei, "yellow");
                namei.sendMessage(ChatColor.YELLOW + "You are on team yellow!");
                Iterator<Integer> itr = signWalls.keySet().iterator();
                while (itr.hasNext()) {
                    Integer numi = itr.next();
                    Sign sign = (Sign) signWalls.get(numi).getSignLocations().get(yellowplayers + 2).getBlock().getState();
                    sign.setLine(3, "" + ChatColor.YELLOW + namei.getName());
                    sign.update();
                }
            }
            namei.setHealth(20);
            namei.setFoodLevel(20);
            namei.setFireTicks(0);
        }
    }

    public void CreateDropLocation() {
        Location start = tempLoc;
        Location end = tempLoc1;
        World world = tempLoc1.getWorld();
        Integer minX = (int) Math.min(start.getX(), end.getX());
        Integer maxX = (int) Math.max(start.getX(), end.getX());
        Integer minY = (int) Math.min(start.getY(), end.getY());
        Integer maxY = (int) Math.max(start.getY(), end.getY());
        Integer minZ = (int) Math.min(start.getZ(), end.getZ());
        Integer maxZ = (int) Math.max(start.getZ(), end.getZ());
        totalDrops++;
        droploc1.put(totalDrops, tempLoc);
        droploc2.put(totalDrops, tempLoc1);
        for (int x = minX; x <= maxX; ++x) {
            for (int y = minY; y <= maxY; ++y) {
                for (int z = minZ; z <= maxZ; ++z) {
                    drops++;
                    drop.put(drops, world.getBlockAt(x, y, z).getLocation());
                }
            }
        }
    }

    public void broadcastWinner() {
        if (redplayers == total) {
            Bukkit.broadcastMessage(ChatColor.RED + "Red team has won the arena on arena " + getName());
        } else if (blueplayers == total) {
            Bukkit.broadcastMessage(ChatColor.BLUE + "Blue team has won the arena on arena " + getName());
        } else if (greenplayers == total) {
            Bukkit.broadcastMessage(ChatColor.GREEN + "Green team has won the arena on arena " + getName());
        } else if (yellowplayers == total) {
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Yellow team has won the arena on arena " + getName());
        }
    }

    public void End() {
        playerList.clear();
        redplayers = 0;
        blueplayers = 0;
        greenplayers = 0;
        yellowplayers = 0;
        started = false;
        total = 0;
        RestoreBlocks();
        counter = null;
        blocksLocation.clear();
        blockstype.clear();
        playerList.clear();
        Iterator<Integer> itr = signWalls.keySet().iterator();
        while (itr.hasNext()) {
            Integer numi = itr.next();
            signWalls.get(numi).setProgress("Waiting");
            signWalls.get(numi).setTime(null);
            signWalls.get(numi).updateSigns(min, total, playerList);
        }
    }

    public void showRegion() {
        showing = true;
        showcount = 5;
        Player player = seeing;
        World world = saveregion.get(num).getWorld();
        for (int x = (int) saveregion1.get(num).getX(); x <= (int) saveregion.get(num).getX(); ++x) {
            showStore.put(world.getBlockAt(x, (int) saveregion.get(num).getY(), (int) saveregion.get(num).getZ()).getLocation(), world.getBlockAt(x, (int) saveregion.get(num).getY(), (int) saveregion.get(num).getZ()).getState());
            showStore.put(world.getBlockAt(x, (int) saveregion.get(num).getY(), (int) saveregion1.get(num).getZ()).getLocation(), world.getBlockAt(x, (int) saveregion.get(num).getY(), (int) saveregion1.get(num).getZ()).getState());
            showStore.put(world.getBlockAt(x, (int) saveregion1.get(num).getY(), (int) saveregion.get(num).getZ()).getLocation(), world.getBlockAt(x, (int) saveregion1.get(num).getY(), (int) saveregion.get(num).getZ()).getState());
            showStore.put(world.getBlockAt(x, (int) saveregion1.get(num).getY(), (int) saveregion1.get(num).getZ()).getLocation(), world.getBlockAt(x, (int) saveregion1.get(num).getY(), (int) saveregion1.get(num).getZ()).getState());
            player.sendBlockChange(world.getBlockAt(x, (int) saveregion1.get(num).getY(), (int) saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt(x, (int) saveregion1.get(num).getY(), (int) saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt(x, (int) saveregion.get(num).getY(), (int) saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt(x, (int) saveregion.get(num).getY(), (int) saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
        }
        for (int y = (int) saveregion1.get(num).getY(); y <= (int) saveregion.get(num).getY(); ++y) {
            if (world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion.get(num).getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion.get(num).getZ()).getLocation(), world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion.get(num).getZ()).getState());
            }
            if (world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getLocation(), world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getState());
            }
            if (world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion.get(num).getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion.get(num).getZ()).getLocation(), world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion.get(num).getZ()).getState());
            }
            if (world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getLocation(), world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getState());
            }
            player.sendBlockChange(world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) saveregion1.get(num).getX(), y, (int) saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion1.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) saveregion.get(num).getX(), y, (int) saveregion.get(num).getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
        }
        for (int z = (int) saveregion1.get(num).getZ(); z <= (int) saveregion.get(num).getZ(); ++z) {
            if (world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion.get(num).getY(), z).getLocation(), world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion.get(num).getY(), z).getState());
            }
            if (world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion1.get(num).getY(), z).getLocation(), world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion1.get(num).getY(), z).getState());
            }
            if (world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion.get(num).getY(), z).getLocation(), world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion.get(num).getY(), z).getState());
            }
            if (world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion1.get(num).getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion1.get(num).getY(), z).getLocation(), world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion1.get(num).getY(), z).getState());
            }
            player.sendBlockChange(world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion1.get(num).getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) saveregion1.get(num).getX(), (int) saveregion.get(num).getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion1.get(num).getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) saveregion.get(num).getX(), (int) saveregion.get(num).getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
        }
    }

    public void saveRegionRestore() {
        if (showcount != null) {
            showcount--;
            if (showcount == 0) {
                showcount = null;
                showing = false;
                Iterator<Location> location = showStore.keySet().iterator();
                while (location.hasNext()) {
                    Location loc = location.next();
                    Player player = seeing;
                    player.sendBlockChange(loc, showStore.get(loc).getType(), (byte) 0);
                }
                showStore.clear();
                seeing = null;
            }
        }
    }

    public void ShowArena() {
        World world = loc2.getWorld();
        Integer maxX = Math.max((int) loc1.getX(), (int) loc2.getX());
        Integer minX = Math.min((int) loc1.getX(), (int) loc2.getX());
        Integer maxY = Math.max((int) loc1.getY(), (int) loc2.getY());
        Integer minY = Math.min((int) loc1.getY(), (int) loc2.getY());
        Integer maxZ = Math.max((int) loc1.getZ(), (int) loc2.getZ());
        Integer minZ = Math.min((int) loc1.getZ(), (int) loc2.getZ());
        Location loc3 = world.getBlockAt(maxX, maxY, maxZ).getLocation();
        Location loc4 = world.getBlockAt(minX, minY, minZ).getLocation();
        showing = true;
        showcount = 5;
        Player player = seeing;
        for (int x = (int) loc4.getX(); x <= (int) loc3.getX(); ++x) {
            showStore.put(world.getBlockAt(x, (int) loc3.getY(), (int) loc3.getZ()).getLocation(), world.getBlockAt(x, (int) loc3.getY(), (int) loc3.getZ()).getState());
            showStore.put(world.getBlockAt(x, (int) loc3.getY(), (int) loc4.getZ()).getLocation(), world.getBlockAt(x, (int) loc3.getY(), (int) loc4.getZ()).getState());
            showStore.put(world.getBlockAt(x, (int) loc4.getY(), (int) loc3.getZ()).getLocation(), world.getBlockAt(x, (int) loc4.getY(), (int) loc3.getZ()).getState());
            showStore.put(world.getBlockAt(x, (int) loc4.getY(), (int) loc4.getZ()).getLocation(), world.getBlockAt(x, (int) loc4.getY(), (int) loc4.getZ()).getState());
            player.sendBlockChange(world.getBlockAt(x, (int) loc3.getY(), (int) loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt(x, (int) loc3.getY(), (int) loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt(x, (int) loc4.getY(), (int) loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt(x, (int) loc4.getY(), (int) loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
        }

        for (int y = (int) loc4.getY(); y <= (int) loc3.getY(); ++y) {
            if (world.getBlockAt((int) loc3.getX(), y, (int) loc3.getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc3.getX(), y, (int) loc3.getZ()).getLocation(), world.getBlockAt((int) loc3.getX(), y, (int) loc3.getZ()).getState());
            }
            if (world.getBlockAt((int) loc3.getX(), y, (int) loc4.getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc3.getX(), y, (int) loc4.getZ()).getLocation(), world.getBlockAt((int) loc3.getX(), y, (int) loc4.getZ()).getState());
            }
            if (world.getBlockAt((int) loc4.getX(), y, (int) loc3.getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc4.getX(), y, (int) loc3.getZ()).getLocation(), world.getBlockAt((int) loc4.getX(), y, (int) loc3.getZ()).getState());
            }
            if (world.getBlockAt((int) loc4.getX(), y, (int) loc4.getZ()).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc4.getX(), y, (int) loc4.getZ()).getLocation(), world.getBlockAt((int) loc4.getX(), y, (int) loc4.getZ()).getState());
            }
            player.sendBlockChange(world.getBlockAt((int) loc4.getX(), y, (int) loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) loc4.getX(), y, (int) loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) loc3.getX(), y, (int) loc3.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) loc3.getX(), y, (int) loc4.getZ()).getLocation(), Material.GLOWSTONE, (byte) 0);
        }
        for (int z = (int) loc4.getZ(); z <= (int) loc3.getZ(); ++z) {
            if (world.getBlockAt((int) loc3.getX(), (int) loc3.getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc3.getX(), (int) loc3.getY(), z).getLocation(), world.getBlockAt((int) loc3.getX(), (int) loc3.getY(), z).getState());
            }
            if (world.getBlockAt((int) loc3.getX(), (int) loc4.getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc3.getX(), (int) loc4.getY(), z).getLocation(), world.getBlockAt((int) loc3.getX(), (int) loc4.getY(), z).getState());
            }
            if (world.getBlockAt((int) loc4.getX(), (int) loc3.getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc4.getX(), (int) loc3.getY(), z).getLocation(), world.getBlockAt((int) loc4.getX(), (int) loc3.getY(), z).getState());
            }
            if (world.getBlockAt((int) loc4.getX(), (int) loc4.getY(), z).getType() != Material.GLOWSTONE) {
                showStore.put(world.getBlockAt((int) loc4.getX(), (int) loc4.getY(), z).getLocation(), world.getBlockAt((int) loc4.getX(), (int) loc4.getY(), z).getState());
            }
            player.sendBlockChange(world.getBlockAt((int) loc3.getX(), (int) loc3.getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) loc3.getX(), (int) loc4.getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) loc4.getX(), (int) loc3.getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
            player.sendBlockChange(world.getBlockAt((int) loc4.getX(), (int) loc4.getY(), z).getLocation(), Material.GLOWSTONE, (byte) 0);
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

    public void setSignWalls(Map<Integer, SignWall> signWalls) {
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

    public void addRedSpawn() {
        reds++;
        redspawns.put(reds, playerLoc);
    }

    public void addBlueSpawn() {
        blues++;
        bluespawns.put(blues, playerLoc);
    }

    public void addGreenSpawn() {
        greens++;
        greenspawns.put(greens, playerLoc);
    }

    public void addYellowSpawn() {
        yellows++;
        yellowspawns.put(yellows, playerLoc);
    }

    public void min() {
        min = (int) Math.min(Math.min(reds, blues), Math.min(yellows, greens));
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

    public Map<Location, BlockState> getShowStore() {
        return showStore;
    }

    public void setShowStore(Map<Location, BlockState> showStore) {
        this.showStore = showStore;
    }

    public void setSeeing(Player seeing) {
        this.seeing = seeing;
    }

    public Player getSeeing() {
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

    public void JoinRed() {
        playerList.put(random, "Red");
    }

    public void JoinBlue() {
        playerList.put(random, "Blue");
    }

    public void JoinGreen() {
        playerList.put(random, "Green");
    }

    public void JoinYellow() {
        playerList.put(random, "Yellow");
    }
}