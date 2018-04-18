package templeengine.src.core;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.util.ArrayList;

/**
 * The {@code Animation} class holds an array of {@link Frame}s, which form an animation.
 *
 * <p>
 * With this class you can easily create animations out of spritesheets.
 *
 * As an example, lets say your idle spritesheet is arranged in a 4x2 layout.
 * You would create an animation out of that like this:
 * </p>
 * <pre><code>
 * //inside a GameObject class method
 * idle = new Animation(new Image("templeengine/examples/puzzle/res/player.png"),true);
 * //set the split 2 rows and 4 columns with each frame taking 60 / 4 (15) frames to change to the next one.
 * idle.split(2,4,60 / 4);
 * //change the playing animation
 * changeAnimation(idle);
 * </code></pre>
 */
public class Animation {

    /**
     * Whether to loop the animation on end or not.
     */
    public boolean isLooping = false;
    /**
     * Spritesheet for the animation.
     */
    private Image spriteSheet;
    /**
     * The array of frames for this animation.
     */
    private ArrayList<Frame> frameData;

    /**
     * Constructs an animation with the given parameters.
     *
     * @param spriteSheet the sheet for the animation.
     * @param isLooping whether animation loops or not.
     */
    public Animation(Image spriteSheet, boolean isLooping) {

        frameData = new ArrayList<>();
        this.spriteSheet = spriteSheet;
        this.isLooping = isLooping;
    }

    /**
     * Adds a frame to the end of the animation.
     *
     * @param data to add.
     */
    public void addFrame(Frame data) { frameData.add(data); }
    /**
     * Gets a frame.
     *
     * @param index of the frame.
     * @return the frame.
     */
    public Frame getFrame(int index) { return frameData.get(index); }
    /**
     * Removes a frame.
     *
     * @param index to remove.
     */
    public void removeFrame(int index) { frameData.remove(index); }
    /**
     * Clears the frames from the animation
     */
    public void clear() { frameData.clear(); }
    /**
     * Gets the amount of frames in the animation.
     *
     * @return the frame count.
     */
    public int getFrameCount() { return frameData.size(); }

    /**
     * Sets the spritesheet.
     *
     * @param spriteSheet the spritesheet.
     */
    public void setSheet(Image spriteSheet) { this.spriteSheet = spriteSheet; }
    /**
     * Gets the spritesheet.
     *
     * @return the spritesheet.
     */
    public Image getSheet() { return spriteSheet; }

    /**
     * Splits the current animation into specified rows and columns with a set duration on each frame.
     *
     * @param rows to split into.
     * @param columns to split into.
     * @param duration for the frames.
     */
    public void split(int rows, int columns, int duration) {

        if(duration <= 0)
            duration = 1;

        double width = spriteSheet.getWidth() / columns;
        double height = spriteSheet.getHeight() / rows;

        for(int i = 0; i < rows; i++) {

            for(int j = 0; j < columns; j++) {

                addFrame(new Frame(new Rectangle2D(j * width, i * height, width, height), duration));
            }
        }
    }
}
