package Entity.Player;

import Utils.LoadSave;

import java.awt.image.BufferedImage;

public class PlayerConstants {
    public enum Types {
        BOMBER,
        BALD_PIRATE,
        CUCUMBER,
        BIG_GUY;

    }

    public enum States {
        IDLE,
        TURN_LEFT,
        TURN_RIGHT,
        RUN,
        JUMP,
        FALL,
        ATTACK,
        DIE,
        HIT,
        GROUND;

    }



    public static int GetSpriteSpeed (Types type, States state) {
        int speed = 0;
        switch (type) {
            case BOMBER, BALD_PIRATE, CUCUMBER, BIG_GUY -> {
                speed = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT, HIT, ATTACK, GROUND, DIE -> 0;
                    case RUN, JUMP, FALL -> 4;
                };
            }
        }
       return speed;
    }

    public static int GetSpriteAnimationFrame (Types type, States state) {
        int frame = 1;

        switch (type) {
            case BOMBER -> {
                frame = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT -> 26;
                    case RUN-> 14;
                    case JUMP, GROUND -> 4;
                    case FALL -> 2;
                    case HIT -> 8;
                    case ATTACK -> 1;
                    case DIE -> 6;
                };
            }
            case BIG_GUY -> {
                frame = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT -> 38;
                    case RUN-> 16;
                    case JUMP, GROUND -> 4;
                    case FALL -> 2;
                    case HIT -> 8;
                    case ATTACK -> 11;
                    case DIE -> 6;
                };
            }
            case CUCUMBER -> {
                frame = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT -> 33;
                    case RUN-> 12;
                    case JUMP, GROUND -> 4;
                    case FALL -> 2;
                    case HIT -> 8;
                    case ATTACK -> 11;
                    case DIE -> 6;
                };
            }
            case BALD_PIRATE -> {
                frame = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT -> 34;
                    case RUN-> 14;
                    case JUMP, GROUND -> 4;
                    case FALL -> 2;
                    case HIT -> 8;
                    case ATTACK -> 12;
                    case DIE -> 6;
                };
            }
        }

        return frame;
    }

    public static int GetSpriteIndex (Types type, States state) {
        int index = 0;
        switch (type) {
            case BOMBER -> {
                index = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT -> 0;
                    case RUN -> 1;
                    case JUMP -> 2;
                    case FALL -> 3;
                    case HIT -> 4;
                    case DIE -> 5;
                    case GROUND -> 6;
                    case ATTACK -> 10;
                };
            }
            case BIG_GUY,  BALD_PIRATE, CUCUMBER  -> {
                index = switch (state) {
                    case IDLE, TURN_LEFT, TURN_RIGHT -> 0;
                    case RUN -> 1;
                    case JUMP -> 2;
                    case FALL -> 3;
                    case HIT -> 6;
                    case DIE -> 7;
                    case GROUND -> 8;
                    case ATTACK -> 5;
                };
            }
        }
        return index;
    }

    public static BufferedImage getSpriteImage (Types type) {
       return switch (type) {
            case BOMBER -> LoadSave.loadImage(LoadSave.PLAYER_BOMBER);
           case BALD_PIRATE -> LoadSave.loadImage(LoadSave.PLAYER_BALD_PIRATE);
           case CUCUMBER -> LoadSave.loadImage(LoadSave.PLAYER_CUCUMBER);
           case BIG_GUY -> LoadSave.loadImage(LoadSave.PLAYER_BIG_GUY);
       };
    }

    public static int[] getSpriteSize (Types type) {
        return switch (type) {
            case BOMBER -> new int[]{58, 58};
            case BALD_PIRATE -> new int[]{63, 67};
            case CUCUMBER -> new int[] {64, 68};
            case BIG_GUY -> new int[] {77, 74};
        };
    }


}
