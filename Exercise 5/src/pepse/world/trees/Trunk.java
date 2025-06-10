package pepse.world.trees;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.RectangleRenderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.world.Observer;

import java.awt.*;

/**
 * Represents the trunk of a tree in the world.
 */
public class Trunk extends GameObject implements Observer {

    /** The width of the trunk. */
    public static final int WIDTH = 30;
    private static final Color TRUNK_COLOR = new Color(100, 50, 20);
    private static final String TAG = "trunk";


    /**
     * Constructs a new trunk object.
     *
     * @param topLeftCorner The top-left corner position of the trunk.
     * @param trunkHeight The height of the trunk.
     */
    public Trunk(Vector2 topLeftCorner, float trunkHeight) {
        super(topLeftCorner, new Vector2(WIDTH, trunkHeight),
                new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(TAG);
    }

    /**
     * Activates all the wanted behaviours of this object when the Avatar jumps.
     */
    @Override
    public void pushJumpNotification() {
        this.changeColor();
    }

    private void changeColor(){
        this.renderer().setRenderable(
                new RectangleRenderable(ColorSupplier.approximateColor(TRUNK_COLOR)));
    }
}
