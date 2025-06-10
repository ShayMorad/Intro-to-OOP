package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the halo around the sun in the game world.
 */
public class SunHalo {
    private static final Color HALO = new Color(255,255,0, 40);
    private static final String TAG = "sunHalo";
    private static final float SIZE = 170;


    /**
     * Creates a GameObject representing the halo around the sun.
     *
     * @param sun The sun GameObject.
     * @return The sun halo GameObject.
     */
    public static GameObject create(GameObject sun){
        Vector2 initialCenter = sun.getCenter();
        GameObject sunHalo = new GameObject(Vector2.ZERO, new Vector2(SIZE, SIZE), new OvalRenderable(HALO));
        sunHalo.setCenter(initialCenter);
        sunHalo.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sunHalo.setTag(TAG);
        sunHalo.addComponent((deltaTime -> sunHalo.setCenter(sun.getCenter())));
        return sunHalo;
    }
}
