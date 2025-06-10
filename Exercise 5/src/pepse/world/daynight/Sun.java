package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.OvalRenderable;
import danogl.util.Vector2;

import java.awt.*;

import static pepse.world.Terrain.TERRAIN_HEIGHT_FACTOR;

/**
 * Represents the sun in the game world.
 */
public class Sun {
    private static final String TAG = "sun";
    private static final float SIZE = 99;
    private static final float MAX_ANGLE = 360f;

    /**
     * Creates a GameObject representing the sun.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the day-night cycle.
     * @return The sun GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {

        Vector2 initialCenter = new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() * (1 - TERRAIN_HEIGHT_FACTOR));
        GameObject sun = new GameObject(initialCenter,
                new Vector2(SIZE, SIZE), new OvalRenderable(Color.YELLOW));
        sun.setCenter(initialCenter);
        sun.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        sun.setTag(TAG);
        Vector2 cycleCenter = new Vector2(windowDimensions.x() / 2,
                windowDimensions.y() * TERRAIN_HEIGHT_FACTOR);
        new Transition<Float>(
                sun, (Float angle) ->
                        sun.setCenter(initialCenter.subtract(cycleCenter).rotated(angle).add(cycleCenter)),
                0f, MAX_ANGLE, Transition.CUBIC_INTERPOLATOR_FLOAT, cycleLength,
                Transition.TransitionType.TRANSITION_LOOP, null);
        return sun;
    }
}
