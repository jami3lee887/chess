import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class SaveToReplay1 will check if the SaveToReplay1 button has been pressed, then call on methods that will save the game to a file.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class SaveToReplay1 extends Buttons
{
    /**
     * This Act method is called constantly and will check to see whether or not the SaveToReplay1 button was pressed.
     * If it was pressed, it will call on methods to save the game to the user profile.
     */
    public void act() {
        // Check if the button was pressed and if there is not a check mark in the world already (File hasn't already been saved)
        if (Greenfoot.mousePressed(this) && (getWorld().getObjects(CheckMark.class).size() == 0)) {
            // Call on copyList method, pass the file number to save it to
            ((ChessBoard)getWorld()).copyList(1);
            // Call on method to create the check mark, pass in y value for location
            ((ChessBoard)getWorld()).createCheckMark(590);
        }
    }
}
