package templeengine.src.core;

import javafx.application.Application;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * The {@code Temple} is a JavaFX {@link Application}, basically the main class of the engine itself.
 *
 * <p>
 * This class is where the engine starts up the JavaFX app, starts up a game loop and creates the GameCanvas
 * of the game being played.
 * </p>
 *
 */
public class Temple extends Application
{
    /**
     * A flag used in various places. Setting to true will show CollisionInterfaces and QuadTree stuff.
     */
    public static boolean debug = false;

    /**
     * The root of the main javafx scene.
     */
    private static final AnchorPane root = new AnchorPane();
    /**
     * The javafx scene of the app.
     */
    private static Scene scene;

    /**
     * The input class of the engine. Captures keystrokes and sets flags inside the Input class.
     */
    private Input input;
    /**
     * The current game being played.
     */
    private static GameCanvas activeGame = null;
    /**
     * The game loop.
     */
    private GameLoop loop;

    /**
     * The starting point of any JavaFX application.
     *
     * Creates the game loop, creates the GameCanvas, creates the camera of the game.
     * Sets up keycapturing for the input class.
     *
     * @param stage of the app.
     */
    @Override
    public void start(Stage stage)
    {
        Runnable updater = () -> activeGame.update();
        Runnable fupdater = () -> activeGame.fixedUpdate();
        //Runnable renderer = () -> activeGame.render();

        loop = new GameLoop(updater, fupdater);

        scene = new Scene(root, activeGame.getCameraWidth(), activeGame.getCameraHeight());

        input = new Input();

        new GameCamera(activeGame.getCameraWidth(), activeGame.getCameraHeight());

        scene.setOnKeyPressed((e) -> input.keyPressed(e));
        scene.setOnKeyReleased((e) -> input.keyReleased(e));

        //TODO: figure out the mystery of parallelcamera doing some freaky shit
        PerspectiveCamera camera;
        scene.setCamera(camera = new PerspectiveCamera());
        GameCamera.setCamera(camera);

        stage.setScene(scene);
        stage.setTitle(activeGame.getName());
        stage.setResizable(false);
        stage.show();

        play();
    }

    /**
     * Calls for GameCanvas.load(), which starts the game itself and afterwards starts the game loop.
     */
    private void play()
    {
        Pane pane = new Pane();
        activeGame.load(pane);
        root.getChildren().add(0, pane);
        loop.start();
    }

    /**
     * Sets the game to start and calls for the JavaFX app to launch.
     *
     * @param game the game to start.
     */
    public static void enter(GameCanvas game) {

        activeGame = game;
        launch();
    }

    /**
     * Gets the game being played.
     *
     * @return activeGame the game being played.
     */
    public static GameCanvas getActiveGame() {

        return activeGame;
    }

    /**
     * Gets the main scene of the game.
     *
     * @return scene of the game.
     */
    public static Scene getScene() {

        return scene;
    }
}
