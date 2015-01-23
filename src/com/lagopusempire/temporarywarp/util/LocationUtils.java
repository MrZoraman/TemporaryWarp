package com.lagopusempire.temporarywarp.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class LocationUtils
{
    //no constructor. Static class
    private LocationUtils() {}
    
    public static Location loadLocation(FileConfiguration config, String path)
    {
        String worldName = config.getString(path + ".world");
        
        if(worldName == null)
        {
            return null;
        }
        
        double x = config.getDouble(path + ".X");
        double y = config.getDouble(path + ".Y");
        double z = config.getDouble(path + ".Z");
        float yaw = (float)config.getDouble(path + ".Yaw");
        float pitch = (float)config.getDouble(path + ".Pitch");
        
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
}
