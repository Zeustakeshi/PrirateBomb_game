package UI.Boards;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Board {
    void setPosition (int x, int y);
    void draw (Graphics g);
    void update ();
    int getWidth();
    int getHeight();
    int getX ();
    int getY ();
    void onMouseClick (MouseEvent e);
    void onMouseRelease (MouseEvent e);
    void onMouseEnter (MouseEvent e);
    void onMouseDragged (MouseEvent e);
    void onKeyPress (KeyEvent e);
    void onKeyRelease (KeyEvent e);

}
