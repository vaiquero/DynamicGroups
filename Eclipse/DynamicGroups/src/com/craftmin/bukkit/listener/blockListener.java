package com.craftmin.bukkit.listener;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;

import com.craftmin.bukkit.DynamicGroups;
import com.craftmin.bukkit.region.Region;

public class blockListener extends BlockListener {

	private DynamicGroups plugin = null;
	
	public blockListener(DynamicGroups Plugin) {
		plugin = Plugin;
	}
	
	//Allow each region to allow newusers to build etc?
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.isCancelled()) { return; }
		if(plugin.isEnabled()) {
			event.setCancelled(cancel(event.getBlock().getLocation(), event.getPlayer()));
		}
	}
	
	public void onBlockDamage(BlockDamageEvent event) {
		if(event.isCancelled()) { return; }
		if(plugin.isEnabled()) {
			event.setCancelled(cancel(event.getBlock().getLocation(), event.getPlayer()));
		}
	}
	
	public void onBlockPlace(BlockPlaceEvent event) {
		if(event.isCancelled()) { return; }
		if(plugin.isEnabled()) {
			event.setCancelled(cancel(event.getBlock().getLocation(), event.getPlayer()));
		}
	}
	
	public boolean cancel(Location block, Player ply) {
		for(Region rgn : plugin.getRegionList()) {
			if(rgn.isInRegion(block)) {
				ply.sendMessage("You are in " + rgn.getRegionName());
				if(rgn.containsUser(ply.getName())) {
					ply.sendMessage("You can build");
				} else {
					ply.sendMessage("You cannot build");
					return true;
				}
			}
		}
		return false;
	}
	
}
