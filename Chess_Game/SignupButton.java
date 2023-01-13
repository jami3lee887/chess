import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class SignupButton will check if the sign up button has been pressed, and will switch the world to the sign up world, where people can create accounts
 * 
 * @author Karim Elsawi
 * @version 1.0
 * @since 2022-1-14
 */
public class SignupButton extends Buttons
{
    /**
     * This Act method is called constantly and will check to see whether or not the sign up button was pressed.
     * If it was pressed, it will change to the sign up world, where the user can sign in.
     */
    public void act(){
        // Checks if button was pressed
        if (Greenfoot.mouseClicked(this)) {
            // Change the worlds
            Greenfoot.setWorld (new SignupWorld());
        }
    }
}
