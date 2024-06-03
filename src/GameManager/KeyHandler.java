package GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean upPressesd, downPressed, leftPressed, rightPressed, pausedPressed;

    @Override
    public void keyTyped(KeyEvent e) {
      
    }

    @Override
    //Logic to use the arrow keys to move the mino
    public void keyPressed(KeyEvent e) {
        
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_UP){
            upPressesd = true ;
        }
        if (code ==KeyEvent.VK_DOWN) {
            downPressed = true ;
        }
        if (code == KeyEvent.VK_LEFT) {
            leftPressed = true;
        }
        if (code == KeyEvent.VK_RIGHT ) {
            rightPressed = true;
        }
        if (code == KeyEvent.VK_SPACE) {
            if (pausedPressed) {
                pausedPressed = false;
                GamePanel.music.play(0, true);
                GamePanel.music.loop();
            }
            else {
                pausedPressed = true;
                GamePanel.music.stop();

            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    
}
