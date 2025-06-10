package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

import static bricker.main.Constants.BASIC;

/**
 * BasicCollisionStrategy represents a simple collision strategy for a brick in a brick breaker game.
 * This strategy is responsible for handling collision events between game objects.
 */
public class BasicCollisionStrategy implements CollisionStrategy {
    private final GameObjectCollection gameObjects;
    private final Counter bricksLeft;
    private int tag;

    /**
     * Constructor for BasicCollisionStrategy.
     *
     * @param gameObjects The collection of game objects for collision management.
     * @param bricksLeft  The counter to keep track of remaining bricks in the game.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjects, Counter bricksLeft) {
        this.gameObjects = gameObjects;
        this.bricksLeft = bricksLeft;
        this.setTag(BASIC);
    }

    /**
     * Handles the collision event between two game objects.
     * If the collision involves a brick, it is removed from the game, and the brick count is decremented.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        if (gameObjects.removeGameObject(obj1, Layer.STATIC_OBJECTS)) {
            this.bricksLeft.decrement();
        }
    }

    /**
     * Gets the object's tag. The tag has no inherent meaning on its own
     * and is not used by the BasicCollisionStrategy class itself;
     * it can be assigned any meaning by the user of the class,
     * and is simply a convenience placeholder for custom info.
     *
     * @return The tag of the object represented by an integer.
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Sets the object's tag. The tag has no inherent meaning on its own
     * and is not used by the BasicCollisionStrategy class itself;
     * it can be assigned any meaning by the user of the class,
     * and is simply a convenience placeholder for custom info.
     *
     * @param tag The tag of the strategy, can get a tag from the Constants class.
     */
    public void setTag(int tag) {
        this.tag = tag;
    }
}
