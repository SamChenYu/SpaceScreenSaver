
package Main;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Planet {
    
    int screenWidth;
    int screenHeight;
    
    
    
    public double x;
    double y;
    double xStretchFactor;
    double yStretchFactor;
    double domainMin, domainMax;
    double scale = 1;
    boolean higherSelection = true;
    
    int imageWidth;
    int imageHeight;
    
    
    BufferedImage image;

    public Planet(int screenWidth, int screenHeight, double xStretchFactor, double yStretchFactor, String filepath, int imageWidth, int imageHeight, double scale) {
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.xStretchFactor = xStretchFactor;
        this.yStretchFactor = yStretchFactor;
        this.scale = scale;
        calculateDomain();
        x = screenWidth / 2;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        
        try {
            image = ImageIO.read(getClass().getResourceAsStream(filepath));
        } catch(IOException e) {
            System.out.println("planet image loading failed");
            System.out.println(e);
        }
        
    }
    
    public void calculateDomain() {
        domainMin = Math.sqrt((800*800)/(xStretchFactor)) + screenWidth/2;
        domainMax = (-1)*(Math.sqrt((800*800)/(xStretchFactor))) + screenWidth/2;
        System.out.println(domainMin + " " + domainMax);
    }
    
    public void update() {
        
        
        
        
        
        if(x > domainMin-0.5) {
            higherSelection = true;
        }
        if (x < domainMax+0.5) {
            higherSelection = false;
        }
        
        if(higherSelection) {
            x--;
        } else {
            x++;
        }
        
        //Elliptical function:
        
        y = (800*800) - (x-(screenWidth/2))*(x-(screenWidth/2))*(xStretchFactor);
        y = Math.sqrt(y/yStretchFactor);
        
        if(higherSelection) {
            y += (screenHeight/2);
        } else {
            y = (y*-1) + (screenHeight/2);
        }
        
//        System.out.println("x: " + x + "y: " + y);
    }
    
    public void draw(Graphics2D g2) {
        int castX = (int) Math.round(x);
        int castY = (int) Math.round(y);
        castX-=144;
        castY-=144;
        int castScaleX = (int) Math.round(imageWidth*scale);
        int castScaleY = (int) Math.round(imageHeight*scale);
        g2.drawImage(image,castX,castY,castScaleX,castScaleY,null);
    }
    
    public boolean front() {
        return higherSelection;
        // true = sun first
        // false = earth first
    }
    
    
}
