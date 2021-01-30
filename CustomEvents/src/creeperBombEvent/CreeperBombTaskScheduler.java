package creeperBombEvent;

import org.bukkit.Bukkit;
import org.bukkit.entity.Creeper;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;

public class CreeperBombTaskScheduler extends BukkitRunnable{
	
	private final JavaPlugin plugin;
	
	Creeper creeperBomb;
	
	public CreeperBombTaskScheduler(JavaPlugin plugin) {
		this.plugin = plugin;
	}

	public void setCreeper(Creeper creeper) {
		creeperBomb = creeper;
	}
	
	@Override
	public void run() {
		creeperBomb.explode();
		Bukkit.getServer().broadcastMessage(ChatColor.YELLOW + "THE NUKE WAS NOT ELIMINATED AND THUS CAUSED WIDESPREAD DESTRUCTION OF PROPERTY :)");
		CreeperBombManager.getInstance().resetCreeperBomb();
	}
}
