package UI.button;

import java.awt.*;
import java.awt.event.MouseEvent;

public interface Button {
    void setPosition (int x, int y);
    void draw (Graphics g);
    void update ();
    void  onMouseRelease (MouseEvent e);
    void onMouseClick(MouseEvent e);
    void onMouseEnter (MouseEvent e);
    void onMouseDragged(MouseEvent e);
    boolean isMouseEnter(MouseEvent e);
    int getWidth ();
    int getHeight();
    void resetState ();

}
