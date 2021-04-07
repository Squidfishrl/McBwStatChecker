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
import net.minecraft.util.IChatComponent;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.files.PlayerLog;
import playerLogger.SquidFish.mod.files.Settings;


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

    	
		ArrayList<String> playerList;
		
		if(Settings.settings_read_value_str(Reference.SETTINGS_PLAYER_LOCATION).equals(Reference.SETTING_PLAYER_LOCATION_BW_GAME)) {
			playerList = fetch_playerList_coloured();
		}else {
			playerList = fetch_playerList_uncoloured();
		}
		
//		if(args.length != 0) {
//			if(args[0].equals("coloured")) {
//				playerList = fetch_playerList_coloured();
//			}else {
//				playerList = fetch_playerList_uncoloured();
//			}
//		}else {
//			playerList = fetch_playerList_uncoloured();
//		}
		
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


	public static ArrayList<String> fetch_playerList_uncoloured(){
		
		ArrayList<String> playerNameList = new ArrayList<String>();
		
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
		
		for (NetworkPlayerInfo loadedPlayer : players) {

			String loadedPlayerName = Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(loadedPlayer);
//			String loadedPlayerName = loadedPlayer.getDisplayName().getFormattedText();
			if(loadedPlayerName != null) {
				playerNameList.add(loadedPlayerName.substring(2));  // remove colour code
			}
		}
		
		return playerNameList;
	}
	
	public static ArrayList<String> fetch_playerList_coloured(){
		// shows text formatting on all players, alongside watchdogs and npcs
		// should be used only when in a bw game
		
		ArrayList<String> playerNameList = new ArrayList<String>();
		boolean errors = false;
		
		Collection<NetworkPlayerInfo> players = Minecraft.getMinecraft().getNetHandler().getPlayerInfoMap();
		
		for (NetworkPlayerInfo loadedPlayer : players) {
			
			String loadedPlayerName = Minecraft.getMinecraft().ingameGUI.getTabList().getPlayerName(loadedPlayer);
			if(loadedPlayerName != null) {
				
				try {
					char PlayerTeamColour = ' ';
					PlayerTeamColour = loadedPlayerName.charAt(4);

					String editedName = loadedPlayerName.split(" ", 0)[1];
					playerNameList.add(editedName.substring(4) + " " + PlayerTeamColour);
				}catch(NullPointerException e){
					e.printStackTrace();
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("error for no reason pog"));
				}catch(ArrayIndexOutOfBoundsException e) {
					e.printStackTrace();
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("error for no reason lmao"));
					errors = true;
				}catch(StringIndexOutOfBoundsException e) {
					e.printStackTrace();
					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("error for no reason bruh"));
				}
			
			}
		}
//		if(errors) {
//			try {
//				Thread.sleep(5000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("fml"));
//			}
//			return fetch_playerList_coloured();
//		}else {}
		return playerNameList;
	}

}
