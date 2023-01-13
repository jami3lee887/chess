import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class SignOutButton will check if the sign out button has been pressed, and will switch the world to the login world, where the user can log in again.
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class SignOutButton extends Buttons
{
    /**
     * This Act method is called constantly and will check to see whether or not the button was pressed.
     * If pressed, it will change the world back to the log in world
     */
    public void act() {
        // If button is pressed
        if (Greenfoot.mousePressed(this)) {
            // Change the world
            Greenfoot.setWorld(new StartScreen());
        }
    }
}
