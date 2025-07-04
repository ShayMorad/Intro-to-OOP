package pepse.world;

import danogl.GameObject;
import danogl.components.GameObjectPhysics;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * Represents a block element in the game world.
 */
public class Block extends GameObject {
    /**
     * The size of the block element.
     */
    public static final int SIZE = 30;
    private static final String TAG = "ground";


    /**
     * Constructs a Block object.
     *
     * @param topLeftCorner The top-left corner position of the block.
     * @param renderable    The renderable component for the block.
     */
    public Block(Vector2 topLeftCorner, Renderable renderable) {
        super(topLeftCorner, Vector2.ONES.mult(SIZE), renderable);
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        physics().setMass(GameObjectPhysics.IMMOVABLE_MASS);
        this.setTag(TAG);
    }
}

