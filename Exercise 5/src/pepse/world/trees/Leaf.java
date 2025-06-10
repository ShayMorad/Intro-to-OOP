package pepse.world.trees;

import danogl.GameObject;
import danogl.components.ScheduledTask;
import danogl.components.Transition;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.world.Observer;

import java.awt.*;


/**
 * Represents a leaf object in the tree simulation.
 */
public class Leaf extends GameObject implements Observer {
    /**
     * The size of the leaf element.
     */
    public static final int SIZE = 30;
    private static final Color LEAF_COLOR = new Color(50, 200, 30);
    private static final String TAG = "leaf";
    private static final float FINAL_ANGLE = 90f;
    private static final float INITIAL_ANGLE = 0f;
    private static final int ROTATE_TRANSITION_TIME = 2;
    private static final float INCREMENT_FACTOR = 1.1f;
    private static final float DIMENSION_TRANSITION_TIME = 0.5f;
    private static final float DECREMENT_FACTOR = 10 / 11f;

    /**
     * Constructs a new leaf object.
     *
     * @param topLeftCorner Position of the leaf's top-left corner, in window coordinates (pixels).
     */
    public Leaf(Vector2 topLeftCorner) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), new RectangleRenderable(LEAF_COLOR));
        this.setTag(TAG);
        this.changeDimensions();
    }


    /**
     * Activates all the wanted behaviours of this object when the Avatar jumps.
     */
    @Override
    public void pushJumpNotification() {
        this.createAngleTransition();
    }

    private void changeDimensions(){
        new ScheduledTask(this,(float) Math.random(),false,this::increaseDimensionsTransition);
    }

    private void createAngleTransition(){
        new Transition<Float>(this,
                this.renderer()::setRenderableAngle,
                INITIAL_ANGLE, FINAL_ANGLE,
                Transition.CUBIC_INTERPOLATOR_FLOAT,
                ROTATE_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_ONCE, null);
    }

    private void increaseDimensionsTransition(){
        new Transition<Vector2>(this,
                this::setDimensions,
                this.getDimensions(), this.getDimensions().mult(INCREMENT_FACTOR),
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                DIMENSION_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                this::decreaseDimensionsTransition);
    }
    private void decreaseDimensionsTransition(){
        new Transition<Vector2>(this,
                this::setDimensions,
                this.getDimensions(), this.getDimensions().mult(DECREMENT_FACTOR),
                Transition.CUBIC_INTERPOLATOR_VECTOR,
                DIMENSION_TRANSITION_TIME,
                Transition.TransitionType.TRANSITION_ONCE,
                this::increaseDimensionsTransition);
    }


}
