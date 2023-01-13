import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Stores information hidden from user regarding user influenced variables / integers.
 * 
 * @author Karim Elsawi
 * @version 1.0
 */
public class HiddenWorld extends World
{
    public static int r1=120,g1=120,b1=250;
    public static int r2=100,g2=180,b2=220;
    public static int whiteTime=60;
    public static int blackTime=60;
    /**
     * Constructor for objects of class EmptyWorld.
     * 
     */
    public HiddenWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(600, 400, 1); 
    }
}
