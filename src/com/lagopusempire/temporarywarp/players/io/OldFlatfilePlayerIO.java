package com.lagopusempire.temporarywarp.players.io;

import com.lagopusempire.temporarywarp.evilmidget38.UUIDFetcher;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class OldFlatfilePlayerIO implements IPlayerIO
{
    private final FileConfiguration config;
    
    public OldFlatfilePlayerIO(FileConfiguration config)
    {
        this.config = config;
    }
    
    @Override
    public Map<UUID, String> loadPendingPlayers() throws Exception
    {
        Map<UUID, String> players = new HashMap<UUID, String>();
        
        //player, warp name
        Map<String, String> oldPlayers = new HashMap<String, String>();
        
        Set<String> warpNames = config.getConfigurationSection("").getKeys(false);
        for(String warpName : warpNames)
        {
            if(warpName.equals("defaultLocation") || warpName.equals("warps"))
            {
                continue;
            }
            
            Set<String> playersPendingReturn = config.getConfigurationSection(warpName + ".pendingPlayers").getKeys(false);
            
            for(String playerName : playersPendingReturn)
            {
                oldPlayers.put(playerName, warpName);
            }
        }
        
        final UUIDFetcher fetcher = new UUIDFetcher(new ArrayList<String>(oldPlayers.keySet()));
        Map<String, UUID> result = fetcher.call();
        
        for(Map.Entry<String, UUID> entry : result.entrySet())
        {
            players.put(entry.getValue(), oldPlayers.get(entry.getKey()));
        }
        
        return players;
    }

    @Override
    public void savePendingPlayers(Map<UUID, String> players) throws Exception
    {
        throw new UnsupportedOperationException("Can't save pending player warps back to the old format!");
    }
    
}
