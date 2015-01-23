package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.util.LocationUtils;
import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class OldFlatfileWarpIO implements IWarpLoader, IWarpSaver
{
    private final FileConfiguration config;
    
    public OldFlatfileWarpIO(FileConfiguration config)
    {
        this.config = config;
    }
    
    @Override
    public Map<String, Warp> loadWarps() throws Exception
    {
        Map<String, Warp> warps = new HashMap<String, Warp>();
        
        Set<String> warpNames = config.getConfigurationSection("").getKeys(false);
        for(String warpName : warpNames)
        {
            // public Warp(Location loc, Location returnLoc, double cost, String name, double length)
            Location loc = LocationUtils.loadLocation(config, warpName);
            Location returnLoc = null;
            if(config.contains(warpName + ".returnLocation"))
            {
                returnLoc = LocationUtils.loadLocation(config, warpName + ".returnLocation");
            }
            
//            double 
        }
        
        return warps;
    }

    @Override
    public void saveWarp(Warp warp) throws Exception
    {
        
    }
}
