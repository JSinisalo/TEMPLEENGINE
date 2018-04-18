package templeengine.src.core;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

/**
 * The {@code GameCanvas} is the backbone of the game itself.
 *
 * <p>
 * This is the class you want to extend when starting off with the game.
 * With this class extended all you need to do is call is its constructor,
 * and from within call this constructor and then {@code Temple.enter(this)}
 * </p>
 *
 * <p>
 * After entering the Temple the engine will call load() and start the game loop.
 * The game loop automatically updates every GameObject in the {@code objects} list.
 * You can easily add objects to the list by calling {@code addObject(GameObject o)}.
 * </p>
 *
 * <p>
 * Example code on how to set up a GameCanvas properly.
 * </p>
 *
 * <pre><code>
 * import templeengine.src.core.GameCanvas;
 * import templeengine.src.core.Temple;
 *
 * public class Example extends GameCanvas {
 *
 *  public Example(String name, double cameraWidth, double cameraHeight, double sceneWidth, double sceneHeight) {
 *
 *      super(name, cameraWidth, cameraHeight, sceneWidth, sceneHeight);
 *      Temple.enter(this);
 *   }
 *
 *  //@Override
 *  public void load() {
 *      //start game here
 *  }
 * }
 * </code></pre>
 */
public abstract class GameCanvas {

    /**
     * The camera width of the canvas.
     */
    private double cameraWidth;
    /**
     * The camera height of the canvas.
     */
    private double cameraHeight;
    /**
     * The width of the canvas.
     */
    private double sceneWidth;
    /**
     * The height of the canvas.
     */
    private double sceneHeight;
    /**
     * The name of the canvas.
     */
    private String name;

    /**
     * The objects waiting addition to the list of objects.
     */
    private ArrayList<GameObject> addObjects = new ArrayList<>();
    /**
     * The objects that will be updated on fixed and normal update.
     */
    private ArrayList<GameObject> objects = new ArrayList<>();
    /**
     * The objects waiting removal from the list of objects.
     */
    private ArrayList<GameObject> removeObjects = new ArrayList<>();
    /**
     * The colliders waiting addition to the list of colliders.
     */
    private ArrayList<CollisionInterface> addColliders = new ArrayList<>();
    /**
     * The colliders that will be updated and evaluated on fixed update.
     */
    private ArrayList<CollisionInterface> colliders = new ArrayList<>();
    /**
     * The colliders waiting removal from the list of objects.
     */
    private ArrayList<CollisionInterface> removeColliders = new ArrayList<>();

    /**
     * The QuadTree of this canvas.
     */
    private QuadTree quadTree;
    /**
     * The pane of this canvas, holds all visual nodes of the game.
     */
    protected Pane pane;

    /**
     * Creates a new GameCanvas and a QuadTree for it, according to the desired size.
     *
     * @param name of the canvas.
     * @param cameraWidth width of the camera.
     * @param cameraHeight height of the camera.
     * @param sceneWidth width of canvas and QuadTree.
     * @param sceneHeight width of canvas and QuadTree.
     */
    public GameCanvas(String name, double cameraWidth, double cameraHeight, double sceneWidth, double sceneHeight) {

        this.name = name;
        this.cameraWidth = cameraWidth;
        this.cameraHeight = cameraHeight;
        this.sceneWidth = sceneWidth;
        this.sceneHeight = sceneHeight;

        quadTree = new QuadTree(0, new Rectangle(0,0, sceneWidth, sceneHeight));
    }

    /**
     * Sets up the base values of any GameCanvas and calls for the abstract load().
     *
     * @param pane of the canvas.
     */
    public void load(Pane pane) { this.pane = pane; load(); }

    /**
     * The first method to be called once the engine has been set up.
     */
    public abstract void load();

