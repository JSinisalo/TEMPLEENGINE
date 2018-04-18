package templeengine.examples.puzzle;

import javafx.scene.shape.Shape;
import templeengine.src.core.CollisionRectangle;
import templeengine.src.core.GameObject;

/**
 * The {@code DeathTrigger} is the edge block in the game.
 *
 * <p>
 * Resets the player to its initial position when hit by it.
 * </p>
 */
public class DeathTrigger extends GameObject {

    /**
     * Constructs the trigger.
     *
     * @param name of the trigger.
     * @param width of the trigger.
     * @param height of the trigger.
     */
    public DeathTrigger(String name, double width, double height)
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

            hitObject.setXY(((Player) hitObject).getInitialPositionX(),((Player) hitObject).getInitialPositionY());
            hitObject.setVelocityX(0);
            hitObject.setVelocityY(0);
        }
    }
}
