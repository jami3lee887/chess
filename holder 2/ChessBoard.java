import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;

/**
 * The ChessBoard Class is a world that the chess game is played in. All the logic for the pieces, including checks, checkmates, updating the grid and turns are all written here (Michael).
 * The class will also write all of the information to a save file that the user can select after the game is complete (Jamie).
 * 
 * @author Michael Tuccillo / Jamie Lee
 * @version 1.0
 */
public class ChessBoard extends World {
    //the grid in which all pieces will be stored
    private ChessPiece[][] allPieces = new ChessPiece[8][8];
    List<HighlightBlock> highlightedBlocks =new ArrayList<HighlightBlock>();
    List<int[]> pastMoves = new ArrayList<int[]>();
    ChessPiece[] enpassantPieces = new ChessPiece[2];
    long currentTime;
    long delayTime = 0;
    int whiteTime;
    int blackTime;
    int time;
    DisplayText whiteTimePrint = new DisplayText();
    DisplayText blackTimePrint = new DisplayText();
    DisplayText winnerPrint = new DisplayText();
    String turn = "white";
    ChessPiece promotingPiece;
    ChessPiece[] promotePieces = new ChessPiece[4];
    boolean check;
    boolean fakeCheck;
    boolean test;
    boolean mate;
    boolean checkingMate;
    boolean stillInCheck;
    boolean canEnpassant;
    int[] enpassantLocation = new int[2];
    int imageBumperBlack = 0;
    int imageBumperWhite = 0;
    String winner = "nobody";
    int specialPiece;
    String username;
    boolean checkmate;
    int numOfPieces = 0;
    int blackMovesTillStalemate = 0;
    int whiteMovesTillStalemate = 0;
    boolean onlyWhiteKingLeft;
    boolean onlyBlackKingLeft;
    List<int[]> blackPastMoves = new ArrayList<int[]>();
    List<int[]> whitePastMoves = new ArrayList<int[]>();
    int moves = 0;
    boolean whiteRepeat;
    boolean blackRepeat;
    
    
    /**
     * Constructor for the class ChessBoard. This constructor sets the the tiles on which the pieces are displayed on, the pieces, the timer, and the buttons.
     * 
     * @param usernameIn this is the username of the user, and it is used for saving their game to the appropriate file (different users can save to different files)
     * @param timeIn is the time mode of the game
     */
    public ChessBoard(String usernameIn, int timeIn)
    {   
        // Create a new world with 1200x800 cells with a cell size of 1x1 pixels.
        super(1200, 800, 1); 
        whiteTime = timeIn;
        blackTime = timeIn;
        time = timeIn;
        
        TileSetter setter = new TileSetter();
        setBackground(setter.setTiles(Color.BLACK));
        
        //sets the username to the username brought in
        username = usernameIn;
        
        //instantiates the black/white king/queen
        QueenPiece blackQueen = new QueenPiece("blackQueen.png", "blackQueen");
        QueenPiece whiteQueen = new QueenPiece("whiteQueen.png", "whiteQueen");
        KingPiece blackKing = new KingPiece("blackKing.png", "blackKing");
        KingPiece whiteKing = new KingPiece("whiteKing.png", "whiteKing");
        
        //setting where the white/black king/queen are
        allPieces[0][3] = blackQueen; //for this black queen, it places it in the 0th row, and then 3 column (starting at 0)
        allPieces[7][3] = whiteQueen;
        allPieces[0][4] = blackKing;
        allPieces[7][4] = whiteKing;
        
        //instantiated the white and black rooks, bishops, and knights
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
        
        //places the instantiated white and black rooks, bishops, and knights
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
        
        //instanties white and black pawns
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
        
        //places all black pawns
        allPieces[1][0] = blackPawnA;
        allPieces[1][1] = blackPawnB;
        allPieces[1][2] = blackPawnC;
        allPieces[1][3] = blackPawnD;
        allPieces[1][4] = blackPawnE;
        allPieces[1][5] = blackPawnF;
        allPieces[1][6] = blackPawnG;
        allPieces[1][7] = blackPawnH;
        
        //places all white pawns
        allPieces[6][0] = whitePawnA;
        allPieces[6][1] = whitePawnB;
        allPieces[6][2] = whitePawnC;
        allPieces[6][3] = whitePawnD;
        allPieces[6][4] = whitePawnE;
        allPieces[6][5] = whitePawnF;
        allPieces[6][6] = whitePawnG;
        allPieces[6][7] = whitePawnH;
        
        //places all pieces where they should be on the screen
        for (int i = 0; i < 8; i ++){
            for (int j = 0; j < 8; j ++){
                if (allPieces[j][i] != null){
                    this.addObject(allPieces[j][i], i*100 + 50, (j*100)+50);
                }
            }
        }

        //these are used as images
        BlackPunchOutButton bPOButton = new BlackPunchOutButton();
        this.addObject(bPOButton, 1000, 325);
        WhitePunchOutButton wPOButton = new WhitePunchOutButton();
        this.addObject(wPOButton, 1000, 475);
        
        //creates an instance of the BackButton class, then puts in on the screen
        BackButton backButton = new BackButton("gamemodeSelector", username);
        this.addObject(backButton,870, 770);
        
        //these show black and white's timers
        this.addObject(whiteTimePrint, 1000, 475);
        //shows black and white's starting time - adds a 0 to the time as numbers like 300 and 600 only show one 0 for the "seconds" remaining 
        whiteTimePrint.setText((whiteTime/60)+":"+(whiteTime%60 +"0"), 100, Color.WHITE);
        this.addObject(blackTimePrint, 1000, 325);
        blackTimePrint.setText((blackTime/60)+":"+(blackTime%60 +"0"), 100, Color.WHITE);
        this.addObject(winnerPrint, 1000, 125);
        winnerPrint.setText("", 25, Color.WHITE);

    }
    
