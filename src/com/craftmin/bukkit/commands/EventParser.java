package com.craftmin.bukkit.commands;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import com.craftmin.bukkit.DynamicGroups;
import com.craftmin.bukkit.misc.Axis;
import com.craftmin.bukkit.misc.MapUtils;

public class EventParser implements Runnable {
	
	private DynamicGroups plugin = null;
	private Event event = null;
	
	
	public EventParser(Event Event, DynamicGroups Plugin) {
		event = Event;
		plugin = Plugin;
		new Thread(this, "DG_CommandParser").start();
	}
	
	@Override
	public void run() {
		if(event != null && plugin != null) {
			if(event instanceof PlayerCommandPreprocessEvent) {
				PlayerCommandPreprocessEvent chatEvent = (PlayerCommandPreprocessEvent) event;
				if(chatEvent != null) {
					String Message = chatEvent.getMessage().trim();
					if(Message.startsWith("/dg")) {
						CommandParser.processCommand(chatEvent, plugin);
					}
				}
			} else if(event instanceof PlayerInteractEvent) {
				PlayerInteractEvent interactEvent = (PlayerInteractEvent) event;
				if(interactEvent != null) {
					//Save in a single text file with Groupname, and each axis
					Player ply = interactEvent.getPlayer();
					if(plugin.getPlayerToolMap() != null) {
						if(MapUtils.isToolOn(plugin.getPlayerToolMap(), ply)) {
							Axis playerAxis = MapUtils.getAxis(plugin.getPlayerAxisMap(), ply);
							if(playerAxis == null) {
								playerAxis = new Axis();
							}
							if(interactEvent.getAction() == Action.LEFT_CLICK_BLOCK) {
								playerAxis.setPoint1(interactEvent.getClickedBlock().getLocation());
								ply.sendMessage("§4Set First Position (" + playerAxis.getPoint1().toString() + ")");
							} else if(interactEvent.getAction() == Action.RIGHT_CLICK_BLOCK) {
								playerAxis.setPoint2(interactEvent.getClickedBlock().getLocation());
								ply.sendMessage("§4Set Second Position (" + playerAxis.getPoint2().toString() + ")");
							}
							MapUtils.setAxis(plugin.getPlayerAxisMap(), ply, playerAxis);
						}
					}
				}
			}
			
		}
	}

}
