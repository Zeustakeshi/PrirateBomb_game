package Objects.Bomb;

public class BombConstants {
    public enum States {
        IDLE,
        DETONATION
    }

    public static int getObjectAnimationFrame (States state) {
        return switch (state) {
            case IDLE -> 1;
            case DETONATION -> 10;
        };
    }

    public static int getObjectIndex (States state) {
        return switch (state) {
            case IDLE -> 0;
            case DETONATION -> 1;
        };
    }
}
