package com.craftmin.bukkit.misc;

import java.io.File;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;

public class Utils {

	//For Blocks Only
	public static String locationToString(Location location) {
		return String.valueOf(location.getBlockX()) + "," + String.valueOf(location.getBlockY()) + "," + String.valueOf(location.getBlockZ());
	}
	
	public static Location stringToLocation(String location) {
		if(location.contains(",")) {
			String[] split = location.split(",");
			if(split.length == 3) {
				Location nLoc = new Location(null, 
						Double.valueOf(split[0]), 
						Double.valueOf(split[1]), 
						Double.valueOf(split[2]));
				return nLoc;
			}
		}
		return null;
	}
	
	public static boolean setupWorldPaths(String Parent, Server server) {
		String parentPath = Parent.trim();
		if(parentPath.endsWith("/")) {
			parentPath = parentPath.substring(0, parentPath.length()-1);
		}
		for(World world : server.getWorlds()) {
			String path = parentPath + "/" + world.getName();
			File file = new File(path);
			if(!file.exists()) {
				if(!file.mkdir()) {
					file.mkdirs();
				}
			}
		}
		return false;
	}
	
	public static String cleanString(String input) {
		String ReT = "";
		for(char letterz : input.toCharArray()) {
			if(!String.valueOf(letterz).equals(" ")) {
				ReT += letterz;
			}
		}
		return ReT;		
	}
	
	public static String mergeArray(String[] Args, int index) {
		String ReT = "";
		for(int i = index; i < Args.length; i++) {
			ReT += " " + Args[i];
		}
		return ReT;
	}
	
}
