package com.craftmin.bukkit.commands;

public class CommandDefinition {
	
	
	public static class Command {
		
		/*
		 * listregions
		 * saveall
		 * loadall
		 * adduser <regionname> <user>
		 * removeuser <regionname> <user>
		 * 
		 * 
		 */
		
		
		public static final String[] Commands = {	"selectregion", "createkingdom",
													"adduser", "removeuser", "listregions"};

		//public static final int COMMAND_ELSE = -1;
		public static final int COMMAND_SELECTREGION = 	0;
		public static final int COMMAND_SAVEREGION = 	1;
		public static final int COMMAND_ADDUSER = 		2;
		public static final int COMMAND_REMOVEUSER = 	3;
		public static final int COMMAND_LISTREGIONS = 	4;
		
	}
	
	public static int getDGCOmmandValue(String Command) {
		String cmd = Command.trim();
		if(cmd != null && cmd.length() > 0) {
			int i = 0;
			for(String staticCmd : CommandDefinition.Command.Commands) {
				if(staticCmd.equalsIgnoreCase(cmd)) {
					return i;
				} i++;
			}
		}
		return -1;
	}

}
