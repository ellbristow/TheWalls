package rubik_cube_man.plugins.walls.blockFileData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.inventory.ItemStack;

public class ChestData implements Serializable {

    private static final long serialVersionUID = 6457563082520945086L;
    private Map<Integer, Integer> type = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> amount = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> durariblitiy = new HashMap<Integer, Integer>();
    private Map<Integer, Integer> data = new HashMap<Integer, Integer>();

    public ChestData(ItemStack[] iss) {
        Integer num = 0;
        for (ItemStack is : iss) {
            if (is != null) {
                type.put(num, is.getTypeId());
                amount.put(num, is.getAmount());
                durariblitiy.put(num, (int) is.getDurability());
                data.put(num, (int) is.getData().getData());
            }
            num++;
        }
    }

    @SuppressWarnings("deprecation")
    public ItemStack[] getChestData() {
        ItemStack[] iss = new ItemStack[27];
        for (Integer num : type.keySet()) {
            int damage = durariblitiy.get(num);
            int thisData = data.get(num);
            iss[num] = new ItemStack(type.get(num), amount.get(num), (short) damage, (byte) thisData);
        }
        return iss;
    }
}
