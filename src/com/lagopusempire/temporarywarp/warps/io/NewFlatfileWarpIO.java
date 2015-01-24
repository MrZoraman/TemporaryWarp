package com.lagopusempire.temporarywarp.warps.io;

import com.lagopusempire.temporarywarp.ReturnType;
import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.util.LocationUtils;
import com.lagopusempire.temporarywarp.warps.Warp;
import java.util.Map;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class NewFlatfileWarpIO implements IWarpLoader, IWarpSaver
{
    private final ConfigAccessor configAccessor;
    
    public NewFlatfileWarpIO(ConfigAccessor configAccessor)
    {
        this.configAccessor = configAccessor;
    }
    
    @Override
    public Map<String, Warp> loadWarps() throws Exception
    {
        
        return null;
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
}
