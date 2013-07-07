package rubik_cube_man.plugins.walls.blockFileData;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ArenaData implements Serializable{

	private static final long serialVersionUID = 3827864816525437344L;
	private Map<Integer, Integer> blockId = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> blockData = new HashMap<Integer, Integer>();
	private Map<Integer, String[]> lines = new HashMap<Integer, String[]>();
	private Map<Integer, ChestData> chests = new HashMap<Integer, ChestData>();
	private String world;
	private Map<Integer, Integer> x = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> y = new HashMap<Integer, Integer>();
	private Map<Integer, Integer> z = new HashMap<Integer, Integer>();
	private Integer startx;
	private Integer startY;
	private Integer startZ;
	private Integer endX;
	private Integer endY;
	private Integer endZ;
	
	public ArenaData(Map<Integer, Integer> x, Map<Integer, Integer> y, Map<Integer, Integer> z, String world, Map<Integer, Integer> blockIds, Map<Integer, Integer> blockData, Map<Integer, String[]> lines, Map<Integer, ChestData> chests, Integer startX, Integer startY, Integer startZ, Integer endX, Integer endY, Integer endZ){
		this.blockId = blockIds;
		this.blockData = blockData;
		this.lines = lines;
		this.chests = chests;
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
		this.startx = startX;
		this.startY = startY;
		this.startZ = startZ;
		this.endX = endX;
		this.endY = endY;
		this.endZ = endZ;
	}

	public Map<Integer, Integer> getBlockId() {
		return blockId;
	}

	public void setBlockId(Map<Integer, Integer> blockId) {
		this.blockId = blockId;
	}

	public Map<Integer, Integer> getBlockData() {
		return blockData;
	}

	public void setBlockData(Map<Integer, Integer> blockData) {
		this.blockData = blockData;
	}

	public Map<Integer, String[]> getLines() {
		return lines;
	}

	public void setLines(Map<Integer, String[]> lines) {
		this.lines = lines;
	}

	public Map<Integer, ChestData> getChests() {
		return chests;
	}

	public void setChests(Map<Integer, ChestData> chests) {
		this.chests = chests;
	}

	public String getWorld() {
		return world;
	}

	public void setWorld(String world) {
		this.world = world;
	}

	public Map<Integer, Integer> getX() {
		return x;
	}

	public void setX(Map<Integer, Integer> x) {
		this.x = x;
	}

	public Map<Integer, Integer> getY() {
		return y;
	}

	public void setY(Map<Integer, Integer> y) {
		this.y = y;
	}

	public Map<Integer, Integer> getZ() {
		return z;
	}

	public void setZ(Map<Integer, Integer> z) {
		this.z = z;
	}

	public Integer getStartx() {
		return startx;
	}

	public void setStartx(Integer startx) {
		this.startx = startx;
	}

	public Integer getStartY() {
		return startY;
	}

	public void setStartY(Integer startY) {
		this.startY = startY;
	}

	public Integer getEndX() {
		return endX;
	}

	public void setEndX(Integer endX) {
		this.endX = endX;
	}

	public Integer getStartZ() {
		return startZ;
	}

	public void setStartZ(Integer startZ) {
		this.startZ = startZ;
	}

	public Integer getEndY() {
		return endY;
	}

	public void setEndY(Integer endY) {
		this.endY = endY;
	}

	public Integer getEndZ() {
		return endZ;
	}

	public void setEndZ(Integer endZ) {
		this.endZ = endZ;
	}
}
