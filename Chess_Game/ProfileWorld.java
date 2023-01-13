import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The ProfileWorld is the class where the user can see their 3 saved games, as well as change the colour of chessboard tiles.
 * 
 * @author Jamie Lee / Karim Elsawi
 * @version 1.0
 */
public class ProfileWorld extends World
{
    
    Textbox r1 = new Textbox("Red value 1");
    Textbox g1 = new Textbox("Green value 1");
    Textbox b1 = new Textbox("Blue value 1");
    
    Textbox r2 = new Textbox("Red value 2");
    Textbox g2 = new Textbox("Green value 2");
    Textbox b2 = new Textbox("Blue value 2");   
    
    DisplayText errorMessage = new DisplayText();
    
    boolean good1 = false;

    /**
     * Constructor for objects of class ProfileWorld. This constructor puts all text needed on the screen, as well as instantiates the buttons and text boxes that will
     * be put on the screen.
     * 
     * 
     * @param username this string is used for linking the associated gamenumber with the account username, as each user can store 3 seperates games. 
     * 
     */
    public ProfileWorld(String username)
    {    
        // Create a new world with 1200x800 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        DisplayText title = new DisplayText();
        this.addObject(title, 600, 50);
        title.setText(username+"'s Profile", 80, Color.BLACK);
        
        DisplayText subTitle = new DisplayText();
        this.addObject(subTitle, 250, 200);
        subTitle.setText("Your Saved Games: ", 60, Color.BLACK);
        
        //creates instance of a button, adds it to a screen
        this.addObject(errorMessage,850,680);
        errorMessage.setText("", 50, Color.RED);

        DisplayText whiteTile = new DisplayText();
        this.addObject(whiteTile, 850, 250);
        whiteTile.setText("White Tile: ", 40, Color.BLACK);
        
        DisplayText blackTile = new DisplayText();
        this.addObject(blackTile, 850, 450);
        blackTile.setText("Black Tile: ", 40, Color.BLACK);
        
        Game1Button game1Button = new Game1Button(username);
        this.addObject(game1Button,250,300);
        
        Game2Button game2Button = new Game2Button(username);
        this.addObject(game2Button,250,450);
        
        Game3Button game3Button = new Game3Button(username);
        this.addObject(game3Button,250,600);
        
        BackButton backButton = new BackButton("gamemodeSelector", username);
        this.addObject(backButton, 60, 770);
        
        ColourSaverButton colorPicker = new ColourSaverButton(this);
        this.addObject(colorPicker, 850, 750);
        
        //white tile
        this.addObject(r1,850,300);
        this.addObject(g1,850,350);
        this.addObject(b1,850,400);
        
        //black tile
        this.addObject(r2,850,500);
        this.addObject(g2,850,550);
        this.addObject(b2,850,600);
        
        DisplayText tileColourText = new DisplayText();
        this.addObject(tileColourText, 900, 200);
        tileColourText.setText("Select Game Tile Colours:",60,Color.BLACK);
    }
    

    /**
    *This method converts the string RGB values for the white tiles into integers
    **/
    public String whiteTileValue(){
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;
    
        //makes sure that the information entered is only a positive integer between 0-255
        if (r1.getValue().matches("[0-9]+") && Integer.parseInt(r1.getValue()) <256) {
            //if the information is valid, sets the value for the color as the value inputted
            HiddenWorld.r1= Integer.parseInt(r1.getValue());
            //checks a boolean saying that the value is valid
            check1 = true;
        } 
        if (g1.getValue().matches("[0-9]+") && Integer.parseInt(g1.getValue()) <256) {
            HiddenWorld.g1 = Integer.parseInt(g1.getValue());
            check2 = true;
        }
        if (b1.getValue().matches("[0-9]+") && Integer.parseInt(b1.getValue()) <256) {
            HiddenWorld.b1 = Integer.parseInt(b1.getValue());
            check3 = true;
        } 
        
        //if all text boxes are valid
        if (check1 == true && check2 == true && check3 == true) {
            //clear error message
            errorMessage.setText("", 25, Color.RED);
            good1 = true;
        }
        //if any text boxes are not valid, throw error message
        else {
            errorMessage.setText("Make sure you are typing an integer between 0-255", 25, Color.RED);
        }
        
        return "";
        
    }
    /**
    *This method converts the string RGB values for the black tiles into integers
    **/
    public String blackTileValue(){
        boolean check1 = false;
        boolean check2 = false;
        boolean check3 = false;
        if (r2.getValue().matches("[0-9]+") && Integer.parseInt(r2.getValue()) <256) {
            //if the information is valid, sets the value for the color as the value inputted
            HiddenWorld.r2= Integer.parseInt(r2.getValue());
            //checks a boolean saying that the value is valid
            check1 = true;
        } 
        if (g2.getValue().matches("[0-9]+") && Integer.parseInt(g2.getValue()) <256) {
            HiddenWorld.g2 = Integer.parseInt(g2.getValue());
            check2 = true;
        }
        if (b2.getValue().matches("[0-9]+") && Integer.parseInt(b2.getValue()) <256) {
            HiddenWorld.b2 = Integer.parseInt(b2.getValue());
            check3 = true;
        } 
        
        //if all text boxes are valid
        if (check1 == true && check2 == true && check3 == true && good1 == true) {
            //clear error message
            errorMessage.setText("", 25, Color.RED);
            good1 = false;
        }
        //if any text boxes are not valid, throw error message
        else {
            errorMessage.setText("Make sure you are typing an integer between 0-255", 25, Color.RED);
        }
        return "";
        
    }
}
