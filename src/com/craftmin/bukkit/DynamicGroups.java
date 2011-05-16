package com.craftmin.bukkit;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.Event.Priority;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;
import org.bukkit.util.config.Configuration;

import com.craftmin.bukkit.listener.blockListener;
import com.craftmin.bukkit.listener.playerListener;
import com.craftmin.bukkit.misc.Axis;
import com.craftmin.bukkit.misc.Utils;
import com.craftmin.bukkit.region.Region;

public class DynamicGroups extends JavaPlugin {

	public playerListener internalPlayerListener = new playerListener(this);
	public blockListener internalBlockListener = new blockListener(this);
	
	static final Logger log = Logger.getLogger("Minecraft");
	
	private HashMap<String, Boolean> playerToolMap = new HashMap<String, Boolean>();
	private HashMap<String, Axis> playerAxisMap = new HashMap<String, Axis>();
	private List<Region> regionList = new ArrayList<Region>();
	

	@Override
	public void onDisable() {
		//Clear all Arrays, List's & Maps
	}

	@Override
	public void onEnable() {
		String Dir = "plugins/DynamicGroups";
		File workDir = new File(Dir);
		if(!workDir.exists()) {
			if(!workDir.mkdir()) {
				workDir.mkdirs();
			}
		}
		
		addHooks();
		
		loadWorldRegions();
		
		writeConsole("Initialized!");
	}
	
	void addHooks() {
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_COMMAND_PREPROCESS, internalPlayerListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_INTERACT, internalPlayerListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.PLAYER_PICKUP_ITEM, internalPlayerListener, Priority.Monitor, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_PLACE, internalBlockListener, Priority.Highest, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_BREAK, internalBlockListener, Priority.Highest, this);
		this.getServer().getPluginManager().registerEvent(Event.Type.BLOCK_DAMAGE, internalBlockListener, Priority.Highest, this);
	}
	
	public Region getRegion(File regionFile) {
	
		Configuration config = new Configuration(regionFile);
		config.load();

		Location first = Utils.stringToLocation(config.getString("Vector.First"));
		if(first == null) { return null; }
		Location second = Utils.stringToLocation(config.getString("Vector.Second"));
		if(second == null) { return null; }
		
		Vector bVecPos1 = new Vector(first.getBlockX(), first.getBlockY(), first.getBlockZ());
		Vector bVecPos2 = new Vector(second.getBlockX(), second.getBlockY(), second.getBlockZ());
		
		Region gRegion = new Region(bVecPos1, bVecPos2, config.getString("World"), config.getString("RegionName"));
		List<String> users = new ArrayList<String>();
		String userString = config.getString("Users", "[]");
		
		if(userString.startsWith("[")) {
			userString = userString.substring(1, userString.length());
		}
		if(userString.endsWith("]")) {
			userString = userString.substring(0, userString.length() -1);
		}
		
		if(userString.contains(",")) {
			for(String usr : userString.split(",")) {
				if(usr.trim().length() > 0) {
					users.add(usr.trim().toLowerCase());
				}
			}
		} else {
			if(userString.trim().length() > 0) {
				users.add(userString);
			}
		}
		
		gRegion.setUserList(users);
				
		return gRegion;
	}
	
	public boolean isRegionValid(Region region) {
		if(region.getMaximumPoint() == null) {
			return false;
		}
		if(region.getMinimumPoint() == null) {
			return false;
		}
		if(region.getUserList() == null) {
			return false;
		}
		return true;
	}
	
	public void loadRegions(String World) {
		String dataPath = "plugins/DynamicGroups/WorldData/" + World;
		File workDirectory = new File(dataPath);
		if(!workDirectory.exists()) { return; }
		final File[] children = workDirectory.listFiles();
		if (children != null) {
			for(File child : children) {
				String name = child.getName();
				if(name.endsWith(".rgn")) {
					Region region = getRegion(child);
					if(isRegionValid(region)) {
						regionList.add(region);
					} else {
						writeConsole("Error Loading Region '" + name + "'");
					}
				}
			}
		}			
	}
		
	public void loadWorldRegions() {
		writeConsole("Loading Regions");
		regionList = new ArrayList<Region>();
		for(World world : this.getServer().getWorlds()) {
			loadRegions(world.getName());
		}
		writeConsole("Loaded Regions (" + String.valueOf(regionList.size()) + ")");
	}
	
	public Region getRegion(String regionName) {
		for(Region rgn : regionList) {
			if(rgn.getRegionName().trim().equalsIgnoreCase(regionName.trim())) {
				return rgn;
			}
		}
		return null;
	}
	
	public boolean updateRegion(Region region) {
		for(Region rgn : regionList) {
			if(rgn.getRegionName().trim().equalsIgnoreCase(region.getRegionName().trim())) {
				regionList.remove(rgn);
			}
		}
		regionList.add(region);
		return false;
	}
	
	public void writeConsole(String msg) {
		writeConsole(msg, isEnabled());
	}
	
	public void writeConsole(String msg, boolean force) {
		if(force) {
			log.info("[DynamicGroups] " + msg);
		}
	}

	public void setPlayerToolMap(HashMap<String, Boolean> playerToolMap) {
		this.playerToolMap = playerToolMap;
	}

	public HashMap<String, Boolean> getPlayerToolMap() {
		return playerToolMap;
	}

	public void setPlayerAxisMap(HashMap<String, Axis> playerAxisMap) {
		this.playerAxisMap = playerAxisMap;
	}

	public HashMap<String, Axis> getPlayerAxisMap() {
		return playerAxisMap;
	}

	public void setRegionList(List<Region> regionList) {
		this.regionList = regionList;
	}

	public List<Region> getRegionList() {
		return regionList;
	}
	
}
