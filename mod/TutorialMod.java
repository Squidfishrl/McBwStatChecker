package com.tutorial.mod;


import com.tutorial.mod.command.FetchPlayers;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions = "*")
public class TutorialMod {
	

	
	@EventHandler
	public void pre_init(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new FetchPlayers());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
	}
	

	
	@EventHandler
	public void post_init(FMLPostInitializationEvent event) {
		
	}
}
