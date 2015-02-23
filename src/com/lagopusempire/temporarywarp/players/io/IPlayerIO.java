package com.lagopusempire.temporarywarp.players.io;

import com.lagopusempire.temporarywarp.TWarpSetupFailException;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author MrZoraman
 */
public interface IPlayerIO
{
    public Map<UUID, String> loadPendingPlayers() throws TWarpSetupFailException;
    
    public void savePendingPlayers(Map<UUID, String> players);
}
