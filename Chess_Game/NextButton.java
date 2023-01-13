import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class NextButton will check if the next button has been pressed, and will call on the nextMove method, where the next move will be played.
 * 
 * @author Jamie Lee
 * @version 1.0
 * @since 2022-1-14
 */
public class NextButton extends Buttons
{
    
    /**
     * This Act method is called constantly and will check to see whether or not the nextButton was pressed.
     * If it was pressed, it will call on the nextMove method in the GameReviewBoard world.
     */
    public void act() {
        // If button was pressed
        if (Greenfoot.mousePressed(this)) {
            // Call on next move method
            ((GameReviewBoard)getWorld()).nextMove();
        }
        
    }
}
