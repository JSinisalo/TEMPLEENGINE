package templeengine.src.core;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * The {@code GameSound} lets the user easily play a sound of their choice.
 *
 * <p>
 * Example code to play a sound:
 * </p>
 * <pre><code>
 * GameSound.playSound("res/sound.wav", getClass());
 * </code></pre>
 */
public class GameSound {

    /**
     * The player for the sound.
     */
    private static MediaPlayer player;
    /**
     * The player for the music.
     */
    private static MediaPlayer mPlayer;

    /**
     * Plays the desired sound.
     *
     * @param fileName where the sound is located relative to the class file.
     * @param c the class the sound is played from.
     */
    public static void playSound(String fileName, Class c){

        Media m = new Media(c.getResource(fileName).toString());
        player = new MediaPlayer(m);
        player.play();
    }

    /**
     * Plays the desired music.
     *
     * @param fileName where the sound is located relative to the class file.
     * @param c the class the sound is played from.
     */
    public static void playMusic(String fileName, Class c) {

        Media m = new Media(c.getResource(fileName).toString());
        mPlayer = new MediaPlayer(m);
        mPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mPlayer.play();
    }

    /**
     * Pauses music.
     */
    public static void pauseMusic() {

        mPlayer.pause();
    }

    /**
     * Resumes music.
     */
    public static void resumeMusic() {

        mPlayer.play();
    }
}
