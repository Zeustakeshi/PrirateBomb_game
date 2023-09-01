package Inputs;
import GameStates.GameStates;
import Main.GamePanel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
public class KeyboardInputs implements KeyListener {
    private GamePanel gamePanel;
    public KeyboardInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        this.gamePanel.getGame().getCurrentState().keyTyped(e);

    }

    @Override
    public void keyPressed(KeyEvent e) {
       this.gamePanel.getGame().getCurrentState().keyPress(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        this.gamePanel.getGame().getCurrentState().keyRelease(e);
    }
}
