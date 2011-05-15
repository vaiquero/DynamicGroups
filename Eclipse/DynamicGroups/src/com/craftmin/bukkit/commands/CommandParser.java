package com.craftmin.bukkit.commands;

import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import com.craftmin.bukkit.DynamicGroups;
public class CommandParser {
	
	
	public static void processCommand(PlayerCommandPreprocessEvent chatEvent, DynamicGroups plugin) {
		String Message = chatEvent.getMessage();
		if(Message.contains(" ")) {
			String[] Commands = Message.split(" ");
			if(Commands[1] != null && Commands[1].trim().length() > 0) {
				Player player = chatEvent.getPlayer();
				switch(CommandDefinition.getDGCOmmandValue(Commands[1].trim())) {
					case CommandDefinition.Command.COMMAND_SELECTREGION: {
						Command.selectRegion(player, plugin);
						break;
					} case CommandDefinition.Command.COMMAND_SAVEREGION: {
						Command.saveRegion(player, Commands, plugin);
						break;
					} case CommandDefinition.Command.COMMAND_ADDUSER: {
						Command.addUser(player, Commands, plugin);
						break;
					} case CommandDefinition.Command.COMMAND_REMOVEUSER: {
						Command.removeUser(player, Commands, plugin);
						break;
					} case CommandDefinition.Command.COMMAND_LISTREGIONS: {
						Command.listRegions(player, plugin);
						break;
					} default: {
						//Unknown DG Command
						break;
					}
				}
			}
		} else {
			if(Message.equalsIgnoreCase("/dg")) {
				chatEvent.getPlayer().sendMessage("Invalid Arguments");
			}
		}
	}

}
