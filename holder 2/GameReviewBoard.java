import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.*;
import java.io.FileOutputStream;


/**
 * Write a description of class GameReviewBoard here.
 * 
 * @author Jamie Lee 
 * @version 1.0
 */
public class GameReviewBoard extends World {

    ChessPiece[][] allPieces = new ChessPiece[9][8];
    List<Integer> moveList = new ArrayList<>();
    long currentTime;
    long delayTime = 0;
    int whiteTime = 600;
    int blackTime = 600;
    DisplayText whiteTimePrint = new DisplayText();
    DisplayText blackTimePrint = new DisplayText();
    String turn = "white";
    ChessPiece promotingPiece;
    ChessPiece[] promotePieces = new ChessPiece[4];
    //starts at -1 as nextMove button increase by +1
    int moveNumber = -1;
    long fileCount;
    DisplayText moveNumberOnScreen = new DisplayText();
    ChessPiece tempPiece;
    int imageBumperBlack = 0;
    int imageBumperWhite = 0;
    String username;
    
    
    //instantiating all pieces on the chess board
    QueenPiece blackQueen = new QueenPiece("blackQueen.png", "blackQueen");
    QueenPiece whiteQueen = new QueenPiece("whiteQueen.png", "whiteQueen");
    KingPiece blackKing = new KingPiece("blackKing.png", "blackKing");
    KingPiece whiteKing = new KingPiece("whiteKing.png", "whiteKing");
    
    BishopPiece blackBishopC = new BishopPiece("blackBishop.png", "blackBishop");
    BishopPiece blackBishopF = new BishopPiece("blackBishop.png", "blackBishop");
    BishopPiece whiteBishopC = new BishopPiece("whiteBishop.png", "whiteBishop");
    BishopPiece whiteBishopF = new BishopPiece("whiteBishop.png", "whiteBishop");
    RookPiece blackRookA = new RookPiece("blackRook.png", "blackRook");
    RookPiece blackRookH = new RookPiece("blackRook.png", "blackRook");
    RookPiece whiteRookA = new RookPiece("whiteRook.png", "whiteRook");
    RookPiece whiteRookH = new RookPiece("whiteRook.png", "whiteRook");
    KnightPiece blackKnightB= new KnightPiece("blackKnight.png", "blackKnight");
    KnightPiece blackKnightG= new KnightPiece("blackKnight.png", "blackKnight");
    KnightPiece whiteKnightB = new KnightPiece("whiteKnight.png", "whiteKnight");
    KnightPiece whiteKnightG = new KnightPiece("whiteKnight.png", "whiteKnight");

    PawnPiece blackPawnA = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnB = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnC = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnD = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnE = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnF = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnG = new PawnPiece("blackPawn.png", "blackPawn");
    PawnPiece blackPawnH = new PawnPiece("blackPawn.png", "blackPawn");
    
