package fr.lightnew.game;

public enum GameStats {
    LOBBY,
    GAME,
    END;

    private static GameStats currentState;

    public static boolean inState(GameStats states) {return (currentState == states);}

    public static void setState(GameStats states) {currentState = states;}

    public static GameStats getState() {return currentState;}
}
