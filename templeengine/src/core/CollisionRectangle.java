package templeengine.src.core;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * The {@code CollisionRectangle} is a rectangle shaped collider.
 *
 * <p>
 * This class extends the JavaFX shape rectangle.
 *
 * You can have multiple colliders on a single GameObject, and you can position their origins so they won't overlap,
 * although overlapping isn't an issue either, since colliders from the same object wont collide with each other.
 *
 * Example code of adding a collider to a GameObject:
 * </p>
 * <pre><code>
 * //inside a GameObject class method
 * addCollider(new CollisionRectangle(this, 0, 0, 64, 64));
 * </code></pre>
 */
public class CollisionRectangle extends Rectangle implements CollisionInterface {

    /**
     * OriginX of the collider.
     */
    private double originX;
    /**
     * OriginY of the collider.
     */
    private double originY;

    /**
     * Parent GameObject of the collider.
     */
    private GameObject parent;

    /**
     * Constructs the collider.
     *
     * With Temple.debug = true, you can see the collider.
     *
     * @param parent parent of this collider.
     * @param originX origin x.
     * @param originY origin y.
     * @param width width of the collider.
     * @param height height of the collider.
     */
    public CollisionRectangle(GameObject parent, double originX, double originY, double width, double height) {

        super(width, height);

        setOriginX(originX);
        setOriginY(originY);

        if(Temple.debug) {
            setFill(new Color(0, 0, 1 ,0.5));
            Temple.getActiveGame().getPane().getChildren().addAll(this);
        }

        this.parent = parent;
    }

    /**
     * Gets the origin x of the collider.
     *
     * @return origin x.
     */
    @Override
    public double getOriginX() { return originX; }

    /**
     * Gets the origin y of the collider.
     *
     * @return origin y.
     */
    @Override
    public double getOriginY() { return originY; }

    /**
     * Sets the origin x of the collider.
     *
     * @param originX .
     */
    @Override
    public void setOriginX(double originX) { this.originX = originX; }

    /**
     * Sets the origin y of the collider.
     *
     * @param originY .
     */
    @Override
    public void setOriginY(double originY) { this.originY = originY; }

    /**
     * Gets the x of the collider.
     *
     * @return x .
     */
    @Override
    public double getCX() { return getX(); }

    /**
     * Gets the y of the collider.
     *
     * @return y .
     */
    @Override
    public double getCY() { return getY(); }

    /**
     * Sets the x of the collider.
     *
     * @param cx .
     */
    @Override
    public void setCX(double cx) { setX(cx); }

    /**
     * Sets the y of the collider.
     *
     * @param cy .
     */
    @Override
    public void setCY(double cy) { setY(cy); }

    /**
     * Gets the height of the collider.
     *
     * @return height .
     */
    @Override
    public double getCHeight() { return getHeight(); }

    /**
     * Gets the width of the collider.
     *
     * @return width .
     */
    @Override
    public double getCWidth() { return getWidth(); }

    /**
     * Gets the shape of the collider.
     *
     * @return shape .
     */
    @Override
    public Shape getShape() { return this; }

    /**
     * Gets the parent of the collider.
     *
     * @return parent .
     */
    @Override
    public GameObject getParentObject() { return parent; }
}
