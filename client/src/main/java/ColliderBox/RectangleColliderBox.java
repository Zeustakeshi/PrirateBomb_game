package ColliderBox;
import java.awt.*;
import java.awt.geom.Rectangle2D;

public class RectangleColliderBox extends Rectangle2D.Double implements ColliderBox<RectangleColliderBox> {
    public RectangleColliderBox (double x, double y, double w, double h) {
        super(x, y, w, h);
    }
    public RectangleColliderBox () {super(0, 0, 20, 20);}


    @Override
    public boolean intersects(RectangleColliderBox object) {
        return isInsertCollision(
                this.x,
                this.y,
                this.width,
                this.height,
                object.x,
                object.y,
                object.width,
                object.height
        );
    }



    public static boolean intersects(RectangleColliderBox x1, RectangleColliderBox x2) {
        return x1.intersects(x2);
    }


    private boolean isInsertCollision(
            double x1, double y1, double w1, double h1, double x2, double y2, double w2, double h2
    ) {
        return (x1 <= x2 + w2 && x1 >= x2 - w1 && y1 - h2 <= y2 && y1 >= y2 - h2);
    }

    @Override
    public void draw (Graphics g, int levelOffsetX) {
        g.setColor(Color.PINK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawRect(
                (int) this.x - levelOffsetX,
                (int) this.y,
                (int) this.width,
                (int) this.height
        );
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.PINK);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawRect(
                (int) this.x,
                (int) this.y,
                (int) this.width,
                (int) this.height
        );
    }

    @Override
    public void draw (Graphics g, int levelOffsetX, Color color) {
        g.setColor(color);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(2));
        g.drawRect(
                (int) this.x - levelOffsetX,
                (int) this.y,
                (int) this.width,
                (int) this.height
        );
    }

    @Override
    public void update (double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

}
