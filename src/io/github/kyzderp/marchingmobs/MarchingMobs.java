/**
 * This file is part of HorseInfoTags.
 * 
 * HorseInfoTags is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * HorseInfoTags is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with HorseInfoTags.  If not, see <http://www.gnu.org/licenses/>.
 * */

package io.github.kyzderp.marchingmobs;

import io.github.kyzderp.marchingmobs.listeners.EntityListener;
import io.github.kyzderp.marchingmobs.v1_9.mobs.MarchingCow;

import java.io.File;

import net.minecraft.server.v1_9_R1.World;

import org.bukkit.Bukkit;
import org.bukkit.command.BlockCommandSender;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Kyzeragon 3/4/2016
 */

public class MarchingMobs extends JavaPlugin 
{
	private Settings settings;
	private EntityListener listener;

	@Override
	public void onEnable()
	{
		final File configFile = new File(this.getDataFolder(), "config.yml");
		if (!configFile.exists())
			this.saveDefaultConfig();
		this.reloadConfig();

		this.settings = new Settings(this);
		this.settings.loadSettings();
		
		this.listener = new EntityListener(this);
		this.getServer().getPluginManager().registerEvents(this.listener, this);

		CustomEntityType.registerEntities();
	}

	@Override
	public void onDisable()
	{
		CustomEntityType.unregisterEntities();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		if (cmd.getName().equalsIgnoreCase("march"))
		{
		/*	if (args.length == 1)
			{
				Player player = Bukkit.getPlayer(sender.getName());
				if (args[0].equalsIgnoreCase("cow"))
				{
					World world = ((CraftWorld) player.getWorld()).getHandle();
					MarchingCow cow = new MarchingCow(world);
					cow.setLocation(player.getLocation().getX(), 
							player.getLocation().getY(), 
							player.getLocation().getZ(), 
							player.getLocation().getYaw(), 
							player.getLocation().getPitch());
					cow.isMarcher = true;
					world.addEntity(cow, SpawnReason.CUSTOM);
					return true;
				}
				return false;
			}*/
			
			if (args.length == 7)
			{ 
				org.bukkit.World bukkitWorld;
				if (sender instanceof BlockCommandSender)
					bukkitWorld = ((BlockCommandSender) sender).getBlock().getWorld();
				else
					bukkitWorld = Bukkit.getPlayer(sender.getName()).getWorld();
					
				if (args[0].equalsIgnoreCase("cow"))
				{
					double x, y, z;
					float yaw, pitch, speed;
					// Parse ints
					try {
						x = Double.parseDouble(args[1]) + 0.5;
						y = Double.parseDouble(args[2]);
						z = Double.parseDouble(args[3]) + 0.5;
					} catch (NumberFormatException e) {
						sender.sendMessage("\u00A7cThe x, y, and z must be doubles! "
								+ "Usage: /march cow <x> <y> <z> <yaw> <pitch> <speed>");
						return true;
					}
					// Parse floats
					try {
						yaw = Float.parseFloat(args[4]);
						pitch = Float.parseFloat(args[5]);
						speed = Float.parseFloat(args[6]);
						if (speed < 0 || speed > 1)
						{
							sender.sendMessage("\u00A7cThe speed must be in the range 0~1! "
									+ "Usage: /march cow <x> <y> <z> <yaw> <pitch> <speed>");
							return true;
						}
					} catch (NumberFormatException e) {
						sender.sendMessage("\u00A7cThe yaw, pitch, and speed must be floats! "
								+ "Usage: /march cow <x> <y> <z> <yaw> <pitch> <speed>");
						return true;
					}
					
					World world = ((CraftWorld) bukkitWorld).getHandle();
					MarchingCow cow = new MarchingCow(world);
					cow.setLocation(x, y, z, yaw, pitch);
					cow.setMarcher(yaw, pitch, speed);
					cow.setMarchRideable(true);
					world.addEntity(cow, SpawnReason.CUSTOM);
					return true;
				}
				return false;
			} // /march cow x y z yaw pitch speed

			return false;
		}
		return false;
	}
}
