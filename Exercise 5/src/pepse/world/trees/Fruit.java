package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.components.ScheduledTask;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Observer;

import java.awt.*;
import java.util.function.Consumer;

/**
 * Represents a fruit GameObject in the world.
 */
public class Fruit extends GameObject implements Observer {
    private static final Vector2 SIZE = new Vector2(25, 25);
    private static final String AVATAR = "avatar";
    private static final int NUM_COLORS = 4;
    private static final String TAG = "fruit";
    private static final float ENERGY = 10;
    private final Color[] colors = {Color.red, Color.ORANGE, Color.PINK, Color.GREEN};
    private final Consumer<Float> addEnergy;
    private final float cycleTime;
    private int colorIndex = 0;
    private Color lastColor = Color.red;


    /**
     * Constructs a new Fruit object.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param originalRenderable    The renderable representing the object.
     *                              Can be null, in which case the GameObject will not be rendered.
     * @param addEnergy    Consumer for adding energy when the fruit is consumed.
     * @param cycleTime    The time cycle for the fruit animation.
     */
    public Fruit(Vector2 topLeftCorner, Renderable originalRenderable,
                 Consumer<Float> addEnergy, float cycleTime) {
        super(topLeftCorner, SIZE, originalRenderable);
        this.addEnergy = addEnergy;
        this.cycleTime = cycleTime;
        this.setTag(TAG);
    }


    /**
     * Updates the fruit color if necessary.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(this.renderer().getRenderable() != null && this.lastColor != colors[colorIndex]){
            this.lastColor = colors[colorIndex];
            this.renderer().setRenderable(new OvalRenderable(colors[colorIndex]));
        }
    }

    /**
     * Handles collision events for the fruit object.
     * If the fruit collides with the avatar, and it's visible, it triggers consumption.
     * After the collision, it changes the fruit's color if necessary.
     *
     * @param other     The GameObject the fruit collided with.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (other.getTag().equals(AVATAR) && this.renderer().getRenderable() != null) {
            this.consume();
        }
    }

    /**
     * Activates all the wanted behaviours of this object when the Avatar jumps.
     */
    @Override
    public void pushJumpNotification() {
        this.updateColorIndex();
    }

    private void consume() {
        this.addEnergy.accept(ENERGY);
        this.renderer().setRenderable(null);
        this.lastColor = colors[colorIndex];
        new ScheduledTask(this, cycleTime, false, () ->
                this.renderer().setRenderable(new OvalRenderable(this.lastColor)));
    }

    private void updateColorIndex(){
        this.colorIndex =  (colorIndex + 1) % NUM_COLORS;
    }


}
