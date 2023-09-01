package ColliderBox;

import java.awt.*;

public interface ColliderBox<Type> {
    boolean intersects (Type other);
    void draw (Graphics g);
    void draw (Graphics g, int levelOffsetX);
    void draw (Graphics g, int levelOffsetX, Color color);
    void update (double x, double y, double width, double height);
}
