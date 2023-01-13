import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class BishopPiece is an extension of ChessPiece and is responsible for creating the image and name of the Bishop piece.
 * It also detects when a bishop has been pressed, or when a pawn promotes to a bishop.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class BishopPiece extends ChessPiece
{
    /**
     * Constructor for objects of class BishopPiece. This constructor will take in the name and image of the bishop, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public BishopPiece(String imageIn, String pieceName){
        super(imageIn, pieceName);
    }
    
    /**
     * This Act method is called constantly and will check to see whether or not the bishop Piece was pressed.
     * If it was pressed, it will check to see if it was chosen as a promotion, then it will set the image and name to the corrosponding turn.
     * In addition, when pressed it will call on a method that gets the legal moves for bishops.
     */
    public void act()
    {
        // Checks to see if bishop was pressed
        if (Greenfoot.mouseClicked(this) && this.getWorld().getClass() == ChessBoard.class){
            
            // Checks if the bishop that was pressed was the grey promoting one
            if (promoting == true && this.pieceName.substring(0,9).equals("promotion")){
                ((ChessBoard)getWorld()).decPromoted("Bishop");
                
                // If its white's turn
                if ((((ChessBoard)getWorld()).getTurn()).equals("white")){
                    // Set image and name to white values
                    setImage("whiteBishop.png");
                    setPieceName("whiteBishop");
                    
                }
                // If its black's turn
                else if ((((ChessBoard)getWorld()).getTurn()).equals("black")){
                    // Set name and image to black values
                    setImage("blackBishop.png");
                    setPieceName("blackBishop");
                }
                
                // Remove all other grey pieces
                for (int i = 0; i < 4; i++){
                    // If piece is not this piece
                    if (((ChessBoard)getWorld()).getPromotePieces()[i] != this){
                        // Remove the piece
                       getWorld().removeObject(((ChessBoard)getWorld()).getPromotePieces()[i]);
                    }
                }
                ((ChessBoard)getWorld()).setPromotingPiece(this);
            }
            // If it wasn't a promoting bishop that was pressed
            else {
                // Call on method that gets legal moves for bishops
                ChessBoard world = (ChessBoard)getWorld();
                getLegalMoves(world.getAllPieces(), this);
            }
        }
    }
}
