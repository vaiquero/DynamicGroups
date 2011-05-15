package com.craftmin.bukkit.misc;

import java.util.HashMap;

import org.bukkit.entity.Player;

public class MapUtils {

	public static boolean isToolOn(HashMap<String, Boolean> hMap, Player Ply) {
		if(hMap != null && Ply != null) {
			for(String plyName : hMap.keySet()) {
				if(plyName.equalsIgnoreCase(Ply.getName().trim())) {
					return hMap.get(plyName);
				}
			}
		}
		return false;
	}
	
	public static boolean setTool(HashMap<String, Boolean> hMap, Player Ply, boolean Value) {
		if(hMap != null && Ply != null) {
			for(String plyName : hMap.keySet()) {
				if(plyName.equalsIgnoreCase(Ply.getName().trim())) {
					hMap.remove(plyName);
				}
			}
			hMap.put(Ply.getName().trim().toLowerCase(), Value);
			return true;
		}
		return false;
	}
	
	public static Axis getAxis(HashMap<String, Axis> hMap, Player Ply) {
		if(hMap != null && Ply != null) {
			for(String plyName : hMap.keySet()) {
				if(plyName.equalsIgnoreCase(Ply.getName().trim())) {
					return hMap.get(plyName);
				}
			}
		}
		return null;
	}
	
	public static boolean setAxis(HashMap<String, Axis> hMap, Player Ply, Axis axis) {
		if(hMap != null && Ply != null) {
			for(String plyName : hMap.keySet()) {
				if(plyName.equalsIgnoreCase(Ply.getName().trim())) {
					hMap.remove(plyName);
				}
			}
			hMap.put(Ply.getName().trim().toLowerCase(), axis);
			return true;
		}
		return false;
	}
	
}
