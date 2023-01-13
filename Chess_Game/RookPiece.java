import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * The class RookPiece is an extension of ChessPiece and is responsible for creating the image and name of the rook piece.
 * It also detects when a rook has been pressed, or when a pawn promotes to a rook.
 * 
 * @author Michael Tuccillo
 * @version 1.0
 * @since 2022-1-14
 */
public class RookPiece extends ChessPiece
{
    /**
     * Constructor for objects of class RookPiece. This constructor will take in the name and image of the rook, then set the image and name based on what was provided.
     * @param String imageIn is the name of image file
     * @param String pieceName is the name of the piece
     */
    public RookPiece(String imageIn, String pieceName){
        super(imageIn, pieceName);
    }
    
 
    /**
     * This Act method is called constantly and will check to see whether or not the Rook Piece was pressed.
     * If it was pressed, it will check to see if it was chosen as a promotion, then it will set the image and name to the corrosponding turn.
     * In addition, when pressed it will call on a method that gets the legal moves for rooks.
     */
    public void act()
    {
        // Check if rook was pressed and the current world is ChessBoard
        if (Greenfoot.mouseClicked(this) && this.getWorld().getClass() == ChessBoard.class){ 
            
            // Checks if the user is promoting a pawn, and the rook that was pressed was the grey promotion rook on the right
            if (promoting == true && this.pieceName.substring(0,9).equals("promotion")){
                ((ChessBoard)getWorld()).decPromoted("Rook");
                
                // If its whites turn
                if ((((ChessBoard)getWorld()).getTurn()).equals("white")){
                    //Set image and name to the white values
                    setImage("whiteRook.png");
                    setPieceName("whiteRook");   
                }
                // Checks if it was blacks turn
                else if ((((ChessBoard)getWorld()).getTurn()).equals("black")){
                    // Set image and name to the black values
                    setImage("blackRook.png");
                    setPieceName("blackRook");
                }
                
                // For loop that removes the other grey promotion pieces on the right side
                for (int i = 0; i < 4; i++){
                    // If the piece is not this piece
                    if (((ChessBoard)getWorld()).getPromotePieces()[i] != this){
                        // Remove it from this world
                        getWorld().removeObject(((ChessBoard)getWorld()).getPromotePieces()[i]);
                    }
                }
                ((ChessBoard)getWorld()).setPromotingPiece(this);
            }
            // If a normal rook was pressed and not the grey promotion one
            else {
                // Get the legal moves of that rook
                ChessBoard world = (ChessBoard)getWorld();
                getLegalMoves(world.getAllPieces(), this);
            }
        }
    }
}
