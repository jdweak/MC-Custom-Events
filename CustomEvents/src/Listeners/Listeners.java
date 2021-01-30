package Listeners;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Blaze;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.DragonFireball;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Enderman;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Phantom;
import org.bukkit.entity.PigZombie;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.SpectralArrow;
import org.bukkit.entity.Vex;
import org.bukkit.entity.WitherSkeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.potion.PotionType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import creeperBombEvent.CreeperBombManager;
import me.jdweak.customMobs.Main;

public class Listeners implements Listener{
	
	JavaPlugin mainClass;
	
	public Listeners(JavaPlugin plugin) {
		super();
		mainClass = plugin;
	}
	
	
	
	@EventHandler
	public void event(AreaEffectCloudApplyEvent event) {
		if(event.getEntity().getBasePotionData().getType() == PotionType.SPEED) {
			event.setCancelled(true);
		}
		
	}

	@EventHandler
	public void durabilityEvent(PlayerItemDamageEvent event) {
		if(event.getDamage() > 8) {
			event.setDamage(8);
		}
	}
	
	
	@EventHandler
	public void skeletonShoot(EntityShootBowEvent event) {
		if(event.getBow().getItemMeta().getDisplayName().equalsIgnoreCase("machine gun")) { //bow named machine gun

				BukkitRunnable fireArrows = new ArrowFireTask(mainClass, 5, event.getEntity());
				fireArrows.runTaskTimer(mainClass, 20, 10);
			}
		}
	
//	@EventHandler
//	public void arrowHit(ProjectileHitEvent event) {
//		if(!(event.getEntity().getShooter() instanceof Player)) {
//			if(event.getEntity() instanceof Arrow) {
//				if(Math.random() * 100 > 50) {
//					event.getEntity().getShooter().launchProjectile(SpectralArrow.class);
//				} else {
//					event.getEntity().getShooter().launchProjectile(Fireball.class);
//				}
//			}
//			
//		}
//	}
	
	

	@SuppressWarnings("deprecation")
	@EventHandler
	public void event(CreatureSpawnEvent event) {
		if(event.getSpawnReason() != SpawnReason.CUSTOM) {
			
		if(event.getEntityType() == EntityType.CREEPER) {
				CreeperBombManager.getInstance().createCreeperBomb((Creeper) event.getEntity());
		} else if(event.getEntityType() == EntityType.SKELETON) {
			Skeleton x = (Skeleton) event.getEntity();
			ItemStack y = new ItemStack(Material.BOW);
			ItemMeta itemMeta = y.getItemMeta();
			itemMeta.setDisplayName("machine gun");
			y.setItemMeta(itemMeta);
			y.addEnchantment(Enchantment.ARROW_DAMAGE, 3);
			y.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
			x.getEquipment().setItemInHand(y);
			x.getEquipment().setItemInHandDropChance(0);
		} else if(event.getEntityType() == EntityType.ZOMBIE) {
			Zombie x = (Zombie) event.getEntity();
			x.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			x.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			x.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			x.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			x.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 5));
		} else if(event.getEntityType() == EntityType.BAT) {
			Bat x = (Bat) event.getEntity();
			x.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 99999999, 100));
			Vex y = (Vex) event.getEntity().getWorld().spawnEntity(x.getLocation(), EntityType.VEX);
			y.addPassenger(x);
			 event.setCancelled(true);
		} else if(event.getEntityType() == EntityType.BLAZE) {
			Blaze x = (Blaze) event.getEntity();
			if(event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
				Blaze y = (Blaze) event.getEntity().getWorld().spawnEntity(x.getLocation(), EntityType.BLAZE);
				x.addPassenger(y);
				for(int i = 0; i < 5; i ++) {
				Blaze z = (Blaze) event.getEntity().getWorld().spawnEntity(x.getLocation(), EntityType.BLAZE);
				y.addPassenger(z);
				y = z;
				}
			}
		} else if(event.getEntityType() == EntityType.WITHER_SKELETON) {
			System.out.println("wither skeleton spawned");
			WitherSkeleton x = (WitherSkeleton) event.getEntity();
			ItemStack y = new ItemStack(Material.IRON_AXE);
			ItemStack z = new ItemStack(Material.IRON_CHESTPLATE);
			y.addUnsafeEnchantment(Enchantment.KNOCKBACK, 5);
			x.getEquipment().setItemInMainHand(y);
			x.getEquipment().setChestplate(z);
			x.getEquipment().setItemInMainHandDropChance(0);
			
		} else if(event.getEntityType() == EntityType.PIGLIN) {
			PigZombie x = (PigZombie) event.getEntity();
			x.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 900000, 1));
			x.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 900000, 2));
			Zombie y = (Zombie) x.getWorld().spawnEntity(x.getLocation(), EntityType.ZOMBIE);
			y.setBaby(true);
			y.addPassenger(x);
		} else if(event.getEntityType() == EntityType.ENDERMAN) {
			Enderman x = (Enderman) event.getEntity();
			WitherSkeleton y = (WitherSkeleton) x.getWorld().spawnEntity(x.getLocation(), EntityType.WITHER_SKELETON);
			y.addPassenger(x);
		}
		
		/*else if(event.getEntityType() == EntityType.ZOMBIE) {
			Zombie x = (Zombie) event.getEntity();
			x.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			x.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			x.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			x.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			x.getEquipment().setItemInHand(new ItemStack(Material.GOLDEN_AXE));
			x.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 20));
		} else if(event.getEntityType() == EntityType.SKELETON) {
			Skeleton x = (Skeleton) event.getEntity();
			x.getEquipment().setHelmet(new ItemStack(Material.GOLDEN_HELMET));
			x.getEquipment().setBoots(new ItemStack(Material.GOLDEN_BOOTS));
			x.getEquipment().setChestplate(new ItemStack(Material.GOLDEN_CHESTPLATE));
			x.getEquipment().setLeggings(new ItemStack(Material.GOLDEN_LEGGINGS));
			x.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 10000, 20));}
		*/
	
	}
	}
}
