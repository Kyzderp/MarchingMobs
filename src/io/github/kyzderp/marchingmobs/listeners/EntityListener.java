package io.github.kyzderp.marchingmobs.listeners;

import io.github.kyzderp.marchingmobs.v1_9.mobs.MarchingCow;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftCow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EntityListener implements Listener 
{
	@SuppressWarnings("unused")
	private JavaPlugin plugin;
	
	public EntityListener(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPlayerRightClickMob(PlayerInteractEntityEvent event)
	{
		if (!(event.getRightClicked() instanceof CraftCow))
			return;
		if (!(((CraftCow)event.getRightClicked()).getHandle() instanceof MarchingCow))
			return;
		
		Player player = event.getPlayer();
		MarchingCow cow = (MarchingCow)((CraftCow)event.getRightClicked()).getHandle();
		
		if (cow.getMarchRideable())
		{
			event.getRightClicked().setPassenger(player);
		}
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent event)
	{
		if (!(event.getEntity() instanceof Player))
			return;

		Player player = (Player) event.getEntity();
		Entity vehicle = player.getVehicle();
		if (vehicle == null || !(vehicle instanceof CraftCow) 
				|| !(((CraftCow)vehicle).getHandle() instanceof MarchingCow))
			return;
		event.setCancelled(true);
	}
	
	@EventHandler
	public void onDamageEntity(EntityDamageByEntityEvent event)
	{
		if (!(event.getDamager() instanceof Player))
			return;
		if (!(event.getEntity() instanceof CraftCow))
			return;
		if (!(((CraftCow)event.getEntity()).getHandle() instanceof MarchingCow))
			return;
		MarchingCow cow = (MarchingCow) ((CraftCow)event.getEntity()).getHandle();
		if (cow.getMarcher())
			event.setCancelled(true);
	}
}
