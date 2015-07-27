package misc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.scheduler.BukkitRunnable;

import main.MainClass;

public class Time implements CommandExecutor, Listener {
	/* 
	 * Sehr alte Klasse; wurde noch nicht überarbeitet bzw. neugeschrieben.
	 * (Merkt man offensichtlich am extremen Hardcoding)
	*/
	private static boolean isActive = false;
	
	public Time() {
		if(isActive == false) {
			timeMC();
			isActive = true;
		}
	}
	
	@EventHandler
	public void onPreCommand(PlayerCommandPreprocessEvent event) {
		if(event.getMessage().length() > 4) {
			if(event.getMessage().substring(1, 5).equals("time")) {
				event.getPlayer().sendMessage(ChatColor.RED + "Nope.");
				event.setCancelled(true);
			}
		}
	}
	
	private void timeMC() {
		MainClass.getPlug().getServer().getScheduler().scheduleSyncRepeatingTask(MainClass.getPlug(), new Runnable() {
			public void run() {
				if(Bukkit.getWorld("world").getTime() == 0) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + "6:00" + ChatColor.YELLOW + " Uhr.");
				}
				else if(Bukkit.getWorld("world").getTime() == 6000) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + "12:00" + ChatColor.YELLOW + " Uhr.");
				}
				else if(Bukkit.getWorld("world").getTime() == 12000) {
					Bukkit.broadcastMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + "18:00" + ChatColor.YELLOW + " Uhr.");
				}
				else if(Bukkit.getWorld("world").getTime() == 18000) {
					if(MainClass.getPlug().getConfig().getString("Day").equals("Sonntag")) {
						MainClass.getPlug().getConfig().set("Day", "Montag");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Montag" + ChatColor.YELLOW + ".");
					}
					else if(MainClass.getPlug().getConfig().getString("Day").equals("Montag")) {
						MainClass.getPlug().getConfig().set("Day", "Dienstag");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Dienstag" + ChatColor.YELLOW + ".");
					}
					else if(MainClass.getPlug().getConfig().getString("Day").equals("Dienstag")) {
						MainClass.getPlug().getConfig().set("Day", "Mittwoch");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Mittwoch" + ChatColor.YELLOW + ".");
					}
					else if(MainClass.getPlug().getConfig().getString("Day").equals("Mittwoch")) {
						MainClass.getPlug().getConfig().set("Day", "Donnerstag");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Donnerstag" + ChatColor.YELLOW + ".");
					}
					else if(MainClass.getPlug().getConfig().getString("Day").equals("Donnerstag")) {
						MainClass.getPlug().getConfig().set("Day", "Freitag");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Freitag" + ChatColor.YELLOW + ".");
						new BukkitRunnable() {
							@Override
							public void run() {
								Jobs.broadcastJobs();
								for(Player players : Bukkit.getOnlinePlayers()) {
									players.playSound(players.getLocation(), Sound.LEVEL_UP, 4.0F, 1.0F);
								}
							}
						}.runTaskLater(MainClass.getPlug(), 200L);
					}
					else if(MainClass.getPlug().getConfig().getString("Day").equals("Freitag")) {
						MainClass.getPlug().getConfig().set("Day", "Samstag");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Samstag" + ChatColor.YELLOW + ".");
					}
					else if(MainClass.getPlug().getConfig().getString("Day").equals("Samstag")) {
						MainClass.getPlug().getConfig().set("Day", "Sonntag");
						MainClass.getPlug().saveConfig();
						Bukkit.broadcastMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + "Sonntag" + ChatColor.YELLOW + ".");
					}
					Bukkit.broadcastMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + "0:00 Uhr" + ChatColor.YELLOW + " Uhr.");
				}
			}
		}, 0L, 1L);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("showmethetime")) {
			if(sender instanceof Player) {
				if(args.length != 0) {
					Player p = (Player) sender;
					p.sendMessage(ChatColor.RED + "Wrong command. Correct usage: /showmethetime");
				}
				else {
					Player p = (Player) sender;
					p.sendMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + MainClass.getPlug().getConfig().getString("Day") + ChatColor.YELLOW + ".");
					double t = (double) Bukkit.getWorld("world").getTime();
					if(t > 18000) {
						if(((int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60))) < 10) {
							p.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) - 19) + ":0" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
						else {
							p.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) - 19) + ":" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
					}
					else {
						if(((int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60))) < 10) {
							p.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) + 5) + ":0" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
						else {
							p.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) + 5) + ":" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
					}
				}
			}
			else if(sender instanceof ConsoleCommandSender) {
				if(args.length != 0) {
					sender.sendMessage(ChatColor.RED + "Wrong command. Correct usage: /showmethetime");
				}
				else {
					sender.sendMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + MainClass.getPlug().getConfig().getString("Day") + ChatColor.YELLOW + ".");
					double t = (double) Bukkit.getWorld("world").getTime();
					if(t > 18000) {
						if(((int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60))) < 10) {
							sender.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) - 19) + ":0" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
						else {
							sender.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) - 19) + ":" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
					}
					else {
						if(((int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60))) < 10) {
							sender.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) + 5) + ":0" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
						else {
							sender.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) + 5) + ":" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
					}
				}
			}
			else {
				if(args.length == 1) {
					Player target = MainClass.getPlug().getServer().getPlayer(args[0]);
					target.sendMessage(ChatColor.YELLOW + "Heute ist " + ChatColor.GOLD + MainClass.getPlug().getConfig().getString("Day") + ChatColor.YELLOW + ".");
					double t = (double) Bukkit.getWorld("world").getTime();
					if(t > 18000) {
						if(((int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60))) < 10) {
							target.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) - 19) + ":0" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
						else {
							target.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) - 19) + ":" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
					}
					else {
						if(((int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60))) < 10) {
							target.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) + 5) + ":0" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
						else {
							target.sendMessage(ChatColor.YELLOW + "Es ist " + ChatColor.GOLD + (int) (Math.ceil(t/1000) + 5) + ":" + (int) (60 - Math.ceil((Math.abs(t/1000 - Math.ceil(t/1000)))*60)) + ChatColor.YELLOW + " Uhr.");
						}
					}
				}
			}
		}
		return false;
	}
}
