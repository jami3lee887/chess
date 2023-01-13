
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

/**
 * The class ChessPiece holds the methods that will get all the possible moves for the pieces that were clicked, as well as set the position 
 * of the piece after the move was made.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class ChessPiece extends Actor
{
    // Starting pos of piece
    int initialRow;
    int initialCol;
    
    // Variables that control when to stop checking specific moves
    boolean endRightCount;
    boolean endLeftCount;
    boolean endUpCount;
    boolean endDownCount;
    boolean endTopRightDiag;
    boolean endTopLeftDiag;
    boolean endBottomRightDiag;
    boolean endBottomLeftDiag;
    boolean pawnHasMoved;
    boolean checked;
    boolean check;
    
    // Variables that hold if the user in the middle of promoting or checking for checks.
    static protected boolean promoting;
    static boolean checking;
    public ChessPiece [] promotionPieces = new ChessPiece[4];
    String lastPiece;
    boolean attackingPawn;
    ChessPiece attackedPawn;
    ChessPiece attackingPawnPiece;
    static boolean tookViaEnpassant;
    boolean canCastleRight;
    boolean canCastleLeft;
    static boolean castled;
    boolean kingHasMoved;
    boolean rookHasMoved;
    String pieceName;
    
    // Lists that hold variations of possible moves
    List<int[]> list=new ArrayList<int[]>();  
    List<int[]> test=new ArrayList<int[]>(); 
    List<int[]> realMoves =new ArrayList<int[]>(); 
    List<ChessPiece> enpassantPieces =new ArrayList<ChessPiece>(); 
    
    /**
     * Constructor for objects of class ChessPiece. This constructor will take in the name and image of the piece, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public ChessPiece(String imageIn, String pieceName){
        this.setImage(imageIn);
        setPieceName(pieceName);
    }
    
    /**
     * This method will take the provided piece name, and set the name of the piece to that name.
     * @param String pieceNameIn is the name of the piece
     */
    public void setPieceName(String pieceNameIn){
        this.pieceName = pieceNameIn;
    }
    
    /** 
     * This method will take the piece provided piece, and parse through the grid of pieces until it finds itself.
     * It will then set its location holding variables to this location.
     * @param ChessPiece[][] grid is the 2d array that holds the location of all the pieces
     * @param ChessPiece piece is the current piece it is looking for
     */
    public void setInitialPos(ChessPiece[][] grid, ChessPiece piece){
        // For loops that will go through every index of the grid
        for (int i = 0; i < 8;i++){
                for (int j = 0; j < 8;j++){
    
                    // Once the piece is found in the grid
                    if (piece == grid[i][j]){
                        // Set row
                        this.initialRow = i;
                        // Set col
                        this.initialCol = j;
                    }
                    
                }
        }
    }
    
    /**
     * This method will take the piece that was clicked, and determine which type of piece it is.
     * It will then call on methods that will get the legal moves for that specific piece.
     * @param ChessPiece[][] grid is the 2d array that holds the location of all the pieces
     * @param ChessPiece clickedPiece is the piece that was clicked
     */
    public void getLegalMoves(ChessPiece[][] grid, ChessPiece clickedPiece){
        
        // If the piece that was presed matches whos turn it is, and the user is not in the middle of promoting
        if (clickedPiece.pieceName.substring(0, 5).equals(((ChessBoard)getWorld()).getTurn()) && promoting == false){
            
            // Removes all existing highlights and clear the lists holding move info
            ((ChessBoard)getWorld()).removeHighlights();
            list.clear();
            realMoves.clear();
            //Set the initial pos of the piece
            setInitialPos(grid, clickedPiece);
            canCastleRight = false;
            canCastleLeft = false;
            
            // Checks to see if clicked piece is a rook
            if ((clickedPiece.pieceName).equals("blackRook") || (clickedPiece.pieceName).equals("whiteRook")){
                setUpDown(grid, clickedPiece);
            }
            
            // Checks to see if clicked piece is a king
            if ((clickedPiece.pieceName).equals("blackKing") || (clickedPiece.pieceName).equals("whiteKing")){
                kingsMove(grid, clickedPiece);
            }
            
            // Checks to see if clicked piece is a bishop
            if ((clickedPiece.pieceName).equals("blackBishop") || (clickedPiece.pieceName).equals("whiteBishop")){
                setDiags(grid, clickedPiece);
            }
            
            // Checks to see if clicked piece is a queen
            if ((clickedPiece.pieceName).equals("blackQueen") || (clickedPiece.pieceName).equals("whiteQueen")){
                setDiags(grid, clickedPiece);
                setUpDown(grid, clickedPiece);
            }
            
            // Checks to see if clicked piece is a knight
            if ((clickedPiece.pieceName).equals("blackKnight") || (clickedPiece.pieceName).equals("whiteKnight")){
                setKnight(grid, clickedPiece);
            }
            
            // Checks to see if clicked piece is a pawn
            if ((clickedPiece.pieceName).equals("blackPawn") || (clickedPiece.pieceName).equals("whitePawn")){
                setPawn(grid, clickedPiece);
            }
            
            // Call on methid that will set highlights on the board
            ((ChessBoard)getWorld()).setHighlightBlock(grid,clickedPiece, realMoves);
        }
    }
    
    /**
     * The method setPawnMoved will set the status of the piece to moved, indicating that it has moved
     */
    public void setPawnMoved(){
        this.pawnHasMoved = true;
    }
    
    /**
     * The method setKingMoved will set the status of the piece to moved, indicating that it has moved
     */
    public void setKingMoved(){
        this.kingHasMoved = true;
    }
    
    /**
     * The method setRookHasMoved will set the status of the piece to moved, indicating that it has moved
     */
    public void setRookHasMoved(){
        this.rookHasMoved = true;
    }
    
    /**
     * The method addToListKingKnight will take in the possible move for the knight or king, and add it to the list of possible moves
     * @param int[] possibleMove is an integer array that holds the column and row of the possible move
     */
    public void addToListKingKnight(int[] possibleMove){
        
        // If the game is not checking for mate or check, and the possible move is not empty
        if (checking == false && ((ChessBoard)getWorld()).getCheckingMate() == false && possibleMove != null){
            // Add it to the move list
            realMoves.add(possibleMove);
            test.add(possibleMove);
        }
    }
    
    /** 
     * The method castling is responsible for checking if the king can castle to either side.
     * It will ensure that all the requirements are met, then set a variable indicating that the king can castle.
     * @param ChessPiece grid is a 2d array that holds the current location of the all the pieces
     * @param ChessPiece clickedPiece is the king that was clicked
     */
    public void castling(ChessPiece[][] grid, ChessPiece clickedPiece){
        
        canCastleRight = true;
        canCastleLeft = true;
        
        // For loop that will check either side of the king until the rook
        for (int i = 1; i < 4; i++){
            
            // True until it reaches rook on right side
            if (initialCol + i < 7){
                
                // If any of the spaces between the rook and king are not empty, then the king cannot castle to the right
                if (grid[this.initialRow][this.initialCol + i] != null){
                    canCastleRight = false;
                }
                
            }
            
            // True until it reaches the rook on left side
            if (initialCol - i > 0){
                
                // If any of the spaces between the rook and king are not empty, then the king cannot castle to the left
                if (grid[this.initialRow][this.initialCol - i] != null){
                    canCastleLeft = false;
                }
                
            }
        }
        
        // If the default right rook location is not empty
        if (grid[this.initialRow][7] != null){
            
            // Can castle right is true if there is no piece between rook and king, rook has moved == false ensures that the piece is a rook in the right corner
            if (canCastleRight == true && grid[this.initialRow][7].rookHasMoved == false){
                
                // Checks if castling location is not being attacked by piece. Will return null if it is
                int[] possibleMove = getPossibleMoves(0,2,clickedPiece);
                // Checks space to right of king, it it is being attacked by a piece, getPossibleMoves will return null, meaning if it were to move there, it would be in check
                int[] checkSpaceToRight = getPossibleMoves(0,1,clickedPiece);
                
                // If the space to the right, and 2 to the right did not receive a null value (that square is not being attacked) 
                if (checkSpaceToRight != null && possibleMove != null){
                    // Add castle move to possible move list
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove); 
                }
                // If the square to the right or 2 to the right is being attacked, king cant castle
                else {
                    canCastleRight = false;
                }
                
            }
            // If the rook has already moved, king cant castle
            else {
                canCastleRight = false;
            }
        }
        // If rook location is null, cant castle
        else {
            canCastleRight = false;
        }
        
        // Same thing as above, but for the left side castling
        if (grid[this.initialRow][0] != null){
            
            if (canCastleLeft == true && grid[this.initialRow][0].rookHasMoved == false){
                int[] possibleMove = getPossibleMoves(0,-2,clickedPiece);
                int[] checkSpaceToLeft = getPossibleMoves(0,-1,clickedPiece);
                
                if (checkSpaceToLeft != null && possibleMove != null){
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove); 
                }
                else {
                    canCastleLeft = false;
                }
                
            }
            else {
                canCastleLeft = false;
            }
            
        }
        else {
                canCastleLeft = false;
            }
    }
    
    /**
     * The method KingsMove will check the kings move set and check if the move is a legal move or not. It will ensure there is not its own piece in the move location, or the move 
     * will put the king off the board.
     * @param ChessPiece[][] grid is a 2d array that holds the location of the pieces
     * @param ChessPiece clickedPiece is the piece that was clicked
     */
    public void kingsMove(ChessPiece[][] grid, ChessPiece clickedPiece){
        
        // If the game is not already checking for check and the king is not currently in check
        if (checking == false && ((ChessBoard)getWorld()).getCheck() == false){
            
            // If the king has not moved
            if (clickedPiece.kingHasMoved == false){
                // Call on castling
                castling(grid, clickedPiece);
            }
            
        }
        
        // Checks to see if the row above king is not off the board
        if (initialRow-1 > -1){
            
            // Call on method that ensures moving 1 up will not cause check
            int[] possibleMove = getPossibleMoves(-1,0, clickedPiece);
            
            // If the possible move is not null (not legal)
            if (possibleMove != null){
                
                // If the move 1 up has a piece there, ensure it is an enemy piece
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    if (!grid[initialRow-1][initialCol].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If the move location is empty, add it as a possible move
                else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
            }
            
        }
        
        // Checks to see if the row below king is not off the board
        if (initialRow + 1 < 8){
            
            int[] possibleMove = getPossibleMoves(1,0, clickedPiece);
            
            // If the move down is not null (not legal)
            if (possibleMove != null){
                
                // If the move 1 down has a piece there, ensure it is an enemy piece
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    if (!grid[initialRow+1][initialCol].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If there is no piece, add move
                else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks to see if move to the left is not off board
        if (initialCol - 1 > -1){
            
            int[] possibleMove = getPossibleMoves(0,-1, clickedPiece);
            
            // If move isnt null (legal move)
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // If its not the same team piece
                    if (!grid[initialRow][initialCol-1].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If the location of move is empty
                else { 
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks to see if move to right is not off board
        if (initialCol + 1 < 8){
            int[] possibleMove = getPossibleMoves(0,1, clickedPiece);
            
            // If possible move is not null (not legal)
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure it is enemy piece
                    if (!grid[initialRow][initialCol+1].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If empty add move
                else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks to see if bottom right move is not off board
        if (initialCol + 1 < 8 && initialRow + 1 < 8){
            
            int[] possibleMove = getPossibleMoves(1,1, clickedPiece);
            
            // If move is not null (not legal)
            if (possibleMove != null){
                
                // If location of move is not empty
                
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure it is not same team
                    if (!grid[initialRow+1][initialCol+1].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If empty add move
                else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks to see top left move is not off board
        if (initialCol + 1 < 8 && initialRow - 1 > -1){
            
            int[] possibleMove = getPossibleMoves(-1,1, clickedPiece);
            
            // If possible move is not null (not legal move)
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure not same team
                    if (!grid[initialRow-1][initialCol+1].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If empty add move
                else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks to see if bottom left move is not off board
        if (initialCol - 1 > -1 && initialRow + 1 < 8){
            
            int[] possibleMove = getPossibleMoves(1,-1, clickedPiece);
            
            // If move is not null (not legal)
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure its not same team
                    if (!grid[initialRow+1][initialCol-1].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If empty add move
                else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks to see if top left move is not off board
        if (initialCol - 1 > -1 && initialRow - 1 > -1){
            
            int[] possibleMove = getPossibleMoves(-1,-1, clickedPiece);
            
            // Checks if possible move is not null (not legal move)
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure its not same team
                    if (!grid[initialRow-1][initialCol-1].pieceName.substring(0,5).equals(clickedPiece.pieceName.substring(0,5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                }
                // If empty add move
                else{
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
    }
    
    
    /**
     * The method setPawn will check the pawn move set and check if the move is a legal move or not. It will ensure there is not its own piece in the move location, or the move 
     * will put the pawn  off the board.
     * @param ChessPiece[][] grid is a 2d array that holds the location of the pieces
     * @param ChessPiece clickedPiece is the piece that was clicked
     */
    public void setPawn(ChessPiece[][] grid, ChessPiece clickedPiece){
        
        // If there is an enpassant possible on the board
        if (((ChessBoard)getWorld()).getEnpassant() == true && checking == false){
            
            // Get the location of the enpassant
            int[] tempArray = ((ChessBoard)getWorld()).getEnpassantLocation();
            
            // If the distance from the clicked piece row to the enpassant location row is 2
            if (Math.abs(clickedPiece.initialRow - tempArray[0]) == 2){
                int tempAdjuster = 0;
                
                // Checks who piece belongs to
                if (clickedPiece.pieceName.substring(0,5).equals("white")){
                    tempAdjuster = -1;
                }
                else if (clickedPiece.pieceName.substring(0,5).equals("black")){
                    tempAdjuster = 1;
                }
                
                // If the enpassant pawn is the to left or right of this pawn, then this pawn is the one that can enpassant
                if ((clickedPiece.initialCol + 1) == tempArray[1]){
                    enpassantPieces.add(clickedPiece);
                    list.add(getPossibleMoves(tempAdjuster,1, clickedPiece));
                }
                
                if ((clickedPiece.initialCol - 1) == tempArray[1]){
                    enpassantPieces.add(clickedPiece);
                    list.add(getPossibleMoves(tempAdjuster,-1, clickedPiece));
                }
                
            }
            
        }
        
        // If the clicked piece is white
        if (clickedPiece.pieceName.substring(0,5).equals("white")){
            
            // Checks to see if row above pawn is not off board
            if (initialRow-1 > -1){
                
                // If the pawn hasnt moved yet
                if (this.pawnHasMoved == false){
                    
                    // If the square 2 above and directly above are empty
                    if (grid[initialRow-2][initialCol] == null && grid[initialRow-1][initialCol] == null){
                        // Add move that lets pawn move 2 up
                        list.add(getPossibleMoves(-2,0, clickedPiece));
                    }
                    
                    // If the square directly above is empty add it as a move
                    if (grid[initialRow-1][initialCol] == null){
                        list.add(getPossibleMoves(-1,0, clickedPiece));
                    }
                    
                }
                
                // If the pawn has moved 
                if (this.pawnHasMoved == true){
                    
                    // Checks if square above is empty then adds it as a move
                    if (grid[initialRow-1][initialCol] == null){
                        list.add(getPossibleMoves(-1,0, clickedPiece));
                    }
                    
                }
                
                // Checks if top right move is not off board
                if (initialCol + 1 < 8){
                    
                    // Ensures top right is not null and the piece there is not a team piece
                    if (grid[initialRow-1][initialCol+1] != null && (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow - 1][initialCol +1 ].pieceName.substring(0, 5)))){
                        list.add(getPossibleMoves(-1,+1, clickedPiece));
                    }
                    
                }
                
                // Checks if top left move is not off board
                if (initialCol - 1 > -1){
                    
                    // Ensures top left is not null and the piece there is not a team piece
                    if (grid[initialRow-1][initialCol-1] != null && (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow - 1][initialCol -1 ].pieceName.substring(0, 5)))){
                        list.add(getPossibleMoves(-1,-1, clickedPiece));
                    }
                    
                }
                
            }
            
        }
        
        // If the clicked piece is black
        else if (clickedPiece.pieceName.substring(0,5).equals("black")){
            
            // If row below is not off board
            if (initialRow+1 < 8){
                
                // If the pawn has not already moved
                if (this.pawnHasMoved == false){
                    
                    // Checks squares below 2 and 1 to see if empty, then add them as moves
                    if (grid[initialRow+2][initialCol] == null && grid[initialRow+1][initialCol] == null){
                        list.add(getPossibleMoves(2,0, clickedPiece));
                    }
                    
                    if (grid[initialRow+1][initialCol] == null){
                        list.add(getPossibleMoves(1,0, clickedPiece));
                    }
                    
                }
                // If the pawn has already moved
                else if (this.pawnHasMoved == true){
                    
                    // If move below is empty add it to list
                    if (grid[initialRow+1][initialCol] == null){
                        list.add(getPossibleMoves(1,0, clickedPiece));
                    }
                    
                }
                
                // If bottom right move is not off board
                if (initialCol + 1 < 8){
                    
                    // Checks to see if bottom right move is not empty and if the piece there is not a team piece
                    if (grid[initialRow+1][initialCol+1] != null && (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow + 1][initialCol +1 ].pieceName.substring(0, 5)))){
                        list.add(getPossibleMoves(1,1, clickedPiece));
                    }
                    
                }
                
                // If bottom left move is not off board
                if (initialCol - 1 > -1){
                    
                    // Ensures location is no empty and piece there is not same team
                    if (grid[initialRow+1][initialCol-1] != null && (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow + 1][initialCol -1 ].pieceName.substring(0, 5)))){
                        list.add(getPossibleMoves(1,-1, clickedPiece));
                    }
                    
                }
                
            }
            
        }
    }
    
    /**
     * The method setKnight will check the knights move set and check if the move is a legal move or not. It will ensure there is not its own piece in the move location, or the move 
     * will put the knight off the board.
     * @param ChessPiece[][] grid is a 2d array that holds the location of the pieces
     * @param ChessPiece clickedPiece is the piece that was clicked
     */
    public void setKnight(ChessPiece[][] grid, ChessPiece clickedPiece){
        
        // If move up 2 and left 1 is not off board
        if (((initialRow - 2) > -1) && ((initialCol - 1) > -1)){
            // Get move for that location, null if causes check to own king
            int[] possibleMove = getPossibleMoves(-2,-1, clickedPiece);
            
            // If moving this piece does not cause a check on yourself
            if (possibleMove != null){
                
                // If the move location is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure it is enemy piece then add as a possible move
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // If move location is empty then add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        } 
        
        // If move up 2 right 1 is not off board
        if (((initialRow - 2) > -1) && ((initialCol + 1) < 8)){
            int[] possibleMove = getPossibleMoves(-2,1, clickedPiece);
            
            // If move does not cause self check
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure it is enemy piece
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    } 
                    
                // Location is empty add as move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // If move down 2 left 1 is not off board
        if (((initialRow + 2) < 8) && ((initialCol - 1) > -1)){
            int[] possibleMove = getPossibleMoves(2,-1, clickedPiece);
            
            // If move does not cause self check
            if (possibleMove != null){
                
                // If location is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure it is enemy piece
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // If location is empty add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // If move down 2 right 1 is not off board
        if (((initialRow + 2) < 8) && ((initialCol + 1) < 8)){
            int[] possibleMove = getPossibleMoves(2,1, clickedPiece);
            
            // If move does not cause self check
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure its not the same team
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // Location empty add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks if move up 1 left 2 is not off board
        if (((initialRow - 1) > -1) && ((initialCol - 2) > -1)){
            int[] possibleMove = getPossibleMoves(-1,-2, clickedPiece);
            
            // If move does not cause self check
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure different team
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // Location of move empty, add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // Checks if move up 1 right 2 is not off board
        if (((initialRow - 1) > -1) && ((initialCol + 2) < 8)){
            int[] possibleMove = getPossibleMoves(-1,2, clickedPiece);
            
            // If move does not cause self check
            if (possibleMove != null){
                
                // If location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure different team
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // Location empty add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // If move down 1 left 2 is not off board
        if (((initialRow + 1) < 8) && ((initialCol - 2) > -1)){
            int[] possibleMove = getPossibleMoves(1,-2, clickedPiece);
            
            // Move doesnt cause self check
            if (possibleMove != null){
                
                // Move location is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure different team
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // Move location empty, add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
        // If move down 1 right 2 is not off board
        if (((initialRow + 1) < 8) && ((initialCol + 2) < 8)){
            int[] possibleMove = getPossibleMoves(1,2, clickedPiece);
            
            // Ensure move doesnt cause self check
            if (possibleMove != null){
                
                // Location of move is not empty
                if (grid[possibleMove[0]][possibleMove[1]] != null){
                    
                    // Ensure different team
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[possibleMove[0]][possibleMove[1]].pieceName.substring(0, 5))){
                        list.add(possibleMove);
                        addToListKingKnight(possibleMove);
                    }
                    
                // Location of move is empty, add move
                } else {
                    list.add(possibleMove);
                    addToListKingKnight(possibleMove);
                }
                
            }
            
        }
        
    }
    
    /**
     * The method setUpDown will check all the horizontal and vertical moves for rooks and queens. 
     * It will add all the legal moves to the list of moves.
     * @param ChessPiece[][] grid is a 2d array that holds the location of the pieces
     * @param ChessPiece clickedPiece is the piece that was clicked
     */
    public void setUpDown(ChessPiece[][] grid, ChessPiece clickedPiece){
        
        // For loop that is the length and width of the board
        for (int i = 1; i < 8;i++){
            
            // Checks row below piece to see if it will not be off the board, and that it has not been told to stop checking down moves
            if ((initialRow + i < 8) && (endDownCount == false)){
                
                // location of move below is not empty
                if (grid[initialRow + i][initialCol] != null){
                    
                    // Ensure it is a different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow + i][initialCol].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(i,0, clickedPiece));
                    }
                    
                    // Stop counting down moves
                    endDownCount = true;
                }
                // If down move is empty, add move to list
                else if (endDownCount == false){
                    list.add(getPossibleMoves(i,0, clickedPiece));
                }
                
            }
            
            // Checks if row above is not off board, and it hasnt been told to stop checking up 
            if (initialRow - i > -1 && endUpCount == false){
                
                // If move up location not empty
                if (grid[initialRow - i][initialCol] != null){
                    
                    // Ensure piece is different team
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow - i][initialCol].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(-i,0, clickedPiece));
                    }
                    
                    // Stop counting up
                    endUpCount = true;
                }
                // If move location empty then add the move to the list
                else if (endUpCount == false){
                    list.add(getPossibleMoves(-i,-0, clickedPiece));
                }
                
            }
            
            // Checks if column to the right is not off board and it hasnt been told to stop counting to the right
            if ((initialCol + i < 8 && (endRightCount == false))){
                
                // Location of move is not empty
                if (grid[initialRow][initialCol + i] != null){
                    
                    // Enusre different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow][initialCol+i].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(0,i, clickedPiece));
                    }
                    
                    // Stop counting right
                    endRightCount = true;
                }
                // Move location is empty, add move to the list
                else if (endRightCount == false){
                    list.add(getPossibleMoves(0,i, clickedPiece));
                }
                
            }
            
            // Checks if column to the left is not off board and it hasnt been told to stop counting to the left
            if ((initialCol - i > 0 && (endLeftCount == false)|| initialCol - i == 0 && (endLeftCount == false))){
                
                // If location of move is not empty
                if (grid[initialRow][initialCol - i] != null){
                    
                    // Ensure different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow][initialCol-i].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(0,-i, clickedPiece));
                    }
                    
                    endLeftCount = true;
                }
                // Location of move is empty, add move to the list
                else if (endLeftCount == false){
                    list.add(getPossibleMoves(0,-i, clickedPiece));
                } 
                
            }
            
        }
        
        // Reset control variables
        endDownCount = false;
        endUpCount = false;
        endRightCount = false;
        endLeftCount = false;
    }
    
    /**
     * The method setDiags will check all the diagonal moves for bishops and queens. 
     * It will add all the legal moves to the list of moves.
     * @param ChessPiece[][] grid is a 2d array that holds the location of the pieces
     * @param ChessPiece clickedPiece is the piece that was clicked
     */
    public void setDiags(ChessPiece[][] grid, ChessPiece clickedPiece){
        // For loop the length of the board
        for (int i = 1; i < 8;i++){
            
            // If bottom right move is not off board and it hasnt been told to stop counting
            if ((initialRow + i < 8) && (initialCol + i < 8) && (endBottomRightDiag == false)){
                
                // Checks if location of move is not empty
                if (grid[initialRow + i][initialCol + i] != null){
                    
                    // Ensure different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow+i][initialCol+i].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(i,i, clickedPiece));
                    }
                    
                    // Stop counting bottom right
                    endBottomRightDiag = true;
                }
                // Location of move is empty, add as a possible move
                else if (endBottomRightDiag == false){
                    list.add(getPossibleMoves(i,i, clickedPiece));
                }
                
            }
            
            // If bottom left move is not off board and it hasnt been told to stop counting
            if ((initialRow + i < 8) && (initialCol - i > -1 ) && (endBottomLeftDiag == false)){
                
                // If location of move is not empty
                if (grid[initialRow + i][initialCol - i] != null){
                    
                    // Ensure different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow+i][initialCol-i].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(i,-i, clickedPiece));
                    }
                    
                    // Stop counting bottom left
                    endBottomLeftDiag = true;
                }
                // Location of move empty, add to list
                else if (endBottomLeftDiag == false){
                    list.add(getPossibleMoves(i,-i, clickedPiece));
                }
                
            }
            
            // If top right move is not off board and it hasnt been told to stop counting
            if ((initialRow - i > -1) && (initialCol + i < 8) && (endTopRightDiag == false)){
                
                // If location of move is not empty
                if (grid[initialRow - i][initialCol + i] != null){
                    
                    // Ensure different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow-i][initialCol+i].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(-i,i, clickedPiece));
                    }
                    
                    // Stop counting top right
                    endTopRightDiag = true;
                }
                // If location of move is empty, add move
                else if (endTopRightDiag == false){
                    list.add(getPossibleMoves(-i,i, clickedPiece));
                }
                
            }
            
            // If top left move is not off board and it hasnt been told to stop counting
            if ((initialRow - i > -1) && (initialCol - i > -1) && (endTopLeftDiag == false)){
                
                // If location of move is not empty
                if (grid[initialRow - i][initialCol - i] != null){
                    
                    // Ensure different colour
                    if (!clickedPiece.pieceName.substring(0, 5).equals(grid[initialRow-i][initialCol-i].pieceName.substring(0, 5))){
                        list.add(getPossibleMoves(-i,-i, clickedPiece));  
                    }
                    
                    // Stop counting top left
                    endTopLeftDiag = true;
                }
                // If location of move is empty, add to list
                else if (endTopLeftDiag == false){
                    list.add(getPossibleMoves(-i,-i, clickedPiece));
                }
                
            }
            
        }
        
        // Reset control variables
        endBottomLeftDiag = false;
        endBottomRightDiag = false;
        endTopLeftDiag = false;
        endTopRightDiag = false;
    }
    
    /**
     * The method setPosition will set the location of the piece being moved, and remove the object the piece is taking.
     * It also detects if a pawn has moved into a location where it can be promoted and will erase all move related lists.
     * @param int col is the col the piece is moving to
     * @param int row is the row the piece is moving to
     * @param ChessPiece piece is the piece that is being moved
     */
    public void setPosition(int col, int row, ChessPiece piece){
        // Set piece to new location
        piece.setLocation(col*100+50,row*100+50);
        
        // Get the location of the enpassant
        int[] tempArray = ((ChessBoard)getWorld()).getEnpassantLocation();
        
        // Loop through the list of pieces that can take via enpassant
        for (int i = 0; i < enpassantPieces.size(); i++){
            
            // If the piece that was moved was the enpassant pawn
            if (piece == enpassantPieces.get(i)){
                
                // This checks if the move that was made was indeed the enpassant move
                if (Math.abs(row - tempArray[0]) == 1){
                    int tempAdjuster = 0;
                    
                    if (piece.pieceName.substring(0,5).equals("white")){
                        tempAdjuster = 1;
                    }
                    else if (pieceName.substring(0,5).equals("black")){
                        tempAdjuster = -1;
                    }
                    
                    if (col == tempArray[1]){
                        // Remove the pawn it took via enpassant 
                        getWorld().removeObject(((ChessBoard)getWorld()).getAllPieces()[row + tempAdjuster][col]);
                        tookViaEnpassant = true;
                    }
                    
                }
                
            } 
            
        }
        
        enpassantPieces.clear();
        
        // Checks if king castled right
        if (canCastleRight == true && col == 6){
            // Set location of rook
            ((ChessBoard)getWorld()).getAllPieces()[row][7].setLocation(5*100+50,row*100+50);
            castled = true;
        }
        
        // Checks if king castled left
        if (canCastleLeft == true && col == 2){
            // Set location of rook
            ((ChessBoard)getWorld()).getAllPieces()[row][0].setLocation(3*100+50,row*100+50);
            castled = true;
        }
        
        // Remove the piece it took
        getWorld().removeObject(getOneIntersectingObject(ChessPiece.class));
        
        // Checks if a pawn moved to the back ranks, starts promotion sequence
        if (piece.pieceName.equals("blackPawn")){
            
            if (row == 7){
                ((ChessBoard)getWorld()).promotions();
                promoting = true;
            }
            
        }
        else if (piece.pieceName.equals("whitePawn")){
            
            if (row == 0){
                ((ChessBoard)getWorld()).promotions();
                promoting = true;
            }
            
        }
        
        // Reset lists
        list.clear();
        realMoves.clear();
        test.clear();
        
        ((ChessBoard)getWorld()).updateBoard(col,row, piece);
    }
    
    /**
     * The method getInitialRow will return the starting row of the piece
     * @return int initialRow is the row of the piece
     */
    public int getInitialRow(){
        return initialRow;
    }
    
    /**
     * The method getInitialCol will return the starting column of the piece
     * @return int initialCol is the col of the piece
     */
    public int getInitialCol(){
        return initialCol;
    }     
    
    /**
     * The method getPossibleMoves will add the possible move location to an integer array, then return that array.
     * It will also ensure that the possible move does not cause a self check, then add that move to the list of moves.
     * @param int firstAdjuster is the difference between the initial row and possible move row
     * @param int secondAdjuster is the difference between the initial column and possible move column
     * @param ChessPiece piece is the piece that can do the possible move
     * @return int[] will return an array contain the row and column of the possible move
     */
    public int[] getPossibleMoves(int firstAdjuster, int secondAdjuster, ChessPiece piece) {
        // Create an array and add the row and column of the possible move to it
        int[] possibleMove = new int[2];
        possibleMove[0] = this.initialRow+firstAdjuster;
        possibleMove[1] = this.initialCol+secondAdjuster;
        
        // If the program is not already checking to see if move will cause a self check, and it is not checking for mate
        if (checking == false && ((ChessBoard)getWorld()).getCheckingMate() != true){
            checking = true;
            // Call on method that will test whether move causes self check
            ((ChessBoard)getWorld()).checkUpdateBoard(possibleMove[1], possibleMove[0], piece);
            
            // True if move causes self check
            if (((ChessBoard)getWorld()).getFakeCheck() == true){
                return null;
            }
            // If move does not cause self check
            else if (((ChessBoard)getWorld()).getFakeCheck() == false){
                
                // Ensure piece is not a knight or king
                if ((!piece.pieceName.equals("blackKnight")) && (!piece.pieceName.equals("whiteKnight")) & (!piece.pieceName.equals("whiteKing")) && (!piece.pieceName.equals("blackKing"))){
                    // Add to move list
                    realMoves.add(possibleMove);
                }
                
            }
            
        }
        
        // If program is checking for mate
        if (((ChessBoard)getWorld()).getCheckingMate() == true){
            
            // If the location of the move is not empty
            if ((((ChessBoard)getWorld()).getAllPieces())[possibleMove[0]][possibleMove[1]] != null){
                
                // Checks if piece at location of possible move is not the same colour
                if (!(((ChessBoard)getWorld()).getAllPieces())[possibleMove[0]][possibleMove[1]].pieceName.substring(0,5).equals(((ChessBoard)getWorld()).getTurn())){
                    // Add to list that is used for checking mate
                    test.add(possibleMove);
                }
                
            }
            // Location of move is empty. add to list for checking mate
            else if ((((ChessBoard)getWorld()).getAllPieces())[possibleMove[0]][possibleMove[1]] == null){
                test.add(possibleMove);
            }
            
        }
        
        return possibleMove;
    }
    
    /**
     * The method setChecking will set the checking status
     * @param boolean checkingIn identifies if the program is checking for check
     */
    public void setChecking(boolean checkingIn){
        checking = checkingIn;
    }
    
    /**
     * The method isCheck will go through a list of possible moves and check if that move would take the opposing king. Thus meaning that king is in check.
     * @param ChessPiece[][] grid is a 2d array holding the locations of all the pieces
     * @param String checkingMate determines if it checking for mate or normal check
     * @param ChessPiece piece is the piece that can do the possible move
     * @return boolean that signifies if there is check or checkmate
     */
    public boolean isCheck(ChessPiece[][] grid, String checkingMate, ChessPiece piece){
        
        // For loop that iterates through the list of possible moves
        for (int i = 0; i < list.size(); i ++){
            
            // If the specific value in the list is not null
            if (list.size() > 0 && list.get(i)!=null){
                int [] possMove = list.get(i);
                
                // If the location of the possible move is not empty
                if (grid[possMove[0]][possMove[1]] != null){
                    
                    // If the piece at that location is a king of the opposite colour, it is check
                    if (grid[possMove[0]][possMove[1]].pieceName.equals(((ChessBoard)getWorld()).getTurn()+"King") && checkingMate.equals("check")){
                        list.clear();
                        return true;
                    }
                    
                }
                
            }
            
        }
        
        // If checking for mate
        if (checkingMate.equals("mate")){
            
            // Call on testing method
            if (testing(piece) == true){
                test.clear();
                list.clear();
                return true;
            }
            
        }
        
        return false;
    }

    /**
     * The method testing will go through a list containing all the possible moves of the pieces for whoevers turn it is, 
     * then call on a method that will check each of these moves to determine if they would stop that king from being in check.
     * @param ChessPiece is the piece that can do the possible move
     * @return boolean will return whether it is checkmate or not.
     */
    public boolean testing(ChessPiece piece){
        // If there are possible moves
        if (test.size() > 0){
            
            // For loop that goes through the list
            for (int i = 0; i < test.size(); i ++){
                int[] possibleMove = test.get(i);
        
                // Call on method to see if that move causes the king to longer be in check
                ((ChessBoard)getWorld()).checkUpdateBoard(possibleMove[1], possibleMove[0], piece);
                
                // If the king is no longer in check
                if (((ChessBoard)getWorld()).getFakeCheck() == false){
                    test.clear();
                    return false;
                }
                
            }
            
        }
        // If there are no possible moves
        else if (test.size() == 0){
            return true;
        }
        
        test.clear();
        return true;
    }
    
    /**
     * The method isThereMoves will see if there are possible moves to be made.
     * @param ChessPiece piece is the piece that can do that possible move
     * @return boolean will return true if there are moves.
     */
    public boolean isThereMoves(ChessPiece piece){
        
        // For loop that iterates through the list of possible moves
        for (int i = 0; i < list.size(); i ++){
            
            // If the array at the specific index in the list is not null
            if (list.get(i) != null){
                int [] possibleMove = list.get(i);
                // Call on method to see if possible move cancels any check
                ((ChessBoard)getWorld()).checkUpdateBoard(possibleMove[1], possibleMove[0], piece);
                // If check is cancelled
                
                if (((ChessBoard)getWorld()).fakeCheck == false){    
                    return true;
                }
                
            }
            
        }
        
        return false;
    }
    
    /**
     * The method setList will clear the list list.
     */
    public void setList(){
        list.clear();
    }
    
    /**
     * The method setRealMoves will clear the list realMoves.
     */
    public void setRealMoves(){
        realMoves.clear();
    }
    
    /**
     * The method setTestList will clear the list test.
     */
    public void setTestList(){
        test.clear();
    }
}
