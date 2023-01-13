import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Scanner;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.nio.file.*;


/**
 * The SignupWorld class allows for users to create a new account, if they do not already have one. After a valid account is created, it will then automatically log them in.
 * 
 * @author Jamie Lee / Karim Elsawi
 * @version 1.0
 */
public class SignupWorld extends World
{
    List<String> nameList = new ArrayList<>();
    List<String> passList = new ArrayList<>();
    
    Textbox user = new Textbox("user");
    Textbox pass = new Textbox("pass");

    DisplayText errorMessage = new DisplayText();
          
    /**
     * Constructor for objects of class SignupWorld. This constructor puts all text needed on the screen, as well as instantiates the buttons and text boxes that will
     * be put on the screen. 
     * 
     */
    public SignupWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        // Text and button that allows the user to change to the login world
        DisplayText loginInst = new DisplayText();
        this.addObject(loginInst, 400, 600);
        loginInst.setText("Already have an account? Login! ", 30, Color.BLACK);
        
        LoginButton login = new LoginButton();
        this.addObject(login,400,700);
        
        SubmitButton signUpSubmit = new SubmitButton();
        this.addObject(signUpSubmit,1075,250);
        
        // text prompting user to provide their desired username
        DisplayText username = new DisplayText();
        this.addObject(username, 400, 200);
        username.setText("Insert desired username here: ", 30, Color.BLACK);
        
        //text prompting user to provide their desired password
        DisplayText password = new DisplayText();
        this.addObject(password, 400, 300);
        password.setText("Insert desired password here: ", 30, Color.BLACK);
        
        // button and text prompting user to return to startscreen
        DisplayText back = new DisplayText();
        this.addObject(back, 950, 750);
        back.setText("Press the Logo to return to start ", 30, Color.BLACK);
        
        // title text
        DisplayText title = new DisplayText();
        this.addObject(title, 600, 100);
        title.setText("SIGN UP", 100, Color.BLACK);
        
        LogoButton returnStart = new LogoButton();
        this.addObject(returnStart,950,600);

        // adding the user and password text boxes
        this.addObject(user, 800,200);
        this.addObject(pass, 800,300);
        
        this.addObject(errorMessage, 800, 350);
        errorMessage.setText("", 10, Color.RED);
        
    }
    /**
     * This writeInfo method writes the user's desired username and password to a file, which saves it. It however first checks to see if either the user hasn't inputted an empty 
     * password or username, but as well as if the username isn't already taken 
     * 
     * @return either a blank string or the valid username is entered depending on if the username is valid.
     */
    public String writeInfo() {
            
            try {
                //gets the file
                Path file = Paths.get("text\\accountList.txt");
                //checks how long the file is
                long fileCount = Files.lines(file).count();
                //gets all names (which is every other line)
                for (int i =0; i < fileCount; i+=2) {
                    //gets the username at the i'th index
                    String lineI = Files.readAllLines(Paths.get("text\\accountList.txt")).get(i);
                    //adds the name to a list of all names
                    nameList.add(lineI);
                }
                //if they try to enter a blank username
                if ((user.getValue().length() == 0) || (pass.getValue().length() == 0)){
                    errorMessage.setText("Hey! You left a textbox blank", 25, Color.RED);
                    
                }
                //sees if the username they tries to enter already exists, by checking if the username is already in the list
                else if (nameList.contains(user.getValue())) {
                    errorMessage.setText("Username already exists", 25, Color.RED);
                }
                
                //if non of the conditions above are true (meaning that the username is valid), it will write 2 lines to the program.
                else  {
                    //opens the text file
                    BufferedWriter accountList = new BufferedWriter(new FileWriter("text\\accountList.txt",true));
                    //writes the username entered
                    accountList.write(user.getValue());
                    accountList.newLine();
                    //writes the password entered
                    accountList.write(pass.getValue());            
                    accountList.newLine();
                    //closes the text file
                    accountList.close();
                    
                    //returns the valid username
                    String userName = user.getValue();
                    return userName;
                }
                
            }
            //catches error
            catch(Exception ex){
                
            } 
            //if an issue occurs, return a blank string, meaning the desired username is not valid or available
            return "";
            
    }
    
    /**
     * This tabbing method will call a function within the instances of the text boxes (where the user inputs their information), that switches which text box is focused.
     * 
     */
    public void tabbing(String callingFrom) {
        //if calling from usernamebox, switch focus to password box
        if (callingFrom == "user") {
            pass.focus();
        }
        //vice versa
        if (callingFrom == "pass") {
            user.focus();
        }
        
    }
    public void cancelTab(String callingFrom) {
        if (callingFrom == "user") {
            pass.cancelBlink();
        }
        if (callingFrom == "pass") {
            user.cancelBlink();
        }
        
    }
}