    PawnPiece whitePawnA = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnB = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnC = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnD = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnE = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnF = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnG = new PawnPiece("whitePawn.png", "whitePawn");
    PawnPiece whitePawnH = new PawnPiece("whitePawn.png", "whitePawn");
    
    
    /**
     * Constructor for objects of class GameReviewBoard. This constructor puts buttons, text, and tiles onto the screen. It will also put all chess pieces into a grid, and then put them 
     * onto the actual screen.
     * 
     * @param gameNumber the gamenumber of the saved game - used to load the correct saved game
     * @param usernameIn the username associated with the saved game - used to load the correct saved game
     * 
     */
    public GameReviewBoard(int gameNumber, String usernameIn)
    {   
        // Create a new world with 1200x800 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
                
        //sets the tiles of the chessboard
        TileSetter setter = new TileSetter();
        setBackground(setter.setTiles(Color.GRAY));
        
        
        username = usernameIn;
        //"sets" the board - not really resetting
        resetBoard();
        
        //adds all the pieces within the grid onto the screen (the grid is a 2d grid that stores all pieces)
        for (int i = 0; i < allPieces.length-1; i ++){
            for (int j = 0; j < allPieces.length-1; j ++){
                if (allPieces[j][i] != null){
                    this.addObject(allPieces[j][i], i*100 + 50, (j*100)+50);
                }
            }
        }
        
        //pulling of the text file 
        try {
            //gets the textfile of a saved game, depending on the username and game number 
            Path file = Paths.get("text\\"+username+"replay"+gameNumber+".txt");
            //counts the length of the files
            fileCount = Files.lines(file).count();
            //iterates through the file, getting all the moves into a list
            for (int i =0; i < fileCount; i+=1) {
                //gets the integer version of a String number at the i'th index
                String lineI = Files.readAllLines(Paths.get("text\\"+username+"replay"+gameNumber+".txt")).get(i);
                int theNumber = Integer.parseInt(lineI);
                //adds it to a list of moves
                moveList.add(theNumber);
            }
        }
        
        catch (Exception ex) {
            
        }
        
        //adds text created onto the screen
        this.addObject(moveNumberOnScreen, 990, 200);
        //a temporary setting of the text
        moveNumberOnScreen.setText("Move Number: 0", 50, Color.WHITE);
        
        //creates and adds button to the screen
        NextButton nextButton = new NextButton();
        this.addObject(nextButton, 1075, 550);
        
        PrevButton prevButton = new PrevButton();
        this.addObject(prevButton, 925, 550);
        
        BackButton backButton = new BackButton("profile", username);
        this.addObject(backButton, 1130, 770);
    }
    
