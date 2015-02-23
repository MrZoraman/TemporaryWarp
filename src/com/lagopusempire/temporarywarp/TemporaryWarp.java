package com.lagopusempire.temporarywarp;

import com.lagopusempire.temporarywarp.players.PlayerStorageConverter;
import com.lagopusempire.temporarywarp.players.io.IPlayerIO;
import com.lagopusempire.temporarywarp.players.io.NewFlatfilePlayerIO;
import com.lagopusempire.temporarywarp.players.io.OldFlatfilePlayerIO;
import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.util.ConfigConstants;
import com.lagopusempire.temporarywarp.warps.WarpManager;
import com.lagopusempire.temporarywarp.warps.WarpStorageConverter;
import com.lagopusempire.temporarywarp.warps.io.OldFlatfileWarpIO;
import com.lagopusempire.temporarywarp.warps.io.IWarpIO;
import com.lagopusempire.temporarywarp.warps.io.NewFlatfileWarpIO;
import java.io.File;
import java.util.logging.Level;
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
        
        boolean newLocationsFile = !fileExists("locations.yml");
        boolean newPlayersFile = !fileExists("players.yml");
        
        ConfigAccessor locations = new ConfigAccessor(this, "locations.yml");
        ConfigAccessor players = new ConfigAccessor(this, "players.yml");
        
        if(newLocationsFile)
        {
            locations.getConfig().set(ConfigConstants.FLATFILE_VERSION, 1);
            locations.saveConfig();
        }
        
        /*
         * Player Data
         */
        if(newPlayersFile)
        {
            getLogger().info("Converting player data from old storage type to the new one...");
            boolean success = updatePlayerStorage(players);
            if(!success)
            {
                getLogger().severe("Something has gone wrong while converting the player data! Disabling...");
                getServer().getPluginManager().disablePlugin(this);
                return;
            }
        }
        
        /*
         * Warp data
         */
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
        
        try
        {
            manager.load();
        }
        catch (Exception ex)
        {
            getLogger().log(Level.SEVERE, "Failed to load warps!", ex);
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        
        
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
            //old version of config, time to update
            getLogger().info("Detected old version of warp storage system. Converting to new format...");
            
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
    
    /**
     * Converts player data
     * @param playersYml The config file to store the data at
     * @return True if all goes well, false if something goes wrong
     */
    private boolean updatePlayerStorage(ConfigAccessor playersYml)
    {
        final IPlayerIO loader = new OldFlatfilePlayerIO(playersYml.getConfig());
        final IPlayerIO saver = new NewFlatfilePlayerIO(playersYml);
        
        final PlayerStorageConverter converter = new PlayerStorageConverter(this, loader, saver);
        
        boolean success = converter.convert();
        if(success)
        {
            getLogger().info("Conversion successful!");
            playersYml.getConfig().set(ConfigConstants.FLATFILE_VERSION, 1);
            playersYml.saveConfig();
        }
        else
        {
            getLogger().severe("Conversion failed!");
            return false;
        }
        
        return true;
    }
    
    private boolean fileExists(String fileName)
    {
        return new File(getDataFolder() + File.separator + fileName).exists();
    }
}
