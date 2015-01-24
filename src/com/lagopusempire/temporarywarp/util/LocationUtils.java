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
            //Try capitalized 'World'
            worldName = config.getString(path + ".World");
            if(worldName == null)
            {
                return null;
            }
        }
        
        double x = config.getDouble(path + ".X");
        double y = config.getDouble(path + ".Y");
        double z = config.getDouble(path + ".Z");
        float yaw = (float)config.getDouble(path + ".Yaw");
        float pitch = (float)config.getDouble(path + ".Pitch");
        
        return new Location(Bukkit.getWorld(worldName), x, y, z, yaw, pitch);
    }
    
    /**
     * Saves a location to the config. Don't forget to save the config!
     * @param config The config to save the location to
     * @param path The yml path to the location
     * @param loc The location to save
     */
    public static void saveLocation(FileConfiguration config, String path, Location loc)
    {
        String worldName = loc.getWorld().getName();
        
        double x = loc.getX();
        double y = loc.getY();
        double z = loc.getZ();
        float yaw = loc.getYaw();
        float pitch = loc.getPitch();
        
        config.set(path + ".World", worldName);
        config.set(path + ".X", x);
        config.set(path + ".Y", y);
        config.set(path + ".Z", z);
        config.set(path + ".Yaw", yaw);
        config.set(path + ".Pitch", pitch);
    }
}
