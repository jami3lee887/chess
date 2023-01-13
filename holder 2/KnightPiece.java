
import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class KnightPiece is an extension of ChessPiece and is responsible for creating the image and name of the Knight piece.
 * It also detects when a knight has been pressed, or when a pawn promotes to a knight.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class KnightPiece extends ChessPiece
{
    /**
     * Constructor for objects of class KnightPiece. This constructor will take in the name and image of the knight, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public KnightPiece(String imageIn, String pieceName){
        super(imageIn, pieceName);
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the Knight Piece was pressed.
     * If it was pressed, it will check to see if it was chosen as a promotion, then it will set the image and name to the corrosponding turn.
     * In addition, when pressed it will call on a method that gets the legal moves for Knights.
     */
    public void act()
    {
        // Check if Knight was pressed and the current world is ChessBoard
        if (Greenfoot.mouseClicked(this) && this.getWorld().getClass() == ChessBoard.class){
            
            // Checks if the user is promoting a knight, and the knight that what was pressed was the grey promotion knight on the right
            if (promoting == true && this.pieceName.substring(0,9).equals("promotion")){
                ((ChessBoard)getWorld()).decPromoted("Knight");
                
                // Checks if its whites turn
                if ((((ChessBoard)getWorld()).getTurn()).equals("white")){
                    // Set image and name to white values
                    setImage("whiteKnight.png");
                    setPieceName("whiteKnight");
                    
                }
                // Checks if its blacks turn
                else if ((((ChessBoard)getWorld()).getTurn()).equals("black")){
                    // Sets image and name to black values
                    setImage("blackKnight.png");
                    setPieceName("blackKnight");
                }
                
                // For loop to remove other grey pieces
                for (int i = 0; i < 4; i++){
                    // If the piece is not this piece
                    if (((ChessBoard)getWorld()).getPromotePieces()[i] != this){
                        // Remove it
                        getWorld().removeObject(((ChessBoard)getWorld()).getPromotePieces()[i]);
                    }
                }
                ((ChessBoard)getWorld()).setPromotingPiece(this);
            }
            // If the knight that was pressed was a normal knight, not a promotion
            else {
                // Call on method to get the legal moves
                ChessBoard world = (ChessBoard)getWorld();
                getLegalMoves(world.getAllPieces(), this);
            }
        }
    }
}
