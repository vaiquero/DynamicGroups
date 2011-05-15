package com.craftmin.bukkit.region;

import java.io.File;
import java.io.IOException;

import org.bukkit.util.config.Configuration;

import com.craftmin.bukkit.DynamicGroups;
import com.craftmin.bukkit.misc.Axis;
import com.craftmin.bukkit.misc.Utils;

public class Manager {

	public static boolean saveRegion(DynamicGroups plugin, Region region) {
		return saveRegion(plugin, region.getWorldName(), region.getAxis(), region.getRegionName(), region.getUserListString());
	}
	
	public static boolean saveRegion(DynamicGroups plugin, String WorldName, Axis axis, String RegionName, String Users) {
		String savePath = "plugins/DynamicGroups/WorldData/";
		Utils.setupWorldPaths(savePath, plugin.getServer());
		
		File cFile = new File(savePath + WorldName + "/" + RegionName + ".rgn");
		if(!cFile.exists()) {
			try {
				cFile.createNewFile();
			} catch (IOException e) {
				plugin.writeConsole("Error Creating Config for Region " + RegionName + ": " + e.getMessage());
				return false;
			}
		}
		Configuration config = new Configuration(cFile);
		config.setProperty("RegionName", RegionName);
		config.setProperty("Vector.First", Utils.locationToString(axis.getPoint1()));
		config.setProperty("Vector.Second", Utils.locationToString(axis.getPoint2()));
		config.setProperty("Users", Users);
		config.setProperty("World", WorldName);
		return config.save();
	}
	
}
