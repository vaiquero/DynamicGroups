package com.craftmin.bukkit.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import com.craftmin.bukkit.DynamicGroups;
import com.craftmin.bukkit.misc.Axis;
import com.craftmin.bukkit.misc.MapUtils;
import com.craftmin.bukkit.misc.Utils;
import com.craftmin.bukkit.region.Region;

public class Command {

	public static boolean selectRegion(Player player, DynamicGroups plugin) {
		int ToolID = plugin.getSettings().getSelectionToolID();
		if(player.getItemInHand().getTypeId() == ToolID) {
			if(MapUtils.isToolOn(plugin.getPlayerToolMap(), player)) {
				MapUtils.setTool(plugin.getPlayerToolMap(), player, false);
				player.sendMessage("Region Selecting is now Turned Off.");
			} else {
				MapUtils.setTool(plugin.getPlayerToolMap(), player, true);
				player.sendMessage("Region Selecting is now Turned On.");
			}
		} else {
			player.sendMessage("Please hold an ID " + String.valueOf(ToolID));
		}
		return false;
	}

	public static boolean saveRegion(Player player, String[] Args, DynamicGroups plugin) {
		if(plugin.getPlayerAxisMap() != null) {
			Axis axis = MapUtils.getAxis(plugin.getPlayerAxisMap(), player);
			if(axis != null && axis.getPoint1() != null && axis.getPoint2() != null) {				
				if(Args.length >= 3 && Args[2] != null && Args[2].trim().length() > 0) {
					
					Vector bVecPos1 = new Vector(axis.getPoint1().getBlockX(), axis.getPoint1().getBlockY(),
							axis.getPoint1().getBlockZ());
					Vector bVecPos2 = new Vector(axis.getPoint2().getBlockX(), axis.getPoint2().getBlockY(),
							axis.getPoint2().getBlockZ());
					
					Region createdRegion = new Region(bVecPos1, bVecPos2, player.getWorld().getName(), Args[2]);
					plugin.getRegionList().add(createdRegion);
					
					createdRegion.setOwner(player.getName().trim().toLowerCase());
					
					if(createdRegion.save(plugin)) {
						player.sendMessage("Saved Region '" + Args[2] + "' Successfully.");
					} else {
						player.sendMessage("Error Saving Region '" + Args[2] + "'.");
					}
					return true;
				} else {
					player.sendMessage("Please Review Your Args:");
					player.sendMessage("   /dg saveregion <name>");
				}
				
				
				
			} else {
				player.sendMessage("Please Select A Region");
			}
		}
		return false;
	}
	
	public static boolean addUser(Player player, String[] Args, DynamicGroups plugin) {
		if(Args.length >= 4 && Args[2] != null && Args[2].trim().length() > 0
				 && Args[3] != null && Args[3].trim().length() > 0) {
			
			String regionName = Args[2].trim();
			Region rgn = plugin.getRegion(regionName);
			if(rgn != null) {
				String Players = Utils.cleanString(Utils.mergeArray(Args, 3));
				if(Players.contains(",")) {
					if(Players.startsWith(",")) {
						Players = Players.substring(1, Players.length());
					}
					if(Players.endsWith(",")) {
						Players = Players.substring(0, Players.length() -1);
					}
					if(Players.contains(",")) {
						String[] aPlayers = Players.split(",");
						int count = 0;
						for(String ply : aPlayers) {
							if(ply.trim().length() > 0) {
								rgn.addUser(ply);
								count++;
							}
						}
						
						
						if(rgn.save(plugin)) {
							player.sendMessage(String.valueOf(count) + " Players Added to Region '" + rgn.getRegionName() + "'");
						} else {
							player.sendMessage("Failed to add " + String.valueOf(count) + " Players to Region '" + rgn.getRegionName() + "'");
						}
						
						return true;
					} else {
						//add Single
					}
				} else {
					//Add single player
				}
				rgn.addUser(Players);
				
				if(rgn.save(plugin)) {
					player.sendMessage("'" + Players + "' Added to Region '" + rgn.getRegionName() + "'");
				} else {
					player.sendMessage("Error adding '" + Players + "' to Region '" + rgn.getRegionName() + "'");
				}
				return true;
			} else {
				player.sendMessage("No such Region '" + regionName + "'");
			}	
		} else {
			player.sendMessage("Please Review Your Args:");
			player.sendMessage("   /dg adduser <region> <name|name,name1>");
		}
		return false;
	}
	
	public static boolean removeUser(Player player, String[] Args, DynamicGroups plugin) {
		if(Args.length >= 4 && Args[2] != null && Args[2].trim().length() > 0
				 && Args[3] != null && Args[3].trim().length() > 0) {
			
			String regionName = Args[2].trim();
			Region rgn = plugin.getRegion(regionName);
			if(rgn != null) {
				String Players = Utils.cleanString(Utils.mergeArray(Args, 3));
				if(Players.contains(",")) {
					if(Players.startsWith(",")) {
						Players = Players.substring(1, Players.length());
					}
					if(Players.endsWith(",")) {
						Players = Players.substring(0, Players.length() -1);
					}
					if(Players.contains(",")) {
						String[] aPlayers = Players.split(",");
						int count = 0;
						for(String ply : aPlayers) {
							if(ply.trim().length() > 0) {
								rgn.removeUser(ply);
								count++;
							}
						}
						if(rgn.save(plugin)) {
							player.sendMessage(String.valueOf(count) + " Players Removed From Region '" + rgn.getRegionName() + "'");
						} else {
							player.sendMessage("Failed to remove " + String.valueOf(count) + " Players from Region '" + rgn.getRegionName() + "'");
						}
						return true;
					}
				}
				rgn.removeUser(Players);

				if(rgn.save(plugin)) {
					player.sendMessage("'" + Players + "' removed from Region '" + rgn.getRegionName() + "'");
				} else {
					player.sendMessage("Error removing '" + Players + "' from Region '" + rgn.getRegionName() + "'");
				}
				
				return true;
			} else {
				player.sendMessage("No such Region '" + regionName + "'");
			}	
		} else {
			player.sendMessage("Please Review Your Args:");
			player.sendMessage("   /dg adduser <region> <name|name,name1>");
		}
		return false;
	}
	
	public static void listRegions(Player player, DynamicGroups plugin) {
		player.sendMessage("Current Regions (" + String.valueOf(plugin.getRegionList().size()) + "):");
		for(Region rgn : plugin.getRegionList()) {
			player.sendMessage("  - " + rgn.getRegionName());
		}
	}
	
	public static void groupChat(Player player, String[] Args, DynamicGroups plugin) {
		//Player has to be in Region to chat.
		boolean inRegion = false;
		if(Args.length > 2) {
			String Message = Utils.mergeArray(Args, 2).trim();
			if(Message.trim().length() > 0) {
				for(Region rgn : plugin.getRegionList()) {
					if(rgn.isInRegion(player.getLocation())) {
						if(rgn.containsUser(player.getName())) {
							for(String ply : rgn.getUserList()) {
								Player msgPly = plugin.getServer().getPlayer(ply);
								if(msgPly != null) {
									msgPly.sendMessage(ChatColor.DARK_GREEN + 
											"[" + rgn.getRegionName() + "] " + ChatColor.GRAY +
											player.getName() + ChatColor.DARK_GRAY + ": " +
											ChatColor.WHITE +
											Message);

									inRegion = true;
								}
							}
						}
					}
				}
			}
		}
		if(!inRegion) {
			player.sendMessage(ChatColor.BLUE + "You need to be in a Region & It's Group to Chat");
		}
	}
}
