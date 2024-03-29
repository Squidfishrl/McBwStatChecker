package playerLogger.SquidFish.mod.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import playerLogger.SquidFish.mod.Reference;

public class PlayerLog {
	
	public static void clear_log() throws IOException{
		
		FileWriter nameWriter = new FileWriter(Reference.PLAYER_LOG_FILE_NAME);
			
		nameWriter.write("");
		nameWriter.close();
	}
	
	public static void append_to_log(String playerName) throws FileNotFoundException {
		
			PrintWriter pw = new PrintWriter(new FileOutputStream(Reference.PLAYER_LOG_FILE_NAME, true));
			pw.println("add "+ playerName);
			pw.close();
	}
	
	public static void remove_to_log(String playerName) throws FileNotFoundException {
		// adds rm <playername>
		PrintWriter pw = new PrintWriter(new FileOutputStream(Reference.PLAYER_LOG_FILE_NAME, true));
		pw.println("rm " + playerName);
		pw.close();
	}	
	
	public static void rm_from_log(String playerName) throws IOException {
		// removes playername from the file
		File playerLog = new File(Reference.PLAYER_LOG_FILE_NAME);
		File tempFile = new File("tempFileForPlayerLog.txt");
		
		BufferedReader reader = new BufferedReader(new FileReader(playerLog));
		BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));
		
		String currentLine;
		
		while( (currentLine = reader.readLine()) != null ) {
			
			if(currentLine.indexOf(playerName) != -1) {
				continue;
			}
			
			writer.write(currentLine + "\n");
		}
		
		writer.close();
		reader.close();
		
		tempFile.renameTo(playerLog);
		
	}
}
