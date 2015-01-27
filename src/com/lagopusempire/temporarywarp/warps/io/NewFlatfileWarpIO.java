package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.ReturnType;
import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.util.LocationUtils;
import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class NewFlatfileWarpIO implements IWarpIO
{
    private final ConfigAccessor configAccessor;
    
    public NewFlatfileWarpIO(ConfigAccessor configAccessor)
    {
        this.configAccessor = configAccessor;
    }
    
    @Override
    public Map<String, Warp> loadWarps() throws Exception
    {
        final FileConfiguration config = configAccessor.getConfig();
        
        Map<String, Warp> warps = new HashMap<String, Warp>();
        for(String warpName : config.getConfigurationSection("Warps").getKeys(false))
        {
            //public Warp(Location loc, Location returnLoc, double cost, String name, double length, ReturnType returnType)
            
            String path = "Warps." + warpName;
            Location loc = LocationUtils.loadLocation(config, path + ".Location");
            double cost = config.getDouble(path  + ".Cost");
            double length = config.getDouble(path  + ".Length");
            ReturnType returnType = ReturnType.valueOf(config.getString(path  + ".ReturnType"));
            
            Location returnLoc = null;
            
            if(returnType == ReturnType.WARP_SPECIFIC)
            {
                returnLoc = LocationUtils.loadLocation(config, path  + ".ReturnLocation");
            }
            
            Warp warp = new Warp(loc, returnLoc, cost, warpName, length, returnType);
            warps.put(warpName, warp);
        }
        
        return warps;
    }

    /**
     * Saves a warp
     * @param warp The warp to save
     * @throws Exception If something goes wrong during io
     */
    @Override
    public void saveWarp(Warp warp) throws Exception
    {
        final FileConfiguration config = configAccessor.getConfig();
        
        String path = "Warps." + warp.getName();
        
        LocationUtils.saveLocation(config, path + ".Location", warp.getLoc());
        config.set(path + ".Cost", warp.getCost());
        config.set(path + ".Length", warp.getLength());
        config.set(path + ".ReturnType", warp.getReturnType().toString());
        
        if(warp.getReturnType() == ReturnType.WARP_SPECIFIC)
        {
            LocationUtils.saveLocation(config, path + ".ReturnLocation", warp.getReturnLoc());
        }
        
        configAccessor.saveConfig();
    }

    @Override
    public Location getDefaultLocation() throws Exception
    {
        final FileConfiguration config = configAccessor.getConfig();
        if(!config.contains("DefaultReturnLocation"))
        {
            Location loc = Bukkit.getServer().getWorlds().get(0).getSpawnLocation();
            saveDefaultLocation(loc);
            return loc;
        }
        else
        {
            return LocationUtils.loadLocation(config, "DefaultReturnLocation");
        }
    }

    @Override
    public void saveDefaultLocation(Location loc) throws Exception
    {
        LocationUtils.saveLocation(configAccessor.getConfig(), "DefaultReturnLocation", loc);
        configAccessor.saveConfig();
    }
}
