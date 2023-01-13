import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.Hashtable;
import java.util.List;

/**
 * This class deals with the highlighting of boxes that the user can move 
 * their piece to. This applies to both the peices that the clicked piece 
 * can take, but as well as the piece that the user is currently selecting.
 * 
 * @author Michael Tuccillo
 * @version v1
 */
public class HighlightBlock extends Actor
{
    ChessPiece piece;
    int row;
    int col;
    
    /**
     * This constructor gets the allowed moves of the piece selected, then 
     * creates highlighted blocks to place at those locations.
     * 
     * @param clickedPiece the piece selected by the user
     * @param listOfMoves the list of possible moves for the user that
     * need highlighted blocks
     * @param specificMove this is an interator that is passed with the piece's information
     */
    public HighlightBlock(ChessPiece clickedPiece, List<int[]> listOfMoves, int specificMove){
        if (listOfMoves.get(specificMove) != null){
            int[] tempList = new int[2];
            tempList = listOfMoves.get(specificMove);
            //stores the brought information as rows and cols
            this.row = tempList[0];
            this.col = tempList[1];
            piece = clickedPiece;
            //creates a highlighted block at that location
            GreenfootImage highlightedBlock = new GreenfootImage (90, 90);
            highlightedBlock.setTransparency(150);
            highlightedBlock.setColor(Color.RED);
            highlightedBlock.fill();
            this.setImage(highlightedBlock);
        }
    }
    /**
     * This constructor creates highlighted blocks around the piece that the user selects
     * 
     * @param clickedPiece the piece selected by the user
     */
    public HighlightBlock(ChessPiece pieceIn){
        piece = pieceIn;
        //create a 90x90 blue block over the piece selected
        GreenfootImage highlightedBlock = new GreenfootImage (90, 90);
        highlightedBlock.setTransparency(150);
        highlightedBlock.setColor(Color.BLUE);
        highlightedBlock.fill();
        this.setImage(highlightedBlock); 
    }
    /**
     * This method gets the row of the block clicked
     * @return integer The row of the highlighted block
     */
    public int getRow(){
        return row;
    }
    /**
     * This method gets the column of the block clicked
     * @return integer The column of the highlighted block
     */
    public int getCol(){
        return col;
    }
    
    /**
     * Act - do whatever the HighlightBlock wants to do. This method is called whenever
     * the 'Act' or 'Run' button gets pressed in the environment. It will see 
     * which block has been clicked then it will remove highlights.
     */
    public void act()
    {
        // If mouse was clicked
        if (Greenfoot.mouseClicked(this)){
            // If the clicked block is not the block that highlights the piece
            if (((this.getX()-50)/100 != piece.getInitialCol()) || ((this.getY()-50)/100 != piece.getInitialRow())){
                // Set pos of piece to pos of highlight block
                piece.setPosition(this.col, this.row, piece);
            }
            // Remove all highlights from the board
            ((ChessBoard)getWorld()).removeHighlights(); 
        }
    }
}
