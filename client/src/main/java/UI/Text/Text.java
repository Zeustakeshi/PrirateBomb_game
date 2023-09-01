package UI.Text;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public interface Text {
    BufferedImage[] getImages (String text);
    void draw (Graphics g, int x, int y);
    void update ();
    String getText();
    void setText (String newText);
    int getWidth ();
    int getHeight();

    static List<Integer> StringToIndex(String text) {
        List<Integer> array = new ArrayList<>();
        text = text.toLowerCase(Locale.ROOT);

        for (int i = 0; i < text.length(); ++i) {
            if (text.charAt(i) >= 'a' && text.charAt(i) <= 'z') {
                array.add((text.charAt(i) - 'a'));
            }else if (text.charAt(i) >= '0' && text.charAt(i) <= '9') {
                array.add((text.charAt(i) - '0' + 'z' - 'a' + 1));
            }else if (text.charAt(i) == 32) {
                array.add(36);
            }
        }

        return array;
    }

    void setSpace (int space);

}
