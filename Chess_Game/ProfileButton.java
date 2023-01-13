import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class ProfileButton will check if the profile button has been pressed, and will switch the worlds to the profile board, 
 * where the user can access their profile and previous games
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class ProfileButton extends Buttons
{
    String username;
    
    /**
     * Constructor for objects of class ProfileButton. This constructor will take in the username of the user, then set the username based on what was provided.
     * @param String usernameIn is the username of the user who is signed in
     */
    public ProfileButton(String usernameIn) {
        username = usernameIn;
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the profile button was pressed.
     * If it was pressed, it will change to the profile world, where the user can see previous games.
     */
    public void act() {
        // Checks if button was pressed
        if (Greenfoot.mousePressed(this)) {
            // Set world to ProfileWorld
            Greenfoot.setWorld(new ProfileWorld(username));
        }
    }
}
