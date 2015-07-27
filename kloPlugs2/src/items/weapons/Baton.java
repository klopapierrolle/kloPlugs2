package items.weapons;

import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import main.MainClass;

public class Baton implements Listener {
	private HashMap<Player, PotionEffect> immobilizedList = new HashMap<Player, PotionEffect>();
	private Plugin plugin = MainClass.getPlug();
	
	public Baton() {
		baton();
	}
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if(event.getDamager() instanceof Player) {
			Player d = (Player) event.getDamager();
			if(d.getItemInHand().hasItemMeta()) {
				if(d.getItemInHand().getItemMeta().getDisplayName().equals("Baton")) {
					if(event.getEntity() instanceof Player) {
						Player p = (Player) event.getEntity();
						for(PotionEffect currentEffect : p.getActivePotionEffects()) {
							if(currentEffect.getType().equals(PotionEffectType.SLOW)) {
								immobilizedList.put(p, currentEffect);
								p.removePotionEffect(PotionEffectType.SLOW);
								break;
							}
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2));
						new BukkitRunnable() {
							@Override
							public void run() {
								p.removePotionEffect(PotionEffectType.SLOW);
								if(immobilizedList.containsKey(p)) {
									p.addPotionEffect(immobilizedList.get(p));
								}
								immobilizedList.remove(p);
							}
						}.runTaskLater(plugin, 60L);
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getInventory().getType() == InventoryType.ANVIL) {
			if(event.getSlotType() == SlotType.RESULT) {
				if(event.getCurrentItem().getType() == Material.STICK) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Baton")) {
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
	}
	
	private void baton() {
		ItemStack baton = new ItemStack(Material.STICK);
		ItemMeta batonM = baton.getItemMeta();
		batonM.setDisplayName("Baton");
		batonM.addEnchant(Enchantment.DAMAGE_ALL, 1, true);
		baton.setItemMeta(batonM);
		
		ShapedRecipe Stick = new ShapedRecipe(baton);
		Stick.shape("$$$", "$%$", "$$$");
		Stick.setIngredient('$', Material.STICK);
		Stick.setIngredient('%', Material.IRON_INGOT);
		plugin.getServer().addRecipe(Stick);
	}
}