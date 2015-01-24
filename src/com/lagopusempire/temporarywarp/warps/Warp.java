package com.lagopusempire.temporarywarp.warps;

import com.lagopusempire.temporarywarp.ReturnType;
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
    
    @Override
    public String toString()
    {
        String str = "Warp " + name + ":\n";
        str += "    Loc:\n";
        str += "        World: " + loc.getWorld().getName() + "\n";
        str += "        X: " + loc.getX() + "\n";
        str += "        Y: " + loc.getY() + "\n";
        str += "        Z: " + loc.getZ() + "\n";
        str += "        Yaw: " + loc.getYaw() + "\n";
        str += "        Pitch: " + loc.getPitch() + "\n";
        
        if(returnLoc != null)
        {
            str += "    Return Loc:\n";
            str += "        World: " + returnLoc.getWorld().getName() + "\n";
            str += "        X: " + returnLoc.getX() + "\n";
            str += "        Y: " + returnLoc.getY() + "\n";
            str += "        Z: " + returnLoc.getZ() + "\n";
            str += "        Yaw: " + returnLoc.getYaw() + "\n";
            str += "        Pitch: " + returnLoc.getPitch() + "\n";
        }
        
        
        str += "    Cost: " + cost + "\n";
        str += "    Length: " + length + "\n";
        str += "    Return Type: " + returnType.toString();
        
        return str;
    }
}
