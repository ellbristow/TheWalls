package rubik_cube_man.plugins.walls.blockFileData;

import lucariatias.plugins.walls.ObjectFileLib;
import rubik_cube_man.plugins.walls.Walls;

public class SaveDataRunnable implements Runnable {

    private ArenaBlocksAndInfo arena;
    private Walls plugin;
    private String arenaName;

    public SaveDataRunnable(ArenaBlocksAndInfo arena, Walls plugin, String arenaName) {
        this.arena = arena;
        this.plugin = plugin;
        this.arenaName = arenaName;
    }

    @Override
    public void run() {
        ObjectFileLib.saveObject(plugin, arena, arenaName + " Blocks");
    }
}
