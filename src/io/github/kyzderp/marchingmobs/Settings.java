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

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Settings 
{
	private JavaPlugin plugin;

	public Settings(JavaPlugin plugin)
	{
		this.plugin = plugin;
	}

	//////////////////////// LOAD ///////////////////////
	public void loadSettings()
	{
		FileConfiguration config = this.plugin.getConfig();
		config.options().copyDefaults(true);
	}

	////////////////////// GETTERS //////////////////////

}

