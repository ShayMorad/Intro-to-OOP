package pepse.world.daynight;

import danogl.GameObject;
import danogl.components.CoordinateSpace;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;

import java.awt.*;

/**
 * Represents the night cycle in the game world.
 */
public class Night {
    private static final String TAG = "dayNightCycle";
    private static final Float MIDNIGHT_OPACITY = 0.5f;

    /**
     * Creates a GameObject representing the night cycle.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param cycleLength      The length of the day-night cycle.
     * @return The night cycle GameObject.
     */
    public static GameObject create(Vector2 windowDimensions, float cycleLength) {
        GameObject dayNightCycle = new GameObject(Vector2.ZERO, windowDimensions,
                new RectangleRenderable(Color.BLACK));
        dayNightCycle.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        dayNightCycle.setTag(TAG);
        new Transition<Float>(
                dayNightCycle,
                dayNightCycle.renderer()::setOpaqueness,
                0f, MIDNIGHT_OPACITY,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                cycleLength/2,
                Transition.TransitionType.TRANSITION_BACK_AND_FORTH,
                null);
        return dayNightCycle;
    }
}
