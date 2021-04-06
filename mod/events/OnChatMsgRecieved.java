package playerLogger.SquidFish.mod.events;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.Settings;

public class OnChatMsgRecieved {

	@SubscribeEvent
	public void onChat(ClientChatReceivedEvent event) {
		
		if(Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS)) {
			
			String msg = event.message.getUnformattedText();
			
			if(player_join(msg)) {
				// a player has joined
				
				String playerName = msg.split(" ", 2)[0];
				
				try {
					PrintWriter pw = new PrintWriter(new FileOutputStream(Reference.PLAYER_LOG_FILE_NAME, true));
					pw.println(playerName);
					pw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}else if(player_quit(msg)) {
				// remove player from player list
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
