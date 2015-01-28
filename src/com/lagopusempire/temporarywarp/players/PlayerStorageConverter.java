package com.lagopusempire.temporarywarp.players;

import com.lagopusempire.temporarywarp.players.io.IPlayerIO;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author MrZoraman
 */
public class PlayerStorageConverter
{
    private final IPlayerIO loader;
    private final IPlayerIO saver;
    
    private final JavaPlugin plugin;
    
    public PlayerStorageConverter(JavaPlugin plugin, IPlayerIO loader, IPlayerIO saver)
    {
        this.plugin = plugin;
        
        this.loader = loader;
        this.saver = saver;
    }
    
    /**
     * converts player data
     * @return True if all went well, false if something went wrong.
     */
    public boolean convert()
    {
        System.out.println("converting...");
        System.out.println(loader.getClass());
        try
        {
            Map<UUID, String> players = loader.loadPendingPlayers();
            
            for(Map.Entry<UUID, String> entry : players.entrySet())
            {
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }
            
            saver.savePendingPlayers(players);
        }
        catch (Exception ex)
        {
            plugin.getLogger().log(Level.SEVERE, "Failed to convert player data!", ex);
            return false;
        }
        
        return true;
    }
}
