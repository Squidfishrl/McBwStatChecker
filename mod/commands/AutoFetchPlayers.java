package playerLogger.SquidFish.mod.commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import playerLogger.SquidFish.mod.Reference;
import playerLogger.SquidFish.mod.Settings;


public class AutoFetchPlayers extends CommandBase{

	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "fetch_players_auto";
	}

	@Override
	public String getCommandUsage(ICommandSender sender) {
		// TODO Auto-generated method stub
		return "Automatically log players";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] args) throws CommandException {

		System.out.println("why the hell am I here");
		boolean auto_setting = (boolean) Settings.settings_read_value_bool(Reference.SETTINGS_AUTO_FETCH_PLAYERS);
		String replyMsg = "";
		
		if(auto_setting == true) {
			replyMsg = "[OFF] "+ Reference.SETTINGS_AUTO_FETCH_PLAYERS;
			Settings.settings_change_value(Reference.SETTINGS_AUTO_FETCH_PLAYERS, "false");
		}else {
			replyMsg = "[ON] " + Reference.SETTINGS_AUTO_FETCH_PLAYERS;
			Settings.settings_change_value(Reference.SETTINGS_AUTO_FETCH_PLAYERS, "true");
		}
		
		sender.addChatMessage(new ChatComponentText(replyMsg));
	}
	
	@Override
	public int getRequiredPermissionLevel(){
	  return 0;
	}
	

}
