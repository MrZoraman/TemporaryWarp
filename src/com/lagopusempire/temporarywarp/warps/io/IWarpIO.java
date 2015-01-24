package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.Map;

/**
 *
 * @author MrZoraman
 */
public interface IWarpIO
{
    public Map<String, Warp> loadWarps() throws Exception;
    
    public void saveWarp(Warp warp) throws Exception;
}
