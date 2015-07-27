package main;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import items.misc.IdentityCard;
import items.misc.InformationStick;
import items.security.SearchWarrant;
import items.weapons.Baton;
import items.weapons.Beretta;
import items.weapons.HunterBow;
import items.weapons.M16;
import items.weapons.M40;
import items.weapons.Minigun;
import items.weapons.Weapon;
import misc.Jobs;
import misc.Time;
import security.PrivateChests;

public class MainClass extends JavaPlugin implements Listener {
	/*
	 * Die Arbeiten an diesem Plugin sind noch stark im Gange.
	 * 
	 * Es ist eine neugeschriebene Version eines meiner ersten Plugins; dieses verwendete ich �fters und f�gte auch Dinge hinzu,
	 * bis es extrem un�bersichtlich und nur noch veraltet war. Wenige Fragmente (das betrifft gr��tenteils die "Time"-Klasse) wurden vorerst
	 * �bernommen, da sie bis auf weiteres ihren Zweck erf�llen. Jedoch m�ssen sie bald erneuert, "ent-hardcoded" oder komplett ersetzt werden.
	 * 
	 * Ich h�tte auch ein �lteres, fertiges Plugin ausw�hlen k�nnen, jedoch w�rden einem dort die Augen schon nach den ersten Zeilen bluten.^^
	 * 
	 * Zur Orientierung:
	 * Es handelt sich um eine Art "Rollenspielprojekt", welches ich mit Freunden/Bekannten zum Spa� durchf�hre. Daher die teils seltsam wirkenden
	 * Gegenst�nde und Regelungen. (:
	 */
	
	private static Plugin plugin;
	
	@Override
	public void onEnable() {
		System.out.println("kloPlugs V. 2.0 gestartet.");
		plugin = this;
		registerCommands();
		registerEvents();
		getConfig().options().copyDefaults(true);
		saveConfig();
	}
	@Override
	public void onDisable() {
		System.out.println("kloPlugs V. 2.0 gestoppt.");
		plugin = null;
	}
	private void registerCommands() {
		getCommand("showmethetime").setExecutor(new Time());
		getCommand("job").setExecutor(new Jobs());
	}
	private void registerEvents() {
		PluginManager pm = this.getServer().getPluginManager();
		pm.registerEvents(this, this);
		pm.registerEvents(new Time(), this);
		pm.registerEvents(new Beretta(), this);
		pm.registerEvents(new M16(), this);
		pm.registerEvents(new Minigun(), this);
		pm.registerEvents(new M40(), this);
		pm.registerEvents(new Baton(), this);
		pm.registerEvents(new IdentityCard(), this);
		pm.registerEvents(new InformationStick(), this);
		pm.registerEvents(new Jobs(), this);
		pm.registerEvents(new SearchWarrant(), this);
		pm.registerEvents(new PrivateChests(), this);
		pm.registerEvents(new HunterBow(), this);
		pm.registerEvents(new Weapon(), this);
	}
	
	public static Plugin getPlug() {
		return plugin;
	}
}