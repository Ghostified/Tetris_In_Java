package GameManager;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel  extends JPanel implements Runnable{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 720;
    final int FPS = 60; //refresh rate of the screen per second
    Thread gameThread; // thread to run the game loop
    PlayManager playManager;
    public static Sound music = new Sound ();
    public static Sound soundEffect = new Sound ();


    public GamePanel() {

        //Panel Settings
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBackground(Color.BLACK);
        this.setLayout(null);

        //Adding the key listener to the panel to listen to the key events that moves the mino
        this.addKeyListener(new KeyHandler());
        this.setFocusable(true);

        playManager = new PlayManager();
    }

    //loop to run the game
    public void launchGame() {
        gameThread = new Thread(this);
        gameThread.start();

        //Play the background music
        music.play(0, true);
        music.loop();
    }



    //Create a run method to run the game loop
    @Override
    public void run () {
        //Game Loop that runs the game at a specific FPS
        double drawInterval = 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
        
    }

    //Method to update the game
    private void update() {
        //call playManager update method 
        //we only update the screen information if the game is not paused
        if (KeyHandler.pausedPressed == false && playManager.gameOver == false) {
            playManager.update();
        }
        
    }

    public void paintComponent (Graphics g) {
        //parsre the graphics object to the playManager draw method
        super.paintComponent(g);

        //call the draw method
        Graphics2D g2 = (Graphics2D)g;
        playManager.draw(g2);
    }

}
