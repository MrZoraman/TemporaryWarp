package com.lagopusempire.temporarywarp;

import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import com.lagopusempire.temporarywarp.warps.WarpManager;
import com.lagopusempire.temporarywarp.warps.io.IWarpLoader;
import com.lagopusempire.temporarywarp.warps.io.OldFlatfileWarpIO;
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
        IWarpLoader loader = new OldFlatfileWarpIO(config.getConfig());
        
        WarpManager manager = new WarpManager(this, loader, null);
        
        
        
//        BukkitCommandSystem cs = new BukkitCommandSystem(this);
        
        getLogger().info("TemporaryWarp Enabled!");
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String alias, String[] args)
    {
        
        return true;
    }
}
