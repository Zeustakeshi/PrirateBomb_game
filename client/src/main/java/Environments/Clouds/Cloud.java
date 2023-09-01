package Environments.Clouds;

import java.awt.*;

public abstract class Cloud {
    protected abstract void setSizeDefault (int defaultWidth, int defaultHeight);
    public abstract void draw (Graphics g);
    public abstract void update();

}
