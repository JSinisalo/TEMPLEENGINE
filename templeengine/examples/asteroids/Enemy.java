package templeengine.examples.asteroids;

import javafx.scene.image.Image;
import javafx.scene.shape.Shape;
import templeengine.src.core.*;

/**
 * The {@code Enemy} is the threat of this game.
 *
 * <p>
 * Randomly spawned eternally moving death collider.
 * </p>
 */
public class Enemy extends GameObject {

    /**
     * The idle animation.
     */
    private Animation idle;

    /**
     * The last Wall this object hit.
     */
    private Walls lastHit;

    /**
     * Constructs the enemy.
     *
     * @param name name of the player.
     * @param x starting position.
     * @param y starting position.
     */
    public Enemy(String name, double x, double y)
    {
        idle = new Animation(new Image("templeengine/examples/asteroids/res/enemy_idle.png"), true);
        idle.split(4,2,Utilities.randInt(20,60) / 8);

        changeAnimation(idle);

        this.setFitWidth(180 * 2);
        this.setFitHeight(83 * 2);

        this.setOpacity(1);

        this.name = name;

        if(x == 200)
            this.setVelocityX(Utilities.randInt(1,15));
        else
            this.setVelocityX(Utilities.randInt(-1,-15));

        if(y < 600)
            this.setVelocityY(Utilities.randInt(-1,-15));
        else
            this.setVelocityY(Utilities.randInt(1,15));

        addCollider(new CollisionCircle(this, 180 / 2 + 18, 83 / 2 - 12, 70));

        this.setXY(x, y);

        //setExistanceBounds(new BoundingBox(-100,-100,3072 + 100, 1234 + 100));
    }

    /**
     * Called by the engine, when this hits another object.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    @Override
    public void onCollision(Shape intersection, GameObject hitObject) {

        double velX = velocityX;
        double velY = velocityY;

        if(hitObject instanceof Enemy)
            return;

        super.onCollision(intersection, hitObject);

        if(hitObject instanceof Walls && hitObject != lastHit) {

            if(velocityX == 0)
                this.setVelocityX(-velX);

            if(velocityY == 0)
                this.setVelocityY(-velY);

            lastHit = (Walls)hitObject;
        }
    }
}
