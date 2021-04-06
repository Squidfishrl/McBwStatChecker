package playerLogger.SquidFish.mod;


import java.io.File;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import playerLogger.SquidFish.mod.commands.AutoFetchPlayers;
import playerLogger.SquidFish.mod.commands.FetchPlayers;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions = "*")
public class Main {
	

	
	@EventHandler
	public void pre_init(FMLPreInitializationEvent event) {
		
		if(Settings.settings_exist() == false) {
			Settings.settings_write();
		}
		
		ClientCommandHandler.instance.registerCommand(new FetchPlayers());
		ClientCommandHandler.instance.registerCommand(new AutoFetchPlayers());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	

	
	@EventHandler
	public void post_init(FMLPostInitializationEvent event) {
		
	}
}
