package GameStates;

public enum GameStates {
    DASHBOARD,
    HELP,
    INFO,
    OPTIONS,
    QUIT,
    JOIN_ROOM,
    WAITING_ROOM,
    CREATE_ROOM,
    PLAYING,
    TESTING,
    LOADING,
    FINISH;

    public static GameStates state = DASHBOARD;
}
