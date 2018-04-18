package templeengine.examples.asteroids;

import javafx.geometry.BoundingBox;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import templeengine.src.core.*;

/**
 * The {@code Bullet} is the player shoots.
 *
 * <p>
 * Spawns from the player to the direction of the players facing.
 * </p>
 */
public class Bullet extends GameObject {

    /**
     * Parent of the bullet.
     */
    private Player parent;

    /**
     * Constructs the bullet.
     *
     * @param name name of the bullet.
     * @param x starting position.
     * @param y starting position.
     * @param angle starting angle.
     * @param parent of the bullet.
     */
    public Bullet(String name, double x, double y, double angle, Player parent)
    {
        Image idle = new Image("templeengine/examples/asteroids/res/bullet.png");

        this.setImage(idle);

        this.setFitWidth(78);
        this.setFitHeight(150);

        this.setOpacity(1);

        this.name = name;
        this.parent = parent;

        this.setAngle(angle);
        setVelocityForward(15);
        this.setAngle(angle + 90);

        this.isTrigger = true;

        addCollider(new CollisionRectangle(this, 0, 0, 78, 150));

        this.setXY(x, y);

        setExistanceBounds(new BoundingBox(-50,-50,3072, 1234));
    }

    /**
     * Called by the engine, when this or something else enters the trigger area.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    @Override
    public void onTrigger(Shape intersection, GameObject hitObject) {

        if(hitObject instanceof Enemy) {

            this.isCollidable = false;

            if(Temple.getActiveGame() instanceof AsteroidsGame) {

                switch(Utilities.randInt(0,2)) {

                    case 0:
                    case 1:

                        ((AsteroidsGame) Temple.getActiveGame()).addEnemy();

                        break;

                    case 2:

                        ((AsteroidsGame) Temple.getActiveGame()).addEnemy();
                        ((AsteroidsGame) Temple.getActiveGame()).addEnemy();

                        break;
                }
            }

            parent.addScore();

            Temple.getActiveGame().removeObject(hitObject);
            GameSound.playSound("res/hit.wav", getClass());
            Temple.getActiveGame().removeObject(this);
        }
    }
}
