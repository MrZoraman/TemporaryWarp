package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.Map;

/**
 * Understands how to load warps.
 * @author MrZoraman
 * If this were java 8, this would be a functional interface
 */
public interface IWarpLoader
{
    public Map<String, Warp> loadWarps() throws Exception;
}
