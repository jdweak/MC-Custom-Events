package me.jdweak.customMobs;

import org.bukkit.plugin.java.JavaPlugin;
import Listeners.Listeners;
import creeperBombEvent.CreeperBombManager;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	public static Main getInstance() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		instance = this;
		
		getServer().getPluginManager().registerEvents(new Listeners(this), this);
		getServer().getPluginManager().registerEvents(new CreeperBombManager(), this);
		System.out.println("plugin enabled with updates");
	}
	
	public static JavaPlugin getJavaPlugin() {
		return instance;
	}
	
	
}