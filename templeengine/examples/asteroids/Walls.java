package templeengine.examples.asteroids;

import templeengine.src.core.CollisionRectangle;
import templeengine.src.core.GameObject;

/**
 * The {@code Walls} is the edge block in the game.
 *
 * <p>
 * Prevents passage.
 * </p>
 */
public class Walls extends GameObject {

    /**
     * Constructs the wall.
     *
     * @param name of the wall.
     * @param width of the wall.
     * @param height of the wall.
     */
    public Walls(String name, double width, double height)
    {
        this.name = name;

        addCollider(new CollisionRectangle(this, 0, 0, width, height));
        this.isCollidable = true;
        this.isTrigger = false;
        this.isStatic = true;
    }
}

