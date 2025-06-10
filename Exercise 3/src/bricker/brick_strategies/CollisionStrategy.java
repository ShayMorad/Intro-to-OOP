package bricker.brick_strategies;

import danogl.GameObject;

/**
 * CollisionStrategy is an interface defining the contract for handling collision events between game
 * objects.
 * Implementing classes should provide specific behavior for the onCollision method.
 */
public interface CollisionStrategy {

    /**
     * Handles the collision event between two game objects.
     * Implementing classes should define the specific behavior for collision resolution.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    void onCollision(GameObject obj1, GameObject obj2);

    /**
     * Gets the object's tag. The tag has no inherent meaning on its own
     * and is not used by the CollisionStrategy object itself;
     * it can be assigned any meaning by the user of the class,
     * and is simply a convenience placeholder for custom info.
     *
     * @return the tag of the strategy represented by an integer.
     */
    int getTag();

}
