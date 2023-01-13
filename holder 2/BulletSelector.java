import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Selects the 1 minute blitz gamemode for the chessboard
 * 
 * @author Karim Elsawi
 * @version 1.0
 */
public class BulletSelector extends SelectorButton
{
       
    String username;

    /**
     * Constructor for objects of class BulletSelector button. 
     * @param String usernameIn is the username of the user who is signed in
     */
    public BulletSelector(String usernameIn) {
        username = usernameIn;
    }

    /**
     * Act - do whatever the bulletSelector wants to do. This method is called whenever
     * the button gets pressed in the environment.
     */
    public void act()
    {
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new ChessBoard(username,60));
        }
    }
}
