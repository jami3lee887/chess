import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class Game2Button will check if the game 2 button has been pressed, and will switch the worlds to the review board, where the second saved game can be accessed.
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class Game2Button extends Buttons
{
    String username;
    
    /**
     * Constructor for objects of class Game2Button. This constructor will take in the username of the user, then set the username based on what was provided.
     * @param String usernameIn is the username of the user who is signed in
     */
    public Game2Button(String usernameIn) {
        username = usernameIn;
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the game 2 button was pressed.
     * If it was pressed, it will change to the GameReviewBoard world, where the second saved game can be reviewed.
     */
    public void act() {
        // If button was pressed
        if (Greenfoot.mousePressed(this)) {
            // Change world
            Greenfoot.setWorld(new GameReviewBoard(2, username));
        }
    }
}
