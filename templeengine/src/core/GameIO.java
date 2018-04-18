package templeengine.src.core;

import javafx.scene.image.Image;

import java.io.*;
import java.util.Properties;

/**
 * The {@code GameIO} lets the user easily store Java Properties into files, also provides support for .map files.
 *
 * <p>
 * With this class you have to keep in mind, that you cannot modify the contents of a .jar package, since they are read only.
 * If you want to keep track of highscores or such the file you create will be outside of the .jar file,
 * or you could just not keep everything in a single .jar and instead just use the .jar as a launcher.
 *
 * .map files can be read from the .jar.
 *
 * Examples of both .map files and .dat files can be found in the two example games.
 * </p>
 *
 * <p>
 * Example code to save something to a file:
 * </p>
 * <pre><code>
 * GameIO.saveProperty("./names","name1","Juho");
 * GameIO.saveProperty("./names","surname1","Sinisalo");
 * </code></pre>
 *
 * <p>
 * Example code to load something from a file:
 * </p>
 * <pre><code>
 * String name = GameIO.loadProperty("./names","name1");
 * String surname = GameIO.loadProperty("./names","surname1");
 * </code></pre>
 *
 * <p>
 * You can also load entire Properties of a file, not just one at a time.
 * </p>
 */
public abstract class GameIO {

    /**
     * Saves properties to the desired file.
     *
     * @param fileName the path of the file.
     * @param p properties to save to the file.
     */
    public static void saveProperties(String fileName, Properties p) {

        File f = new File(fileName + ".dat");

        try {

            FileOutputStream fr = new FileOutputStream(f);

            p.store(fr,Temple.getActiveGame().getName() + " Properties");
            fr.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Load properties from the desired file.
     *
     * @param fileName the path of the file.
     * @return properties of the file or null if file not found.
     */
    public static Properties loadProperties(String fileName) {

        File f = new File(fileName + ".dat");

        try {

            FileInputStream fi = new FileInputStream(f);
            Properties p = new Properties();

            p.load(fi);
            fi.close();

            return p;

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Saves a property to the desired file.
     *
     * @param fileName the path of the file.
     * @param key key of the property.
     * @param value of the property.
     */
    public static void saveProperty(String fileName, String key, String value) {

        File f = new File(fileName + ".dat");

        try {

            FileOutputStream fr = new FileOutputStream(f);
            Properties p = new Properties();

            p.setProperty(key, value);

            p.store(fr,Temple.getActiveGame().getName() + " Properties");
            fr.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Loads a property from the desired file.
     *
     * @param fileName the path of the file.
     * @param key the property desired.
     * @return the value of the property or null if invalid file.
     */
    public static String loadProperty(String fileName, String key) {

        File f = new File(fileName + ".dat");

        try {

            FileInputStream fi = new FileInputStream(f);
            Properties p = new Properties();

            p.load(fi);
            fi.close();

            return p.getProperty(key);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }

    /**
     * Saves a TileMap to a .map file.
     *
     * @param fileName the path of the file relative to the class file location.
     * @param map the TileMap to save.
     * @param c the class which calls for the function.
     */
    public static void saveMap(String fileName, TileMap map, Class c) {

        String line = "";

        try {

            PrintWriter buffer =  new PrintWriter(new File(c.getResource(fileName + ".map").getPath()));

            buffer.write(map.getTileSheet().getUrl());
            buffer.write(System.getProperty("line.separator"));
            buffer.write(map.getMapWidth() + "");
            buffer.write(System.getProperty("line.separator"));
            buffer.write(map.getMapHeight() + "");
            buffer.write(System.getProperty("line.separator"));
            buffer.write(map.getTileWidth() + "");
            buffer.write(System.getProperty("line.separator"));
            buffer.write(map.getTileHeight() + "");
            buffer.write(System.getProperty("line.separator"));
            buffer.write(map.getScale() + "");
            buffer.write(System.getProperty("line.separator"));

            for(int i = 0; i < map.getMap().length; i++) {

                line += map.getMap()[i] + " ";

                if((i + 1) % map.getMapWidth() == 0) {

                    buffer.write(line);
                    buffer.write(System.getProperty("line.separator"));
                    line = "";
                }
            }

            line = "";

            for(int i = 0; i < map.getCollisionMap().length; i++) {

                line += map.getCollisionMap()[i] + " ";

                if((i + 1) % map.getMapWidth() == 0) {

                    buffer.write(line);
                    buffer.write(System.getProperty("line.separator"));
                    line = "";
                }
            }

            buffer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Loads a TileMap from a .map file.
     *
     * The returned TileMap doesn't have to be built since its built by this.
     *
     * @param fileName the path of the file relative to the class file location.
     * @param xOffset to position the map by this.
     * @param yOffset to position the map by this.
     * @param c the class which calls for the function.
     * @return the TileMap generated.
     */
    public static TileMap loadMap(String fileName, int xOffset, int yOffset, Class c) {

        try {

            BufferedReader buffer = new BufferedReader(new InputStreamReader(c.getResourceAsStream(fileName + ".map")));

            String url = buffer.readLine();
            int mapWidth = Integer.parseInt(buffer.readLine());
            int mapHeight = Integer.parseInt(buffer.readLine());
            int tileWidth = Integer.parseInt(buffer.readLine());
            int tileHeight = Integer.parseInt(buffer.readLine());
            double scale = Double.parseDouble(buffer.readLine());

            TileMap map = new TileMap(mapWidth, mapHeight, tileWidth, tileHeight, new Image(c.getResourceAsStream(url)), scale, xOffset, yOffset);

            String line = buffer.readLine();

            int[] mapArray = new int[mapWidth * mapHeight];
            int[] cMapArray = new int[mapWidth * mapHeight];
            int j = 0;
            int l = 0;

            while(line != null) {

                if(line.equals("") || line.equals(" "))
                    continue;

                line = line.trim();
                String[] a = line.split("\\s+");

                if(j < mapWidth * mapHeight) {
                    for(String anA : a) {

                        mapArray[j] = Integer.parseInt(anA);
                        j++;
                    }
                } else {
                    for(String anA : a) {

                        cMapArray[l] = Integer.parseInt(anA);
                        l++;
                    }
                }

                line = buffer.readLine();
            }

            map.setMap(mapArray);
            map.setCollisionMap(cMapArray);

            buffer.close();

            return map;

        } catch (IOException e) {

            e.printStackTrace();
        }

        return null;
    }
}
