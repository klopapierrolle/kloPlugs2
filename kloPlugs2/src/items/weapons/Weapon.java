package items.weapons;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import main.MainClass;

public class Weapon implements Listener {
	private HashMap<Player, PotionEffect> immobilizedList = new HashMap<Player, PotionEffect>();
	private Plugin plugin = MainClass.getPlug();
	
	public static final int BERETTA = 0;
	public static final int M16 = 1;
	public static final int MINIGUN = 2;
	public static final int M40 = 3;
	public static final int HBOW = -1;
	
	public static HashMap<Projectile, Integer> bulletList = new HashMap<Projectile, Integer>();
	
	public static List<Player> inHandcuffsList = new ArrayList<Player>();
	
	public static HashMap<Player, Integer> berettaAmmoList = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> M16AmmoList = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> minigunAmmoList = new HashMap<Player, Integer>();
	public static HashMap<Player, Integer> M40AmmoList = new HashMap<Player, Integer>();
	
	public static List<Player> berettaFreqList = new ArrayList<Player>();
	public static List<Player> M16FreqList = new ArrayList<Player>();
	public static List<Player> minigunFreqList = new ArrayList<Player>();
	public static List<Player> M40FreqList = new ArrayList<Player>();
	public static List<Player> HBowFreqList = new ArrayList<Player>();
	
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		Entity d = event.getDamager();
		if(d instanceof Projectile) {
			if(bulletList.containsKey(d)) {
				if(event.getEntity() instanceof Player) {
					Player p = (Player) event.getEntity();
					switch(bulletList.get(d)) {
					case 0:
						event.setDamage(8.0);
						for(PotionEffect currentEffect : p.getActivePotionEffects()) {
							if(currentEffect.getType().equals(PotionEffectType.SLOW)) {
								immobilizedList.put(p, currentEffect);
								p.removePotionEffect(PotionEffectType.SLOW);
								break;
							}
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70, 2));
						new BukkitRunnable() {
							@Override
							public void run() {
								p.removePotionEffect(PotionEffectType.SLOW);
								if(immobilizedList.containsKey(p)) {
									p.addPotionEffect(immobilizedList.get(p));
								}
								immobilizedList.remove(p);
							}
						}.runTaskLater(plugin, 70L);
						break;
					case 1:
						event.setDamage(8.0);
						for(PotionEffect currentEffect : p.getActivePotionEffects()) {
							if(currentEffect.getType().equals(PotionEffectType.SLOW)) {
								immobilizedList.put(p, currentEffect);
								p.removePotionEffect(PotionEffectType.SLOW);
								break;
							}
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70, 2));
						new BukkitRunnable() {
							@Override
							public void run() {
								p.removePotionEffect(PotionEffectType.SLOW);
								if(immobilizedList.containsKey(p)) {
									p.addPotionEffect(immobilizedList.get(p));
								}
								immobilizedList.remove(p);
							}
						}.runTaskLater(plugin, 70L);
						break;
					case 2:
						event.setDamage(18.0);
						for(PotionEffect currentEffect : p.getActivePotionEffects()) {
							if(currentEffect.getType().equals(PotionEffectType.SLOW)) {
								immobilizedList.put(p, currentEffect);
								p.removePotionEffect(PotionEffectType.SLOW);
								break;
							}
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70, 2));
						new BukkitRunnable() {
							@Override
							public void run() {
								p.removePotionEffect(PotionEffectType.SLOW);
								if(immobilizedList.containsKey(p)) {
									p.addPotionEffect(immobilizedList.get(p));
								}
								immobilizedList.remove(p);
							}
						}.runTaskLater(plugin, 70L);
						break;
					case 3:
						event.setDamage(100.0);
						for(PotionEffect currentEffect : p.getActivePotionEffects()) {
							if(currentEffect.getType().equals(PotionEffectType.SLOW)) {
								immobilizedList.put(p, currentEffect);
								p.removePotionEffect(PotionEffectType.SLOW);
								break;
							}
						}
						p.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 70, 2));
						new BukkitRunnable() {
							@Override
							public void run() {
								p.removePotionEffect(PotionEffectType.SLOW);
								if(immobilizedList.containsKey(p)) {
									p.addPotionEffect(immobilizedList.get(p));
								}
								immobilizedList.remove(p);
							}
						}.runTaskLater(plugin, 70L);
						break;
					}
				}
				else {
					switch(bulletList.get(d)) {
					case 0:
						event.setDamage(8.0);
						break;
					case 1:
						event.setDamage(8.0);
						break;
					case 2:
						event.setDamage(18.0);
						break;
					case 3:
						event.setDamage(100.0);
						break;
					case -1:
						event.getEntity().setFireTicks(20);
						event.setDamage(100.0);
						break;
					}
				}
			}
		}
	}
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.SNOW_BALL) {
				event.setCancelled(true);
				p.sendMessage(ChatColor.RED + "Du kannst keine Kugel werfen.");
			}
		}
	}
}
