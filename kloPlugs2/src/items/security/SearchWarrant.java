package items.security;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.inventory.InventoryType.SlotType;

import misc.JobTypes;
import misc.Jobs;

public class SearchWarrant implements Listener {
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		Player p = (Player) event.getWhoClicked();
		if(event.getInventory().getType() == InventoryType.ANVIL) {
			if(event.getSlotType() == SlotType.RESULT) {
				if(event.getCurrentItem().getType() == Material.PAPER) {
					if(event.getCurrentItem().hasItemMeta()) {
						if(event.getCurrentItem().getItemMeta().getDisplayName().substring(0, 10).equals("Beschluss:")) {
							if(!Jobs.hasJob(p, JobTypes.JUDGE)) {
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
