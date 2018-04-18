package templeengine.src.core;

import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

/**
 * The {@code TileMap} is a class used to construct maps out of {@link Tile}s.
 *
 * <p>
 * TileMap uses its own .map resource files to read the map data from.
 * .map file consists of the image used for the tiles which the map is made out of
 * and width and heights of the map itself and the tiles
 * and an array of the tile arrangement itself.
 * </p>
 *
 * <p>
 * .map files can be made in two ways, either by hand or utilizing the GameIO.saveMap() function.
 * TileMaps can be constructed in two ways, either by hardcoding it directly, or utilizing the GameIO.loadMap() function.
 * </p>
 *
 * <p>
 * Each cell in a TileMap is just an int.
 * The int points to the nth tile of the desired width and height in the tileset image.
 * Take the following for example:
 *
 * The tileset has 9 tiles in it arranged in a 3x3 square.
 * If we wanted to show the tile at the center of the tileset we would write the tile as 5,
 * because its the 5th tile in the tileset. The counting starts from the upper left corner going right
 * and down each time the width has been reached.
 *
 * So a 3x3 square is as following:
 * </p>
 * <pre><code>
 * 1 2 3
 * 4 5 6
 * 7 8 9
 * </code></pre>
 * <p>
 * Lets say our TileMap is 4x4. In our tilesheet we have the first tile as a corner tile and the tile 8 is a middle tile
 * while 6 is an edge tile. Our square TileMap would look like this in the .map file:
 * </p>
 * <pre><code>
 * 1 6 6 1
 * 6 8 8 6
 * 6 8 8 6
 * 1 6 6 1
 * </code></pre>
 *
 * <p>
 * TileMap can also store a collision map of the TileMap.
 * In our 4x4 we want the edges to collide and the insides to not.
 * Our collision map will look like this:
 * </p>
 * <pre><code>
 * 1 1 1 1
 * 1 0 0 1
 * 1 0 0 1
 * 1 1 1 1
 * </code></pre>
 *
 * <p>
 * .map file structure is as follows:
 * </p>
 *
 * <pre><code>
 * res/tileset.png                       //the location of the tileset image (relative to the location of the class file from which its called from)
 * 10                                    //the width of the map
 * 10                                    //the height of the map
 * 32                                    //the width of the tiles
 * 32                                    //the height of the tile
 * 2.0                                   //a number to scale the end result with
 * 28 26 27 27 27 27 27 27 30 28         //the first row of the map image tiles, has width amount of integers
 * 8 491 492 493 494 433 496 497 508 8
 * 38 58 512 513 514 515 516 58 518 38
 * 38 521 1 1 1 1 1 527 528 38
 * 38 531 58 533 534 535 536 537 58 38
 * 38 510 491 492 493 494 58 496 497 38
 * 38 520 511 512 513 514 515 516 517 38
 * 38 58 521 1 1 1 1 1 527 38
 * 48 540 531 532 533 534 535 58 537 48
 * 28 26 27 27 27 27 27 27 30 28         //the heighth row of the map image tiles
 * 0 0 0 0 0 0 0 0 0 0                   //first row of the collision map
 * 0 0 0 0 0 0 0 0 0 0
 * 0 1 0 0 0 0 0 1 0 0
 * 0 0 0 0 0 0 0 0 0 0
 * 0 0 1 0 0 0 0 0 1 0
 * 0 0 0 0 0 0 1 0 0 0
 * 0 0 0 0 0 0 0 0 0 0
 * 0 1 0 0 0 0 0 0 0 0
 * 0 0 0 0 0 0 0 1 0 0
 * 0 0 0 0 0 0 0 0 0 0
 * </code></pre>
 *
 * <p>
 * An example of making a TileMap in code:
 * </p>
 *
 * <pre><code>
 * map = new TileMap(10,10,32,32,new Image("templeengine/examples/test/res/tileset.png"),2,16,16);
 *
 * map.setMap(new int[] {  6, 7, 7, 7, 7, 7, 7, 7, 7, 8,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 20,21,21,21,21,21,21,21,21,22,
 * 34,35,35,35,35,35,35,35,35,36  });
 *
 * map.setCollisionMap(new int[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,
 * 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
 * 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
 * 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
 * 1, 0, 0, 0, 0, 0, 0, 1, 0, 1,
 * 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
 * 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
 * 1, 0, 0, 0, 0, 0, 0, 0, 0, 1,
 * 1, 0, 1, 1, 1, 1, 1, 0, 0, 1,
 * 1, 1, 1, 0, 0, 0, 1, 1, 1, 1  });
 * </code></pre>
 *
 * <p>
 * This TileMap could then be saved in a .map file by utilizing GameIO.saveMap(map);
 * </p>
 */
public class TileMap {

    /**
     * The width of the tileset.
     */
    private int sheetWidth;
    /**
     * The height of the tileset.
     */
    private int sheetHeight;

    /**
     * The width of the map.
     */
    private int mapWidth;
    /**
     * The height of the map.
     */
    private int mapHeight;

    /**
     * The width of the tiles.
     */
    private int tileWidth;
    /**
     * The height of the tiles.
     */
    private int tileHeight;

