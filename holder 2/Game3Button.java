import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class Game3Button will check if the game 3 button has been pressed, and will switch the worlds to the review board, where the third saved game can be accessed.
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class Game3Button extends Buttons
{
    String username;
    
    /**
     * Constructor for objects of class Game3Button. This constructor will take in the username of the user, then set the username based on what was provided.
     * @param String usernameIn is the username of the user who is signed in
     */
    public Game3Button(String usernameIn) {
        username = usernameIn;
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the game 3 button was pressed.
     * If it was pressed, it will change to the GameReviewBoard world, where the third saved game can be reviewed.
     */
    public void act() {
        // Checks if button is pressed
        if (Greenfoot.mousePressed(this)) {
            //Change worlds
            Greenfoot.setWorld(new GameReviewBoard(3, username));
        }
    }
}
