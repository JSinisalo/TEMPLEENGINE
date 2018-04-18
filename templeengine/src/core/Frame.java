package templeengine.src.core;

import javafx.geometry.Rectangle2D;

/**
 * The {@code Frame} contains data, which to make an {@link Animation} out of.
 *
 * <p>
 * This class contains the rectangle which to set the viewport of the imageview of the animation to in this frame.
 * Also contains the duration of the frame.
 * Usually no need to make these yourself, since Animation.split() does that for you.
 * </p>
 */
public class Frame {

    /**
     * The viewport data for the imageview of the animation.
     */
    private Rectangle2D data;
    /**
     * The duration of the animation.
     */
    private int duration;

    /**
     * Constructs a frame with the given parameters.
     *
     * @param data of the frame.
     * @param duration of the frame.
     */
    public Frame(Rectangle2D data, int duration) {

        this.data = data;
        this.duration = duration;
    }

    /**
     * Sets the data for the frame.
     *
     * @param data of the frame.
     */
    public void setData(Rectangle2D data) { this.data = data; }
    /**
     * Gets the data from the frame.
     *
     * @return data of the frame.
     */
    public Rectangle2D getData() { return data; }

    /**
     * Sets the duration of the frame.
     *
     * @param duration of the frame.
     */
    public void setDuration(int duration) { this.duration = duration; }
    /**
     * Gets the duration of the frame.
     *
     * @return duration of the frame.
     */
    public int getDuration() { return duration; }
}
