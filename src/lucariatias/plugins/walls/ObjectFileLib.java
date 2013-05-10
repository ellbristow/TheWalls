package lucariatias.plugins.walls;

import java.io.*;
import org.bukkit.plugin.java.JavaPlugin;

/*
 * ObjectFileLib - saves objects to files Copyright (c) 2013 Ross Binden
 *
 * This program is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class ObjectFileLib implements Serializable {

    private static final long serialVersionUID = 1001216325485243060L;

    public static void saveObject(JavaPlugin plugin, Object object, String fileName) {
        try {
            if (!plugin.getDataFolder().exists()) {
                plugin.getDataFolder().mkdir();
            }
            File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + fileName);
            file.delete();
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(object);
            oos.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public static Object loadObject(JavaPlugin plugin, String fileName) {
        try {
            File file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + fileName);
            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                Object object = ois.readObject();
                ois.close();
                return object;
            }
        } catch (IOException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
