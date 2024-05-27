package GameManager;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {

    public static boolean upPressesd, downPressed, leftPressed, rightPressed;

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
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    
}
