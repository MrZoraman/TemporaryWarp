package com.lagopusempire.temporarywarp.warps;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MrZoraman
 */
public class WarpManagerBase
{
    protected Map<String, Warp> warps = new HashMap<String, Warp>();
    
    public WarpManagerBase() throws Exception
    {
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
}
