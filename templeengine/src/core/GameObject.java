package templeengine.src.core;

import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * The {@code GameObject} is a {@link ImageView} used for any game object you want to render on screen.
 *
 * <p>
 * This class utilizes the already powerful {@link ImageView} class and adds in extra
 * functionality for game related functions, such as automatic updates in conjunction with
 * the {@link GameCanvas} class, automatic collision detection and acting upon it, etc.
 * </p>
 *
 * <p>
 * You can find example codes on GameObjects in the examples folder.
 * </p>
 *
 */
public abstract class GameObject extends ImageView {

    /**
     * This {@link ArrayList} holds all the {@link CollisionInterface}s of the GameObject.
     */
    private ArrayList<CollisionInterface> colliders;

    /**
     * Used for rotating the object.
     */
    protected double angle;

    /**
     * The currently playing {@link Animation} of the object.
     */
    protected Animation currentAnimation;
    /**
     * Current frame of the current animation.
     */
    protected int currentFrame;
    /**
     * Last frame the animation was updated on.
     */
    protected int lastFrame;

    /**
     * VelocityX of the object. Added to position every update.
     */
    protected double velocityX;
    /**
     * VelocityY of the object. Added to position every update.
     */
    protected double velocityY;

    /**
     * X position of the object.
     */
    protected double x;
    /**
     * Y position of the object.
     */
    protected double y;

    /**
     * Name of the object. Can be used to differentiate between objects of the same class.
     */
    protected String name;

    /**
     * Whether the object can trigger {@code onCollision()} calls or not.
     */
    public boolean isCollidable = true;
    /**
     * If an object is marked static (and can collide), it won't search for objects to collide with,
     * but can be collided with. Marking static objects such as walls as static is a good idea.
     */
    public boolean isStatic = false;
    /**
     * If an object is marked as a trigger (and can collide), instead of calling {@code onCollision()},
     * it will call {@code onTrigger()} instead.
     */
    public boolean isTrigger = false;

    /**
     * Bounds from within leaving will destroy the object automatically. Does nothing if not set.
     */
    private Bounds existanceBounds;

    public GameObject() {

        colliders = new ArrayList<>();
    }

    /**
     * Sets the existanceBounds.
     *
     * @param bounds the bounds to be set.
     */
    public void setExistanceBounds(Bounds bounds) { this.existanceBounds = bounds; }

    /**
     * Aligns the objects colliders to match its position and rotation.
     */
    public void alignColliders() {

        for (CollisionInterface collider : colliders) {

            collider.setCX(x + collider.getOriginX());
            collider.setCY(y + collider.getOriginY());

            collider.getShape().setRotate(collider.getParentObject().getRotate());
        }
    }

    /**
     * Changes the currentAnimation of the object and sets the currentFrame to zero.
     *
     * @param animation animation to be set.
     */
    public void changeAnimation(Animation animation) {

        changeAnimation(animation,0);
    }

    /**
     * Changes the currentAnimation of the object and sets the currentFrame to a desired value.
     *
     * @param animation animation to be set.
     * @param frame the frame to start the new animation from.
     */
    public void changeAnimation(Animation animation, int frame) {

        currentFrame = frame;
        currentAnimation = animation;

        this.setImage(animation.getSheet());
        this.setViewport(currentAnimation.getFrame(currentFrame).getData());
    }

    /**
     * Updates the object in fixed intervals.
     *
     * Fixed update is called within fixed intervals, and such are good for physics checks.
     * GameObjects update the position based on velocity, and aligns its colliders to the parent.
     */
    public void fixedUpdate() {

        x += velocityX;
        y += velocityY;

        setTranslateX(x);
        setTranslateY(y);

        alignColliders();

        if(existanceBounds != null) {

            if(!existanceBounds.contains(x,y)) {

                Temple.getActiveGame().removeObject(this);
            }
        }
    }

    /**
     * Updates the object whenever possible.
     *
     * Normal update is called every tick of the game engine,
     * thus running at a higher rate than fixedUpdate()
     * This makes it unsuitable for expensive calls like collision checks,
     * but great for other stuff
     *
     * GameObjects update their animations here.
     */
    public void update() {

        if(currentAnimation != null) {

            if(GameTime.frameCount() - lastFrame >= currentAnimation.getFrame(currentFrame).getDuration()) {

                if(currentFrame >= currentAnimation.getFrameCount() - 1) {

                    if(currentAnimation.isLooping)
                        currentFrame = 0;
                    else
                        currentFrame = currentAnimation.getFrameCount() - 1;

                } else {

                    currentFrame++;
                }

                this.setViewport(currentAnimation.getFrame(currentFrame).getData());

                lastFrame = GameTime.frameCount();
            }
        }
    }

