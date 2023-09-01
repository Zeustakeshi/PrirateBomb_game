package UI.Icons;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface Icon {
    enum Icons {
        HOME,
        NEXT,
        DELETE
    }
    void draw (Graphics g, int x, int y);
    void update ();
    int getWidth ();
    int getHeight();

    static int getIconIndex (Icons icon) {
        return switch (icon) {
            case HOME -> 10;
            case NEXT -> 19;
            case DELETE-> 20;
        };
    }

}
