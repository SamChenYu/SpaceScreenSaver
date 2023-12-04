
package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Sun {
    
    double x,y;
    double scale = 1; // sun image is 360x360 px
    BufferedImage image;
    
    public Sun(int screenWidth, int screenHeight) {
        
        x = (screenWidth / 2) - 180*scale;
        y = (screenHeight / 2) - 180*scale;
        System.out.println(x);
        System.out.println(y);
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/sun.png"));
        } catch(IOException e) {
            System.out.println("sun image loading failed");
            System.out.println(e);
        }
        
        
    }
    
    
    public void draw(Graphics2D g2) {
        int castX = (int) Math.round(x);
        int castY = (int) Math.round(y);
        int castScale = (int) Math.round(360.0 * scale);
        g2.drawImage(image,castX,castY,castScale, castScale ,null);
    }
    
    
    
}
