package misc;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Formatter;
import java.util.Scanner;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import main.MainClass;

public class Jobs implements Listener, CommandExecutor {
	private static Plugin plugin = MainClass.getPlug();
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("job")) {
			if(sender instanceof Player || sender instanceof ConsoleCommandSender) {
				if(args.length != 3) {
					if(args.length != 2) {
						sender.sendMessage(ChatColor.RED + "Fehler. Richtiger command: /job [add/del/get] [Player] {Job}");
						sender.sendMessage(ChatColor.GREEN + "Verfügbare Berufe:");
						for(int i = 0; i < JobTypes.values().length; i++) {
							sender.sendMessage(ChatColor.BLUE + JobTypes.values()[i].getName());
						}
					}
					else {
						if(args[0].equals("get")) {
							if(Bukkit.getPlayer(args[1]) != null) {
								Player target = Bukkit.getPlayer(args[1]);
								if(getJobs(target) != null) {
									sender.sendMessage(ChatColor.GREEN + "Beruf(e) von " + target.getName() + ":");
									for(int i = 0; i < getJobs(target).length; i++) {
										sender.sendMessage(ChatColor.BLUE + getJobs(target)[i].getName());
									}
								}
								else {
									sender.sendMessage(ChatColor.RED + "Dieser Spieler hat keine Berufe.");
								}
							}
							else {
								sender.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht online.");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "Fehler. Richtiger command: /job [add/del] [Player] [Job]");
						}
					}
				}
				else {
					if(Bukkit.getPlayer(args[1]) != null) {
						Player target = Bukkit.getPlayer(args[1]);
						if(args[0].equals("add")) {
							String jobs = "";
							if(getJobs(target) != null) {
								for(int i = 0; i < getJobs(target).length; i++) {
									jobs += (getJobs(target)[i].getName() + ",");
								}
							}
							jobs += args[2];
							try {
								Formatter f = new Formatter(new File(plugin.getDataFolder() + "\\Jobs\\" + target.getName()));
								f.format("%s", jobs);
								f.close();
								sender.sendMessage(ChatColor.GREEN + "Der Beruf wurde dem genannten Spieler hinzugefügt.");
							}
							catch(FileNotFoundException e) {
								e.printStackTrace();
								sender.sendMessage(ChatColor.RED + "Fehler. Bitte Konsole überprüfen.");
							}
						}
						else if(args[0].equals("del")) {
							String jobs = "";
							for(int i = 0; i < getJobs(target).length; i++) {
								if(getJobs(target)[i].getName().equals(args[2])) continue;
								jobs += (getJobs(target)[i].getName() + ",");
							}
							jobs = jobs.substring(0, jobs.length() - 1);
							try {
								Formatter f = new Formatter(new File(plugin.getDataFolder() + "\\Jobs\\" + target.getName()));
								f.format("%s", jobs);
								f.close();
								sender.sendMessage(ChatColor.GREEN + "Der Beruf von dem genannten Spieler wurde entfernt.");
							}
							catch(FileNotFoundException e) {
								e.printStackTrace();
								sender.sendMessage(ChatColor.RED + "Fehler. Bitte Konsole überprüfen.");
							}
						}
						else {
							sender.sendMessage(ChatColor.RED + "Fehler. Richtiger command: /job [add/del] [Player] [Job]");
						}
					}
					else {
						sender.sendMessage(ChatColor.RED + "Dieser Spieler ist nicht online.");
					}
				}
			}
		}
		return false;
	}
	
	public static JobTypes[] getJobs(Player p) {
		File f = new File(plugin.getDataFolder() + "\\Jobs\\" + p.getName());
		if(!f.exists()) return null;
		try {
			Scanner sc = new Scanner(f);
			String[] s = (sc.useDelimiter("\\Z").next()).split(",");
			sc.close();
			JobTypes[] jobs = new JobTypes[s.length];
			boolean isError = true;
			for(int i = 0; i < s.length; i++) {
				for(int j = 0; j < JobTypes.values().length; j++) {
					if(s[i].equals(JobTypes.values()[j].getName())) {
						jobs[i] = toJobType(s[i]);
						isError = false;
						break;
					}
				}
			}
			if(isError) {
				return null;
			}
			else {
				return jobs;
			}
		}
		catch(FileNotFoundException e) {
			return null;
		}
	}
	public static int calculatePayment(JobTypes[] types) {
		int total = 0;
		for(int i = 0; i < types.length; i++) {
			total += Math.round(types[i].getMoney() * (1.0 - types[i].getTax()));
		}
		return total;
	}
	public static void broadcastJobs() {
		Bukkit.broadcastMessage(ChatColor.BLUE.toString() + ChatColor.ITALIC.toString() + ChatColor.UNDERLINE.toString() + "- - - - - Gehälter dieser Woche - - - - -");
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(getJobs(players) == null) continue;
			Bukkit.broadcastMessage(ChatColor.GREEN + players.getName() + ": " + ChatColor.GOLD + calculatePayment(getJobs(players)));
		}
	}
	public static boolean hasJob(Player p, JobTypes type) {
		JobTypes[] playerJobs = getJobs(p);
		boolean hasGivenJob = false;
		for(int i = 0; i < playerJobs.length; i++) {
			if(playerJobs[i] == type) {
				hasGivenJob = true;
				break;
			}
		}
		return hasGivenJob;
	}
	public static JobTypes toJobType(String s) {
		JobTypes type = null;
		for(int i = 0; i < JobTypes.values().length; i++) {
			if(s.equals(JobTypes.values()[i].getName())) {
				type = JobTypes.values()[i];
				break;
			}
		}
		return type;
	}
}
