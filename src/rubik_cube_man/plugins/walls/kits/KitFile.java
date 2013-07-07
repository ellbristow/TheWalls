package rubik_cube_man.plugins.walls.kits;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.configuration.file.YamlConfiguration;

public class KitFile{

	public static Map<String, Kit> kitsList = new HashMap<String, Kit>();
	
	public static void loadKit(File file){
		if (file.exists()){
			YamlConfiguration config = new YamlConfiguration();
			try {
				config.load(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (String section : config.getConfigurationSection("kits").getKeys(false)){
				kitsList.put(section, new Kit(section, config.getStringList("kits." + section)));
			}
		}
	}
	
	public static void saveDefaultKits(File file){
		if (!file.exists()){
			YamlConfiguration config = new YamlConfiguration();
			config.set("kits.miner", Arrays.asList(new String[]{"274 1", "50 20", "373:8230 1"}));
			config.set("kits.farmer", Arrays.asList(new String[]{"292 1", "295 10", "351:15 15"}));
			config.set("kits.warrior", Arrays.asList(new String[]{"272 1", "298 1", "299 1", "300 1", "301 1"}));
			config.set("kits.alchemist", Arrays.asList(new String[]{"373:8229 1", "373:16396 2", "373:8195 1", "373:8226 1"}));
			try {
				config.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
