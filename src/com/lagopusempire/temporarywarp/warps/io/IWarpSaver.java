package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.warps.Warp;

/**
 * Understands how to save warps
 * @author MrZoraman
 * If this were java 8, this would be a functional interface
 */
public interface IWarpSaver
{
    public void saveWarp(Warp warp) throws Exception;
}
