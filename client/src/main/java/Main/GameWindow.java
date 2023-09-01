package Main;

import javax.swing.*;

public class GameWindow {
    public GameWindow (GamePanel gamePanel) {
        JFrame jFrame = new JFrame();

        jFrame.setTitle("Pirate Bomb");
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.add(gamePanel);
        jFrame.pack();
        jFrame.setResizable(false);
        jFrame.setVisible(true);
        jFrame.setLocationRelativeTo(null);
    }
}
