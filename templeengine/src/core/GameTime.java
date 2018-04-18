package templeengine.src.core;

/**
 * The {@code GameTime} class is a seperate tick updater from the main JavaFX app.
 *
 * <p>
 * The purpose of this class is to provide the user with more precise ways to track time in the game,
 * and to provide frame based animations rather than time based.
 *
 * JavaFX application runs at the desired speed of 60fps but can fluctuate above if the machine is faster.
 * GameTime timer attempts to run at only 60fps rather than the maximum possible fps.
 *
 * GameTime class keeps count of the passed frames in the game, allowing the user to time events based on the frames passed
 * rather than the time passed. This is useful in games like fighting games where moves are frame based rather than time.
 *
 * The user can also alter the scale of time, slowing the game down or making it faster if the machine can handle it.
 *
 * Example code of making something happen in cycles of 2 seconds:
 * </p>
 * <pre><code>
 * if(lastFrame + 120 less= GameTime.frameCount()) { //since the timer runs at 60fps 120 frames = 2 seconds
 *
 *  //do thing
 *
 *  lastFrame = GameTime.frameCount();
 * }
 * </code></pre>
 */
public class GameTime {

    /**
     * The frame count since the start of the game.
     */
    private static int frameCount;
    /**
     * Frame counting for fps calculation.
     */
    private static int frameCountS = 0;

    /**
     * Duration of a single frame.
     */
    private static long frameDuration = 1000000000 / 100;

    /**
     * Last time a frame was passed.
     */
    private static long lastTime;
    /**
     * Last time a frame was passed for fps calc.
     */
    private static long lastTimeS;
    /**
     * Last time a frame was passed for fps calc.
     */
    private static long lastTimeStart;

    /**
     * The time scale of the timer, lower values mean slower.
     */
    private static float timeScale = 1.0f;

    /**
     * Current fps of the timer.
     */
    private static int fps;

    /**
     * Constructs a timer.
     */
    public GameTime()
    {
        frameCount = 0;

        lastTime = System.nanoTime();
        lastTimeS = 0;
        lastTimeStart = System.nanoTime();
    }

    /**
     * Sets the timescale. Lower values mean slower time.
     *
     * @param scale the scale.
     */
    public static void setTimeScale(float scale)
    {
        if(scale <= 0 || scale > 100)
            return;

        timeScale = scale;

        frameDuration = (long) ((1000000000 / 100) / timeScale);
    }

    /**
     * Gets the timescale.
     *
     * @return timescale.
     */
    public static float getTimeScale() { return timeScale; }

    /**
     * Updates the timer.
     */
    public void update()
    {
        if(lastTimeS >= 1000000000.0) {

            fps = (int) (frameCountS / (lastTimeS / 1000000000));

            frameCountS = 0;
            lastTimeS = 0;
            lastTimeStart = System.nanoTime();
        }

        if(System.nanoTime() - lastTime > frameDuration)
        {
            frameCount++;
            frameCountS++;
            lastTime = System.nanoTime();
        }

        lastTimeS = lastTime - lastTimeStart;
    }

    /**
     * Gets the fps.
     *
     * @return fps.
     */
    public static int fps() {

        return fps;
    }

    /**
     * Gets the frame count since the start of the game.
     *
     * @return frame count.
     */
    public static int frameCount() {

        return frameCount;
    }
}
