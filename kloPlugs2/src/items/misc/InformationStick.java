package items.misc;

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
import org.bukkit.plugin.Plugin;

import main.MainClass;
import net.md_5.bungee.api.ChatColor;

public class InformationStick implements Listener {
	private Plugin plugin = MainClass.getPlug();
	
	public InformationStick() {
		informationStick();
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent event) {
		Player p = event.getPlayer();
		if(event.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if(p.getItemInHand().getType() == Material.STICK) {
				if(p.getItemInHand().hasItemMeta()) {
					if(p.getItemInHand().getItemMeta().getDisplayName().equals("Informations-Stick")) {
						p.sendMessage(ChatColor.AQUA + "World: " + ChatColor.WHITE + p.getWorld().getName());
						p.sendMessage(ChatColor.AQUA + "Coordinates: " + ChatColor.WHITE + event.getClickedBlock().getLocation().getX() + ChatColor.AQUA + ", " + ChatColor.WHITE + event.getClickedBlock().getLocation().getY() + ChatColor.AQUA + ", " + ChatColor.WHITE + event.getClickedBlock().getLocation().getZ());
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
						if(event.getCurrentItem().getItemMeta().getDisplayName().equals("Informations-Stick")) {
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
	
	private void informationStick() {
		ItemStack infStick = new ItemStack(Material.STICK);
		ItemMeta infStickM = infStick.getItemMeta();
		infStickM.setDisplayName("Informations-Stick");
		infStick.setItemMeta(infStickM);
		
		ShapedRecipe Stick = new ShapedRecipe(infStick);
		Stick.shape("$$$", "$%$", "$$$");
		Stick.setIngredient('$', Material.STICK);
		Stick.setIngredient('%', Material.PAPER);
		plugin.getServer().addRecipe(Stick);
	}
}