    /**
     * Fixed interval updates, checks for collisions between objects
     * in the canvas and calls for the appropriate functions.
     */
    public void fixedUpdate() {

        for (GameObject object : objects) {
            object.fixedUpdate();
        }

        quadTree.clear();

        colliders.addAll(addColliders);
        addColliders.clear();

        colliders.removeAll(removeColliders);
        removeColliders.clear();

        for (CollisionInterface collider : colliders) {
            quadTree.insert(collider);
        }

        ArrayList<CollisionInterface> rcolliders = new ArrayList<>();

        for(int i = 0; i < colliders.size(); i++) {

            rcolliders.clear();

            GameObject parent = colliders.get(i).getParentObject();

            if(parent.isStatic || !parent.isCollidable || parent.getColliders().size() <= 0)
                continue;

            int result = quadTree.retrieve(rcolliders, colliders.get(i));

            //TODO: fuck this hack
            if(result == -1) {
                rcolliders = new ArrayList<>(colliders);
            }

            for(int x = 0; x < rcolliders.size(); x++) {

                if(parent != rcolliders.get(x).getParentObject()) {

                    if(!rcolliders.get(x).getParentObject().isCollidable)
                        continue;

                    Shape intersection = Shape.intersect(colliders.get(i).getShape(), rcolliders.get(x).getShape());

                    if(intersection.getBoundsInLocal().getWidth() != -1) {

                        if(rcolliders.get(x).getParentObject().isTrigger) {
                            parent.onTrigger(intersection, rcolliders.get(x).getParentObject());
                            rcolliders.get(x).getParentObject().onTrigger(intersection, parent);
                        } else if(!parent.isTrigger){
                            parent.onCollision(intersection, rcolliders.get(x).getParentObject());
                        }
                    }
                }
            }
        }
    }

    /**
     * Normal updates, updates the object list by removing and adding the waiting lists.
     */
    public void update() {

        objects.addAll(addObjects);
        pane.getChildren().addAll(addObjects);
        addObjects.clear();

        objects.removeAll(removeObjects);
        pane.getChildren().removeAll(removeObjects);
        removeObjects.clear();

        objects.forEach(GameObject::update);
        objects.forEach(GameObject::postUpdate);
    }

    //public void render() { objects.forEach(GameObject::render); }

    /**
     * Gets the pane of the canvas.
     *
     * @return pane of the canvas.
     */
    public Pane getPane() { return pane; }

    /**
     * Adds an object to the waiting list of objects to be added to the object list next frame.
     *
     * @param o the object to be added
     * @return the object added for chaining.
     */
    public GameObject addObject(GameObject o) { addObjects.add(o); return o; }

    /**
     * Adds a collider to the waiting list of objects to be added to the collider list next frame.
     *
     * @param c the collider to be added
     * @return the collider added for chaining.
     */
    public CollisionInterface addCollider(CollisionInterface c) { addColliders.add(c); return c; }

    /**
     * Adds all objects and colliders to be removed.
     */
    public void clearObjects() { removeObjects.addAll(addObjects); removeObjects.addAll(objects); removeColliders.addAll(addColliders); removeColliders.addAll(colliders); }

    /**
     * Adds an object to the waiting list of objects to be removed to the object list next frame.
     *
     * @param o the object to be removed
     */
    public void removeObject(GameObject o) { removeColliders.addAll(o.getColliders()); removeObjects.add(o); }

    /**
     * Adds a collider to the waiting list of collider to be removed to the collider list next frame.
     *
     * @param c the collider to be removed
     */
    public void removeCollider(CollisionInterface c) { removeColliders.add(c); }

    /**
     * Gets the name of the canvas.
     *
     * @return name of canvas.
     */
    public String getName() { return this.name; }

    /**
     * Gets the width of the canvas.
     *
     * @return width of canvas.
     */
    public double getSceneWidth() { return this.sceneWidth; }
    /**
     * Gets the height of the canvas.
     *
     * @return height of canvas.
     */
    public double getSceneHeight() { return this.sceneHeight; }
    /**
     * Gets the width of the camera.
     *
     * @return width of camera.
     */
    public double getCameraWidth() { return this.cameraWidth; }
    /**
     * Gets the height of the camera.
     *
     * @return height of camera.
     */
    public double getCameraHeight() { return this.cameraHeight; }
}
