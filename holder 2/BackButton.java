import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class BackButton will check if the back button has been pressed, and will switch the worlds based on the result 
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class BackButton extends Buttons
{
    String destination;
    String username;
    
    /**
     * Constructor for objects of class BackButton. This constructor will take in the username of the user, and what world it should revert to.
     * It will then set the username and destination based on what was provided.
     * @param String whereTo is world name it will switch to
     * @param String usernameIn is the username of the user who is signed in
     */
    public BackButton(String whereTo, String usernameIn) {
        destination = whereTo;
        username = usernameIn;
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the back button was pressed.
     * If it was pressed, it will change to the previous world.
     */
    public void act() {
        // Checks if button is pressed
        if (Greenfoot.mousePressed(this)) {
            // Checks what world it is meant to change to, then changes
            if (destination.equals("gamemodeSelector")) {
                Greenfoot.setWorld(new GamemodeWorld(username));
            }
            if (destination.equals("profile")) {
                Greenfoot.setWorld(new ProfileWorld(username));
            }
        }
    }
}
