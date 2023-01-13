import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class LoginButton will check if the login button has been pressed, and will switch the world to the login world, where people can sign in.
 * 
 * @author Karim Elsawi
 * @version 1.0
 * @since 2022-1-14
 */
public class LoginButton extends Buttons
{
    /**
     * This Act method is called constantly and will check to see whether or not the login button was pressed.
     * If it was pressed, it will change to the login world, where the user can sign in.
     */
    public void act() {
        // Checks if it was pressed
        if (Greenfoot.mouseClicked(this)) {
            //Changes the world
            Greenfoot.setWorld (new LoginWorld());              
        }
    }
}