package templeengine.examples.asteroids;

/**
 * The {@code Main} class of the game.
 *
 * <p>
 * Here we start the java program, by making a new instance of the AsteroidGame,
 * which then wakes the Temple engine up, which starts the JavaFX app, and finally calls load() on the game.
 *
 * From the main class you can set a name for the game and camera width/height, and scene width/height.
 * </p>
 */
public class Main {

    /**
     * The starting point of the game. Creates a new instance of the AsteroidGame.
     *
     * @param args not used.
     */
    public static void main(String... args) {

        new AsteroidsGame("LOVE MAX!!!!!! HYPER ULTRA STREET FIGHTER: ADVANCED EDITION: ARCADE: XXGGACR++ REV 2", 720, 720, 3072, 1234);
    }
}
