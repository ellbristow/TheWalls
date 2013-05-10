package rubik_cube_man.plugins.walls.utils;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class UpdateChecker implements Listener {

    private static URL filesFeed;
    private static String version;
    private static String link;

    static {
        try {
            filesFeed = new URL("http://dev.bukkit.org/server-mods/the-walls-minigame/files.rss");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compare plugin version to latest version at BukkitDev.
     *
     * @return (Boolean) true if update is required, otherwise false
     */
    public static boolean UpdateNeeded() {
        try {
            InputStream input = filesFeed.openConnection().getInputStream();
            Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
            Node latestFile = document.getElementsByTagName("item").item(0);
            NodeList children = latestFile.getChildNodes();
            version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
            link = children.item(3).getTextContent();
            if (!version.equals(Bukkit.getPluginManager().getPlugin("Walls").getDescription().getVersion())) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Get the link to the latest version of the plugin for download.
     *
     * @return (String) Link to download for latest version
     */
    public static String getLink() {
        return link;
    }

    /**
     * Get the version of the latest release of the plugin.
     *
     * @return (String) Latest version number
     */
    public static String getVersion() {
        return version;
    }
}