    /**
     * This getAllPieces method returns the 2d grid of all the pieces
     * 
     * @return ChessPiece[][] this is the grid that holds all the pieces 
     * 
     */
    public ChessPiece[][] getAllPieces(){
        return allPieces;
    }
    
    /**
     * This getTurn method gets who's turn it currently is
     * 
     * @return String this returns a string of whoever's turn it currently is
     * 
     */
    public String getTurn(){
        return turn;
    }
    
    /**
     * setHighlightBlock will insantiate and add the highlight blocks to the board.
     * @param ChessPiece[][] grid is a 2d array that holds the current location of all pieces
     * @param ChessPiece piece is the piece that is being moved
     * @param List<int[]> listOfMoves is the list of moves that need to be highlighted
     */
    public void setHighlightBlock(ChessPiece[][] grid, ChessPiece piece, List<int[]> listOfMoves){
        
        // Instantiate and add the blue highlight block that is on the piece that is being moved
        HighlightBlock highlightPiece = new HighlightBlock(piece);
        this.addObject(highlightPiece, (piece.getInitialCol()*100)+50,(piece.getInitialRow()*100)+50);
        highlightedBlocks.add(highlightPiece);
        
        // Loop through the list of moves that need to be highlighted
        for (int i = 0; i < listOfMoves.size(); i ++){
            
            // If the move is not null
            if (listOfMoves.get(i) != null){
                // Instantiate and add the highlight blocks on the possible moves
                HighlightBlock highlighterBlock = new HighlightBlock(piece, listOfMoves, i);
                this.addObject(highlighterBlock, (highlighterBlock.getCol()*100)+50,(highlighterBlock.getRow()*100)+50);
                highlightedBlocks.add(highlighterBlock);
            }
            
        }
        
    }
    
    /**
     * This removeHighlights method removes the highlighted spots currently on the screen (both red and blue)  
     */
    public void removeHighlights(){
        
        //for loop that iterates to the size of the highlighted blocks
        for (int i = 0; i < highlightedBlocks.size(); i++){
            //removes the object from the screen
            removeObject(highlightedBlocks.get(i));
        }
        
        //clears the list of the highlighted blocks
        highlightedBlocks.clear();
        
    }
    
