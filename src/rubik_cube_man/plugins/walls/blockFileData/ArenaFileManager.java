package rubik_cube_man.plugins.walls.blockFileData;

import java.util.HashMap;
import java.util.Map;
import lucariatias.plugins.walls.ObjectFileLib;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import rubik_cube_man.plugins.walls.Walls;

public class ArenaFileManager {

    private static Walls plugin;
    public static Map<String, ArenaBlocksAndInfo> arenas = new HashMap<String, ArenaBlocksAndInfo>();

    public static void setPlugin(Plugin plugin) {
        ArenaFileManager.plugin = (Walls) plugin;
    }

    public static void createANewArena(String arenaName, Location loc1, Location loc2) {
        ArenaBlocksAndInfo arena = new ArenaBlocksAndInfo(loc1, loc2, arenaName.toLowerCase());
        ArenaFileManager.arenas.put(arenaName.toLowerCase(), arena);
    }

    public static void saveToAFile(String arena) {
        if (ArenaFileManager.arenas.containsKey(arena)) {
            ArenaBlocksAndInfo abai = arenas.get(arena);
            plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new SaveDataRunnable(abai, plugin, arena));
        }
    }

    public static void loadArenaFiles(String arenas) {
        ArenaBlocksAndInfo arena = (ArenaBlocksAndInfo) ObjectFileLib.loadObject(plugin, arenas + " Blocks");
        ArenaFileManager.arenas.put(arenas, arena);
    }

    public static void restoreAllArenas() {
        for (String arena : ArenaFileManager.arenas.keySet()) {
            ArenaFileManager.arenas.get(arena).restoreBlocks();
        }
    }

    public static void restoreArena(String arena) {
        ArenaFileManager.arenas.get(arena).restoreBlocks();
    }
}
