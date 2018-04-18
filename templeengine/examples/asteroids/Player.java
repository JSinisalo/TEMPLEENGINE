package templeengine.examples.asteroids;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import templeengine.src.core.*;

import java.io.File;
import java.io.IOException;

/**
 * The {@code Player} is the character the user controls.
 *
 * <p>
 * This GameObject rotates, can shoot, has animations, moves freely, tracks highscore.
 * </p>
 */
public class Player extends GameObject {

    /**
     * Last frame the player shot.
     */
    private int lastShot = 0;
    /**
     * Last frame the player lost.
     */
    private int lastLost = 0;

    /**
     * The idle animation.
     */
    private Animation idle;
    /**
     * The shooting animation.
     */
    private Animation shoot;

    /**
     * The game itself.
     */
    private AsteroidsGame game;

    /**
     * A label for the score.
     */
    private Label label;
    /**
     * The highscore.
     */
    private int highScore;
    /**
     * The score.
     */
    private int score;

    /**
     * Constructs the player.
     *
     * @param name name of the player.
     * @param game .
     */
    public Player(String name, AsteroidsGame game)
    {
        setSmooth(false);

        idle = new Animation(new Image("templeengine/examples/asteroids/res/player_idle.png"), false);
        idle.split(1,1,1);

        shoot = new Animation(new Image("templeengine/examples/asteroids/res/player_shoot.png"),true);
        shoot.split(2,1,1);

        changeAnimation(idle);

        this.setFitWidth(21 * 7);
        this.setFitHeight(19 * 7);

        this.setOpacity(1);

        this.name = name;
        this.game = game;

        addCollider(new CollisionCircle(this, 0, 0, 19 * 3));

        File f = new File("./highscore.dat");
        boolean created = false;

        try {

            if(!f.exists())
                created = f.createNewFile();

        } catch (IOException e) {

            e.printStackTrace();
        }

        if(!created)
            highScore = Integer.parseInt(GameIO.loadProperty("./highscore","highScore"));
        else
            highScore = 0;

        label = new Label("Score: " + score + " Highscore: " + highScore);
        label.setScaleX(3);
        label.setScaleY(3);
        label.setTextFill(Paint.valueOf("#FF00FF"));

        game.getPane().getChildren().addAll(label);
    }

    /**
     * Called by the engine every frame.
     */
    @Override
    public void update() {

        if(lastLost + 120 <= GameTime.frameCount() && lastLost != 0)
            game.reset();

        if(lastLost != 0)
            return;

        if(Input.isKeyPressed(KeyCode.RIGHT)) {

            rotate(9);

        } else if(Input.isKeyPressed(KeyCode.LEFT)) {

            rotate(-9);

        }

        if(Input.isKeyPressed(KeyCode.DOWN)) {

            setVelocityForward(-18);

        } else if(Input.isKeyPressed(KeyCode.UP)) {

            setVelocityForward(18);

        } else {

            setVelocityX(0);
            setVelocityY(0);
        }

        if(Input.isKeyPressed(KeyCode.SPACE) && lastShot + 10 <= GameTime.frameCount()) {

            changeAnimation(shoot);

            game.addObject(new Bullet("yes", this.x, this.y, this.angle, this));
            lastShot = GameTime.frameCount();

        } else if(!Input.isKeyPressed(KeyCode.SPACE)) {

            changeAnimation(idle);
        }

        super.update();
    }

    /**
     * Called by the engine, when this hits another object.
     *
     * @param intersection is the shape of the intersection happened.
     * @param hitObject is the object hit.
     */
    @Override
    public void onCollision(Shape intersection, GameObject hitObject) {

        if(hitObject instanceof Enemy) {

            this.isCollidable = false;
            lastLost = GameTime.frameCount();
            game.lose();
            setVelocityY(0);
            setVelocityX(0);
            game.getPane().getChildren().remove(label);

            if(highScore >= score)
                GameIO.saveProperty("./highscore","highScore",highScore + "");
        }

        super.onCollision(intersection, hitObject);
    }

    /**
     * Called by the engine after every frame.
     */
    @Override
    public void postUpdate() {

        GameCamera.centerOnObject(this);

        label.setTranslateX(GameCamera.getCamera().getTranslateX() + 720/2 - label.getWidth()/2);
        label.setTranslateY(GameCamera.getCamera().getTranslateY() + 680);

        label.setText("Score: " + score + " Highscore: " + highScore);
    }

    /**
     * Adds score.
     */
    public void addScore() {

        score++;

        if(score > highScore) {

            highScore = score;
        }
    }
}
