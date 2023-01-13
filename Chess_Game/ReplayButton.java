import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class ReplayButton will check if the replay button has been pressed, and reinstantiate the ChessBoard world.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class ReplayButton extends Buttons
{  
    
    String username;
    int time;
    
    /**
     * Constructor for objects of class ReplayButton. This constructor will take in the username of the user, then set the username based on what was provided.
     * @param String usernameIn is the username of the user who is signed in
     */
    public ReplayButton(String usernameIn,int timeIn) {
        username = usernameIn;
        time = timeIn;
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the Replay button was pressed.
     * If it was pressed, it will reinstantiate the ChessBoard world.
     */
    public void act() {
        // Check if it was pressed
        if (Greenfoot.mousePressed(this)) {
            // Reinstantiate the chess board world
            ChessBoard playScreen = new ChessBoard(username,time);
            Greenfoot.setWorld(playScreen);
        }
    }
}
