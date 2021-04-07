package playerLogger.SquidFish.mod.events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import playerLogger.SquidFish.mod.PlayerLog;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.Settings;
import playerLogger.SquidFish.mod.commands.FetchPlayers;

public class OnChatMsgRecieved {

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		
		if(Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS)) {
			
			String msg = event.message.getUnformattedText();
			
			if(player_join(msg)) {
				// a player has joined
				
				String playerName = msg.split(" ", 2)[0];
				
				if(playerName.equals(Minecraft.getMinecraft().thePlayer.getName())) {
					// I have joined
					ArrayList<String> playerList = FetchPlayers.fetch_playerList();
					
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
				    		
				    	Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText(name));
					}
				}else {
					// people after me have joined
					try {
						PlayerLog.append_to_log(playerName);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}

				
			}else if(player_quit(msg)) {
				// a player has quit
				String playerName = msg.split(" ", 2)[0];
				
				try {
					PlayerLog.remove_from_log(playerName);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	private static boolean player_join(String msg) {
		if(msg.indexOf(Reference.AUTO_ADD_PLAYER_TRIGGER) > 0) {
			return true;
		}else {
			return false;
		}
	}
	
	private static boolean player_quit(String msg) {
		
		if(msg.indexOf(Reference.AUTO_RM_PLAYER_TRIGGER) > 0) {
			return true;
		}else {
			return false;
		}
	}
}
