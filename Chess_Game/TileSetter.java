import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
/**
 * The tile TileSetter is a class that creates the 8x8 tiles of a chess board
 * 
 * @author Karim Elsawi
 * @version 1.0
 */
public class TileSetter  
{   
    
        
    /**
     * This method creates and places the small 100*100 squares on the 800*800
     * grid
     */
    public GreenfootImage setTiles(Color colourIn){
        
        
        
        int chessHeight = 100;
        int chessWidth = 100;
        
        //creates an image of this size
        GreenfootImage image = new GreenfootImage (1200,800);
        image.setColor(colourIn);
        image.fill();
        
     
       
        // for loop setting chessboard tiles
        for (int i=0; i<8; i++){
            for(int j=0; j<8; j++){
                //every other 100*100 area is filled with the respective colour
                if ((i-j)%2==0){
                       image.setColor(new Color (HiddenWorld.r1,HiddenWorld.g1,HiddenWorld.b1));
                } else {
                   image.setColor(new Color (HiddenWorld.r2,HiddenWorld.g2,HiddenWorld.b2));
                }
                //fills in as a bunch of small squares
                 image.fillRect(i*chessWidth,j*chessHeight,chessWidth,chessHeight);
            }
        }
        

        return image;
    
    }
    
    
}
