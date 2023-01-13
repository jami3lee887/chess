import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The StartScreen class is the start screen to the program. It allows users see an interface that will provide them with the options to either a)sign up or b)log in. 
 * @author Karim Elsawi
 * @version 1.0
 */
public class StartScreen extends World
{

    /**
     * Constructor for objects of class UserInterface.
     * 
     */
    public StartScreen()
    {    
        // Create a new world with 1200x800 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        //sets background to tiles
        TileSetter setter = new TileSetter();
        setBackground(setter.setTiles(Color.BLACK));
        
        LoginButton login = new LoginButton();
        this.addObject(login,400,400);
        
        SignupButton signup = new SignupButton();
        this.addObject(signup,400,600);
        
        LogoButton logo = new LogoButton();
        this.addObject(logo,1000,400);
        
        DisplayText title = new DisplayText();
        this.addObject(title, 400, 100);
        title.setText("Queen's Gambit Presents:", 70, Color.WHITE);
        
        DisplayText subTitle = new DisplayText();
        this.addObject(subTitle, 400, 175);
        subTitle.setText("A Chess Game", 100, Color.WHITE);
        
        
        DisplayText defaultInst = new DisplayText();
        this.addObject(defaultInst, 400, 300);
        defaultInst.setText("Press the following buttons to ", 40, Color.WHITE);

        DisplayText loginInst = new DisplayText();
        this.addObject(loginInst, 150, 400);
        loginInst.setText("Log into your account ", 30, Color.WHITE);
                
        DisplayText signupInst = new DisplayText();
        this.addObject(signupInst, 150, 600);
        signupInst.setText("Sign up for an account ", 30, Color.WHITE);
    }

}
