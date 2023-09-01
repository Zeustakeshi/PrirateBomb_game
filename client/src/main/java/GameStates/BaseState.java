package GameStates;


import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
public interface BaseState {
    void update ();
    void draw(Graphics g);
    void mouseClick(MouseEvent e);
    void mouseRelease(MouseEvent e);
    void mouseEnter(MouseEvent e);
    void keyPress(KeyEvent e);
    void keyTyped(KeyEvent e);
    void keyRelease(KeyEvent e);
    void mouseDragged(MouseEvent e);
    void onMounted ();
    void onUnmounted ();
}
