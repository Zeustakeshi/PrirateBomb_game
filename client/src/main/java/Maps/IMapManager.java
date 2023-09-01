package Maps;

import java.awt.*;

public interface IMapManager {
    IMap getCurrentMap ();
    void setCurrentMap (int index);
    void draw (Graphics g);
    void update();

}