    /**
     * The tileset.
     */
    private Image tileSheet;

    /**
     * The number which to scale the resulting map with.
     */
    private double scale;

    /**
     * The Tile objects of the map.
     */
    private GameObject[] mapObjects;
    /**
     * The image location map of the map.
     */
    private int[] map;
    /**
     * The collision map of the map.
     */
    private int[] collisionMap;

    /**
     * A number to offset the map in x direction.
     */
    private int xOffset;
    /**
     * A number to offset the map in y direction.
     */
    private int yOffset;

    /**
     * Constructs a TileMap with the given parameters.
     *
     * @param mapWidth width of the map.
     * @param mapHeight height of the map.
     * @param tileWidth width of the tiles.
     * @param tileHeight height of the tiles.
     * @param tileSheet tileset of the map.
     * @param scale number to scale the map with.
     * @param xOffset number to offset the map in x direction.
     * @param yOffset number to offset the map in y direction.
     */
    public TileMap(int mapWidth, int mapHeight, int tileWidth, int tileHeight, Image tileSheet, double scale, int xOffset, int yOffset) {

        this.sheetWidth = (int) (tileSheet.getWidth() / tileWidth);
        this.sheetHeight = (int) (tileSheet.getHeight() / tileHeight);
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
        this.tileSheet = tileSheet;

        this.xOffset = xOffset;
        this.yOffset = yOffset;

        this.scale = scale;
    }

    /**
     * Sets the image location map of the map.
     *
     * @param map array of the image locations.
     */
    public void setMap(int[] map) {

        this.map = map;

        this.mapObjects = new GameObject[this.map.length];

        for(int i = 0; i < this.mapObjects.length; i++) {

            this.mapObjects[i] = new Tile(this.tileSheet);

            int x = (this.map[i] % sheetWidth == 0 ? sheetWidth : this.map[i] % sheetWidth) - 1;
            int y = (int) Math.floor(this.map[i] / sheetWidth) - (this.map[i] % sheetWidth == 0 ? 1 : 0);

            this.mapObjects[i].setViewport(new Rectangle2D(x * tileWidth, y * tileHeight, tileWidth, tileHeight));

            x = i % mapWidth;
            y = (int) Math.floor(i / mapWidth);

            this.mapObjects[i].setScaleX(scale);
            this.mapObjects[i].setScaleY(scale);

            this.mapObjects[i].setXY((int) (x * tileWidth * scale), (int) (y * tileHeight * scale));

            //TODO: but why
            this.mapObjects[i].setX(this.mapObjects[i].getX() + xOffset);
            this.mapObjects[i].setY(this.mapObjects[i].getY() + yOffset);

            Temple.getActiveGame().addObject(this.mapObjects[i]);
        }
    }

    /**
     * Sets the collision map of the map.
     *
     * @param map array of collisions.
     */
    public void setCollisionMap(int[] map) {

        this.collisionMap = map;

        for(int i = 0; i < this.collisionMap.length; i++) {

            if(this.collisionMap[i] != 0) {

                this.mapObjects[i].addCollider(new CollisionRectangle(this.mapObjects[i], 0, 0, tileWidth * scale, tileHeight * scale));
                this.mapObjects[i].isStatic = true;
                this.mapObjects[i].isCollidable = true;
            }
        }
    }

    /**
     * Aligns an object to the grid of the map in the desired location.
     *
     * @param o object to move.
     * @param x location to move the object to.
     * @param y location to move the object to.
     */
    public void setPositionRelativeToMap(GameObject o, int x, int y) {

        o.setXY(x * tileWidth * scale, y * tileHeight * scale);
    }

    /**
     * Aligns an object to the grid of an imaginary map with the dimensions given.
     *
     * @param o object to move.
     * @param x location to move the object to.
     * @param y location to move the object to.
     * @param tileWidth width of the tiles.
     * @param tileHeight height of the tiles.
     * @param scale of the map.
     */
    public static void setPositionRelativeToMap(GameObject o, int x, int y, int tileWidth, int tileHeight, double scale) {

        o.setXY(x * tileWidth * scale, y * tileHeight * scale);
    }

    /**
     * Gets the width of the map.
     *
     * @return width of the map.
     */
    public int getMapWidth() { return mapWidth; }
    /**
     * Gets the height of the map.
     *
     * @return height of the map.
     */
    public int getMapHeight() { return mapHeight; }
    /**
     * Gets the width of the tiles.
     *
     * @return width of the tiles.
     */
    public int getTileWidth() { return tileWidth; }
    /**
     * Gets the height of the tiles.
     *
     * @return height of the tiles.
     */
    public int getTileHeight() { return tileHeight; }
    /**
     * Gets the tileset.
     *
     * @return the tileset.
     */
    public Image getTileSheet() { return  tileSheet; }
    /**
     * Gets scale of the map.
     *
     * @return the scale.
     */
    public double getScale() { return scale; }
    /**
     * Gets image location map.
     *
     * @return the image location map array.
     */
    public int[] getMap() { return map; }
    /**
     * Gets collision map.
     *
     * @return the collision map array.
     */
    public int[] getCollisionMap() { return collisionMap; }
}
