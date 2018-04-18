package templeengine.examples.asteroids;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import templeengine.src.core.*;

/**
 * The {@code Asteroid} is the actual main game program.
 *
 * <p>
 * After the constructor has finished calling the engine, the engine will call load(),
 * from which point on the game loop is running and the game can start.
 *
 * You can use this class as the main manager of the game or make your own.
 * </p>
 *
 * <p>
 * This shooter game is meant to showcase camera scrolling features, and GameIO.save/load()
 * and multiple moving objects at the same time.
 * </p>
 */
public class AsteroidsGame extends GameCanvas {

    /**
     * The image of the stage.
     */
    private ImageView stage;
    /**
     * The image of the lose screen.
     */
    private ImageView lose;

    /**
     * Constructs the game and starts the Temple engine.
     *
     * @param name of the game.
     * @param cameraWidth of the game.
     * @param cameraHeight of the game.
     * @param sceneWidth of the game.
     * @param sceneHeight of the game.
     */
    public AsteroidsGame(String name, double cameraWidth, double cameraHeight, double sceneWidth, double sceneHeight) {

        super(name, cameraWidth, cameraHeight, sceneWidth, sceneHeight);

        Temple.enter(this);
    }

    /**
     * Called from the Temple engine once it has finished setting up. This is the starting point of your game.
     */
    @Override
    public void load() {

        stage = new ImageView(new Image("templeengine/examples/asteroids/res/stage.jpg"));

        pane.getChildren().add(stage);

        GameSound.playMusic("res/music.mp3", getClass());
        GameCamera.setBounds(new BoundingBox(0,0,3072 - 720, 1234 - 720));

        reset();
    }

    /**
     * Shows the lose screen.
     */
    public void lose() {

        lose = new ImageView(new Image("templeengine/examples/asteroids/res/lose.jpg"));

        lose.setFitWidth(720);
        lose.setFitHeight(720);
        lose.setTranslateX(GameCamera.getCamera().getTranslateX() - 110);
        lose.setTranslateY(GameCamera.getCamera().getTranslateY());
        lose.setFitWidth(480 * 2);

        GameCamera.centerOnView(lose);

        pane.getChildren().add(lose);
    }

    /**
     * Resets the game.
     */
    public void reset() {

        if(lose != null)
            pane.getChildren().remove(lose);

        GameTime.setTimeScale(1f);

        clearObjects();

        addWalls();

        Player player = new Player("GOD", this);
        player.setXY(3072 / 2, 1234 / 2);
        addObject(player);

        for(int i = 0; i < 4; i++) {
            addEnemy();
        }
    }

    /**
     * Adds an enemy.
     */
    public void addEnemy() {

        if(Utilities.randInt(0, 1) == 1)
            addObject(new Enemy("chun", 200, (double)Utilities.randInt(300,1000)));
        else
            addObject(new Enemy("chun", 2800, (double)Utilities.randInt(300,1000)));
    }

    /**
     * Adds walls to the edges of the stage.
     */
    private void addWalls() {

        Walls wall = new Walls("wall",3072, 200);
        addObject(wall);
        wall.setXY(0,-200);
        addObject(wall = new Walls("wall",3072, 200));
        wall.setXY(0,1234);
        addObject(wall = new Walls("wall",200, 1234));
        wall.setXY(-200,0);
        addObject(wall = new Walls("wall",200, 1234));
        wall.setXY(3072,0);
    }
}