import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class LogoButton will check if the Logo button has been pressed, and will switch the world back to the start screen.
 * 
 * @author Karim Elsawi
 * @version 1.0
 * @since 2022-1-14
 */
public class LogoButton extends Buttons
{
    /**
     * This Act method is called constantly and will check to see whether or not the logo button was pressed.
     * If it was pressed, it will change the world back to the StartScreen world.
     */
    public void act() {
        // Check if logo was pressed
        if (Greenfoot.mouseClicked(this)) {
            // Set world to start screen
            Greenfoot.setWorld (new StartScreen());
        }
    }
}
