package com.tutorial.mod;


import com.tutorial.mod.command.FetchPlayers;
import com.tutorial.mod.proxy.CommonProxy;

import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;


@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, acceptableRemoteVersions = "*")
public class TutorialMod {
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public void pre_init(FMLPreInitializationEvent event) {
		ClientCommandHandler.instance.registerCommand(new FetchPlayers());
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerRenders();
	}
	
//	@EventHandler
//	public void serverLoad(FMLServerStartingEvent event)
//	{
//	    // register server commands
//
//	event.registerServerCommand(new FetchPlayers());
//	}
	
	@EventHandler
	public void post_init(FMLPostInitializationEvent event) {
		
	}
}
