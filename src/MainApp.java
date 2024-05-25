import javax.swing.JFrame;

public class MainApp {
    public static void main(String[] args) throws Exception {
        
        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        //Add and set the Game Panel to the window/ jFrame
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
    
    // public Block (Color c) {
    //     this.c = c;
    // }
}
