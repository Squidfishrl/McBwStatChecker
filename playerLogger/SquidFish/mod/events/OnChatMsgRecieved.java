package playerLogger.SquidFish.mod.events;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.commands.FetchPlayers;
import playerLogger.SquidFish.mod.files.PlayerLog;
import playerLogger.SquidFish.mod.files.Settings;

public class OnChatMsgRecieved {

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) throws IOException {
		
		if(Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS)) {
			
			String msg = event.message.getUnformattedText();
//			PlayerLog.append_to_log(msg);
			
			if(msg.indexOf(Reference.JOIN_BW_PREGAME_TRIGGER) > 0) {
				// a player has joined
				
				String playerName = msg.split(" ", 2)[0];
				
				if(playerName.equals(Minecraft.getMinecraft().thePlayer.getName())) {
					// I have joined

					Settings.settings_change_value(Reference.SETTINGS_PLAYER_LOCATION, Reference.SETTING_PLAYER_LOCATION_BW_PRE_GAME);
					
					ArrayList<String> playerList = FetchPlayers.fetch_playerList_uncoloured();
					
					PlayerLog.clear_log();
					
				   for(String name : playerList) {
					   
						PlayerLog.append_to_log(name);

				    		
				    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(name));
					}

				}else {
					// people after me have joined

					PlayerLog.append_to_log(playerName);

				}

			}else if(msg.indexOf(Reference.QUIT_BW_PREGAME_TRIGGER) > 0) {
				// a player has quit
				
				String playerName = msg.split(" ", 2)[0];
				
				PlayerLog.rm_from_log(playerName);


			}else if(msg.indexOf(Reference.IN_LOBBY_TRIGGER) > 0 && msg.indexOf(Minecraft.getMinecraft().thePlayer.getName()) != -1) {
				// I quit to lobby
				Settings.settings_change_value(Reference.SETTINGS_PLAYER_LOCATION, Reference.SETTING_PLAYER_LOCATION_LOBBY);
				
				PlayerLog.clear_log();

			}else if(msg.indexOf(Reference.BEDWARS_GAME_STARTED_TRIGGER) > 0) {
				// the bedwars game started
				Settings.settings_change_value(Reference.SETTINGS_PLAYER_LOCATION, Reference.SETTING_PLAYER_LOCATION_BW_GAME);
				
				Timer timer = new Timer();
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						ClientCommandHandler.instance.executeCommand(Minecraft.getMinecraft().thePlayer, "/fetch_players");
					}
				}, 10*1000);
				

				
				
//				ArrayList<String> playerList_coloured = fetch_playerList_coloured();
//				Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText("Success!!!!" + playerList_coloured.get(1)));
//				PlayerLog.clear_log();
//				for(String name : playerList_coloured) {
//
//					PlayerLog.append_to_log(name);
//					Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(name));
//				}
				
			}

		}
	}
	
	
}