    /**
     * Called after update. Good for updating camera position on.
     */
    public void postUpdate() {}

    /**
     * Called when this object is a trigger and has intersected with another collidable object.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    public void onTrigger(Shape intersection, GameObject hitObject) {

    }

    /**
     * Called when this object is not a trigger and has intersected with another collidable object.
     *
     * GameObjects are unable to go through other collidable GameObjects and will stop on collision.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    public void onCollision(Shape intersection, GameObject hitObject) {

        double centerX = x + getWidth() / 2;
        double centerY = y - getWidth() / 2;

        double hcenterX = hitObject.x + hitObject.getWidth() / 2;
        double hcenterY = hitObject.y - hitObject.getWidth() / 2;

        boolean left = centerX - hcenterX < 0;
        boolean up = centerY - hcenterY < 0;

        double width = intersection.getBoundsInLocal().getWidth();
        double height = intersection.getBoundsInLocal().getHeight();

        if (width > 0 && getVelocityX() != 0) {

            if(width < height) {

                x += left ? -width : width;
                setVelocityX(0);
            }
        }

        if (height > 0 && getVelocityY() != 0) {

            if(height < width) {

                y += up ? -height : height;
                setVelocityY(0);
            }
        }

        setTranslateX(x);
        setTranslateY(y);
    }

    /**
     * Rotates the object. Positive numbers for clockwise.
     *
     * @param amount of angles to rotate this object by.
     */
    public void rotate(double amount) {

        if(angle < 359)
            angle += amount;
        else
            angle = 0;

        this.setRotate(angle);
    }

    /**
     * Sets the velocity based on the rotation of the object.
     *
     * @param velocity the velocity.
     */
    public void setVelocityForward(double velocity) {

        this.velocityX = velocity * Math.cos(Math.toRadians(angle));
        this.velocityY = velocity * Math.sin(Math.toRadians(angle));
    }

    /**
     * Adds a collider to the object.
     *
     * @param c the collider to add.
     */
    public void addCollider(CollisionInterface c) { colliders.add(c); Temple.getActiveGame().addCollider(c); }
    /**
     * Removes a collider to the object.
     *
     * @param c the collider to remove.
     */
    public void removeCollider(CollisionInterface c) { colliders.remove(c); Temple.getActiveGame().removeCollider(c); }

    /**
     * Gets the collider array.
     *
     * @return colliders.
     */
    public ArrayList<CollisionInterface> getColliders() { return colliders; }

    /**
     * Gets velocityX.
     *
     * @return velocityX.
     */
    public double getVelocityX() { return velocityX; }
    /**
     * Gets velocityY.
     *
     * @return velocityY.
     */
    public double getVelocityY() { return velocityY; }
    /**
     * Sets velocityX.
     *
     * @param velocityX to be set.
     */
    public void setVelocityX(double velocityX) { this.velocityX = velocityX; }
    /**
     * Sets velocityY.
     *
     * @param velocityY to be set.
     */
    public void setVelocityY(double velocityY) { this.velocityY = velocityY; }

    /**
     * Gets the angle.
     *
     * @return angle.
     */
    public double getAngle() { return angle; }
    /**
     * Sets the angle and rotates the object by it.
     *
     * @param angle to be set.
     */
    public void setAngle(double angle) { this.angle = angle; setRotate(angle); }

    /**
     * Gets the height.
     *
     * @return height.
     */
    public double getHeight() { return this.getFitHeight(); }
    /**
     * Gets the width.
     *
     * @return width.
     */
    public double getWidth() { return this.getFitWidth(); }

    /**
     * Gets the x of the object. getX() gets the x of the imageview.
     *
     * @return x of the object.
     */
    public double getX2() { return x; }
    /**
     * Gets the y of the object. getY() gets the y of the imageview.
     *
     * @return y of the object.
     */
    public double getY2() { return y; }

    /**
     * Sets x and y of the object and translates it to the place.
     *
     * @param x position to be set.
     * @param y position to be set.
     */
    public void setXY(double x, double y) { this.x = x; this.y = y; setTranslateX(x); setTranslateY(y); alignColliders(); }

    /**
     * Gets the name.
     *
     * @return name.
     */
    public String getName() { return name; }

    /**
     * Gets the name.
     *
     * @return name.
     */
    @Override
    public String toString() { return name; }
}

