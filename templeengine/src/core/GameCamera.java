package templeengine.src.core;

import javafx.geometry.Bounds;
import javafx.scene.Camera;
import javafx.scene.image.ImageView;

/**
 * The {@code GameCamera} class provides the user with the main camera the game uses.
 *
 * <p>
 * With this class you can easily set bounds for the camera (in which the camera has to be in), and position the camera as well.
 * </p>
 */
public class GameCamera {

    /**
     * The main camera.
     */
    private static Camera camera;
    /**
     * Width of the camera.
     */
    private static double cameraWidth;
    /**
     * Height of the camera.
     */
    private static double cameraHeight;

    /**
     * Bounds of the camera.
     */
    private static Bounds bounds;

    /**
     * Constructs the camera with the given dimensions.
     *
     * @param cWidth width of the camera.
     * @param cHeight height of the camera.
     */
    public GameCamera(double cWidth, double cHeight) {

        cameraWidth = cWidth;
        cameraHeight = cHeight;
    }

    /**
     * Sets the GameCamera.
     *
     * @param c the camera.
     */
    public static void setCamera(Camera c) { camera = c; }

    /**
     * Gets the GameCamera.
     *
     * @return the camera.
     */
    public static Camera getCamera() { return camera; }

    /**
     * Sets the bounds.
     *
     * @param b the bounds.
     */
    public static void setBounds(Bounds b) { bounds = b; }

    /**
     * Centers the camera on an ImageView.
     *
     * @param i the view to center to.
     */
    public static void centerOnView(ImageView i) {

        camera.setTranslateX(i.getTranslateX() - cameraWidth / 2 + i.getFitWidth() / 2);
        camera.setTranslateY(i.getTranslateY() - cameraHeight / 2 + i.getFitHeight() / 2);
    }

    /**
     * Centers the camera on a GameObject.
     *
     * @param o the object to center to.
     */
    public static void centerOnObject(GameObject o) {

        camera.setTranslateX(o.getX2() - cameraWidth / 2 + o.getWidth() / 2);
        camera.setTranslateY(o.getY2() - cameraHeight / 2 + o.getHeight() / 2);

        if(bounds != null) {
            if (camera.getTranslateX() < 0) {
                camera.setTranslateX(0);
            }

            if (camera.getTranslateX() > bounds.getWidth()) {
                camera.setTranslateX(bounds.getWidth());
            }

            if (camera.getTranslateY() < 0) {
                camera.setTranslateY(0);
            }

            if (camera.getTranslateY() > bounds.getHeight()) {
                camera.setTranslateY(bounds.getHeight());
            }
        }
    }
}