    /**
     * This updateBoard method updates the board after taking the information from the movelist. Updates the board for both getting the next move or going back one move.
     * 
     * @param moveNumber gets the index of where the user in the "series/list of moves" (essentialy which page of a book we are in)
     * 
     */
    public void updateBoard(int moveNumber){
        //plays the move sound effect
        Greenfoot.playSound("moveSound.mp3");
        //puts the move number onto the screen
        int printNumber = (moveNumber + 2)/2;
        moveNumberOnScreen.setText("Move Number: "+printNumber, 50, Color.WHITE);
        
        //these variables are used for putting the images onto the right side after they have been taken
        imageBumperBlack = 0;
        imageBumperWhite = 0;
        
        //iterates through all the moves up until which move we are currently on
        //the code will essentially reset then re iterate until the moveNumber is reached 
        for (int i=0; i < moveNumber+1;i++) {
            //takes the element at the proper index, then it sets the proper piece location information to 5 variables
            int theMove = moveList.get(i);
            //gets the first number and sets it
            int iniRow = theMove/10000;
            //gets the second numb and sets it
            int iniCol = (theMove%10000)/1000;
            //gets the third numb and sets it
            int newRow = (theMove%1000)/100;
            //gets the fourth number and sets it
            int newCol = (theMove%100)/10;
            //this is used for special cases like en passant, castling, and queening (this is the 5th number)
            int specialCase = theMove%10;
            
            //gets the piece that was moved
            ChessPiece movedPiece = allPieces[iniRow][iniCol];
        
            //checks if en passant has happened
            if (allPieces[newRow][newCol] != null || specialCase == 5) {
                //declares the piece that was taken
                ChessPiece takenPiece = allPieces[newRow][newCol];
                //if en passant has happened
                if (specialCase == 5) {
                    //if it is a white piece, get the piece that it en passant'ed (which is below)
                    if (movedPiece.pieceName.substring(0,5).equals("white")) {
                        //delcare the piece en passant'ed
                        takenPiece = allPieces[newRow+1][newCol];
                        //empty that spot in the grid
                        allPieces[newRow+1][newCol] = null;
                        
                    }
                    //if it is a black piece, get the piece that it en passant'ed (which is above)
                    else if (movedPiece.pieceName.substring(0,5).equals("black")) {
                        //declare the piece en passant'ed
                        takenPiece = allPieces[newRow-1][newCol];
                        //empty that spot in the grid
                        allPieces[newRow-1][newCol] = null;
                    }
                }
                
                //if the piece taken is black
                if (takenPiece.pieceName.substring(0,5).equals("black")) {
                    //bring it over to the right side of the screen (where all the captured pieces are displayed)
                    takenPiece.setLocation(820+(25*imageBumperBlack), 275);
                    imageBumperBlack += 1;
                    
                }
                //if the piece taken is white
                else if (takenPiece.pieceName.substring(0,5).equals("white")) {
                    //bring it over to the right side of the screen (where all the captured pieces are displayed)
                    takenPiece.setLocation(820+(25*imageBumperWhite),350);
                    imageBumperWhite += 1;
                }
            }
            //sets the moved piece's old location as null (as it has moved out of that spot)
            allPieces[iniRow][iniCol] = null;
            //sets the new spot in the grid as the movedpiece
            allPieces[newRow][newCol] = movedPiece;
            
            //this if statement becomes true when user castles kingside
            if (specialCase == 6) {
                //if it is the black king that is castled
                if (movedPiece.pieceName.substring(0,5).equals("white")) {
                    //gets the castling rook, and empties where it was in the grid
                    ChessPiece castledRook = allPieces[7][7];
                    allPieces[7][7] = null;
                    //puts in the new spot
                    allPieces[7][5] = castledRook;
                    //sets the location of where the castled rook should be in the actual interface
                    castledRook.setLocation(550,750);
                }
                //if the user castles towards the king as black
                else if (movedPiece.pieceName.substring(0,5).equals("black")) {
                    //gets the castling rook, and empties where it was in the grid
                    ChessPiece castledRook = allPieces[0][7];
                    allPieces[0][7] = null;
                    //puts in the new spot
                    allPieces[0][5] = castledRook;
                    //sets the location of where the castled rook should be in the actual interface
                    castledRook.setLocation(550,50);
                }
            }
            //this if statement happens if the user has castled queenside
            else if (specialCase == 7) {
                //if it is the white king that is castled
                if (movedPiece.pieceName.substring(0,5).equals("white")) {
                    //gets the castling rook, and empties where it was in the grid
                    ChessPiece castledRook = allPieces[7][0];
                    allPieces[7][0] = null;
                    //puts in the new spot
                    allPieces[7][3] = castledRook;
                    //sets the location of where the castled rook should be in the actual interface
                    castledRook.setLocation(350,750);
                }
                //if it is the black king that is castled
                else if (movedPiece.pieceName.substring(0,5).equals("black")) {
                    //gets the castling rook, and empties where it was in the grid
                    ChessPiece castledRook = allPieces[0][0];
                    allPieces[0][0] = null;
                    //puts in the new spot
                    allPieces[0][3] = castledRook;
                    //sets the location of where the castled rook should be in the actual interface
                    castledRook.setLocation(350,50);
                }
            }
            
            //for promoting pawns - if pawn that is promoting was on the 1st row, it indicates that it was a white piece
            if (iniRow == 1) {
                //means that a pawn was promoted to a queen
                if (specialCase == 1) {
                    //changing the image of the pawn to a queen
                    movedPiece.setImage("whiteQueen.png"); 
                }
                //pawn promotes to a rook
                else if (specialCase == 2) {
                    movedPiece.setImage("whiteRook.png");
                }
                //pawn promotes to a bishop
                else if (specialCase == 3) {
                    movedPiece.setImage("whiteBishop.png");
                }
                //pawn promotes to a knight
                else if (specialCase == 4) {
                    movedPiece.setImage("whiteKnight.png");
                }
            }
            //for promoting pawns - if pawn that is promoting was on the 6th row, it indicates that it was a black piece
            else if (iniRow == 6) {
                //means that a pawn was promoted to a queen
                if (specialCase == 1) {
                    movedPiece.setImage("blackQueen.png"); 
                }
                //promoted to a rook
                else if (specialCase == 2) {
                    movedPiece.setImage("blackRook.png");
                }
                //promoted to a bishop
                else if (specialCase == 3) {
                    movedPiece.setImage("blackBishop.png");
                }
                //promoted to a knight
                else if (specialCase == 4) {
                    movedPiece.setImage("blackKnight.png");
                }
            }
            
            //moves the piece to the new spot on the interface
            movedPiece.setLocation(newCol*100+50,newRow*100+50);
        }
        
    }
    
