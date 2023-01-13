import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class SaveToReplay3 will check if the SaveToReplay3 button has been pressed, then call on methods that will save the game to a file.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class SaveToReplay3 extends Buttons
{
    /**
     * This Act method is called constantly and will check to see whether or not the SaveToReplay3 button was pressed.
     * If it was pressed, it will call on methods to save the game to the user profile.
     */
    public void act() {
        // Check if the button was pressed and if there is not a check mark in the world already (File hasn't already been saved)
        if (Greenfoot.mousePressed(this) && (getWorld().getObjects(CheckMark.class).size() == 0)) {
            // Call on copyList method, pass the file number to save it to
            ((ChessBoard)getWorld()).copyList(3);
            // Call on method to create the check mark, pass in y value for location
            ((ChessBoard)getWorld()).createCheckMark(700);
        }
    }
}
