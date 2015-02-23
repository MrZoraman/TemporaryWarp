package com.lagopusempire.temporarywarp.players;

import com.lagopusempire.temporarywarp.TWarpSetupFailException;
import com.lagopusempire.temporarywarp.players.io.IPlayerIO;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author MrZoraman
 */
public class PlayerStorageConverter
{
    private final IPlayerIO loader;
    private final IPlayerIO saver;
    
    public PlayerStorageConverter(IPlayerIO loader, IPlayerIO saver)
    {
        this.loader = loader;
        this.saver = saver;
    }
    
    
    public void convert() throws TWarpSetupFailException
    {
        try
        {
            Map<UUID, String> players = loader.loadPendingPlayers();
            
            saver.savePendingPlayers(players);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            throw new TWarpSetupFailException("Failed to convert player data!");
        }
    }
}
