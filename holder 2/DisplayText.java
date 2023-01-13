import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This class allows us to put text onto the screen
 * 
 * @author Michael Tuccillo
 * @version v1
 */
public class DisplayText extends Actor
{
    /**
     * This setText method will create a text image according to the given parameters
     * 
     * @param text  the text that will be printed to the screen
     * @param size  the size of the text that will be printed to the screen
     * @param colour the colour of the text that will be printed to the screen
     */
    
    public void setText(String text, int size, Color colour)
    {
        GreenfootImage image = null;
        image = new GreenfootImage(text,size,colour,null);
        this.setImage(image);
    }


}
