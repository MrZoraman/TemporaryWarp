package com.lagopusempire.temporarywarp;

import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.warps.WarpManager;
import com.lagopusempire.temporarywarp.warps.WarpStorageConverter;
import com.lagopusempire.temporarywarp.warps.io.IWarpIO;
import com.lagopusempire.temporarywarp.warps.io.NewFlatfileWarpIO;
import com.lagopusempire.temporarywarp.warps.io.OldFlatfileWarpIO;
import java.util.logging.Level;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
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
        ConfigAccessor config = new ConfigAccessor(this, "locations.yml");
//        IWarpLoader loader = new OldFlatfileWarpIO(config.getConfig());
//        IWarpSaver saver = new NewFlatfileWarpIO(config);
        
        IWarpIO io = new NewFlatfileWarpIO(config);
        
        
//        WarpStorageConverter converter = new WarpStorageConverter(this, loader, saver);
//        
//        converter.convert(config);
        
        WarpManager manager = new WarpManager(this, io, io);
        
        try
        {
            manager.load();
        }
        catch (Exception ex)
        {
            getLogger().log(Level.SEVERE, null, ex);
        }
        
        manager.printWarps(getLogger());
        
        
//        BukkitCommandSystem cs = new BukkitCommandSystem(this);
        
        getLogger().info("TemporaryWarp Enabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args)
    {
        
        return true;
    }
}
