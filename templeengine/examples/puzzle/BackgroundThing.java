package templeengine.examples.puzzle;

import javafx.scene.image.Image;
import templeengine.src.core.Animation;
import templeengine.src.core.GameObject;

/**
 * The {@code BackgroundThing} is a non interactive animated background decoration.
 *
 * <p>
 * Mainly to showcase the animation.
 * </p>
 */
public class BackgroundThing extends GameObject {

    /**
     * The idle animation.
     */
    private Animation idle;

    /**
     * Constructs the thing.
     *
     * @param name of the thing.
     */
    public BackgroundThing(String name) {

        this.name = name;

        idle = new Animation(new Image("templeengine/examples/puzzle/res/backgroundthing.png"),true);
        idle.split(9,1,60 / 9);

        changeAnimation(idle);

        this.setFitWidth(64 * 5);
        this.setFitHeight(64);

        this.setOpacity(1);
    }
}
