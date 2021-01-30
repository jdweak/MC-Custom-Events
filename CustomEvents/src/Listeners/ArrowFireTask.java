package Listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowFireTask extends BukkitRunnable{
		
		private final JavaPlugin plugin;
		
		int arrowAmount;
		LivingEntity shooter;
		
		public ArrowFireTask(JavaPlugin plugin, int arrowAmount, LivingEntity shooter) {
			this.plugin = plugin;
			this.arrowAmount = arrowAmount;
			this.shooter = shooter;
		}

		@Override
		public void run() {
			if(arrowAmount == 0) {
				return;
			} else {
				shooter.launchProjectile(SpectralArrow.class);
				arrowAmount --;
			}
		}
}
		
	