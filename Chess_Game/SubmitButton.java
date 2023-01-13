import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class SubmitButton will check if the submit button has been pressed, and will switch the world to either the sign up world, where people can create accounts,
 * or the login world, where the user can log into their existing account
 * 
 * @author Jamie Lee / Karim Elsawi
 * @version 1.0
 * @since 2022-1-14
 */
public class SubmitButton extends Buttons
{
    /**
     * This act as loop that constantly checks if the user has clicked the submit button after they have inputted their desired username and password
     */
    public void act()
    {
        if (Greenfoot.mousePressed(this)) {
            //checks which world it is being called from
            if (this.getWorld().getClass() == LoginWorld.class) {
                //checks if username is valid
                String validUsername = ((LoginWorld)getWorld()).checkInfo();
                //if username is valid, change world to the gamemode world
                if (!validUsername.equals("")) {
                    Greenfoot.setWorld (new GamemodeWorld(validUsername));
                }
            }  
            //if its in signup world
            else if (this.getWorld().getClass() == SignupWorld.class) {
                //attempts to write the name to the file
                String validCreatedUsername = ((SignupWorld)getWorld()).writeInfo();
                //if username isn't already taken, then change world to gamemode world
                if (!validCreatedUsername.equals("")) {
                    Greenfoot.setWorld (new GamemodeWorld(validCreatedUsername));
                }
            } 
    
        }
    }
}
