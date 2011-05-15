package com.craftmin.bukkit.region;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import com.craftmin.bukkit.DynamicGroups;
import com.craftmin.bukkit.misc.Axis;

public class Region {

	private Vector point1 = null;
	private Vector point2 = null;
	private String worldName = null;
	private String regionName = null;
	private List<String> userList = new ArrayList<String>();
		
	public Region(Vector Pos1, Vector Pos2, String WorldName, String RegionName) {
		point1 = Pos1;
		point2 = Pos2;
		worldName = WorldName;
		regionName = RegionName;
	}
	public Vector getMinimumPoint()  {
		return new Vector(
				Math.min(this.point1.getX(), 
						 this.point2.getX()), 
				Math.min(this.point1.getY(), 
						 this.point2.getY()), 
				Math.min(this.point1.getZ(), 
						 this.point2.getZ()));
	}
	
	public Vector getMaximumPoint() {
		return new Vector(
				Math.max(this.point1.getX(),
						 this.point2.getX()), 
				Math.max(this.point1.getY(), 
						 this.point2.getY()), 
				Math.max(this.point1.getZ(), 
						 this.point2.getZ()));
	}
	
	public int getArea() {
		Vector min = getMinimumPoint();
	    Vector max = getMaximumPoint();
	    return (int)(
	    		(max.getX() - min.getX() + 1.0D)
	    		* 
	    		(max.getY() - min.getY() + 1.0D) 
	    		* 
	    		(max.getZ() - min.getZ() + 1.0D));
	}
	
	public boolean isInRegion(Location location) {
		Vector point = new Vector(location.getBlockX(), location.getBlockY(), location.getBlockZ());
		double x = point.getX();
	    double y = point.getY();
	    double z = point.getZ();

	    Vector min = getMinimumPoint();
	    Vector max = getMaximumPoint();

	    return (x >= min.getBlockX()) && (x <= max.getBlockX()) && (y >= min.getBlockY()) && (y <= max.getBlockY()) && (z >= min.getBlockZ()) && (z <= max.getBlockZ());
	}
	
	public boolean containsUser(String UserName) {
		for(String User : userList) {
			if(User.trim().equalsIgnoreCase(UserName.trim())) {
				return true;
			}
		}
		return false;
	}

	public void addUser(String UserName) {
		if(!containsUser(UserName)) {
			userList.add(UserName);
		}
	}

	public void removeUser(String UserName) {
		for(String User : userList) {
			if(User.trim().equalsIgnoreCase(UserName.trim())) {
				userList.remove(User);
				return;
			}
		}
	}
	
	public String getUserListString() {
		String ReT = "";
		for(String User : userList) {
			if(User.trim().length() > 0) {
				ReT = "," + User + ReT;
			}
		}
		if(ReT.startsWith(",")) {
			ReT = ReT.substring(1, ReT.length());
		}
		return "[" + ReT + "]";
	}

	public void setWorldName(String worldName) {
		this.worldName = worldName;
	}

	public String getWorldName() {
		return worldName;
	}

	public void setUserList(List<String> userList) {
		this.userList = userList;
	}

	public List<String> getUserList() {
		return userList;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getRegionName() {
		return regionName;
	}

	public Axis getAxis() {
		return new Axis(new Location(null, point1.getX(), point1.getY(), point1.getZ()),
						new Location(null, point2.getX(), point2.getY(), point2.getZ()));
	}
	
	public boolean save(DynamicGroups plugin) {
		return Manager.saveRegion(plugin, this);
	}
	
}
