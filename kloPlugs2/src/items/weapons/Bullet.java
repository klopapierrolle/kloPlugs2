package items.weapons;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class Bullet implements Listener {
	private final Projectile proj;
	private final int type;
	
	public Bullet(Player p, final int type) {
		if(type != -1) {
			proj = p.launchProjectile(Snowball.class);
		}
		else {
			proj = p.launchProjectile(Arrow.class);
		}
		this.type = type;
		playSound(p, type);
		Weapon.bulletList.put(proj, type);
	}
	
	public Projectile getProjectile() {
		return proj;
	}
	public Vector getVelocity() {
		return proj.getVelocity();
	}
	public void setVelocity(Vector velocity) {
		proj.setVelocity(velocity);
	}
	public int getType() {
		return type;
	}
	public void destroyProjectile() {
		proj.remove();
	}
	private void playSound(Player p, int weaponType) {
		switch(weaponType) {
		case Weapon.BERETTA:
			p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_BLAST, 8, 0);
			break;
		case Weapon.M16:
			p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_BLAST, 12, 0);
			break;
		case Weapon.MINIGUN:
			p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_BLAST, 18, 0);
			break;
		case Weapon.M40:
			p.getWorld().playSound(p.getLocation(), Sound.FIREWORK_LARGE_BLAST, 14, 0);
			break;
		default:
		}
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(p.getItemInHand().getType() != null) {
			if(p.getItemInHand().getType() == Material.SNOW_BALL) {
				if(event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
					event.setCancelled(true);
					p.sendMessage(ChatColor.RED + "Du kannst keine Kugel schmeiﬂen!");
				}
			}
		}
	}
}
