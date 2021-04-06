package playerLogger.SquidFish.mod;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.json.JSONException;
import org.json.JSONObject;

import net.minecraft.util.ChatComponentText;


public class Settings {

	
	public static boolean settings_exist() {
		
		File modSettings = new File(Reference.SETTINGS_FILE_NAME);
		
		if(modSettings.exists()) {
			return true;
		}else {
			return false;
		}
	}
	
	public static void settings_write() {
		
		JSONObject jsonObj = new JSONObject();
		
		try {
			jsonObj.put(Reference.SETTINGS_AUTO_FETCH_PLAYERS, false);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		try {
			FileWriter file = new FileWriter(Reference.SETTINGS_FILE_NAME);
			file.write(jsonObj.toString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static String settings_get_json_str() {
		
		String jsonString = "";
		
		try {
			byte[] encoded = Files.readAllBytes(Paths.get(Reference.SETTINGS_FILE_NAME));
			jsonString = new String(encoded, StandardCharsets.US_ASCII);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonString;
	}
	
	public static String settings_read_value_str(String Key) {
		
		String jsonString = settings_get_json_str();
		JSONObject jObj = new JSONObject(jsonString);
		String setting = jObj.getString(Key);
//		System.out.println(setting);
		return setting;
	}

	public static boolean settings_read_value_bool(String Key) {
		
		String jsonString = settings_get_json_str();
		JSONObject jObj = new JSONObject(jsonString);
		boolean setting = jObj.getBoolean(Key);
//		System.out.println(setting);
		return setting;
	}
	
	public static void settings_change_value(String Key, String newValue) {
		String jsonString = settings_get_json_str();
		JSONObject jObj = new JSONObject(jsonString);
		jObj.put(Key, newValue);
		

		
		try {
			FileWriter file = new FileWriter(Reference.SETTINGS_FILE_NAME);
			
			file.write(jObj.toString());
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}

}
