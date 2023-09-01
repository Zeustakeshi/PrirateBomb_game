package ColliderBox;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class CircleColliderBox extends Ellipse2D.Double implements ColliderBox<CircleColliderBox> {
    public CircleColliderBox (double x, double y, double w, double h) {
        super(x, y, w,h);
    }

    public CircleColliderBox () {super(0, 0, 20, 20);}


    @Override
    public boolean intersects(CircleColliderBox other) {
        return false;
    }


    @Override
    public void draw(Graphics g) {

    }

    @Override
    public void draw(Graphics g, int levelOffsetX) {

    }

    @Override
    public void draw(Graphics g, int levelOffsetX, Color color) {

    }


    @Override
    public void update(double x, double y, double width, double height) {

    }

}
