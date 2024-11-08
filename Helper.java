import java.awt.*;
import java.awt.image.BufferedImage;

class Helper{
  public static int advanceArray(int[] array, int pos){
    if(pos+1 == array.length){
      return 0;
    }
    return pos+1;
  }
  public static Image rotateImage(Image image, int degrees){
    BufferedImage idiotic = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    BufferedImage returner = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics2D bGr = idiotic.createGraphics();
    bGr.drawImage(image, 0, 0, null);
    bGr.dispose();
    
    Graphics2D g2 = returner.createGraphics();
 

        g2.rotate(Math.toRadians(degrees), image.getWidth(null) / 2,
                  image.getHeight(null) / 2);
        g2.drawImage(idiotic, null, 0, 0);
    return (Image)returner;
  }
}