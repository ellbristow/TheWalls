package rubik_cube_man.plugins.walls.updatechecking;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.parsers.DocumentBuilderFactory;
import org.bukkit.event.Listener;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rubik_cube_man.plugins.walls.Walls;

public class UpdateChecker implements Listener{

	private Walls plugin;
	private URL filesFeed;
	private String version;
	private String link;
	
	public UpdateChecker(Walls plugin, String url){
		this.plugin = plugin;
		try {
			this.filesFeed = new URL(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}
	public boolean UpdateNeeded(){
		try {
			InputStream input = this.filesFeed.openConnection().getInputStream();
			Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
			Node latestFile = document.getElementsByTagName("item").item(0);
			NodeList children = latestFile.getChildNodes();
			this.version = children.item(1).getTextContent().replaceAll("[a-zA-Z ]", "");
			this.link = children.item(3).getTextContent();
			if (!this.version.equals(plugin.getDescription().getVersion())){
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	public String getLink(){
		return this.link;
	}
	public String getVersion(){
		return this.version;
	}
}
