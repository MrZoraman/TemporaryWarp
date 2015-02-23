package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.Map;
import org.bukkit.Location;

/**
 *
 * @author MrZoraman
 */
public interface IWarpIO
{
    public Map<String, Warp> loadWarps();
    
    public void saveWarp(Warp warp);
    
    public Location getDefaultLocation();
    
    public void saveDefaultLocation(Location loc);
}
