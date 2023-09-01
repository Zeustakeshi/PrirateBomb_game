package Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_BOMBER = "players/bomber.png";
    public static final String PLAYER_BALD_PIRATE = "players/bald_pirate.png";
    public static final String PLAYER_BIG_GUY = "players/big_guy.png";
    public static final String PLAYER_CUCUMBER = "players/cucumber.png";


    public static final String BUTTON = "components/buttons/button.png";
    public static final String SMALL_BUTTON = "components/buttons/small_button.png";
    public static final String BIG_TEXT = "components/texts/big_text.png";
    public static final String SMALL_TEXT = "components/texts/small_text.png";

    public static final String SMALL_ICON = "components/texts/small_icon.png";
    public static final String DASHBOARD_MENU = "components/menus/dashboard_menu.png";

    public static final String WAITING_ROOM_BOARD = "components/menus/waiting_room.png";
    public static final String CREATE_ROOM_BOARD = "components/menus/create_room.png";

    public static final String ENTER_ROOM_ID_BOARD = "components/menus/enterRoomIdBoard.png";

    public static final String BACKGROUND_MENU = "more/background_menu.png";
    public static final String MAP_1 = "more/maps/level_one_data.png";
    public static final String MAP_DATA = "more/maps/outside_sprites.png";
    public static final String PLAYING_BACKGROUND = "more/maps/playing_bg_img.png";
    public static final String MAP_ENVIRONMENT_BIG_CLOUD = "more/maps/big_clouds.png";
    public static final String MAP_ENVIRONMENT_SMALL_CLOUD = "more/maps/small_clouds.png";

    // Game objects
    public static final String OBJECT_BOMB = "objects/boom.png";

    public static BufferedImage loadImage(String src) {

        InputStream inputStream = LoadSave.class.getResourceAsStream("/" + src);


        BufferedImage img = null;
        try {
            if (inputStream != null) {
                img = ImageIO.read(inputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

}
