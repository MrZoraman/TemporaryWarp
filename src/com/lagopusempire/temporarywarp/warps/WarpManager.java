package com.lagopusempire.temporarywarp.warps;

import com.lagopusempire.temporarywarp.warps.io.IWarpIO;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author MrZoraman
 */
public class WarpManager
{
    private Map<String, Warp> warps = null;
    private Location defaultLocation = null;
    
    private final IWarpIO io;
    private final JavaPlugin plugin;
    
    public WarpManager(JavaPlugin plugin, IWarpIO io)
    {
        this.plugin = plugin;
        
        this.io = io;
    }
    
    public void load() throws Exception
    {
        warps = io.loadWarps();
        defaultLocation = io.getDefaultLocation();
    }
    
    /**
     * Saves the warps
     * @return True if all went well, false if something bad happened
     */
    public boolean saveWarps()
    {
        boolean success = true;
        
        Iterator<Warp> it = warps.values().iterator();
        
        while(it.hasNext())
        {
            Warp warp = it.next();
            try
            {
                io.saveWarp(warp);
            }
            catch (Exception ex)
            {
                plugin.getLogger().log(Level.SEVERE, "Failed to save warp " + warp.getName() + "!", ex);
                success = false;
            }
        }
        
        return success;
    }
    
    /**
     * @param warp The warp to add to the manager
     * @return True if the warp name is valid, false if there is already a warp using that name.
     */
    public boolean addWarp(Warp warp)
    {
        return warps.putIfAbsent(warp.getName(), warp) == null;
    }
    
    public Warp getWarp(String name)
    {
        return warps.get(name);
    }
    
    public void printWarps(Logger logger)
    {
        plugin.getLogger().log(Level.INFO, "Warps:");
        for(Warp warp : warps.values())
        {
            warp.printToLogger(plugin.getLogger(), Level.INFO, "    ");
        }
    }
    
    public Location getDefaultLocation()
    {
        return defaultLocation;
    }
}
