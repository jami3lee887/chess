import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class PawnPiece is an extension of ChessPiece and is responsible for creating the image and name of the pawn piece.
 * It also detects when a pawn has been pressed
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class PawnPiece extends ChessPiece
{
    /**
     * Constructor for objects of class PawnPiece. This constructor will take in the name and image of the pawn, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public PawnPiece(String imageIn, String pieceName){
        super(imageIn, pieceName);
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the pawn Piece was pressed.
     * If it was pressed, then it will call on a method that gets the legal moves for pawns.
     */
    public void act()
    {
        // Checks to see if the pawn was pressed and the world is the chessboard
        if (Greenfoot.mouseClicked(this) && this.getWorld().getClass() == ChessBoard.class){
            // Call on method to get legal moves for pawns
            ChessBoard world = (ChessBoard)getWorld();
            getLegalMoves(world.getAllPieces(), this);
        }
    }
}
