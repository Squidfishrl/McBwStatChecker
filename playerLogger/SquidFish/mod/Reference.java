package playerLogger.SquidFish.mod;

import net.minecraft.client.Minecraft;

public class Reference {
	
	public static final String MOD_ID = "pl";
	public static final String MOD_NAME = "PlayerLogger";
	public static final String VERSION = "1.2";

	public static final String PLAYER_LOG_FILE_NAME = "playerNames.txt";
	public static final String SETTINGS_FILE_NAME = "log_settings.json";
	
	public static final String SETTINGS_AUTO_FETCH_PLAYERS = "AutoFetchPlayers";
	public static final String SETTINGS_PLAYER_LOCATION = "PlayerLocation";
	public static final String SETTING_PLAYER_LOCATION_LOBBY = "lobby";
	public static final String SETTING_PLAYER_LOCATION_BW_PRE_GAME = "bwPreGame";
	public static final String SETTING_PLAYER_LOCATION_BW_GAME = "bwGame";
	
	public static final String JOIN_BW_PREGAME_TRIGGER = "has joined";
	public static final String QUIT_BW_PREGAME_TRIGGER = "has quit!";
	public static final String IN_LOBBY_TRIGGER = "joined the lobby!";
//	public static final String BEDWARS_GAME_STARTED = "                                  Bed Wars";
	public static final String BEDWARS_GAME_STARTED_TRIGGER = "  Bed Wars";
}
