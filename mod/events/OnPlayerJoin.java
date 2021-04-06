package playerLogger.SquidFish.mod.events;

import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.Settings;

public class OnPlayerJoin {
	@SubscribeEvent
	public void PlayerJoin(EntityJoinWorldEvent event) {
		
		if(Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS)) {
			if(event.entity != null && event.entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) event.entity;

				try {
					FileWriter nameWriter = new FileWriter(Reference.PLAYER_LOG_FILE_NAME);
					nameWriter.write(player.getName() + "\n");
					nameWriter.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}
}
