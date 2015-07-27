package items.weapons;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
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

public class Minigun implements Listener {
	public Minigun() {
		minigun();
		minigunAmmo();
	}
	
	private void setPlayerLocation(Player p) {
		Location lp = p.getLocation();
		float f = (float) Math.toDegrees(Math.asin((Math.random()*Math.pow(20, -1)) - (Math.pow(40, -1))));
		lp.setPitch(p.getLocation().getPitch() + f);
		lp.setYaw(p.getLocation().getYaw() + f);
		p.teleport(lp);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.GOLD_BARDING) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("Minigun")) {
						if(!Weapon.minigunAmmoList.containsKey(p)) {
							Bullet bullet = new Bullet(p, Weapon.MINIGUN);
							bullet.setVelocity(bullet.getVelocity().multiply(5.0));
							setPlayerLocation(p);
							Weapon.minigunAmmoList.put(p, 116);
							Weapon.minigunFreqList.add(p);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Bullet bullet = new Bullet(p, Weapon.MINIGUN);
									bullet.setVelocity(bullet.getVelocity().multiply(5.0));
								}
							},1L);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Bullet bullet = new Bullet(p, Weapon.MINIGUN);
									bullet.setVelocity(bullet.getVelocity().multiply(5.0));
								}
							},2L);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Weapon.minigunFreqList.remove(p);
									Bullet bullet = new Bullet(p, Weapon.MINIGUN);
									bullet.setVelocity(bullet.getVelocity().multiply(5.0));
								}
							},3L);
						}
						else {
							if(Weapon.minigunAmmoList.get(p) > 0) {
								if(!Weapon.minigunFreqList.contains(p)) {
									Bullet bullet = new Bullet(p, Weapon.MINIGUN);
									bullet.setVelocity(bullet.getVelocity().multiply(5.0));
									setPlayerLocation(p);
									Weapon.minigunAmmoList.put(p, Weapon.minigunAmmoList.get(p) - 4);
									Weapon.minigunFreqList.add(p);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Bullet bullet = new Bullet(p, Weapon.MINIGUN);
											bullet.setVelocity(bullet.getVelocity().multiply(5.0));
										}
									},1L);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Bullet bullet = new Bullet(p, Weapon.MINIGUN);
											bullet.setVelocity(bullet.getVelocity().multiply(5.0));
										}
									},2L);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Weapon.minigunFreqList.remove(p);
											Bullet bullet = new Bullet(p, Weapon.MINIGUN);
											bullet.setVelocity(bullet.getVelocity().multiply(5.0));
										}
									},3L);
								}
							}
							else {
								if(!Weapon.minigunFreqList.contains(p)) {
									p.sendMessage(ChatColor.RED + "Magazin leer!");
								}
							}
						}
					}
				}
			}
			else if(p.getItemInHand().getType() == Material.SLIME_BALL) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("7,62x51mm Minigun Ammo")) {
						if(p.getInventory().contains(Material.GOLD_BARDING)) {
							if(!Weapon.minigunAmmoList.containsKey(p)) {
								p.sendMessage(ChatColor.RED + "Das Magazin ist immer noch voll.");
							}
							else {
								if(Weapon.minigunAmmoList.get(p) >= 120) {
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
											Weapon.minigunAmmoList.put(p, 120);
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
				if(event.getCurrentItem().getType() == Material.GOLD_BARDING) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Minigun")) {
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
	
	private void minigun() {
		ItemStack minigun = new ItemStack(Material.GOLD_BARDING);
		ItemMeta minigunMeta = minigun.getItemMeta();
		minigunMeta.setDisplayName("Minigun");
		minigun.setItemMeta(minigunMeta);
		
		ShapedRecipe GoldHorseArmor = new ShapedRecipe(minigun);
		GoldHorseArmor.shape("$%$", "%S%", "$%$");
		GoldHorseArmor.setIngredient('$', Material.DIAMOND_BLOCK);
		GoldHorseArmor.setIngredient('%', Material.IRON_BLOCK);
		GoldHorseArmor.setIngredient('S', Material.DIAMOND_SWORD);
		MainClass.getPlug().getServer().addRecipe(GoldHorseArmor);
	}
	private void minigunAmmo() {
		ItemStack ammoMinigun = new ItemStack(Material.SLIME_BALL);
		ItemMeta ammoMinigunMeta = ammoMinigun.getItemMeta();
		ammoMinigunMeta.setDisplayName("7,62x51mm Minigun Ammo");
		ammoMinigun.setItemMeta(ammoMinigunMeta);
		
		ShapedRecipe PrisShards = new ShapedRecipe(ammoMinigun);
		PrisShards.shape("$$$", "$%$", "$$$");
		PrisShards.setIngredient('$', Material.IRON_INGOT);
		PrisShards.setIngredient('%', Material.SULPHUR);
		MainClass.getPlug().getServer().addRecipe(PrisShards);
	}
}
