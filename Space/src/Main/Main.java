package Main;


import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;

/*
if you want to set random positions so you don't have to wait, then go under
the UI.java and set the IntialRandomPosition to true
*/

public class Main extends JFrame {
    
    UI ui;
    
    public Main() {
        
        
        
       // Add a ComponentListener to the frame to maximise the screen
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if ((getExtendedState() & JFrame.MAXIMIZED_BOTH) == 0) {
                    // Set the desired resolution when the frame is restored down
                    setSize(1600, 1200);  // Replace with your desired resolution
                }
            }
        });
        
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;
        
        System.out.println(screenWidth);
        System.out.println(screenHeight);
        
        ui = new UI(screenWidth, screenHeight);
        
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
        add(ui);
        setResizable(true);
        setTitle("Space");
        

    }
    
    public static void main(String[] args) {
        new Main();
    }
    
}
