package playerLogger.SquidFish.mod;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class PlayerLog {
	
	public static void clear_log() throws IOException{
		
		FileWriter nameWriter = new FileWriter(Reference.PLAYER_LOG_FILE_NAME);
			
		nameWriter.write("");
		nameWriter.close();
	}
	
	public static void append_to_log(String playerName) throws FileNotFoundException {
		
			PrintWriter pw = new PrintWriter(new FileOutputStream(Reference.PLAYER_LOG_FILE_NAME, true));
			pw.println(playerName);
			pw.close();
	}
	
	public static void remove_from_log(String playerName) throws IOException {
		
		File playerLog = new File(Reference.PLAYER_LOG_FILE_NAME);
		File tempFile = new File("tempFileForPlayerLog.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(playerLog));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		String currentLine;
		
		while( (currentLine = reader.readLine()) != null ) {
			
			if(currentLine.indexOf(playerName) != -1) {
				continue;
			}
			
			writer.write(currentLine + System.getProperty("line.seperator"));
		}
		
		writer.close();
		reader.close();
		
		tempFile.renameTo(playerLog);
		
	}
}
