package com.lagopusempire.temporarywarp.warps;

import com.lagopusempire.temporarywarp.ReturnType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;

/**
 * Represents a warp location
 *
 * @author MrZoraman
 */
public class Warp
{

    private final String name;

    private Location loc;
    private Location returnLoc;
    private double cost;
    private double length;
    private ReturnType returnType;

    public Warp(Location loc, Location returnLoc, double cost, String name, double length, ReturnType returnType)
    {
        if (returnLoc == null && returnType == ReturnType.WARP_SPECIFIC)
        {
            throw new IllegalArgumentException("You can't make a warp specific return point and pass in a null value for the return location!");
        }

        this.loc = loc;
        this.returnLoc = returnLoc;
        this.cost = cost;
        this.name = name;
        this.length = length;
        this.returnType = returnType;
    }

    public Location getLoc()
    {
        return loc;
    }

    public void setLoc(Location loc)
    {
        this.loc = loc;
    }

    public Location getReturnLoc()
    {
        return returnLoc;
    }

    public void setReturnLoc(Location returnLoc)
    {
        this.returnLoc = returnLoc;
    }

    public double getCost()
    {
        return cost;
    }

    public void setCost(double cost)
    {
        this.cost = cost;
    }

    public double getLength()
    {
        return length;
    }

    public void setLength(double length)
    {
        this.length = length;
    }

    public String getName()
    {
        return name;
    }

    public ReturnType getReturnType()
    {
        return returnType;
    }

    public void setReturnType(ReturnType returnType)
    {
        this.returnType = returnType;
    }

    public void printToLogger(Logger logger, Level level, String prefix)
    {
        //it is important for this method not to fail for any reason
        try
        {
            logger.log(level, prefix + name + ":");
            logger.log(level, prefix + "    Location:");
            logger.log(level, prefix + "        World: " + loc.getWorld().getName());
            logger.log(level, prefix + "        X: " + loc.getX());
            logger.log(level, prefix + "        Y: " + loc.getY());
            logger.log(level, prefix + "        Z: " + loc.getZ());
            logger.log(level, prefix + "        Yaw: " + loc.getYaw());
            logger.log(level, prefix + "        Pitch: " + loc.getPitch());
            logger.log(level, prefix + "    Cost: " + cost);
            logger.log(level, prefix + "    Length: " + length);
            logger.log(level, prefix + "    ReturnType: " + returnType.toString());
            
            if (returnType == ReturnType.WARP_SPECIFIC)
            {
                logger.log(level, prefix + "    ReturnLocation:");
                logger.log(level, prefix + "        World: " + returnLoc.getWorld().getName());
                logger.log(level, prefix + "        X: " + returnLoc.getX());
                logger.log(level, prefix + "        Y: " + returnLoc.getY());
                logger.log(level, prefix + "        Z: " + returnLoc.getZ());
                logger.log(level, prefix + "        Yaw: " + returnLoc.getYaw());
                logger.log(level, prefix + "        Pitch: " + returnLoc.getPitch());
            }
        }
        catch (Exception ex)
        {
            logger.log(Level.SEVERE, "Warp " + name + " is corrupted!", ex);
        }
    }
}
