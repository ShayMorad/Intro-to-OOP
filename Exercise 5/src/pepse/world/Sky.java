package pepse.world;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the sky element in the game world.
 */
public class Sky {
    private static final Color BASIC_SKY_COLOR = Color.decode("#80C6E5");
    private static final String TAG = "sky";

    /**
     * Creates a sky GameObject.
     *
     * @param windowDimensions The dimensions of the game window.
     * @return The sky GameObject.
     */
    public static GameObject create(Vector2 windowDimensions){
        GameObject sky = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(BASIC_SKY_COLOR));
        sky.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sky.setTag(TAG);
        return sky;
    }
}
