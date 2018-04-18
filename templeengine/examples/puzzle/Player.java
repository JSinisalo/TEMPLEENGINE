package templeengine.examples.puzzle;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.shape.Shape;
import templeengine.src.core.*;

/**
 * The {@code Player} is the character the user controls.
 *
 * <p>
 * This GameObject has an animation, it can be controlled by using the Input class,
 * it plays a sound once hitting a wall, calls for next map load upon hitting the end block.
 * </p>
 */
public class Player extends GameObject {

    /**
     * The last GameObject this object hit.
     */
    private GameObject last;

    /**
     * The initial x position of this.
     */
    private double ix;
    /**
     * The initial y position of this.
     */
    private double iy;

    /**
     * The idle animation.
     */
    private Animation idle;

    /**
     * The game itself.
     */
    private PuzzleGame game;

    /**
     * Constructs the player.
     *
     * @param name name of the player.
     * @param game to call for a reset once hitting the end.
     */
    public Player(String name, PuzzleGame game)
    {
        idle = new Animation(new Image("templeengine/examples/puzzle/res/player.png"),true);
        idle.split(2,2,60 / 4);

        changeAnimation(idle);

        this.setFitWidth(64);
        this.setFitHeight(64);

        this.setOpacity(1);

        this.name = name;
        this.game = game;

        addCollider(new CollisionRectangle(this, 0, 0, 64, 64));
    }

    /**
     * Sets the initial position of the player.
     *
     * @param ix initial x.
     * @param iy initial y.
     */
    public void setInitialPosition(double ix, double iy) {

        this.ix = ix;
        this.iy = iy;
    }

    /**
     * Gets the initial x.
     *
     * @return initial x.
     */
    public double getInitialPositionX() { return ix; }
    /**
     * Gets the initial y.
     *
     * @return initial y.
     */
    public double getInitialPositionY() { return iy; }

    /**
     * Called by the engine, when this hits another object.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    @Override
    public void onCollision(Shape intersection, GameObject hitObject) {

        super.onCollision(intersection, hitObject);

        if(velocityX == 0 && velocityY == 0 && hitObject != last) {

            last = hitObject;
            GameSound.playSound("res/clink.wav", getClass());
        }
    }

    /**
     * Called by the engine every frame.
     */
    @Override
    public void update() {

        if(this.getVelocityX() == 0 && this.getVelocityY() == 0) {

            if(Input.isKeyPressed(KeyCode.RIGHT)) {

                setVelocityX(8);

            } else if(Input.isKeyPressed(KeyCode.LEFT)) {

                setVelocityX(-8);

            } else if(Input.isKeyPressed(KeyCode.DOWN)) {

                setVelocityY(8);

            } else if(Input.isKeyPressed(KeyCode.UP)) {

                setVelocityY(-8);

            }
        }

        super.update();
    }

    /**
     * Calls for the game to load the next map.
     */
    public void loadNext() {

        game.loadNext();
    }
}
