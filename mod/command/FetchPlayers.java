package com.tutorial.mod.command;

import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

public class FetchPlayers extends CommandBase {

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "fetch_players";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Displays a list of all players";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {
		// TODO Auto-generated method stub
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
    	
		for (NetworkPlayerInfo loadedPlayer : players) {
			String loadedPlayerName = Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(loadedPlayer);
    		if (loadedPlayerName != null){
    			sender.addChatMessage(new ChatComponentText(loadedPlayerName));
    		}
		}		
	}

	@Override
	public int getRequiredPermissionLevel(){
	  return 0;
	}
}
