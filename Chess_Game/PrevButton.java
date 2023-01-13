import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class PrevButton will check if the previous button has been pressed, and will call on the prevMove method, where the previous move will be played.
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class PrevButton extends Buttons
{
    
    /**
     * This Act method is called constantly and will check to see whether or not the prevButton was pressed.
     * If it was pressed, it will call on the prevMove method in the GameReviewBoard world.
     */
    public void act() {
        // Checks to see if button was pressed
        if (Greenfoot.mousePressed(this)) {
            // Call on prevMove method
            ((GameReviewBoard)getWorld()).prevMove();
        }
        
    }
}
