package com.lagopusempire.temporarywarp;

import com.lagopusempire.bukkitlcs.BukkitLCS;
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
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author MrZoraman
 */
public class TemporaryWarp extends JavaPlugin
{
    private ConfigAccessor locationsConfig;
    private ConfigAccessor playersConfig;
    
    private final BukkitLCS lcs = new BukkitLCS();
    
    @Override
    public void onEnable()
    {
        try
        {
            reload();
        }
        catch (TWarpSetupFailException e)
        {
            e.printStackTrace();
            getLogger().severe("Something went wrong while enabling " + getDescription().getName() + ". Disabling...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        
        getCommand("twarp").setExecutor(lcs);
        
        getLogger().info("TemporaryWarp Enabled!");
    }
    
    public void reload() throws TWarpSetupFailException
    {
        setupConfigs();
        
        setupPlayerData();
        setupWarpData();
    }
    
    private void setupPlayerData() throws TWarpSetupFailException
    {
        if(!fileExists("players.yml"))
        {
            getLogger().info("Converting player data from old storage type to the new one...");
            updatePlayerStorage(locationsConfig, playersConfig);
        }
    }
    
    private void setupWarpData() throws TWarpSetupFailException
    {
        updateWarpStorage(locationsConfig);
        
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
                io = new NewFlatfileWarpIO(locationsConfig);
                break;
            default:
                throw new TWarpSetupFailException("Unexpected plugin state! Please contact the author about this issue...");
        }
        
        final WarpManager manager = new WarpManager(this, io);
        
        manager.load();
    }
    
    private void setupConfigs()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
        
        boolean newLocationsFile = !fileExists("locations.yml");
        
        locationsConfig = new ConfigAccessor(this, "locations.yml");
        playersConfig = new ConfigAccessor(this, "players.yml");
        
        if(newLocationsFile)
        {
            locationsConfig.getConfig().set(ConfigConstants.FLATFILE_VERSION, 1);
            locationsConfig.saveConfig();
        }
    }
    
    private void updateWarpStorage(ConfigAccessor locationsYml) throws TWarpSetupFailException
    {
        if(!locationsYml.getConfig().contains(ConfigConstants.FLATFILE_VERSION))
        {
            //old version of config, time to update
            getLogger().info("Detected old version of warp storage system. Converting to new format...");
            
            final IWarpIO loader = new OldFlatfileWarpIO(locationsYml.getConfig());
            final IWarpIO saver = new NewFlatfileWarpIO(locationsYml);
            
            final WarpStorageConverter converter = new WarpStorageConverter(this, loader, saver);
            converter.convert(locationsYml);
            getLogger().info("Conversion successful!");
            locationsYml.getConfig().set(ConfigConstants.FLATFILE_VERSION, 1);
            locationsYml.saveConfig();
        }
    }
    
    private void updatePlayerStorage(ConfigAccessor oldYml, ConfigAccessor playersYml) throws TWarpSetupFailException
    {
        final IPlayerIO loader = new OldFlatfilePlayerIO(oldYml.getConfig());
        final IPlayerIO saver = new NewFlatfilePlayerIO(playersYml);
        
        final PlayerStorageConverter converter = new PlayerStorageConverter(loader, saver);
        
        converter.convert();
        getLogger().info("Conversion successful!");
        playersYml.getConfig().set(ConfigConstants.FLATFILE_VERSION, 1);
        playersYml.saveConfig();
    }
    
    private boolean fileExists(String fileName)
    {
        return new File(getDataFolder() + File.separator + fileName).exists();
    }
}
