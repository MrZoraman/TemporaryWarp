package com.lagopusempire.temporarywarp;

import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.util.ConfigConstants;
import com.lagopusempire.temporarywarp.warps.WarpManager;
import com.lagopusempire.temporarywarp.warps.WarpStorageConverter;
import com.lagopusempire.temporarywarp.warps.io.OldFlatfileWarpIO;
import com.lagopusempire.temporarywarp.warps.io.IWarpIO;
import com.lagopusempire.temporarywarp.warps.io.NewFlatfileWarpIO;
import java.io.File;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author MrZoraman
 */
public class TemporaryWarp extends JavaPlugin
{
    @Override
    public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        boolean newLocationsFile = !locationsFileExists();
        
        ConfigAccessor locations = new ConfigAccessor(this, "locations.yml");
        
        if(newLocationsFile)
        {
            System.out.println("Setting flatfile version");
            locations.getConfig().set("FlatfileVersion", 1);
            locations.saveConfig();
        }
        
        boolean success = updateWarpStorage(locations);
        
        if(success == false)
        {
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        StorageType storageType = null;
        try
        {
            storageType = StorageType.valueOf(getConfig().getString(ConfigConstants.STORAGE_TYPE));
        }
        catch (IllegalArgumentException ex)
        {
            getLogger().severe("Bad storage type! Perhaps you've made a type? So far the only available storage type is 'FLATFILE'");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        IWarpIO io = null;
        switch(storageType)
        {
            case FLATFILE:
                io = new NewFlatfileWarpIO(locations);
                break;
            default:
                getLogger().severe("Unexpected plugin state! Please contact the author about this issue. Disabling...");
                getServer().getPluginManager().disablePlugin(this);
                return;
        }
        
        final WarpManager manager = new WarpManager(this, io);
        
        //TODO: load
        
        
//        IWarpLoader loader = new OldFlatfileWarpIO(config.getConfig());
//        IWarpSaver saver = new NewFlatfileWarpIO(config);
        
//        IWarpIO io = new NewFlatfileWarpIO(config);
//        
//        
////        WarpStorageConverter converter = new WarpStorageConverter(this, loader, saver);
////        
////        converter.convert(config);
//        
//        WarpManager manager = new WarpManager(this, io, io);
//        
//        try
//        {
//            manager.load();
//        }
//        catch (Exception ex)
//        {
//            getLogger().log(Level.SEVERE, null, ex);
//        }
//        
//        manager.printWarps(getLogger());
        
        
//        BukkitCommandSystem cs = new BukkitCommandSystem(this);
        
        getLogger().info("TemporaryWarp Enabled!");
    }
    
    /**
     * Converts flatfile from old format to new one if necessary
     * @param locationsYml the configuration file
     * @return return true if all goes well, False if something has gone terribly wrong
     */
    private boolean updateWarpStorage(ConfigAccessor locationsYml)
    {
        if(!locationsYml.getConfig().contains(ConfigConstants.FLATFILE_VERSION))
        {
            getLogger().info("Detected old version of warp storage system. Converting to new format...");
            
            //old version of config, time to update
            final IWarpIO loader = new OldFlatfileWarpIO(locationsYml.getConfig());
            final IWarpIO saver = new NewFlatfileWarpIO(locationsYml);
            
            final WarpStorageConverter converter = new WarpStorageConverter(this, loader, saver);
            boolean success = converter.convert(locationsYml);
            if(success)
            {
                getLogger().info("Conversion successful!");
                locationsYml.getConfig().set(ConfigConstants.FLATFILE_VERSION, 1);
                locationsYml.saveConfig();
            }
            else
            {
                getLogger().severe("Conversion failed!");
                return false;
            }
        }
        
        return true;
    }
    
    private boolean locationsFileExists()
    {
        return new File(getDataFolder() + File.separator + "locations.yml").exists();
    }
}
