import javax.swing.*;
import java.awt.*;

public class SnakeGame {

    public static void main(String[] args) {

        JFrame frame = new JFrame();

        GamePanel gamePanel = new GamePanel();

        frame.setBounds(10,10,1000,900);
        frame.setBackground(Color.DARK_GRAY);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gamePanel);

    }
}