    /**
     * This promotions method displays the 4 different pieces (rook, queen, knight, bishop) that the user can promote to, when their pawn promotes.  
     * 
     */
    public void promotions(){
        //creates a grey promotion piece, for when both white or black promotes their pawn
        BishopPiece bishopPromote = new BishopPiece("promotionBishop.png", "promotionBishop");
        KnightPiece knightPromote = new KnightPiece("promotionKnight.png", "promotionKnight");
        QueenPiece queenPromote = new QueenPiece("promotionQueen.png", "promotionQueen");
        RookPiece rookPromote = new RookPiece("promotionRook.png", "promotionRook");
        
        this.addObject(bishopPromote, 925, 75);
        this.addObject(knightPromote, 1075, 75);
        this.addObject(queenPromote, 925, 175);
        this.addObject(rookPromote, 1075, 175);
        
        //puts all promotion pieces onto a list of chess pieces
        ChessPiece[] pieces = {bishopPromote, knightPromote, queenPromote, rookPromote}; 
        //sets the promotePieces variable to pieces
        promotePieces = pieces;
    }
    /**
     * This getPromotePieces method returns the list of the 4 promotable pieces (bishops, knights, queens, rooks)
     * 
     * @return ChessPiece[] this a list of the promotion pieces
     * 
     */
    public ChessPiece[] getPromotePieces(){
        return promotePieces;
    }
    /**
     * This checkLastThree method compares the last 3 moves of both white and black and checks to see if a 3-fold repitition has occured.
     * 
     * @param pastMoves this is a list of arrays of the pastMoves (an array of 2, holding the coloumn and row)
     * @return bool returns true or false whether the last 3 moves of both white and black match up 
     */
    public boolean checkLastThree(List<int[]> pastMoves){
        
        // Ensures there has been atleast 3 moves
        if (pastMoves.size() > 4){
            
            // Create arrays that hold the past moves
            int[] pastMoveOdd = pastMoves.get(pastMoves.size()-3);
            int[] nextMoveOdd = pastMoves.get(pastMoves.size()-1);
            int[] nextNextMoveOdd = pastMoves.get(pastMoves.size()-5);
            
            // If they all equal eachother then theres repitition
            if (pastMoveOdd[0] == nextMoveOdd[0] && pastMoveOdd[1] == nextMoveOdd[1] && pastMoveOdd[0] == nextNextMoveOdd[0] && pastMoveOdd[1] == nextNextMoveOdd[1]){
                return true;
            }
            
        }

        return false;
        
    }
    
