import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class KingPiece is an extension of ChessPiece and is responsible for creating the image and name of the king piece.
 * It also detects when a king has been pressed
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class KingPiece extends ChessPiece
{
    /**
     * Constructor for objects of class KingPiece. This constructor will take in the name and image of the king, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public KingPiece(String imageIn, String pieceName){
        super(imageIn, pieceName);
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the king Piece was pressed.
     * If it was pressed, then it will call on a method that gets the legal moves for kings.
     */
    public void act()
    {
        // If it was pressed and it is in the chessboard world
        if (Greenfoot.mouseClicked(this) && this.getWorld().getClass() == ChessBoard.class){
            // Call on method to get legal moves for kings
            ChessBoard world = (ChessBoard)getWorld();
            getLegalMoves(world.getAllPieces(), this);
        }
    }
}
