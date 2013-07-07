package rubik_cube_man.plugins.walls.kits;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class Kit{

	private List<ItemStack> is = new ArrayList<ItemStack>();
	private String name;
	
	public Kit(String name, List<String> items){
		this.name = name;
		this.makeKit(items);
	}

	public void makeKit(List<String> items){
		try{
			for (String itemsInfo : items){
				String[] itemInfo = itemsInfo.split(" ");
				String[] data = new String[]{};
				if (!itemInfo[0].contains(":")){
					itemInfo[0] = itemInfo[0] + ":0";
				}
				data = itemInfo[0].split(":");
				ItemStack is = new ItemStack(Material.getMaterial(Integer.parseInt(data[0])), Integer.parseInt(itemInfo[1]), (short) Integer.parseInt(data[1]));
				itemInfo[1] = null;
				itemInfo[0] = null;
				for (String string : itemInfo){
					if (string != null){
						String[] enchant = string.split(":");
						is.addUnsafeEnchantment(Enchantment.getById(34), Integer.parseInt(enchant[1]));
					}
				}
				this.is.add(is);		
			}
		}catch (Exception e){
			Bukkit.getLogger().info(ChatColor.RED + "[Walls]   There was an error with an item in kit " + this.name);
		}
	}
	
	public List<ItemStack> getIs() {
		return is;
	}

	public void setIs(List<ItemStack> is) {
		this.is = is;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
