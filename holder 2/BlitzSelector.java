import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Selects the 3 minute blitz gamemode for the chessboard
 * 
 * @author Karim Elsawi
 * @version 1.0
 */
public class BlitzSelector extends SelectorButton
{
       
    String username;

    /**
     * Constructor for objects of class BlitzSelector button. 
     * @param String usernameIn is the username of the user who is signed in
     */
    public BlitzSelector(String usernameIn) {
        username = usernameIn;
    }

    /**
     * Act - do whatever the blitzSelector wants to do. This method is called whenever
     * the button gets pressed in the environment.
     */
    public void act()
    {
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new ChessBoard(username,180));
        }
    }
}
