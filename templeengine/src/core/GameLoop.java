package templeengine.src.core;

import javafx.animation.AnimationTimer;
import java.util.function.Consumer;

/**
 * The {@code GameLoop} class runs the innermost JavaFX app game loop.
 */
public class GameLoop extends AnimationTimer {

    /**
     * The GameTime timer to run along with the GameLoop.
     */
    private GameTime timer;
    /**
     * Runner for the updater.
     */
    private final Runnable updater;
    /**
     * Runner for the fixed updater.
     */
    private final Runnable fupdater;

    /**
     * Constructs the runners and the GameTime.
     *
     * @param updater runner for updater.
     * @param fupdater runner for fixed updater.
     */
    public GameLoop(Runnable updater, Runnable fupdater)
    {
        timer = new GameTime();
        this.updater = updater;
        this.fupdater = fupdater;
    }

    /**
     * Timestep for the game loop. 0.0166f = 60fps
     */
    private static final float timeStep = 0.0166f;

    /**
     * Previous time the animationtimer updated.
     */
    private long previousTime = 0;
    /**
     * Time accummulated since last runner update.
     */
    private float accumulatedTime = 0;

    //private float secondsElapsedSinceLastFpsUpdate = 0f;
    //private int framesSinceLastFpsUpdate = 0;

    /**
     * Handles the animationTimer update, updates the runners if enough time has passed.
     *
     * @param currentTime the current time.
     */
    @Override
    public void handle(long currentTime)
    {
        if (previousTime == 0) {
            previousTime = currentTime;
            return;
        }

        float secondsElapsed = (currentTime - previousTime) / 1e9f;
        float secondsElapsedCapped = Math.min(secondsElapsed, timeStep / GameTime.getTimeScale());
        accumulatedTime += secondsElapsedCapped;
        previousTime = currentTime;

        while (accumulatedTime >= timeStep / GameTime.getTimeScale()) {

            //TODO: make the fixed and normal update actually different heh heh
            timer.update();
            fupdater.run();
            updater.run();
            accumulatedTime -= timeStep / GameTime.getTimeScale();
        }

        //secondsElapsedSinceLastFpsUpdate += secondsElapsed;
        //framesSinceLastFpsUpdate++;

        //if (secondsElapsedSinceLastFpsUpdate >= 1.0f) {

            //int fps = Math.round(framesSinceLastFpsUpdate / secondsElapsedSinceLastFpsUpdate);
            //fpsReporter.accept(fps);
            //secondsElapsedSinceLastFpsUpdate = 0;
            //framesSinceLastFpsUpdate = 0;
        //}
    }

    /**
     * Stops the game loop.
     */
    @Override
    public void stop()
    {
        previousTime = 0;
        accumulatedTime = 0;
        //secondsElapsedSinceLastFpsUpdate = 0f;
        //framesSinceLastFpsUpdate = 0;
        super.stop();
    }
}
