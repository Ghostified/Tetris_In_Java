import javax.swing.JFrame;

public class MainApp {
    public static void main(String[] args) throws Exception {
        
        JFrame window = new JFrame("Simple Tetris");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);

        window.setLocationRelativeTo(null);
        window.setVisible(true);

    }
}
