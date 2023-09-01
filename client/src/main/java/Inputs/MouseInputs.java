package Inputs;

import GameStates.GameStates;
import Main.GamePanel;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
public class MouseInputs implements MouseListener, MouseMotionListener  {
    private GamePanel gamePanel;
    public MouseInputs(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }



    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.gamePanel.getGame().getCurrentState().mouseClick(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        this.gamePanel.getGame().getCurrentState().mouseRelease(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.gamePanel.getGame().getCurrentState().mouseEnter(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        this.gamePanel.getGame().getCurrentState().mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
