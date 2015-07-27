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

public class Beretta implements Listener {
	public Beretta() {
		beretta();
		berettaAmmo();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.DIAMOND_BARDING) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("Beretta 92")) {
						if(!Weapon.berettaAmmoList.containsKey(p)) {
							Bullet bullet = new Bullet(p, Weapon.BERETTA);
							bullet.setVelocity(bullet.getVelocity().multiply(3.0));
							Weapon.berettaAmmoList.put(p, 29);
							Weapon.berettaFreqList.add(p);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Weapon.berettaFreqList.remove(p);
								}
							},10L);
						}
						else {
							if(Weapon.berettaAmmoList.get(p) > 0) {
								if(!Weapon.berettaFreqList.contains(p)) {
									Bullet bullet = new Bullet(p, Weapon.BERETTA);
									bullet.setVelocity(bullet.getVelocity().multiply(3.0));
									Weapon.berettaAmmoList.put(p, Weapon.berettaAmmoList.get(p) - 1);
									Weapon.berettaFreqList.add(p);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Weapon.berettaFreqList.remove(p);
										}
									},10L);
								}
							}
							else {
								if(!Weapon.berettaFreqList.contains(p)) {
									p.sendMessage(ChatColor.RED + "Magazin leer!");
								}
							}
						}
					}
				}
			}
			else if(p.getItemInHand().getType() == Material.FIREWORK_CHARGE) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("9mm Ammo")) {
						if(p.getInventory().contains(Material.DIAMOND_BARDING)) {
							if(!Weapon.berettaAmmoList.containsKey(p)) {
								p.sendMessage(ChatColor.RED + "Das Magazin ist immer noch voll.");
							}
							else {
								if(Weapon.berettaAmmoList.get(p) >= 30) {
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
											Weapon.berettaAmmoList.put(p, 30);
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
				if(event.getCurrentItem().getType() == Material.DIAMOND_BARDING) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Beretta 92")) {
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
	
	private void beretta() {
		ItemStack gl = new ItemStack(Material.DIAMOND_BARDING);
		ItemMeta glMeta = gl.getItemMeta();
		glMeta.setDisplayName("Beretta 92");
		gl.setItemMeta(glMeta);
		
		ShapedRecipe DiamondHorseArmor = new ShapedRecipe(gl);
		DiamondHorseArmor.shape("$$%", "GI$", "GG$");
		DiamondHorseArmor.setIngredient('$', Material.IRON_BLOCK);
		DiamondHorseArmor.setIngredient('%', Material.DIAMOND_BLOCK);
		DiamondHorseArmor.setIngredient('G', Material.THIN_GLASS);
		DiamondHorseArmor.setIngredient('I', Material.IRON_INGOT);
		MainClass.getPlug().getServer().addRecipe(DiamondHorseArmor);
		
		ShapedRecipe DiamondHorseArmor2 = new ShapedRecipe(gl);
		DiamondHorseArmor2.shape("$$%", "IG$", "II$");
		DiamondHorseArmor2.setIngredient('$', Material.IRON_BLOCK);
		DiamondHorseArmor2.setIngredient('%', Material.DIAMOND_BLOCK);
		DiamondHorseArmor2.setIngredient('G', Material.THIN_GLASS);
		DiamondHorseArmor2.setIngredient('I', Material.IRON_INGOT);
		MainClass.getPlug().getServer().addRecipe(DiamondHorseArmor2);
	}
	private void berettaAmmo() {
		ItemStack ammo = new ItemStack(Material.FIREWORK_CHARGE);
		ItemMeta ammoMeta = ammo.getItemMeta();
		ammoMeta.setDisplayName("9mm Ammo");
		ammo.setItemMeta(ammoMeta);
		
		ShapedRecipe IronHorseArmor = new ShapedRecipe(ammo);
		IronHorseArmor.shape("$%$", "$%$", "$%$");
		IronHorseArmor.setIngredient('$', Material.IRON_INGOT);
		IronHorseArmor.setIngredient('%', Material.SULPHUR);
		MainClass.getPlug().getServer().addRecipe(IronHorseArmor);
	}
}
