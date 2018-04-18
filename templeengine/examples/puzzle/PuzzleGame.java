package templeengine.examples.puzzle;

import templeengine.src.core.*;

/**
 * The {@code PuzzleGame} is the actual main game program.
 *
 * <p>
 * After the constructor has finished calling the engine, the engine will call load(),
 * from which point on the game loop is running and the game can start.
 *
 * You can use this class as the main manager of the game or make your own.
 * </p>
 *
 * <p>
 * This puzzle example game intends to showcase the TileMap feature, along with the usual collisions, sounds, etc.
 * The game starts by loading the TileMap from the level1.map and putting a few extra things, like triggers on the side
 * to reset the game if you hit them, and the end tile to go to the next level.
 * </p>
 */
public class PuzzleGame extends GameCanvas {

    /**
     * The map of the game.
     */
    private TileMap map;
    /**
     * The next level number.
     */
    private int next = 1;

    /**
     * Constructs the game and starts the Temple engine.
     *
     * @param name of the game.
     * @param cameraWidth of the game.
     * @param cameraHeight of the game.
     * @param sceneWidth of the game.
     * @param sceneHeight of the game.
     */
    public PuzzleGame(String name, double cameraWidth, double cameraHeight, double sceneWidth, double sceneHeight) {

        super(name, cameraWidth, cameraHeight, sceneWidth, sceneHeight);

        Temple.enter(this);
    }

    /**
     * Called from the Temple engine once it has finished setting up. This is the starting point of your game.
     */
    @Override
    public void load() {

        GameSound.playMusic("res/tubes.mp3", getClass());
        loadNext();
    }

    /**
     * Loads the next map according to next variable.
     */
    public void loadNext() {

        map = null;
        clearObjects();

        BackgroundThing thing = new BackgroundThing("thing");
        addObject(thing);
        TileMap.setPositionRelativeToMap(thing, 2, 3, 32, 32, 2.0);
        addObject(thing = new BackgroundThing("thing"));
        TileMap.setPositionRelativeToMap(thing, 3, 7, 32, 32, 2.0);

        map = GameIO.loadMap("res/maps/level" + next,16,16, getClass());

        Player player = new Player("GOD", this);
        EndTrigger end = new EndTrigger("DEVIL", 64, 64);

        switch(next) {

            case 1:

                map.setPositionRelativeToMap(end, 5, 1);
                map.setPositionRelativeToMap(player, 2, 2);
                player.setInitialPosition(player.getX2(), player.getY2());

                break;

            case 2:

                map.setPositionRelativeToMap(end, 2, 1);
                map.setPositionRelativeToMap(player, 7, 6);
                player.setInitialPosition(player.getX2(), player.getY2());
                next = 0;

                break;
        }

        addObject(player);
        addObject(end);
        addDeathBoundaries();

        next++;
    }

    /**
     * Adds triggers to the sides.
     */
    private void addDeathBoundaries() {

        DeathTrigger death = new DeathTrigger("DEATH1",640, 64);
        addObject(death);
        map.setPositionRelativeToMap(death, 0, -1);
        addObject(death = new DeathTrigger("DEATH2", 640, 64));
        map.setPositionRelativeToMap(death, 0, 10);
        addObject(death = new DeathTrigger("DEATH3", 64, 640));
        map.setPositionRelativeToMap(death, -1, 0);
        addObject(death = new DeathTrigger("DEATH4", 64, 640));
        map.setPositionRelativeToMap(death, 10, 0);
    }
}