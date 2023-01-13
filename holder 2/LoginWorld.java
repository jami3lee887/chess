import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.nio.file.*;



/**
 * This LoginWorld class is the log in screen to the program. It allows users to sign into an existing account, and it will
 * deny entry into the program with an non existant username / password
 * 
 * @author Jamie Lee / Karim Elsawi 
 * @version 1.0
 */
public class LoginWorld extends World
{
    //List listA = new ArrayList();
    List<String> nameList = new ArrayList<>();
    List<String> passList = new ArrayList<>();
    //creating text box instances with "identifiers"
    Textbox user = new Textbox("user");
    Textbox pass = new Textbox("pass");
    int namePos;
    DisplayText errorMessage = new DisplayText();
    
    /**
     * Constructor for objects of class LoginWorld. This constructor puts all text needed on the screen, as well as instantiates the buttons and text boxes that will
     * be put on the screen. It also pulls the accountList.txt which is the text file that stores all our log in usernames
     * 
     */
    public LoginWorld()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        
        // Text and button prompting user to register if they havent
        DisplayText signupInst = new DisplayText();
        this.addObject(signupInst, 400, 600);
        signupInst.setText("Don't have an account? Create one!", 30, Color.BLACK);
         
        SignupButton signup = new SignupButton();
        this.addObject(signup,400,700);
        
        SubmitButton logInSubmit = new SubmitButton();
        this.addObject(logInSubmit,1075,250);
        
        // text prompting user to insert their registered username
        DisplayText username = new DisplayText();
        this.addObject(username, 400, 200);
        username.setText("Insert Username here: ", 30, Color.BLACK);
        
        // text prompting user to insert their registered password
        DisplayText password = new DisplayText();
        this.addObject(password, 400, 300);
        password.setText("Insert Password here: ", 30, Color.BLACK);
         
        // button and text prompting user to return to startscreen
        DisplayText back = new DisplayText();
        this.addObject(back, 950, 750);
        back.setText("Press the Logo to return to start ", 30, Color.BLACK);
        
        // title text
        DisplayText title = new DisplayText();
        this.addObject(title, 600, 100);
        title.setText("LOGIN", 100, Color.BLACK);
        
        
        LogoButton returnStart = new LogoButton();
        this.addObject(returnStart,950,600);      
        
        this.addObject(user, 800,200);
        this.addObject(pass, 800,300);
        
        this.addObject(errorMessage, 800, 350);
        errorMessage.setText("", 10, Color.RED);
        
        
        
        try {
            // make a connection to the file
            Path file = Paths.get("text\\accountList.txt");

            //counts how long the file is
            long fileCount = Files.lines(file).count();
            
            //gets every other line (all the usernames / all the passwords)
            for (int i =0; i < fileCount; i+=2) {
                //gets the info on the i'th line
                String lineI = Files.readAllLines(Paths.get("text\\accountList.txt")).get(i);
                //adds the name to a list
                nameList.add(lineI);
            }
            for (int i =1; i < fileCount; i+=2) {
                //gets the info on the i'th line
                String lineI = Files.readAllLines(Paths.get("text\\accountList.txt")).get(i);
                //adds the password to a list of password
                passList.add(lineI);
            }

        }
        //catches any errors
        catch(Exception ex) {
            System.out.println(ex);
        }
        
    }
    /**
     * This checkInfo method will check the information inputted by the user in the textbox. It will first check to see if the username exists, and then it will check to see if the
     * password associated with that username is the password inputted. 
     * 
     * @return String nameEntered returns the valid string
     * 
     */
    public String checkInfo() {
        
        Boolean sendError = true;
        
        //gets the value inside the text boxes
        String nameEntered = user.getValue();
        String passWordEntered = pass.getValue();
        
        //checks to see if the name entered by the user already exists in the system
        if (nameList.contains(user.getValue())) {
            //gets the index of where the name is
            namePos = nameList.indexOf(user.getValue());
            //if the password at the index of where the name is matches the password associated with the account, 
            //it will return the name entered
            if (passList.get(namePos).equals(passWordEntered)) {
                sendError = false;
                return nameEntered;
    
            }
        }
        //if either the username doesn't exist or the password associated with that account does not match, it will pop up an error message on the screen
        if (sendError == true) {
            errorMessage.setText("Incorrect Login Details", 25, Color.RED);
        }
        //if the uer inputs a blank username or password, send an error message
        if ((user.getValue().length() == 0) || (pass.getValue().length() == 0)){
                
            errorMessage.setText("Oops, looks like you left a textbox blank!", 25, Color.RED);
                    
        }
        //returns a blank string if conditions above fail, this indicates an invalid password
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
