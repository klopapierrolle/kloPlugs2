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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;

import main.MainClass;

public class M16 implements Listener {
	public M16() {
		m16();
		m16Ammo();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.IRON_BARDING) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("M16A2")) {
						if(!Weapon.M16AmmoList.containsKey(p)) {
							Bullet bullet = new Bullet(p, Weapon.M16);
							bullet.setVelocity(bullet.getVelocity().multiply(4.0));
							Weapon.M16AmmoList.put(p, 58);
							Weapon.M16FreqList.add(p);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Weapon.M16FreqList.remove(p);
								}
							},6L);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Bullet bullet = new Bullet(p, Weapon.M16);
									bullet.setVelocity(bullet.getVelocity().multiply(4.0));
								}
							},3L);
						}
						else {
							if(Weapon.M16AmmoList.get(p) > 0) {
								if(!Weapon.M16FreqList.contains(p)) {
									Bullet bullet = new Bullet(p, Weapon.M16);
									bullet.setVelocity(bullet.getVelocity().multiply(4.0));
									Weapon.M16AmmoList.put(p, Weapon.M16AmmoList.get(p) - 1);
									Weapon.M16FreqList.add(p);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Weapon.M16FreqList.remove(p);
										}
									},6L);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Bullet bullet = new Bullet(p, Weapon.M16);
											bullet.setVelocity(bullet.getVelocity().multiply(4.0));
										}
									},3L);
								}
							}
							else {
								if(!Weapon.M16FreqList.contains(p)) {
									p.sendMessage(ChatColor.RED + "Magazin leer!");
								}
							}
						}
					}
				}
			}
			else if(p.getItemInHand().getType() == Material.PRISMARINE_SHARD) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("5,56x45mm Ammo")) {
						if(p.getInventory().contains(Material.IRON_BARDING)) {
							if(!Weapon.M16AmmoList.containsKey(p)) {
								p.sendMessage(ChatColor.RED + "Das Magazin ist immer noch voll.");
							}
							else {
								if(Weapon.M16AmmoList.get(p) >= 60) {
									p.sendMessage(ChatColor.RED + "Das Magazin ist immer noch voll.");
								}
								else {
									p.sendMessage(ChatColor.GREEN + "Reloading now!");
									if(p.getItemInHand().getAmount() > 1) {
										p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
									}
									else {
										p.setItemInHand(null);
									}
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Weapon.M16AmmoList.put(p, 60);
											p.sendMessage(ChatColor.GREEN + "Successfully reloaded!");
										}
									},30L);
								}
							}
						}
						else {
							p.sendMessage(ChatColor.RED + "Dafür hast du keine passende Waffe.");
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
				if(event.getCurrentItem().getType() == Material.IRON_BARDING) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("M16A2")) {
							if(p.getGameMode() != GameMode.CREATIVE) {
								p.sendMessage(ChatColor.RED + "Nope.");
								event.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}
	
	private void m16() {
		ItemStack m16 = new ItemStack(Material.IRON_BARDING);
		ItemMeta m16Meta = m16.getItemMeta();
		m16Meta.setDisplayName("M16A2");
		m16.setItemMeta(m16Meta);
		
		ShapedRecipe IronHorseArmor = new ShapedRecipe(m16);
		IronHorseArmor.shape("$G$", "%DD", " ID");
		IronHorseArmor.setIngredient('$', Material.IRON_BLOCK);
		IronHorseArmor.setIngredient('G', Material.THIN_GLASS);
		IronHorseArmor.setIngredient('D', Material.DIAMOND_BLOCK);
		IronHorseArmor.setIngredient('I', Material.IRON_INGOT);
		IronHorseArmor.setIngredient('%', Material.GOLD_BLOCK);
		MainClass.getPlug().getServer().addRecipe(IronHorseArmor);
		
		ShapedRecipe IronHorseArmor2 = new ShapedRecipe(m16);
		IronHorseArmor2.shape("$G$", "DD%", "DI ");
		IronHorseArmor2.setIngredient('$', Material.IRON_BLOCK);
		IronHorseArmor2.setIngredient('G', Material.THIN_GLASS);
		IronHorseArmor2.setIngredient('D', Material.DIAMOND_BLOCK);
		IronHorseArmor2.setIngredient('I', Material.IRON_INGOT);
		IronHorseArmor2.setIngredient('%', Material.GOLD_BLOCK);
		MainClass.getPlug().getServer().addRecipe(IronHorseArmor2);
	}
	private void m16Ammo() {
		ItemStack ammoM16 = new ItemStack(Material.PRISMARINE_SHARD);
		ItemMeta ammoM16Meta = ammoM16.getItemMeta();
		ammoM16Meta.setDisplayName("5,56x45mm Ammo");
		ammoM16.setItemMeta(ammoM16Meta);
		
		ShapedRecipe PrisShards = new ShapedRecipe(ammoM16);
		PrisShards.shape("$$$", "G%G", "$$$");
		PrisShards.setIngredient('$', Material.IRON_INGOT);
		PrisShards.setIngredient('%', Material.SULPHUR);
		PrisShards.setIngredient('G', Material.GOLD_INGOT);
		MainClass.getPlug().getServer().addRecipe(PrisShards);
	}
}