    /**
     * This nextMove method first checks to make sure that we have not reached the move limit. It will then reset the board before calling the updateBoard method. 
     * It also shifts the moveNumber up by one, which then calls the updateBoard method to iterate to one more move in the replay. 
     * 
     */
    public void nextMove() {
        if (moveNumber < fileCount-1) {
            moveNumber += 1;
            resetBoard();
            updateBoard(moveNumber);            
        }
        
    }
    
    /**
     * This prevMove method first checks to make sure that we are at least at the 2nd move. It will then reset the board before calling the updateBoard method. 
     * It also shifts the moveNumber down by one, which then calls the updateBoard method to iterate to one less move in the replay. 
     * 
     */
    public void prevMove() {
        if (moveNumber > -1) {
            moveNumber -= 1;
            resetBoard();
            updateBoard(moveNumber);
        }
        
    }
    
    /**
     * This resetBoard method resets the board, before the updateBoard method iterates back to the position that the user has chosen.
     * 
     */
    public void resetBoard() {
        //resets the grid completlely, emptying every spot in 8x8 grid
        for(int i=0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                allPieces[i][j] = null;
            }
        }
        
        //setting where the white/black king/queen are
        //for this black queen, it places it in the 0th row, and then 3 column (starting at 0)
        allPieces[0][3] = blackQueen;
        allPieces[7][3] = whiteQueen;
        allPieces[0][4] = blackKing;
        allPieces[7][4] = whiteKing;
        
        //places the white and black rooks, bishops, and knights
        allPieces[0][2] = blackBishopC;
        allPieces[0][5] = blackBishopF;
        allPieces[7][2] = whiteBishopC;
        allPieces[7][5] = whiteBishopF;
        allPieces[0][0] = blackRookA;
        allPieces[0][7] = blackRookH;
        allPieces[7][0] = whiteRookA;
        allPieces[7][7] = whiteRookH;
        allPieces[0][1] = blackKnightB;
        allPieces[0][6] = blackKnightG;
        allPieces[7][1] = whiteKnightB;
        allPieces[7][6] = whiteKnightG;
        
        //places all black pawns
        allPieces[1][0] = blackPawnA;
        allPieces[1][1] = blackPawnB;
        allPieces[1][2] = blackPawnC;
        allPieces[1][3] = blackPawnD;
        allPieces[1][4] = blackPawnE; 
        allPieces[1][5] = blackPawnF;
        allPieces[1][6] = blackPawnG;
        allPieces[1][7] = blackPawnH;
        
        //places all the white pawns
        allPieces[6][0] = whitePawnA;
        allPieces[6][1] = whitePawnB;
        allPieces[6][2] = whitePawnC;
        allPieces[6][3] = whitePawnD;
        allPieces[6][4] = whitePawnE;
        allPieces[6][5] = whitePawnF;
        allPieces[6][6] = whitePawnG;
        allPieces[6][7] = whitePawnH;
        
        //places all pieces where they should be on the interface
        for(int i=0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                //checks to see if there a piece at every spoi
                if (allPieces[i][j] != null) {
                    //sets the location of that piece
                    allPieces[i][j].setLocation(j*100+50,i*100+50);
                }
            }
        }
        
        //resets all photo of all 16 black and white pawns - this is needed for fixing promotion photo changes
        for (int j = 0; j<8;j++) {
            //j is the coloumn
            allPieces[6][j].setImage("whitePawn.png");
            allPieces[1][j].setImage("blackPawn.png");
        }
        
    }
}
