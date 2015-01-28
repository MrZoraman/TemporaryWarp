package com.lagopusempire.temporarywarp.players.io;

import java.util.Map;
import java.util.UUID;

/**
 *
 * @author MrZoraman
 */
public interface IPlayerIO
{
    public Map<UUID, String> loadPendingPlayers() throws Exception;
    
    public void savePendingPlayers(Map<UUID, String> players) throws Exception;
}