    /**
     * This updateBoard method updates the board (the pieces seen on the screen) and grid as the user plays a move.  
     * 
     * @param newCol the new column of the piece moved
     * @param newRow the new row of the piece moved
     * @param movedPiece the piece that has been moved
     * 
     */
    public void updateBoard(int newCol, int newRow, ChessPiece movedPiece){
        //plays a moving sound effect
        Greenfoot.playSound("moveSound.mp3");
        
        //calls the updateList method which updates the move happening on the board, onto a file that is used later in the replay world.
        updateList(movedPiece.getInitialRow(), movedPiece.getInitialCol(), newRow, newCol, movedPiece);
        
        // Set the initial position of the moved piece to null in the 2d array
        allPieces[movedPiece.getInitialRow()][movedPiece.getInitialCol()] = null;
        
        // Set the new position of the moved piece in the 2d array
        allPieces[newRow][newCol] = movedPiece;
        
        // Create an array that holds the move that was just made
        int[] possibleMove = new int[2];
        possibleMove[0] = newRow;
        possibleMove[1] = newCol;
        
        // Add the move that just happened to the colours respective list of moves, then check if there is 3 fold repition for that colour
        if (turn.equals("black")){
            blackPastMoves.add(possibleMove);
            blackRepeat = checkLastThree(blackPastMoves);
        }
        else {
            whitePastMoves.add(possibleMove);
            whiteRepeat = checkLastThree(whitePastMoves);
        }
        
        // If white and black both 3 fold repeated, then game is a draw
        if (whiteRepeat == true && blackRepeat == true){
            turn = "";
            winnerPrint.setText("Draw! \n Due to Repetition", 50, Color.WHITE);
        }
        
        //If there is only a king left, add a move to the stalemate counter
        if (onlyBlackKingLeft == true && turn == "black"){
            blackMovesTillStalemate += 1;
        }
        
        if (onlyWhiteKingLeft == true && turn == "white"){
            whiteMovesTillStalemate += 1;
        }
        
        // If there has been 50 moves after only having a king, the game is a draw
        if (whiteMovesTillStalemate == 50 || blackMovesTillStalemate == 50){
            winnerPrint.setText("50 Move Draw", 30, Color.WHITE);
            turn = "";
        }
        
        // If a pawn took another via enpassant
        if (ChessPiece.tookViaEnpassant == true){
            int tempAdjuster = 0;
            
            // If the piece that took the pawn is white or black
            if (movedPiece.pieceName.substring(0,5).equals("white")){
                tempAdjuster = 2;
            }
            else if (movedPiece.pieceName.substring(0,5).equals("black")){
                tempAdjuster = -2;
            }
            
            // Set the position in the 2d array of the pawn that was taken to null
            allPieces[enpassantLocation[0] + tempAdjuster][enpassantLocation[1]] = null;
            specialPiece = 5;
        }
        
        // If the king castled
        if (ChessPiece.castled == true){
            
            // If the king castled right side
            if (newCol > 4){
                allPieces[newRow][5] = allPieces[newRow][7];
                allPieces[newRow][7] = null;
                specialPiece = 6;
            }
            
            // If the king castled left side
            if (newCol < 4){
                allPieces[newRow][3] = allPieces[newRow][0];
                allPieces[newRow][0] = null;
                specialPiece = 7;
            }
            
        }
        
        // Reset variables
        ChessPiece.castled = false;
        ChessPiece.tookViaEnpassant = false;
        
        // Clear enpassant information
        enpassantPieces[0] = null;
        enpassantPieces[1] = null;
        canEnpassant = false;
        
        // If the moved piece was a pawn
        if (movedPiece.pieceName.substring(5,9).equals("Pawn")){
            
            // Checks to see if the moved pawn caused an enpassant possibility (Did it avoid an attack by moving 2 squares)
            if (Math.abs(newRow - movedPiece.getInitialRow()) == 2){
                
                // Check column to the left
                if (newCol-1 > -1){
                    
                    if (allPieces[newRow][newCol-1] != null){
                        
                        if (allPieces[newRow][newCol-1].pieceName.substring(5,9).equals("Pawn") && (!allPieces[newRow][newCol-1].pieceName.substring(0,5).equals(turn))){
                            // There is an enpassant possibility
                            canEnpassant = true;
                            // Add piece to the array of pieces that can take via enpassant
                            enpassantPieces[0] = allPieces[newRow][newCol-1];
                        }
                        
                    }
                    
                }
                
                if (newCol+1 < 8){
                    
                    // Check column to the right
                    if (allPieces[newRow][newCol+1] != null){
                        
                        if (allPieces[newRow][newCol+1].pieceName.substring(5,9).equals("Pawn") && (!allPieces[newRow][newCol+1].pieceName.substring(0,5).equals(turn))){
                            // There is an enpassant possibility
                            canEnpassant = true; 
                            // Add piece to the array of pieces that can take via enpassant
                            enpassantPieces[1] = allPieces[newRow][newCol+1];
                        }
                        
                    }
                    
                }
                
                // Set location of original position of pawn that moved
                enpassantLocation[0] = movedPiece.getInitialRow();
                enpassantLocation[1] = movedPiece.getInitialCol();
            }
            
        }
        
        movedPiece.setPawnMoved();
        movedPiece.setKingMoved();
        movedPiece.setRookHasMoved();
        
        // If piece is not in the middle of promoting then changes turns
        if (ChessPiece.promoting == false){
            
            if (turn.equals("black")){
                turn = "white";
            }
            else if (turn.equals("white")){
                turn = "black";
            }
            
        }
        
        // Check if king is in check
        movedPiece.setChecking(true);
        check = checkForCheck(allPieces);
        
        // If king is in check, check for checkmate
        if (check == true && checkingMate == false){
            checkingMate = true;
            movedPiece.setTestList();
            
            // Call on method that checks for mate
            checkmate = checkForMate(allPieces);
            
            // If its checkmate then end the game
            if (checkmate == true){
                
                if (turn.equals("white")){
                    winnerPrint.setText("Black Wins!\n Checkmate", 50, Color.WHITE);
                    turn = "";
                }
                else if (turn.equals("black")){
                    winnerPrint.setText("White Wins!\n Checkmate", 50, Color.WHITE);
                    turn = "";
                }
                
            }
            
        }
        
        // If there is no checkmate then check for stalemate
        if (checkmate == false && check == false && turn != ""){
            
            // If there is stalemate, then end game
            if (checkForStalemate(allPieces) == true){
                turn = "";
                winnerPrint.setText("Draw! \n Stalemate", 50, Color.WHITE);
            } 
            
        }
        
        // If there was more then 1 piece left
        if (onlyBlackKingLeft == false || onlyWhiteKingLeft == false){
            
            // Loop through the 2d array
            for(int i = 0; i < 8; i++) {
                for(int j = 0; j < 8; j++) {
                    
                    // Once it hits a piece of the same colour, increase the number of pieces on the board
                    if (allPieces[i][j] != null){
                        
                        if (allPieces[i][j].pieceName.substring(0, 5).equals(turn)){
                            numOfPieces += 1;
                        }
                        
                    }
                    
                }
            }
            
            // If there is only 1 piece, set variable to true that starts 50 move draw count
            if (numOfPieces == 1){
                
                if (turn == "black"){
                    onlyBlackKingLeft = true;
                }
                
                if (turn == "white"){
                    onlyWhiteKingLeft = true;
                }
                
            }
            
            numOfPieces = 0;
        }
        
    }
    
