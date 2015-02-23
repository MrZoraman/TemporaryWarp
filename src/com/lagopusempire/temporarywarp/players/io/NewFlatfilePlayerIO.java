package com.lagopusempire.temporarywarp.players.io;

import com.lagopusempire.temporarywarp.TWarpSetupFailException;
import com.lagopusempire.temporarywarp.util.ConfigAccessor;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import org.bukkit.configuration.file.FileConfiguration;

/**
 *
 * @author MrZoraman
 */
public class NewFlatfilePlayerIO implements IPlayerIO
{
    private final ConfigAccessor configAccessor;
    
    public NewFlatfilePlayerIO(ConfigAccessor configAccessor)
    {
        this.configAccessor = configAccessor;
    }

    @Override
    public Map<UUID, String> loadPendingPlayers()
    {
        final FileConfiguration config = configAccessor.getConfig();
        final Map<UUID, String> players = new HashMap<>();
        
        Set<String> uuidStrings = config.getConfigurationSection("Players").getKeys(false);
        for(String uuidString : uuidStrings)
        {
            UUID uuid = UUID.fromString(uuidString);
            String warpName = config.getString("Players." + uuidString);
            players.put(uuid, warpName);
        }
        
        return players;
    }

    @Override
    public void savePendingPlayers(Map<UUID, String> players)
    {
        final FileConfiguration config = configAccessor.getConfig();
        
        for(Map.Entry<UUID, String> entry : players.entrySet())
        {
            config.set("Players." + entry.getKey(), entry.getValue());
        }
        
        configAccessor.saveConfig();
    }  
}
