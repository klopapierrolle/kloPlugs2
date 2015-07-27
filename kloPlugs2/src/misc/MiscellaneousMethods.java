package misc;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public abstract class MiscellaneousMethods {
	public static OfflinePlayer[] getWorldPlayers(String path) {
		File folder = new File(path);
		File[] listOfFiles = folder.listFiles();
		OfflinePlayer[] players = new OfflinePlayer[listOfFiles.length];
		int pos = 0;
		for(int i = 0; i < listOfFiles.length; i++) {
			if(listOfFiles[i].isFile()) {
				if(listOfFiles[i].getName().endsWith(".dat")) {
					players[pos] = Bukkit.getOfflinePlayer(UUID.fromString(listOfFiles[i].getName().substring(0, listOfFiles[i].getName().indexOf('.'))));
					pos++;
				}
			}
		}
		return players;
	}
}
