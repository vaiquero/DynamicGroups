package com.craftmin.bukkit.listener;

import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerPickupItemEvent;

import com.craftmin.bukkit.DynamicGroups;
import com.craftmin.bukkit.commands.EventParser;

public class playerListener extends PlayerListener {

	private DynamicGroups plugin = null;
	
	public playerListener(DynamicGroups Plugin) {
		plugin = Plugin;
	}
	
	public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
		if(plugin.isEnabled()) {
			new EventParser(event, plugin);
		}
	}
	
	public void onPlayerInteract(PlayerInteractEvent event) {
		if(plugin.isEnabled()) {
			new EventParser(event, plugin);
		}
	}
	
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		if(event.isCancelled()) { return; }
		if(plugin.isEnabled()) {
			event.setCancelled(plugin.internalBlockListener.cancel(event.getItem().getLocation(), event.getPlayer()));
		}
	}
	
}
