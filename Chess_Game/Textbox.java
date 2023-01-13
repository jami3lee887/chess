import greenfoot.*;

/**
 * This class is in charge of creating text boxes in order for us to input
 * data into the program
 * @author Jamie Lee/Karim Elsawi
 * @version 1.0
 */
public class Textbox extends Actor
{
    // setting private values
    private static final int MAX_INPUT_WIDTH = 20;
    private static Textbox focusedOn;
    String callingFrom = "";
    private String text;
    private String blinker;
    int timer1;
    int delay1 = 35;
    int afkTimer;
    int delay2 = 120;
    int blinkerTimer = 0;
    boolean typing = false;
    boolean runOnce = false;
    // empty textbox image
    
    /**
     * Constructor for textbox. 
     * @param type  This is where I am calling the function from. This lets
     * the user tab effectively
     */
    public Textbox(String type)
    {
        text = "";
        blinker = "";
        setImage();
        callingFrom = type;
    }
    
    // sets image to the width value determined - white box with black border
    /**
     * This method sets the image, essentially putting the letters the
     * user inputs, onto the screen
     */
    private void setImage()
    {
        //creates an image of the text on the screen
        GreenfootImage image = new GreenfootImage(15*MAX_INPUT_WIDTH, 30);
        image.setColor(Color.BLACK);
        image.fill();
        image.setColor(Color.WHITE);
        //creates a box around the text to surround text
        image.fillRect(3, 3, image.getWidth()-6, 24);
        //shows the text + a blinker
        GreenfootImage textImage = new GreenfootImage(text+blinker, 24, null, null);
        image.drawImage(textImage, (image.getWidth()-textImage.getWidth())/2, 15-textImage.getHeight()/2);
        setImage(image);
    }
  
    /**
     * This act method runs as a constant loop, used as a timer, as well as
     * for checking if the user inputs / submits text
     */
    public void act()
    {
        //timer
        afkTimer += 1;
        blinkerTimer  += 1;
        
        //Click will tell greenfoot that you have 'focusedOn' the image
        if (Greenfoot.mouseClicked(this))
        {
            //calls a function to cancel the tabbing image in the other text box
            if (this.getWorld().getClass() == LoginWorld.class) { 
                ((LoginWorld)getWorld()).cancelTab(callingFrom);
            }
            if (this.getWorld().getClass() == SignupWorld.class) {
                ((SignupWorld)getWorld()).cancelTab(callingFrom);
            }
            
            blinker = "";
            setImage();
            while(Greenfoot.getKey() != null);
            focusedOn = this;
        }
        //When Lclick is pressed it will track keys and rewrite the image
        // will keep tracking until unselected / LClick something else
        if (focusedOn == this)
        {
            //if user is afk, consider them as no longer typing
            if (afkTimer > delay2) {
                afkTimer = 0;
                typing = false;
            }
            //if user is not typing
            if (typing == false) {
                //set/starts a timer
                if (runOnce == true) {
                    blinkerTimer = 35;
                    runOnce = false;
                    setImage();
                }
            
                //blinks on and off
                if (blinkerTimer == 1) {
                    blinker = "|";
                    setImage();
                }
                else if (blinkerTimer == 35) {
                    blinker = "";
                    
                    setImage();
                }
                else if (blinkerTimer >= 70) {
                    blinkerTimer = 0;
                }
                setImage();
            }
            //if typing stop blinking
            else if (typing == true) {
                runOnce = true;
                blinker = "|";
                setImage();
            }
            //if user clicks off text box
            if (Greenfoot.mouseClicked(null) && !Greenfoot.mouseClicked(this))
            {
                focusedOn = null;
                blinker = "";
                setImage();
                return;
            }
            //gets key
            String key = Greenfoot.getKey();
            if (key == null) return;
            //functions as a backspace, and makes sure that the text less is not less than 0.
            else if ("backspace".equals(key) && text.length() > 0) {
                //deletes 1 letter
                text = text.substring(0, text.length() - 1);
                return;
            }
            //clears
            else if ("escape".equals(key)) text = "";
            //adds key to text, as long as its not over username length limit
            if (key.length() == 1 && text.length() < MAX_INPUT_WIDTH) {
                text += key;
                typing = true;
            }
            //brings user to gamemode world 
            if ("enter".equals(key)) {
                //checks which world it is being called from
                if (this.getWorld().getClass() == LoginWorld.class) {
                    //checks if username is valid
                    String validUsername = ((LoginWorld)getWorld()).checkInfo();
                    //if username works, change world
                    if (!validUsername.equals("")) {
                        Greenfoot.setWorld (new GamemodeWorld(validUsername));
                    }
                }  
                else if (this.getWorld().getClass() == SignupWorld.class) {
                    String validCreatedUsername = ((SignupWorld)getWorld()).writeInfo();
                    if (!validCreatedUsername.equals("")) {
                        Greenfoot.setWorld (new GamemodeWorld(validCreatedUsername));
                    }
                } 
            }
            
            setImage();
            //switches focus
            if ("tab".equals(key)) {
                //if focusing one one box, switch to other
                if (this.getWorld().getClass() == LoginWorld.class) {
                    blinker = "";
                    setImage();
                    ((LoginWorld)getWorld()).tabbing(callingFrom);
                }
                if (this.getWorld().getClass() == SignupWorld.class) {
                    blinker = "";
                    setImage();
                    ((SignupWorld)getWorld()).tabbing(callingFrom);
                }
            }
            
        }
        
    }
    /**
     * This method gets the value of the text
     * @return text returns the text the user has inputted
     */
    public String getValue()
    {
        return text;
    }
    /**
     * This method focuses the text to this instance of the class when called
     * on
     * 
     */
    public void focus() {
        focusedOn = this;
        blinker = "";
        setImage();
    } 
    /**
     * This method cancelBlink function cancels the blink of the other text box
     * 
     */
    public void cancelBlink() {
        blinker = "";
        setImage();
    }
    
}