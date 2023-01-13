import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Write a description of class RapidSelector here.
 * 
 * @author Karim Elsawi 
 * @version 1.0
 */
public class RapidSelector extends SelectorButton
{
       
    String username;

    /**
     * Constructor for objects of class RapidSelector button. 
     * @param String usernameIn is the username of the user who is signed in
     */
    public RapidSelector(String usernameIn) {
        username = usernameIn;
    }

    /**
     * Act - do whatever the RapidSelector wants to do. This method is called whenever
     * the button gets pressed in the environment.
     */
    public void act()
    {
            if (Greenfoot.mouseClicked(this)) 
        {
            Greenfoot.setWorld(new ChessBoard(username,600));
        }
    }
}
