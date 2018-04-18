package templeengine.examples.puzzle;

import javafx.scene.shape.Shape;
import templeengine.src.core.CollisionRectangle;
import templeengine.src.core.GameObject;

/**
 * The {@code EndTrigger} is the ending block in the game.
 *
 * <p>
 * Resets the map when hit by the player.
 * </p>
 */
public class EndTrigger extends GameObject {

    /**
     * Constructs the trigger.
     *
     * @param name of the trigger.
     * @param width of the trigger.
     * @param height of the trigger.
     */
    public EndTrigger(String name, double width, double height)
    {
        this.name = name;

        addCollider(new CollisionRectangle(this, 0, 0, width, height));
        this.isCollidable = true;
        this.isTrigger = true;
        this.isStatic = true;
    }

    /**
     * Called by the engine, when this or something else enters the trigger area.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    @Override
    public void onTrigger(Shape intersection, GameObject hitObject) {

        if(hitObject instanceof Player) {

            ((Player) hitObject).loadNext();
            this.isCollidable = false;
        }
    }
}
