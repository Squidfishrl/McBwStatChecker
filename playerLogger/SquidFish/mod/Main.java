package playerLogger.SquidFish.mod;


import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import playerLogger.SquidFish.mod.commands.AutoFetchPlayers;
import playerLogger.SquidFish.mod.commands.FetchPlayers;
import playerLogger.SquidFish.mod.events.OnChatMsgRecieved;
import playerLogger.SquidFish.mod.events.OnPlayerJoin;
import playerLogger.SquidFish.mod.files.Settings;



@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions = "*")
public class Main {
	

	
	@EventHandler
	public void pre_init(FMLPreInitializationEvent event) {
		
		if(Settings.settings_exist() == false) {
			Settings.settings_write();
		}else {
			Settings.settings_change_value(Reference.SETTINGS_PLAYER_LOCATION, "false");
			// so that it changes if ur game crashes during a bw game
		}
		
		ClientCommandHandler.instance.registerCommand(new FetchPlayers());
		ClientCommandHandler.instance.registerCommand(new AutoFetchPlayers());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
//		MinecraftForge.EVENT_BUS.register(new OnPlayerJoin());
		MinecraftForge.EVENT_BUS.register(new OnChatMsgRecieved());
	}
	

	
	@EventHandler
	public void post_init(FMLPostInitializationEvent event) {
		
	}
}
