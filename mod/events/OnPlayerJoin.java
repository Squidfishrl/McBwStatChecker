package playerLogger.SquidFish.mod.events;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import playerLogger.SquidFish.mod.PlayerLog;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.Settings;
import playerLogger.SquidFish.mod.commands.FetchPlayers;

public class OnPlayerJoin {
	@SubscribeEvent
	public void PlayerJoin(EntityJoinWorldEvent event) {
		
		if(Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS)) {
			
			if(event.entity != null && event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;
				
//				System.out.println(Minecraft.getMinecraft().thePlayer.getName());
				
				if( player.getName().equals(Minecraft.getMinecraft().thePlayer.getName()) ) {
					// if switched server
					

					// same as executing /fetch_players
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

//					String populationInfo = Minecraft.getMinecraft().getCurrentServerData().populationInfo;
//					System.out.println(populationInfo);
//					String maxPlayers = populationInfo.split("/", 2)[1];
					
//					if( Integer.parseInt(maxPlayers) < 17) {
//						// if server size is less than 17  (a 4v4v4v4 or 2v2v2v2v2v2v2v2 bedwars game has 16 players)
//						System.out.println("I detect server!");			
//					}
				}
				
//				try {
//					PrintWriter pw = new PrintWriter(new FileOutputStream(Reference.PLAYER_LOG_FILE_NAME, true));
//					pw.println(player.getName());
//					pw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
			}
		}

	}
}
