package templeengine.src.core;

/**
 * The {@code Utilities} class has handy functions for things one might find useful in games.
 */
public class Utilities {

    /**
     * Generates a random integer between min and max.
     *
     * Details explained in http://stackoverflow.com/a/363732
     *
     * @param min The smallest number you want to be able to be generated.
     * @param max The largest number you want to be able to be generated.
     * @return The generated number between min and max.
     */
    public static int randInt(int min, int max) {

        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
