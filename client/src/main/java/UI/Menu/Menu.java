package UI.Menu;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public interface Menu {
    void loadButtons ();
    void loadBackground();
    void draw (Graphics g);
    void draw (Graphics g, int offset);
    void update ();
    void onMouseClick (MouseEvent e);
    void onMouseRelease (MouseEvent e);
    void onMouseEnter (MouseEvent e);
    void onMouseDragged (MouseEvent e);
    void onKeyPress (KeyEvent e);
    void onKeyRelease (KeyEvent e);

}
