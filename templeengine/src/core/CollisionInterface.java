package templeengine.src.core;

import javafx.scene.shape.Shape;

/**
 * The {@code CollisionInterface} is the backbone of the collision system.
 *
 * <p>
 * Classes that implement this interface can be added as colliders for GameObjects.
 * </p>
 */
public interface CollisionInterface {

    /**
     * Gets the origin x of the collider.
     *
     * @return origin x.
     */
    public double getOriginX();
    /**
     * Gets the origin y of the collider.
     *
     * @return origin y.
     */
    public double getOriginY();
    /**
     * Sets the origin x of the collider.
     *
     * @param originX .
     */
    public void setOriginX(double originX);
    /**
     * Sets the origin y of the collider.
     *
     * @param originY .
     */
    public void setOriginY(double originY);

    /**
     * Sets the x of the collider.
     *
     * @param cx .
     */
    public void setCX(double cx);
    /**
     * Sets the y of the collider.
     *
     * @param cy .
     */
    public void setCY(double cy);
    /**
     * Gets the x of the collider.
     *
     * @return x .
     */
    public double getCX();
    /**
     * Gets the y of the collider.
     *
     * @return y .
     */
    public double getCY();

    /**
     * Gets the height of the collider.
     *
     * @return height .
     */
    public double getCHeight();
    /**
     * Gets the width of the collider.
     *
     * @return width .
     */
    public double getCWidth();

    /**
     * Gets the shape of the collider.
     *
     * @return shape .
     */
    public Shape getShape();

    /**
     * Gets the parent of the collider.
     *
     * @return parent .
     */
    public GameObject getParentObject();
}

