import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

public class GamePanel  extends JPanel{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60; //refresh rate of the screen per second
    Thread gameThread; // thread to run the game loop



    public GamePanel() {

        //Panel Settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);


        //Create a game loop to update the screen at regular intervals
        //eg seting fps to 60
    }

}
