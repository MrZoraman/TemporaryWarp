package com.lagopusempire.temporarywarp.warps;

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
    
    public Warp(Location loc, Location returnLoc, double cost, String name, double length)
    {
        this.loc = loc;
        this.returnLoc = returnLoc;
        this.cost = cost;
        this.name = name;
        this.length = length;
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
}
