//package playerLogger.SquidFish.mod.events;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraftforge.event.entity.EntityJoinWorldEvent;
//import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
//import playerLogger.SquidFish.mod.Reference;
//import playerLogger.SquidFish.mod.Settings;
//
//public class OnPlayerJoin {
//	@SubscribeEvent
//	public void PlayerJoin(EntityJoinWorldEvent event) {
//		
//		if(Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS)) {
//			if(event.entity != null && event.entity instanceof EntityPlayer) {
//				EntityPlayer player = (EntityPlayer) event.entity;
//
//				try {
//					PrintWriter pw = new PrintWriter(new FileOutputStream(Reference.PLAYER_LOG_FILE_NAME, true));
//					pw.println(player.getName());
//					pw.close();
//				} catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
//}
