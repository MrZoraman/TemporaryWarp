package com.lagopusempire.temporarywarp.warps;

import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.warps.io.IWarpIO;
import com.lagopusempire.temporarywarp.warps.io.NewFlatfileWarpIO;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author MrZoraman
 */
public class WarpStorageConverter
{

    private final IWarpIO loader;
    private final IWarpIO saver;

    private final JavaPlugin plugin;
    private final Logger logger;

    public WarpStorageConverter(JavaPlugin plugin, IWarpIO loader, IWarpIO saver)
    {
        this.loader = loader;
        this.saver = saver;

        this.plugin = plugin;
        this.logger = plugin.getLogger();
    }

    /**
     * Converts the warps from format to format
     *
     * @param config The configuration file to be cleared while converting (so
     * old doesn't get mixed in with the new, thus keeping things clean)
     * @return True if all went well, false if something wrong happened
     */
    public boolean convert(ConfigAccessor config)
    {
        Map<String, Warp> warps = null;
        Location defaultLocation = null;

        try
        {
            warps = loader.loadWarps();
            defaultLocation = loader.getDefaultLocation();
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE, "Failed to load and convert warps!", ex);
            return false;
        }
        
        
        config.clearConfigAndSave();

        try
        {
            for (Warp warp : warps.values())
            {
                saver.saveWarp(warp);
            }
            
            saver.saveDefaultLocation(defaultLocation);
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE, "Failed to convert and save warps!", ex);
            logger.log(Level.SEVERE, "Because the conversion failed, Attempting to save the warps to a flatfile format (least likely to fail)");
            ConfigAccessor emergencyDump = new ConfigAccessor(plugin, "emergencyWarpDump.yml");
            NewFlatfileWarpIO emergencySaver = new NewFlatfileWarpIO(emergencyDump);
            try
            {
                for(Warp warp : warps.values())
                {
                    emergencySaver.saveWarp(warp);
                }
            }
            catch (Exception ex2)
            {
                logger.log(Level.SEVERE, "Failed to save warps to flatfile!", ex2);
                logger.log(Level.SEVERE, "Plan B has failed! That's ok. I have a plan C up my sleeve that should be failproof. Printing all warps to console! They should be in the same format as the flatfile format, so you should be able to salvage your warp data from here. Hopefully the above stack traces will help you identify what the problem is.");
                logger.log(Level.SEVERE, "Warps:");
                for(Warp warp : warps.values())
                {
                    warp.printToLogger(logger, Level.SEVERE, "    ");
                }
                return false;
            }
            emergencyDump.saveConfig();
            logger.log(Level.SEVERE, "Successfully salvaged the warp data. It is located in emergencyWarpDump.yml");
            logger.log(Level.SEVERE, "You should be able to rename that file to locations.yml, and use it like normal. Don't forget to set the format to 'FLATFILE' in the config though!");
            return false;
        }
        
        return true;
    }
}
