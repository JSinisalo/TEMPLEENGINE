package templeengine.src.core;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Random;

/**
 * The {@code QuadTree} is a relatively simple class to help reduce the expensive collision check calls.
 *
 * <p>
 * QuadTree splits the parent quadrant into 4 quadrants based on the amount of objects in the quadrant.
 * If the amount of objects in the quadrant exceeds the MAXOBJS the quadrant will split into 4 quadrants.
 * An object in a quadrant will only be collision checked against other objects in the same quadrant.
 * An object between quadrants will as of the moment be checked against every other object.
 * </p>
 */
public class QuadTree {

    /**
     * Maximum number of objects allowed in a quadrant before it splits.
     */
    private final int MAXOBJS = 4;
    /**
     * Maximum number of childs allowed in the main quadrant before no more quadrants are made.
     */
    private final int MAXLVLS = 50;

    /**
     * Number of levels in this QuadTree.
     */
    private int level;
    /**
     * Objects in this QuadTree.
     */
    private ArrayList<CollisionInterface> objects;
    /**
     * Bounds of this QuadTree.
     */
    private Rectangle bounds;
    /**
     * Children of this QuadTree.
     */
    private QuadTree[] nodes;

    /**
     * A random for setting a color when Temple.debug = true.
     */
    private Random random = new Random();

    /**
     * Constructs the QuadTree with the level and bounds.
     *
     * @param level of the QuadTree.
     * @param bounds of the QuadTree.
     */
    public QuadTree(int level, Rectangle bounds) {

        this.level = level;
        this.bounds = bounds;

        objects = new ArrayList<>();
        nodes = new QuadTree[4];

        if(Temple.getActiveGame() != null && Temple.debug) {
            this.bounds.setFill(Color.rgb(random.nextInt(256), random.nextInt(256), random.nextInt(256), 0.2));
            Temple.getActiveGame().getPane().getChildren().add(this.bounds);
        }
    }

    /**
     * Clears the QuadTree.
     */
    public void clear() {

        objects.clear();

        if(Temple.getActiveGame() != null && Temple.debug)
            Temple.getActiveGame().getPane().getChildren().remove(this.bounds);

        for (int i = 0; i < nodes.length; i++) {

            if (nodes[i] != null) {

                nodes[i].clear();
                nodes[i] = null;
            }
        }
    }

    /**
     * Splits the QuadTree into 4 quadrants.
     */
    private void split() {

        int subWidth = (int)(bounds.getWidth() / 2);
        int subHeight = (int)(bounds.getHeight() / 2);
        int x = (int)bounds.getX();
        int y = (int)bounds.getY();

        nodes[0] = new QuadTree(level+1, new Rectangle(x + subWidth, y, subWidth, subHeight));
        nodes[1] = new QuadTree(level+1, new Rectangle(x, y, subWidth, subHeight));
        nodes[2] = new QuadTree(level+1, new Rectangle(x, y + subHeight, subWidth, subHeight));
        nodes[3] = new QuadTree(level+1, new Rectangle(x + subWidth, y + subHeight, subWidth, subHeight));
    }

    /**
     * Gets the index of the desired collision object.
     *
     * @param obj the collision object.
     * @return -1 if between quadrants, 0 for right-top, 1 for left-top, 2 for left-bottom, 3 for right-bottom.
     */
    private int getIndex(CollisionInterface obj) {

        int index = -1;

        double verticalMidpoint = bounds.getX() + (bounds.getWidth() / 2);
        double horizontalMidpoint = bounds.getY() + (bounds.getHeight() / 2);

        boolean topQuadrant = (obj.getCY() < horizontalMidpoint && obj.getCY() + obj.getCHeight() < horizontalMidpoint);
        boolean bottomQuadrant = (obj.getCY() > horizontalMidpoint);

        //TODO: is it really supposed to go like this
        if (obj.getCX() < verticalMidpoint && obj.getCX() + obj.getCWidth() < verticalMidpoint) {
            if (topQuadrant) {
                index = 1;
            }
            else if (bottomQuadrant) {
                index = 2;
            }
        }
        else if (obj.getCX() > verticalMidpoint) {
            if (topQuadrant) {
                index = 0;
            }
            else if (bottomQuadrant) {
                index = 3;
            }
        }

        return index;
    }

    /**
     * Inserts an object into the quadrant and checks whether to split it or not
     *
     * @param obj the object to insert.
     */
    public void insert(CollisionInterface obj) {

        if (nodes[0] != null) {
            int index = getIndex(obj);

            if (index != -1) {
                nodes[index].insert(obj);

                return;
            }
        }

        objects.add(obj);

        if (objects.size() > MAXOBJS && level < MAXLVLS) {

            if (nodes[0] == null) {
                split();
            }

            int i = 0;
            while (i < objects.size()) {

                int index = getIndex(objects.get(i));

                if (index != -1) {

                    nodes[index].insert(objects.remove(i));

                } else {

                    i++;
                }
            }
        }
    }

    /**
     * Retrieves an ArrayList of the objects in the same quadrant as the object desired.
     *
     * This function modifies the given returnObjects array, and returns an int.
     *
     * @param returnObjects the list of objects in the same quadrant as obj.
     * @param obj the object of which to retrieve the quadrants objects from.
     * @return index of the obj, -1 if between quadrants, 0 for right-top, 1 for left-top, 2 for left-bottom, 3 for right-bottom.
     */
    public int retrieve(ArrayList<CollisionInterface> returnObjects, CollisionInterface obj) {

        int index = getIndex(obj);

        if (index != -1 && nodes[0] != null) {

            index = nodes[index].retrieve(returnObjects, obj);
        }

        returnObjects.addAll(objects);

        return index;
    }
}