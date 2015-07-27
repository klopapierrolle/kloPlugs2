package items.misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Dye;

import main.MainClass;

public class IdentityCard implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.BOOK) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().substring(0, 9).equals("Ausweis: ")) {
						String args2 = p.getItemInHand().getItemMeta().getDisplayName().substring(9);
						if(MainClass.getPlug().getServer().getPlayer(args2) != null) {
							File f = new File(MainClass.getPlug().getDataFolder() + "\\Ausweise\\" + args2);
							if(f.exists()) {
								try {
									Scanner sc = new Scanner(f);
									String[] values = (sc.useDelimiter("\\Z").next()).split(":");
									sc.close();
									if(values.length != 4) {
										p.sendMessage(ChatColor.RED + "Die Informationen über diese Person sind veraltet oder fehlerhaft.");
									}
									else {
										createIDInventory(p, values[0], values[1], Byte.valueOf(values[2]), values[3]);
									}
								}
								catch(NumberFormatException | FileNotFoundException e) {
									p.sendMessage(ChatColor.RED + "Die Informationen über diese Person sind veraltet oder fehlerhaft.");
								}
							}
							else {
								p.sendMessage(ChatColor.RED + "Es gibt zur Zeit keine Informationen über diese Person in der Datenbank.");
							}
						}
						else {
							p.sendMessage(ChatColor.RED + "Der Personalausweis dieses Spielers ist nicht verfügbar.");
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getInventory().getName().length() >= 9) {
			if(event.getInventory().getName().substring(0, 9).equals("Ausweis: ")) {
				Player p = (Player) event.getWhoClicked();
				p.closeInventory();
				p.sendMessage(ChatColor.BLUE + "Personalausweis geschlossen.");
				event.setCancelled(true);
			}
		}
		if(event.getInventory().getType() == InventoryType.ANVIL) {
			if(event.getCurrentItem().getType() == Material.BOOK) {
				if(event.getCurrentItem().hasItemMeta()) {
					if(event.getCurrentItem().getItemMeta().getDisplayName().substring(0, 9).equals("Ausweis: ")) {
						Player p = (Player) event.getWhoClicked();
						if(p.getGameMode() != GameMode.CREATIVE) {
							event.setCancelled(true);
							p.closeInventory();
							p.sendMessage(ChatColor.RED + "Nope.");
						}
					}
				}
			}
		}
	}
	
	private ItemStack playerHead(Player p) {
		ItemStack playerSkull = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta playerSkullMeta = (SkullMeta) playerSkull.getItemMeta();
		playerSkullMeta.setOwner(p.getName());
		playerSkullMeta.setDisplayName("Name: " + ChatColor.DARK_AQUA + ChatColor.ITALIC + p.getName());
		playerSkull.setItemMeta(playerSkullMeta);
		return playerSkull;
	}
	
	private void createIDInventory(Player p, String playerName, String jobNames, byte WBK, String creepusLevel) {
		Inventory inv = Bukkit.createInventory(null, 27, ("Ausweis: " + p.getName()));
		
		ItemStack name = playerHead(MainClass.getPlug().getServer().getPlayer(playerName));
		inv.setItem(10, name);
		
		ItemStack jobs = new ItemStack(Material.GOLD_PICKAXE);
		ItemMeta jobsMeta = jobs.getItemMeta();
		jobsMeta.setDisplayName("Beruf(e): " + ChatColor.AQUA + jobNames);
		jobs.setItemMeta(jobsMeta);
		inv.setItem(12, jobs);
		
		DyeColor dyeColor = DyeColor.GRAY;
		String wbkName = "Keine";
		if(WBK == 1) {
			dyeColor = DyeColor.RED;
			wbkName = "Rot";
		}
		else if(WBK == 2) {
			dyeColor = DyeColor.YELLOW;
			wbkName = "Gelb";
		}
		else if(WBK == 3) {
			dyeColor = DyeColor.GREEN;
			wbkName = "Grün";
		}
		Dye dye = new Dye();
		dye.setColor(dyeColor);
		ItemStack wbk = dye.toItemStack();
		wbk.setAmount(1);
		ItemMeta wbkMeta = wbk.getItemMeta();
		wbkMeta.setDisplayName("WBK: " + ChatColor.AQUA + wbkName);
		wbk.setItemMeta(wbkMeta);
		inv.setItem(14, wbk);
		
		ItemStack creepus = new ItemStack(Material.SKULL_ITEM, 1, (short) 4);
		ItemMeta creepusMeta = creepus.getItemMeta();
		creepusMeta.setDisplayName("Creepus-Level: " + ChatColor.AQUA + creepusLevel);
		creepus.setItemMeta(creepusMeta);
		inv.setItem(16, creepus);
		
		p.openInventory(inv);
	}
}
