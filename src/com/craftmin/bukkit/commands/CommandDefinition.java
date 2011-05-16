package com.craftmin.bukkit.commands;

public class CommandDefinition {
	
	
	public static class Command {
		
		/*
		 * listregions
		 * saveall
		 * loadall
		 * adduser <regionname> <user>
		 * removeuser <regionname> <user>
		 * setowner <regionname> <user>
		 * 
		 * 
		 */
		
		
		public static final String[] Commands = {	"selectregion", "createkingdom",
													"adduser", "removeuser", "listregions",
													"g"};

		//public static final int COMMAND_ELSE = -1;
		public static final int COMMAND_SELECTREGION = 	0;
		public static final int COMMAND_SAVEREGION = 	1;
		public static final int COMMAND_ADDUSER = 		2;
		public static final int COMMAND_REMOVEUSER = 	3;
		public static final int COMMAND_LISTREGIONS = 	4;
		public static final int COMMAND_GROUPCHAT = 	5;
		
		public static class OwnerCommands {
			//This is just an idea of how 'dynamic groups' will work
			public static final String[] Commands = {	"setrank", "createrank"};
			public static final int COMMAND_SETRANK_1 = 	0;
		}
		
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
