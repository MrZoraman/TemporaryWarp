package com.lagopusempire.temporarywarp;

import com.mrz.dyndns.server.Hoams.zorascommandsystem.bukkitcompat.BukkitCommandSystem;
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
        BukkitCommandSystem cs = new BukkitCommandSystem(this);
        
        getLogger().info("TemporaryWarp Enabled!");
    }
}
