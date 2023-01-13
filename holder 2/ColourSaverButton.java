import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * This button allows the user to select the RGB values of the tiles used in their chess game.
 * 
 * @author Karim Elsawi 
 * @version 1.0
 */
public class ColourSaverButton extends Buttons
{
    ProfileWorld profileworld;
    
    public ColourSaverButton(ProfileWorld pw){
        profileworld = pw;
    }
    
    /**
     * Act - do whatever the ColourSaverButton wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment.
     */
    public void act()
    {
        if (Greenfoot.mouseClicked(this)) {
            
            profileworld.whiteTileValue();
            profileworld.blackTileValue();
            
        }
    }
}
