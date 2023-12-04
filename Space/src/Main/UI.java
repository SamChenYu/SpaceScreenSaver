package Main;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JPanel;
import java.util.Random;


public class UI extends JPanel implements Runnable {
    
    int screenWidth;
    int screenHeight;
    
    Thread thread;
    Random r = new Random();
    ArrayList<Rectangle> stars = new ArrayList<>();
    
    ArrayList<Planet> planetList = new ArrayList<>();
    Planet earth, mars, venus;//, saturn, mercury; // NEW PLANETS HERE
    Sun sun;
    boolean fastSwitch = false;
    int clock = 30;
    int marsClock = clock*2;
    int venusClock = clock*4;
    //int saturnClock = clock*5;
    //int mercuryClock = clock*3;
    
    boolean randomInitialPosition = true; // this is just for testing purposes, if you want to make it
    // then just edit this
    
    
    public UI(int screenWidth, int screenHeight) {
        
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        
        earth = new Planet(screenWidth, screenHeight,0.8,18, "/res/exoplanet.png", 288, 288,0.8);
        mars = new Planet(screenWidth, screenHeight, 0.8, 13,"/res/mars.png", 288, 288,0.8);
        venus = new Planet(screenWidth, screenHeight, 0.8, 10,"/res/dryvenuslikeplanet.png", 288, 288,0.8);
        //saturn = new Planet(screenWidth, screenHeight, 0.8, 8,"/res/spherePlanet.png", 630, 630,0.8);
        //mercury = new Planet(screenWidth, screenHeight, 0.8, 15, "/res/machine_world.png", 320, 320,0.8);
        sun = new Sun(screenWidth, screenHeight);
        
        if(randomInitialPosition) {
           earth.x = r.nextInt(screenWidth);
           mars.x = r.nextInt(screenWidth);
           venus.x = r.nextInt(screenWidth);
           //saturn.x = r.nextInt(screenWidth);
           //mercury.x = r.nextInt(screenWidth);
        }
        
        // NEW PLANETS HERE
        
        planetList.add(earth);
        //planetList.add(mercury);
        planetList.add(mars);
        planetList.add(venus);
        //planetList.add(saturn);
        
        
        /*
        PROCESS FOR ADDING NEW PLANETS:
        new constructor
        new timer
        update planetList
        
        newPlanet.update();
        newPlanet.draw(g2);
        
        */
        
        setBackground(new Color(4,10,18));
        
        startThread();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println(e.getX() + " " + e.getY());
                if(e.getX() <=60 && e.getY() <=60) {
                    if(fastSwitch) {
                        clock = 1;
                        marsClock = clock*2;
                        venusClock = clock*4;
                        //saturnClock = clock*5;
                        //mercuryClock = clock*3;
                        fastSwitch = false;
    
                        
                        
                        
                    } else {
                        clock = 30;
                        marsClock = clock*2;
                        venusClock = clock*4;
                        //saturnClock = clock*5;
                        //mercuryClock = clock*3;
                        fastSwitch = true;
                        
                        
                    }
                }
                
                
            }
        });
        
        
        
    }
    
    
    public void addStars() {
        //2190 1164 resolution... roughly
        for(int x = 0; x < screenWidth; x++) {
            for(int y = 0; y < screenHeight; y++) {
                if(r.nextInt(30000) == 1) {
                    stars.add(new Rectangle(x,y,5,5));
                }
            }
        }
        
        
    }
    
    
    public void startThread() {
        thread = new Thread(this);
        thread.start();
    }
    
    
    
    @Override
    public void run() {
        addStars();
        
        
        // NEW PLANETS HERE
        earth.update();
        mars.update();
        venus.update();
        //saturn.update();
        //mercury.update();
        
        long marsTimer = System.currentTimeMillis();
        long venusTimer = System.currentTimeMillis();
        long saturnTimer = System.currentTimeMillis();
        long mercuryTimer = System.currentTimeMillis();
        while(true){
            
            
            
            earth.update();
            
            if(System.currentTimeMillis() - marsTimer > marsClock) {
                mars.update();
                marsTimer = System.currentTimeMillis();
            }
            
            if(System.currentTimeMillis() - venusTimer > venusClock) {
                venus.update();
                venusTimer = System.currentTimeMillis();
            }
            /*
            if(System.currentTimeMillis() - saturnTimer > saturnClock) {
                saturn.update();
                saturnTimer = System.currentTimeMillis();
            }
            
            if(System.currentTimeMillis() - mercuryTimer > mercuryClock) {
                mercury.update();
                mercuryTimer = System.currentTimeMillis();
            }
            */
            // NEW PLANETS HERE
            repaint();
            
            
            try {
                Thread.sleep(clock);
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
            
        }
        
    }
    
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        
        
        
        
        //Draw the stars

        for(int i = 0; i < stars.size(); i++) {
            int x;
            int y;
            if(r.nextInt(200) == 1) {
                x = 7;
                y = 7;
            } else {
                x = 5;
                y = 5;
            }
            g2.setColor(Color.WHITE);
            g2.fillRect(stars.get(i).x, stars.get(i).y, x, y);
        }
        
        
        
        
        /*
            This calculates which planets to draw behind
            the sun and which to draw in front of the sun
        */
        ArrayList<Planet> behind = new ArrayList<Planet>();
        ArrayList<Planet> front = new ArrayList<Planet>();
        
        for(int i=0; i<planetList.size();i++) {
            if(planetList.get(i).front()) {
                front.add(planetList.get(i));
            } else {
                behind.add(planetList.get(i));
            }
        }
        
        for(int i=0; i<behind.size(); i++) {
            behind.get(i).draw(g2);
        }
        
        sun.draw(g2);
        
        for(int i=0; i<front.size(); i++) {
            front.get(i).draw(g2);
        }
        
    }

    
}
