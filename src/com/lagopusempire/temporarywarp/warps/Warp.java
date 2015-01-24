package com.lagopusempire.temporarywarp.warps;

import com.lagopusempire.temporarywarp.ReturnType;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.Location;

/**
 * Represents a warp location
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
        if(returnLoc == null && returnType == ReturnType.WARP_SPECIFIC)
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
    
    public void printToLogger(Logger logger, Level level)
    {
        logger.log(level, name + ":");
        logger.log(level, "    Location:");
        logger.log(level, "        World: " + loc.getWorld().getName());
        logger.log(level, "        X: " + loc.getX());
        logger.log(level, "        Y: " + loc.getY());
        logger.log(level, "        Z: " + loc.getZ());
        logger.log(level, "        Yaw: " + loc.getYaw());
        logger.log(level, "        Pitch: " + loc.getPitch());
        logger.log(level, "    Cost:" + cost);
        logger.log(level, "    Length: " + length);
        logger.log(level, "    ReturnType: " + returnType.toString());
        
        if(returnType == ReturnType.WARP_SPECIFIC)
        {
            logger.log(level, "    ReturnLocation:");
            logger.log(level, "        World: " + returnLoc.getWorld().getName());
            logger.log(level, "        X: " + returnLoc.getX());
            logger.log(level, "        Y: " + returnLoc.getY());
            logger.log(level, "        Z: " + returnLoc.getZ());
            logger.log(level, "        Yaw: " + returnLoc.getYaw());
            logger.log(level, "        Pitch: " + returnLoc.getPitch());
        }
    }
}
