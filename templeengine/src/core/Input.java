package templeengine.src.core;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.TreeSet;

/**
 * The {@code Input} class is an easy way to check whether a key is being pressed or not.
 *
 * <p>
 * Example code to check whether spacebar is being pressed or not:
 * </p>
 * <pre><code>
 * if(Input.isKeyPressed(KeyCode.SPACE)) {
 *  //do stuff
 * }
 * </code></pre>
 */
public class Input {

    /**
     * The set of keycodes being pressed.
     */
    private static TreeSet<KeyCode> keycodes = new TreeSet<>();

    /**
     * Adds the KeyCode of the KeyEvent to the TreeSet.
     *
     * @param e the KeyEvent to add.
     */
    public void keyPressed(KeyEvent e) {

        keycodes.add(e.getCode());
    }

    /**
     * Removes the KeyCode of the KeyEvent from the TreeSet.
     *
     * @param e the KeyEvent to remove.
     */
    public void keyReleased(KeyEvent e) {

        keycodes.remove(e.getCode());
    }

    /**
     * Checks whether the KeyCode is in the TreeSet or not.
     *
     * @param e the KeyCode to check
     * @return true if the KeyCode is in the TreeSet, false if not.
     */
    public static boolean isKeyPressed(KeyCode e) {

        return keycodes.contains(e);
    }
}
