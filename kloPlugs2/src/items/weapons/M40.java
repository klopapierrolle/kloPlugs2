package items.weapons;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
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
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import main.MainClass;

public class M40 implements Listener {
	private HashMap<Player, PotionEffect[]> immobilizedList = new HashMap<Player, PotionEffect[]>();
	
	public M40() {
		m40();
		m40Ammo();
	}
	
	private void setPlayerLocation(Player p) {
		Location lp = p.getLocation();
		float f = (float) Math.toDegrees(Math.asin((Math.random()*Math.pow(2, -1)) - (Math.pow(4, -1))));
		lp.setPitch(p.getLocation().getPitch() + f);
		lp.setYaw(p.getLocation().getYaw() + f);
		p.teleport(lp);
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.WOOD_HOE) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("M40A1")) {
						if(!Weapon.M40AmmoList.containsKey(p)) {
							Bullet bullet = new Bullet(p, Weapon.M40);
							bullet.setVelocity(bullet.getVelocity().multiply(15.0));
							setPlayerLocation(p);
							Weapon.M40AmmoList.put(p, 9);
							Weapon.M40FreqList.add(p);
							MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
								public void run() {
									Weapon.M40FreqList.remove(p);
								}
							},30L);
						}
						else {
							if(Weapon.M40AmmoList.get(p) > 0) {
								if(!Weapon.M40FreqList.contains(p)) {
									Bullet bullet = new Bullet(p, Weapon.M40);
									bullet.setVelocity(bullet.getVelocity().multiply(15.0));
									setPlayerLocation(p);
									Weapon.M40AmmoList.put(p, Weapon.M40AmmoList.get(p) - 1);
									Weapon.M40FreqList.add(p);
									MainClass.getPlug().getServer().getScheduler().scheduleSyncDelayedTask(MainClass.getPlug(), new Runnable() {
										public void run() {
											Weapon.M40FreqList.remove(p);
										}
									},30L);
								}
							}
							else {
								if(!Weapon.M40FreqList.contains(p)) {
									p.sendMessage(ChatColor.RED + "Magazin leer!");
								}
							}
						}
					}
				}
				else if(p.getItemInHand().getType() == Material.PRISMARINE_CRYSTALS) {
					if(p.getItemInHand().hasItemMeta()) {
						if(p.getItemInHand().getItemMeta().getDisplayName().equals("7,62x51mm M40A1 Ammo")) {
							if(p.getInventory().contains(Material.WOOD_HOE)) {
								if(!Weapon.M40AmmoList.containsKey(p)) {
									p.sendMessage(ChatColor.RED + "Das Magazin ist immer noch voll.");
								}
								else {
									if(Weapon.M40AmmoList.get(p) >= 10) {
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
												Weapon.M40AmmoList.put(p, 10);
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
	}
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		if(event.getInventory().getType() == InventoryType.ANVIL) {
			if(event.getSlotType() == SlotType.RESULT) {
				if(event.getCurrentItem().getType() == Material.WOOD_HOE) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("M40A1")) {
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
	@EventHandler
	public void onPlayerSneak(PlayerToggleSneakEvent event) {
		Player p = event.getPlayer();
		if(!p.isSneaking()) {
			if(p.getItemInHand().getType() == Material.WOOD_HOE) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("M40A1")) {
						boolean b = false;
						PotionEffect[] effects = new PotionEffect[2];
						for(PotionEffect currentEffect : p.getActivePotionEffects()) {
							if(currentEffect.getType().equals(PotionEffectType.SLOW) || currentEffect.getType().equals(PotionEffectType.INVISIBILITY)) {
								b = true;
								if(effects[0] == null) {
									effects[0] = currentEffect;
								}
								else {
									if(!effects[0].getType().equals(currentEffect.getType())) {
										effects[1] = currentEffect;
									}
									else {
										break;
									}
								}
							}
						}
						if(b == false) {
							immobilizedList.put(p, null);
						}
						else {
							immobilizedList.put(p, effects);
						}
						p.removePotionEffect(PotionEffectType.SLOW);
						p.removePotionEffect(PotionEffectType.INVISIBILITY);
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20000, 9));
						p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 20000, 1));
						new BukkitRunnable() {
							@Override
							public void run() {
								if(p.isSneaking()) {
									p.getWorld().playEffect(new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 1, p.getLocation().getZ()), Effect.LAVA_POP, 0, 128);
								}
								else {
									cancel();
								}
							}
						}.runTaskTimer(MainClass.getPlug(), 10L, 14L);
					}
				}
			}
		}
		else {
			if(immobilizedList.containsKey(p)) {
				p.removePotionEffect(PotionEffectType.SLOW);
				p.removePotionEffect(PotionEffectType.INVISIBILITY);
				if(immobilizedList.get(p) == null) {}
				else if(immobilizedList.get(p)[1] == null) {
					p.addPotionEffect(immobilizedList.get(p)[0]);
				}
				else {
					p.addPotionEffect(immobilizedList.get(p)[0]);
					p.addPotionEffect(immobilizedList.get(p)[1]);
				}
				immobilizedList.remove(p);
			}
		}
	}
	
	
	public void m40() {
		ItemStack m40 = new ItemStack(Material.WOOD_HOE);
		ItemMeta m40Meta = m40.getItemMeta();
		m40Meta.setDisplayName("M40A1");
		m40.setItemMeta(m40Meta);
		
		ShapedRecipe WoodenHoe = new ShapedRecipe(m40);
		WoodenHoe.shape("$G$", "DDI", " $D");
		WoodenHoe.setIngredient('$', Material.IRON_INGOT);
		WoodenHoe.setIngredient('G', Material.THIN_GLASS);
		WoodenHoe.setIngredient('D', Material.DIAMOND_BLOCK);
		WoodenHoe.setIngredient('I', Material.IRON_BLOCK);
		MainClass.getPlug().getServer().addRecipe(WoodenHoe);
		
		ShapedRecipe WoodenHoe2 = new ShapedRecipe(m40);
		WoodenHoe2.shape("$G$", "IDD", "D$ ");
		WoodenHoe2.setIngredient('$', Material.IRON_INGOT);
		WoodenHoe2.setIngredient('G', Material.THIN_GLASS);
		WoodenHoe2.setIngredient('D', Material.DIAMOND_BLOCK);
		WoodenHoe2.setIngredient('I', Material.IRON_BLOCK);
		MainClass.getPlug().getServer().addRecipe(WoodenHoe2);
	}
	public void m40Ammo() {
		ItemStack ammoM40 = new ItemStack(Material.PRISMARINE_CRYSTALS);
		ItemMeta ammoM40Meta = ammoM40.getItemMeta();
		ammoM40Meta.setDisplayName("7,62x51mm M40A1 Ammo");
		ammoM40.setItemMeta(ammoM40Meta);
		
		ShapedRecipe PrisCrystal = new ShapedRecipe(ammoM40);
		PrisCrystal.shape("$$$", "%G%", "$$$");
		PrisCrystal.setIngredient('$', Material.IRON_INGOT);
		PrisCrystal.setIngredient('%', Material.SULPHUR);
		PrisCrystal.setIngredient('G', Material.GOLD_INGOT);
		MainClass.getPlug().getServer().addRecipe(PrisCrystal);
	}
}
