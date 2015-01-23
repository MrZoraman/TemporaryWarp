package com.lagopusempire.temporarywarp.warps;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author MrZoraman
 */
public abstract class WarpManagerBase
{
    private Map<String, Warp> warps = new HashMap<String, Warp>();
    
    public WarpManagerBase()
    {
    }
    
    protected abstract Map<String, Warp> implementedLoadWarps();
    protected abstract void saveWarp(Warp warp);
    
    //todo: create warp
    
    public final void loadWarps()
    {
        Map<String, Warp> warps = implementedLoadWarps();
        this.warps = warps;
    }
    
    public Warp getWarp(String name)
    {
        return warps.get(name);
    }
}