    /**
     * This getCheck method returns true or false whether the king is in check
     * 
     * @return boolean this gets if the king is checked
     * 
     */
    public boolean getCheck(){
        return check;
    }
    /**
     * This getEnpassant method returns true or false whether a pawn can take a pawn en passant
     * 
     * @return boolean this returns whether a pawn can take en passant 
     * 
     */
    public boolean getEnpassant(){
        return canEnpassant;
    }
    
    /**
     * This getEnpassantLocation method returns an array (of size 2, for the row and column), of where the en passant'ed piece is happening
     * 
     * @return int[] the array of where the en passant occurs
     * 
     */
    public int[] getEnpassantLocation(){
        return enpassantLocation;
    }
    
    /**
     * This checkUpdateBoard method checks for checks on a "pseudo" grid
     * 
     * @return int[] the array of where the en passant occurs
     * 
     */
    public void checkUpdateBoard(int newCol, int newRow, ChessPiece movedPiece){
        //creating a new temporary grid
        ChessPiece[][] tempGrid = new ChessPiece[8][8];
        for(int i=0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                //placing all pieces inside the tempgrid
                tempGrid[i][j] = getAllPieces()[i][j];
            }
        }
        //sets the initial of the moved piece as null
        tempGrid[movedPiece.getInitialRow()][movedPiece.getInitialCol()] = null;
        //sets the position of the moved piece
        tempGrid[newRow][newCol] = movedPiece;
        //sets checking to true
        movedPiece.setChecking(true);
        //calls the check for check method (sees if the piece is still in check)
        fakeCheck = checkForCheck(tempGrid);
    }
    
    /**
     * This getFakeCheck method returns true of false whether a fake check has occured in the "pseudo grid" (the grid that checks for checks)
     * 
     * @return boolean this returns whether a fake check has occured
     * 
     */
    public boolean getFakeCheck(){
        return fakeCheck;
    }
    /**
     * This checkForStalement method returns true or false whether a player has no legal moves
     * 
     * @return boolean this returns whether a player has no legal moves 
     * 
     */
    public boolean checkForStalemate(ChessPiece[][] grid){
        // Loop through 2d array
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                
                // If there is a piece at that location
                if (grid[i][j] != null){
                    
                    // If the piece matches whos turn it is
                    if (grid[i][j].pieceName.substring(0, 5).equals(turn)){
                        ChessPiece.checking = true;
                        // Call on method that gets all possible moves
                        test(i,j,grid);
                        
                        // Calls on method that sees if there are possible moves, if it returns true, then theres no stalemate
                        if (grid[i][j].isThereMoves(grid[i][j]) == true){
                            ChessPiece.checking = false;
                            return false;
                        }
                        
                    }
                    
                }
                
            }
        } 
        
        ChessPiece.checking = false;
        return true;
    }
    /**
     * This checkForCheck method checks if the king is in check and returns true or false whether it is or it is not
     * 
     * @return boolean this returns whether the king is in check
     * 
     */
    public boolean checkForCheck(ChessPiece[][] grid){
        // Loop through the 2d array
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                
                // If there is a piece at that location
                if (grid[i][j] != null){
                    
                    // If it matches whos turn it is
                    if (!grid[i][j].pieceName.substring(0, 5).equals(turn)){
                        // Call on method that gets all possible moves
                        test(i,j,grid);
                        
                        // Call on method that will see if any of those possible moves cause check, if it returns true, then king is in check
                        if (grid[i][j].isCheck(grid, "check", grid[i][j]) == true){
                            ChessPiece.checking = false;
                            return true;
                        }
                        
                    }
                    
                }
                
            }
        }
        
        ChessPiece.checking = false;
        return false;
    }
    
    /**
     * This test method gets all the possible moves of the piece that is passed in
     * 
     * @param row this is the row of where the piece is currently 
     * @param col this is the coloumn of where the piece is currently
     * @param grid this is the grid of all the pieces
     * 
     */
    public void test(int row, int col, ChessPiece[][] grid){
        // Set initial position of the piece 
        grid[row][col].setInitialPos(grid, grid[row][col]);
        
        // Reset move holding lists
        grid[row][col].setList();
        grid[row][col].setTestList();
        
        // Check if piece is a queen
        if (grid[row][col].pieceName.length()>9){
            
            if (grid[row][col].pieceName.substring(5, 10).equals("Queen")){
                // Get all possible moves of this queen
                grid[row][col].setDiags(grid, grid[row][col]);
                grid[row][col].setUpDown(grid, grid[row][col]);
            }
            
        }
        
        // Check if piece name is greater than 10 characters
        if (grid[row][col].pieceName.length()>10){
            
            if (grid[row][col].pieceName.substring(5, 11).equals("Bishop")){
                // Get all possible moves of this bishop
                grid[row][col].setDiags(grid, grid[row][col]);
            }
            
            if (grid[row][col].pieceName.substring(5, 11).equals("Knight")){
                // Get all possible moves of this knight
                grid[row][col].setKnight(grid, grid[row][col]);
            }
            
        }
        
        // Check if piece is rook
        if (grid[row][col].pieceName.substring(5, 9).equals("Rook")){
            // Get all possible moves of this rook
            grid[row][col].setUpDown(grid, grid[row][col]);
        }
        
        // Check if piece is a pawn
        if (grid[row][col].pieceName.substring(5, 9).equals("Pawn")){
            // Get all possible moves of this pawn
            grid[row][col].setPawn(grid, grid[row][col]);
        }
        
        // Check if piece is a king
        if (grid[row][col].pieceName.substring(5, 9).equals("King")){
            // Get all possible moves of this king
            grid[row][col].kingsMove(grid, grid[row][col]);
        }
        
    }
    /**
     * This checkForStalement method checks for all the possible moves for either white or black and checks if those moves can cancel the check or not. 
     * If not, this means that it is checkmate
     * 
     * @param grid this is the 2d grid that contains all the pieces
     * @return boolean this returns whether it is checkmate or not
     * 
     */
    public boolean checkForMate(ChessPiece[][] grid){
        // Loop through 2d array
        for(int i = 0; i < 8; i++) {
            for(int j = 0; j < 8; j++) {
                
                // If theres a piece at this location
                if (grid[i][j] != null){
                    
                    // If it matches the turn
                    if (grid[i][j].pieceName.substring(0, 5).equals(turn)){
                        ChessPiece.checking = true;
                        // Get all possible moves of this piece
                        test(i,j, grid);
                        
                        // Call on method that sees any of those possible moves cancel the check 
                        stillInCheck = grid[i][j].isCheck(grid, "mate", grid[i][j]);
                        
                        // If they do cancel the check, then its not mate
                        if (stillInCheck == false){
                            ChessPiece.checking = false;
                            checkingMate = false;
                            return false;
                        }
                        
                    }
                    
                    // Reset list
                    grid[i][j].setList();
                }
                
            }
        }
        
        ChessPiece.checking = false;
        checkingMate = false;
        return true;
    }
    
    /**
     * This checkForCheck method gets if the king is checkmated
     * 
     * @return boolean this returns whether the king is checkmated
     * 
     */
    public boolean getCheckingMate(){
        return checkingMate;
    }
    
    /**
     * This decPromoted method declares the type of piece that has been promoted by the user. This is used for replays, as the type of piece promoted is required for the replays
     * to know what kind of piece has been promoted.
     * 
     * @param pieceType this the type of piece that has been promoted 
     * 
     */
    public void decPromoted(String pieceType) {
        //if the piece promoted is declared as a queen
        if (pieceType.equals("Queen")) {
            //sets the specialPiece variable as 1, representing a queen that has been promoted
            specialPiece = 1;
        }
        else if (pieceType.equals("Rook")) {
            //sets the specialPiece variable as 2, representing a rook that has been promoted
            specialPiece = 2;
        }
        else if (pieceType.equals("Bishop")) {
            //sets the specialPiece variable as 3, representing a bishop that has been promoted
            specialPiece = 3;
        }
        else if (pieceType.equals("Knight")) {
            //sets the specialPiece variable as 4, representing a knight that has been promoted
            specialPiece = 4;
        }
    } 
    /**
     * This setPromotingPiece sets the promoted pawn to the desired promoted piece
     * 
     * @param piece this is the piece that is being promoted to
     * 
     */
    public void setPromotingPiece(ChessPiece piece){
        for (int i = 1; i < 3;i++){
            for (int j = 0; j < 8;j++){
                
                // Checks if there is a piece at the back ranks
                if (allPieces[(i*i*i)-1][j] != null){
                    
                    // If the piece at the back rank is a pawn, then remove the object and replace it with the chosen promotion piece
                    if ("Pawn".equals(allPieces[(i*i*i)-1][j].pieceName.substring(5, 9))){
                        removeObject(allPieces[(i*i*i)-1][j]);
                        allPieces[(i*i*i)-1][j] = piece;
                        piece.setLocation(j*100+50, ((i*i*i)-1) *100+50);
                    }
                    
                }
                
            }  
        }
        
        // Change turns
        if (turn.equals("black")){
            turn = "white";
        }
        else if (turn.equals("white")){
            turn = "black";
        }
        
        piece.setChecking(true);
        // Check to see if other colour is in check
        check = checkForCheck(allPieces);
        
        // Check for mate
        if (check == true && checkingMate == false){
            checkingMate = true;
            System.out.println(checkForMate(allPieces));
        }
        
        ChessPiece.promoting = false;
    }
    
    /**
     * This updateList method updates the list of moves to later be used in the replay feature.
     * 
     * @param iniRow the initial row of the piece moved
     * @param iniCol the inital coloumn of the piece moved
     * @param newRow the new row of the piece moved
     * @param newCol the new coloumn of the piece moved
     * @param movedPiece the piece that has been moved
     * 
     */    
    public void updateList(int iniRow, int iniCol, int newRow, int newCol, ChessPiece movedPiece) {
        //creates an array 5 big;
        int[] positionSet = new int[5];
        //setting what is inside the array
        positionSet[0] = iniRow; 
        positionSet[1] = iniCol;
        positionSet[2] = newRow;
        positionSet[3] = newCol;
        //reserved for a special move, 0 meaning nothing special
        positionSet[4] = 0;
        
        //adding that array holding movement information to a list of all the past moves
        pastMoves.add(positionSet);
        
        //a foor loop that checks if a special move has happened
        for (int u = 1; u<8; u++) {
            //if if the special piece number matches the index of the for loop
            if (specialPiece == u) {
                //creates a temporary array to store the movement information of the previous move
                int[] tempPositionSet = new int[5];
                //iterates though the array to to take the number that is 2 behind in the pastMoves list
                for (int i=0; i<5;i++){
                    //sets the temporary array with those values
                    tempPositionSet[i] = pastMoves.get(pastMoves.size()-2)[i];
                    //puts the specialcase number as the last number
                    tempPositionSet[4] = u;
                }
                //removes the past move that has been "edited"
                pastMoves.remove(pastMoves.size()-2);
                //puts in the new "edited" past move
                pastMoves.add(pastMoves.size()-1, tempPositionSet);
                //resets the specialPiece variable, meaning nothing special has happened (no castling, no en passant, no promotion);
                specialPiece = 0;
            }
        }
        
        //try / catch for opening files
        try {
            //creates 3 files for the user logged in, to be used in the replay feature
            File file1 = new File("text\\"+username+"replay1.txt");
            file1.createNewFile(); // if file already exists will do nothing 
            File file2 = new File("text\\"+username+"replay2.txt");
            file2.createNewFile(); // if file already exists will do nothing 
            File file3 = new File("text\\"+username+"replay3.txt");
            file3.createNewFile(); // if file already exists will do nothing 
            
            //opens the text file that holds the current game's move order
            BufferedWriter moveList = new BufferedWriter(new FileWriter("text\\allGamesReplay.txt"));
                    
            for(int i = 0; i<pastMoves.size(); i++) {
                for (int j = 0; j < positionSet.length; j++) {
                    //writes the current game's move order to a text file
                    String tempString = Integer.toString(pastMoves.get(i)[j]);
                    moveList.write(tempString);     
                }
                moveList.newLine();
            }
            //closes the text file
            moveList.close();
        }
        //will catch an error thrown if the file cannot be found
        catch(IOException ex){
            System.out.println("file not found!");
        }
    }
    
    /**
     * This copyList method is used to copy the current game onto the user's desired game save file. This is used for replays.
     * 
     * @param numberToSave this integer is the file number of which the game will save to.
     * 
     */
    public void copyList(int numberToSave) {
        //try / catch for file opening
        try {
            //the current game's move order file
            FileInputStream oldFile = new FileInputStream("text\\allGamesReplay.txt");
            //the new file in which the game's move order will be copied to
            FileOutputStream newFile = new FileOutputStream("text\\"+username+"replay"+numberToSave+".txt");
            int i;
            //iterates through old file and writes it to the new file
            while ((i=oldFile.read())!=-1) {
                newFile.write((char)i);
            }
        }
        catch(IOException ex){
            System.out.println("file not found!!!");
        }
    }
    
    /**
     * This createCheckMark method creates a checkmark to help indicate which file the user has saved to
     * 
     */
    public void createCheckMark(int yLoc){
        //creates a checkmark
        CheckMark check = new CheckMark();
        this.addObject(check, 825, yLoc);
    }

    /**
     * This act method runs as a loop that controls the function of ours timers, as well as for checking for the end state of the game.
     * 
     */
    public void act() {
        //gets the current time
        currentTime = System.currentTimeMillis();
        //a timer of 1 second, and there is not a winner yet
        if (currentTime > delayTime + 1000 && winner.equals("nobody")) {
            //resets delay
            delayTime = System.currentTimeMillis();
            //if it is white's turn to move, white's time will lower by 1 every second
            if (turn.equals("white")) {
                whiteTime -= 1;
            }
            //if it is black's turn to move, black's time will lower by 1 every second
            else if (turn.equals("black")) {
                blackTime -= 1;
            }
        }
        //sets white's time
        if ((whiteTime%60) >= 10) {
            whiteTimePrint.setText((whiteTime/60)+":"+(whiteTime%60), 100, Color.WHITE);
        }
        //sets white's time but adds an extra 0 to the seconds section
        else {
            whiteTimePrint.setText((whiteTime/60)+":0"+(whiteTime%60), 100, Color.WHITE);
        }
        
        //sets black's time
        if ((blackTime%60) >= 10) {
            blackTimePrint.setText((blackTime/60)+":"+(blackTime%60), 100, Color.WHITE);
        }
        //sets black's time but adds an extra 0 to the seconds section
        else {
            blackTimePrint.setText((blackTime/60)+":0"+(blackTime%60), 100, Color.WHITE);
        }
        
        //if white runs out of time
        if (whiteTime == 0) {
            //declares black as winner
            winner = "black";
            winnerPrint.setText("Black wins! \n White ran out of time", 50, Color.WHITE);
            //sets the turn variable to none so that the user cannot make anymore moves
            turn = "";
        }
        //if black runs out of time
        else if (blackTime == 0) {
            //declares white as the winner
            winner = "white";
            winnerPrint.setText("White wins! \n Black ran out of time", 50, Color.WHITE);
            //sets the turn variable to none so that the user cannot make anymore moves
            turn = "";
        }
        //when the game is over
        if (turn == ""){
            //pops up the replay / saving options for the game
            ReplayButton replayBut = new ReplayButton(username,time);
            this.addObject(replayBut, 1060, 770);
            SaveToReplay1 replay1 = new SaveToReplay1();
            this.addObject(replay1, 1000, 590);
            SaveToReplay2 replay2 = new SaveToReplay2();
            this.addObject(replay2, 1000, 645);
            SaveToReplay3 replay3 = new SaveToReplay3();
            this.addObject(replay3, 1000, 700);
            removeHighlights();
            turn = "gameOver";
        }
    }
   
}
