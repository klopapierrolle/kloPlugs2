package items.weapons;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import main.MainClass;

public class HunterBow implements Listener {
	private Plugin plugin = MainClass.getPlug();
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.BOW) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("Hunter-Bow")) {
						event.setCancelled(true);
						if(p.getInventory().contains(Material.ARROW)) {
							if(!Weapon.HBowFreqList.contains(p)) {
								p.getInventory().getItem(p.getInventory().first(Material.ARROW)).setAmount(p.getInventory().getItem(p.getInventory().first(Material.ARROW)).getAmount() - 1);
								Weapon.HBowFreqList.add(p);
								Bullet b = new Bullet(p, Weapon.HBOW);
								b.setVelocity(b.getVelocity().multiply(3.0));
								new BukkitRunnable() {
									@Override
									public void run() {
										Vector v = b.getVelocity();
										new BukkitRunnable() {
											@Override
											public void run() {
												Vector w = b.getVelocity();
												if(v.equals(w)) {
													b.destroyProjectile();
													cancel();
												}
											}
										}.runTaskLater(plugin, 5L);
									}
								}.runTaskTimer(plugin, 0L, 5L);
								new BukkitRunnable() {
									@Override
									public void run() {
										Weapon.HBowFreqList.remove(p);
									}
								}.runTaskLater(plugin, 30L);
							}
						}
						else {
							p.sendMessage(ChatColor.RED + "Keine Pfeile mehr!");
						}
					}
				}
			}
		}
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		if(event.getInventory().getType() == InventoryType.ANVIL) {
			if(event.getSlotType() == SlotType.RESULT) {
				if(event.getCurrentItem().getType() == Material.BOW) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Hunter-Bow")) {
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
	}
}
