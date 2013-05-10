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

public class ArenaBlocksAndInfo implements Serializable {

    private static final long serialVersionUID = 1436755451917501905L;
    private Map<Integer, Integer> blockId = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> blockData = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> x = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> y = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> z = new HashMap<Integer, Integer>();
    private Map<Integer, String[]> lines = new HashMap<Integer, String[]>();
    private Map<Integer, ChestData> chests = new HashMap<Integer, ChestData>();
    private String world;

    public ArenaBlocksAndInfo(Location loc1, Location loc2, String arenaName) {
        world = loc1.getWorld().getName();
        updateInfo(loc1, loc2);
    }

    public final void updateInfo(Location loc1, Location loc2) {
        Integer num = 0;
        Location start = loc1;
        Location end = loc2;
        World thisWorld = Bukkit.getWorld(world);
        Integer minX = (int) Math.min(start.getX(), end.getX());
        Integer maxX = (int) Math.max(start.getX(), end.getX());
        Integer minY = (int) Math.min(start.getY(), end.getY());
        Integer maxY = (int) Math.max(start.getY(), end.getY());
        Integer minZ = (int) Math.min(start.getZ(), end.getZ());
        Integer maxZ = (int) Math.max(start.getZ(), end.getZ());
        for (int thisX = minX; thisX <= maxX; ++thisX) {
            for (int thisY = minY; thisY <= maxY; ++thisY) {
                for (int thisZ = minZ; thisZ <= maxZ; ++thisZ) {
                    num++;
                    x.put(num, thisX);
                    y.put(num, thisY);
                    z.put(num, thisZ);
                    blockId.put(num, thisWorld.getBlockTypeIdAt(thisX, thisY, thisZ));
                    blockData.put(num, (int) thisWorld.getBlockAt(thisX, thisY, thisZ).getData());
                    if (thisWorld.getBlockAt(thisX, thisY, thisZ).getType() == Material.CHEST) {
                        Chest chest = (Chest) thisWorld.getBlockAt(thisX, thisY, thisZ).getState();
                        ItemStack[] is = chest.getBlockInventory().getContents();
                        ChestData cd = new ChestData(is);
                        chests.put(num, cd);

                    } else if (thisWorld.getBlockAt(thisX, thisY, thisZ).getType() == Material.WALL_SIGN || thisWorld.getBlockAt(thisX, thisY, thisZ).getType() == Material.WALL_SIGN) {
                        Sign sign = (Sign) thisWorld.getBlockAt(thisX, thisY, thisZ).getState();
                        lines.put(num, sign.getLines());
                    }
                }
            }
        }
    }

    public void restoreBlocks() {
        World thisWorld = Bukkit.getWorld(world);
        for (Integer num : blockId.keySet()) {
            Location loc = thisWorld.getBlockAt(x.get(num), y.get(num), z.get(num)).getLocation();
            loc.getBlock().setType(Material.getMaterial(blockId.get(num)));
            int data = blockData.get(num);
            loc.getBlock().setData((byte) data);
            if (lines.containsKey(num)) {
                Sign sign = (Sign) loc.getBlock().getState();
                sign.setLine(0, lines.get(num)[0]);
                sign.setLine(1, lines.get(num)[1]);
                sign.setLine(2, lines.get(num)[2]);
                sign.setLine(3, lines.get(num)[3]);
                sign.update();
            }
            if (chests.containsKey(num)) {
                Chest chest = (Chest) loc.getBlock().getState();
                chest.getBlockInventory().setContents(chests.get(num).getChestData());
            }
        }
    }
}
