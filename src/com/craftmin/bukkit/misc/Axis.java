package com.craftmin.bukkit.misc;

import org.bukkit.Location;

public class Axis {
	
	private Location point1 = null;
	private Location point2 = null;	

	public Axis() {
	}

	public Axis(Location Point1, Location Point2) {
		point1 = Point1;
		point2 = Point2;
	}

	public void setPoint1(Location point1) {
		this.point1 = point1;
	}

	public Location getPoint1() {
		return point1;
	}

	public void setPoint2(Location point2) {
		this.point2 = point2;
	}

	public Location getPoint2() {
		return point2;
	}
	
	@Override
	public String toString() {
		return "";
	}
	
}
