package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import static bricker.main.Constants.BRICK_NAME;

/**
 * Brick represents a game object in a brick breaker game that acts as a brick with collision strategies.
 * It extends GameObject and implements collision handling with other game objects.
 */
public class Brick extends GameObject {
    private final CollisionStrategy collisionStrategy;

    /**
     * Construct a new GameObject instance.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions    Width and height in window coordinates.
     * @param renderable    The renderable representing the object. Can be null, in which case
     *                      the GameObject will not be rendered.
     * @param collisionStrategy The effect the brick will have on collision.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
        this.setTag(BRICK_NAME);
    }

    /**
     * Handles the collision event between the brick and another game object.
     * Delegates the collision handling to the associated collision strategy.
     * Checks and prints a message if the brick has a DoubleCollisionStrategy.
     *
     * @param other The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        this.collisionStrategy.onCollision(this, other);
    }


}
