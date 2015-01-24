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
    public Map<String, Warp> loadWarps() throws Exception;
    
    public void saveWarp(Warp warp) throws Exception;
    
    public Location getDefaultLocation();
    
    public void saveDefaultLocation(Location loc);
}
