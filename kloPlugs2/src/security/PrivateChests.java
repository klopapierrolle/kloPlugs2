package security;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import main.MainClass;
import misc.JobTypes;
import misc.Jobs;
import misc.MiscellaneousMethods;

public class PrivateChests implements Listener {
	private Plugin plugin = MainClass.getPlug();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(event.getClickedBlock().getType() == Material.CHEST) {
				if(getOwner(event.getClickedBlock()) != null) {
					if(p != getOwner(event.getClickedBlock())) {
						if(Jobs.hasJob(p, JobTypes.POLICEMAN)) {
							if(p.getItemInHand().getType() == Material.PAPER) {
								if(p.getItemInHand().hasItemMeta()) {
									if(p.getItemInHand().getItemMeta().getDisplayName().equals("Beschluss:" + getOwner(event.getClickedBlock()).getName().substring(0, 10))) {
										p.sendMessage(ChatColor.GREEN + "Truhe von " + getOwner(event.getClickedBlock()).getName() + " geöffnet.");
									}
									else {
										event.setCancelled(true);
										p.sendMessage(ChatColor.RED + "Das ist nicht deine Truhe und du hast keinen Beschluss.");
									}
								}
								else {
									event.setCancelled(true);
									p.sendMessage(ChatColor.RED + "Das ist nicht deine Truhe und du hast keinen Beschluss.");
								}
							}
							else {
								event.setCancelled(true);
								p.sendMessage(ChatColor.RED + "Das ist nicht deine Truhe und du hast keinen Beschluss.");
							}
						}
						else {
							event.setCancelled(true);
							p.sendMessage(ChatColor.RED + "Das ist nicht deine Truhe.");
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player p = event.getPlayer();
		if(event.getBlock().getType() == Material.CHEST) {
			if(p != getOwner(event.getBlock())) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "Das ist nicht deine Truhe.");
			}
			else {
				if(p.getGameMode() != GameMode.CREATIVE) {
					event.setCancelled(true);
					p.sendMessage(ChatColor.RED + "Das darfst du nur im Creative-Mode.");
				}
			}
		}
	}
	
	private OfflinePlayer getOwner(Block block) {
		OfflinePlayer player = null;
		for(OfflinePlayer currentPlayer : MiscellaneousMethods.getWorldPlayers(block.getWorld().getName() + "\\playerdata")) {
			File f = new File(plugin.getDataFolder() + "\\Security\\" + currentPlayer.getName());
			if(f.exists()) {
				try {
					Scanner sc = new Scanner(f);
					String[] blockLocations = sc.useDelimiter("\\Z").next().split(";");
					sc.close();
					final Double[] coordLocation = {block.getLocation().getX(), block.getLocation().getY(), block.getLocation().getZ()};
					boolean isBlockCoord = false;
					for(int i = 0; i < blockLocations.length; i++) {
						String[] currentBlockLocation = blockLocations[i].split(":");
						int k = 0;
						for(int j = 0; j < currentBlockLocation.length; j++) {
							if(currentBlockLocation.length != 3) {
								Bukkit.broadcastMessage(ChatColor.RED + "Fehler in der Sicherheitsdatei von " + currentPlayer.getName() + ".");
								continue;
							}
							if(Double.parseDouble(currentBlockLocation[j]) == coordLocation[j]) {
								k++;
							}
							else {
								k--;
							}
						}
						if(k == 3) {
							isBlockCoord = true;
						}
					}
					if(isBlockCoord) {
						player = currentPlayer;
						break;
					}
				}
				catch(NumberFormatException | FileNotFoundException e) {
					Bukkit.broadcastMessage(ChatColor.RED + "Fehler in der Sicherheitsdatei von " + currentPlayer.getName() + ".");
					continue;
				}
			}
		}
		return player;
	}
}
