package playerLogger.SquidFish.mod.commands;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import playerLogger.SquidFish.mod.PlayerLog;


public class FetchPlayers extends CommandBase {
	
	
	@Override
	public String getCommandName() {

		return "fetch_players";
	}

	
	@Override
	public String getCommandUsage(ICommandSender sender) {
		
		return "Displays a list of all players";
	}

	
	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {

    	
		ArrayList<String> playerList = fetch_playerList();
		
		try {
			PlayerLog.clear_log();
		} catch (IOException e) {
			e.printStackTrace();
		}

	   for(String name : playerList) {
		   
	    	try {
				PlayerLog.append_to_log(name);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	    		
	    	sender.addChatMessage(new ChatComponentText(name));
		}

	}

	
	@Override
	public int getRequiredPermissionLevel(){
		
	  return 0;
	}


	public static ArrayList<String> fetch_playerList(){
		
		ArrayList<String> playerNameList = new ArrayList<String>();
		
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
		
		for (NetworkPlayerInfo loadedPlayer : players) {
			String loadedPlayerName = Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(loadedPlayer);
			
			if(loadedPlayerName != null) {
				playerNameList.add(loadedPlayerName);
			}
		}
		
		return playerNameList;
	}

}
