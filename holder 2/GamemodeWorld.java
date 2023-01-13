import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The GamemodeWorld class is the screen in the program that allows users to select their gamemode of preference.
 * 
 * @author Karim Elsawi 
 * @version 1.0
 */
public class GamemodeWorld extends World
{

    /**
     * Constructor for objects of class GamemodeWorld. This constructor puts all text needed on the screen, as well as instantiates the buttons that will be put on the screen. 
     * 
     * @param userNameIn this string is used for linking the associated account username with their game / profile.
     * 
     */
    public GamemodeWorld(String userNameIn)
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 

        
        TileSetter setter = new TileSetter();
        setBackground(setter.setTiles(Color.WHITE));
                
        DisplayText title = new DisplayText();
        this.addObject(title, 400, 100);
        title.setText("Gamemode Selector", 80, Color.WHITE);
        
        DisplayText userNameDisplay = new DisplayText();
        this.addObject(userNameDisplay, 400, 250);
        userNameDisplay.setText("Welcome "+userNameIn+"!", 30, Color.WHITE);
        
        BlitzSelector blitz = new BlitzSelector(userNameIn);
        this.addObject(blitz,200,450);
        
        Blitz2Selector blitz2 = new Blitz2Selector(userNameIn);
        this.addObject(blitz2,200,650);
        
        BulletSelector bullet = new BulletSelector(userNameIn);
        this.addObject(bullet,600,450);
        
        RapidSelector rapid = new RapidSelector(userNameIn);
        this.addObject(rapid,600,650);
        
        
        ProfileButton profileButton = new ProfileButton(userNameIn);
        this.addObject(profileButton,1000,150);
        
        SignOutButton signOutButton = new SignOutButton();
        this.addObject(signOutButton, 1000, 650);
        
    }
}
