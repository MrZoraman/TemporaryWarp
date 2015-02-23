package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.ReturnType;
import com.lagopusempire.temporarywarp.util.LocationUtils;
import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class OldFlatfileWarpIO implements IWarpIO
{
    private final FileConfiguration config;
    
    public OldFlatfileWarpIO(FileConfiguration config)
    {
        this.config = config;
    }
    
    @Override
    public Map<String, Warp> loadWarps()
    {
        Map<String, Warp> warps = new HashMap<>();
        
        Set<String> warpNames = config.getConfigurationSection("").getKeys(false);
        for(String warpName : warpNames)
        {
            if(warpName.equals("defaultLocation") || warpName.equals("warps"))
            {
                continue;
            }
            
            // public Warp(Location loc, Location returnLoc, double cost, String name, double length)
            Location loc = LocationUtils.loadLocation(config, warpName);
            Location returnLoc = null;
            if(config.contains(warpName + ".returnLocation"))
            {
                returnLoc = LocationUtils.loadLocation(config, warpName + ".returnLocation");
            }
            
            double cost = config.getDouble(warpName + ".cost");
            double length = config.getDouble(warpName + ".time");
            
            ReturnType returnType = ReturnType.GLOBAL;
            
            if(config.getBoolean(warpName + ".usingDefaultReturn") == false)
            {
                returnType = ReturnType.WARP_SPECIFIC;
            }
            
            Warp warp = new Warp(loc, returnLoc, cost, warpName, length, returnType);
            warps.put(warpName, warp);
        }
        
        return warps;
    }

    @Override
    public void saveWarp(Warp warp)
    {
        //NOPE!
        throw new UnsupportedOperationException("Cannot save warps back to the old format!");
    }

    @Override
    public Location getDefaultLocation()
    {
        if(!config.contains("defaultLocation"))
        {
            Location loc = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
            return loc;
        }
        else
        {
            return LocationUtils.loadLocation(config, "defaultLocation");
        }
    }

    @Override
    public void saveDefaultLocation(Location loc)
    {
        throw new UnsupportedOperationException("Cannot save warps back to the old format!");
    }
}
