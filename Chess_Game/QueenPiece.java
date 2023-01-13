import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class QueenPiece is an extension of ChessPiece and is responsible for creating the image and name of the Queen piece.
 * It also detects when a queen has been pressed, or when a pawn promotes to a queen.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class QueenPiece extends ChessPiece
{
    /**
     * Constructor for objects of class QueenPiece. This constructor will take in the name and image of the queen, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public QueenPiece(String imageIn, String pieceName){
        super(imageIn, pieceName);
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the Queen Piece was pressed.
     * If it was pressed, it will check to see if it was chosen as a promotion, then it will set the image and name to the corrosponding turn.
     * In addition, when pressed it will call on a method that gets the legal moves for queens.
     */
    public void act()
    {
        // Check if Queen was pressed and the current world is ChessBoard
        if (Greenfoot.mouseClicked(this) && this.getWorld().getClass() == ChessBoard.class){
            
            // Checks if the user is promoting a pawn, and the queen that what was pressed was the grey promotion queen on the right
            if (promoting == true && this.pieceName.substring(0,9).equals("promotion")){
                ((ChessBoard)getWorld()).decPromoted("Queen");
                
                // If its whites turn
                if ((((ChessBoard)getWorld()).getTurn()).equals("white")){
                    // Set name and image to white info
                    setImage("whiteQueen.png");
                    setPieceName("whiteQueen");                       
                }
                // If its blacks turn
                else if ((((ChessBoard)getWorld()).getTurn()).equals("black")){
                    // Set name and image to black info
                    setImage("blackQueen.png");
                    setPieceName("blackQueen");
                }
                
                // For loop that removes the other grey promotion pieces on the right side
                for (int i = 0; i < 4; i++){
                    // If the piece is not this piece
                    if (((ChessBoard)getWorld()).getPromotePieces()[i] != this){
                        // Remove from world
                        getWorld().removeObject(((ChessBoard)getWorld()).getPromotePieces()[i]);
                    }
                }
                ((ChessBoard)getWorld()).setPromotingPiece(this);
            }
            // If a normal queen was pressed
            else {
                // Call on method that gets legal moves for queens
                ChessBoard world = (ChessBoard)getWorld();
                getLegalMoves(world.getAllPieces(), this);
            }
        }
    }
}
