package bricker.brick_strategies;
import bricker.gameobjects.Heart;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.*;
/**
 * ExtraHPCollisionStrategy represents a collision strategy for a brick in a brick breaker game with
 * the ability to provide extra lives.
 * It extends the BasicCollisionStrategy and handles the collision event by adding a heart (extra life)
 * object to the game.
 */
public class ExtraHPCollisionStrategy extends BasicCollisionStrategy {
    private final GameObjectCollection gameObjects;
    private final Renderable heartImage;
    private final Counter lives;
    /**
     * Constructor for ExtraHPCollisionStrategy.
     *
     * @param gameObjects The collection of game objects for collision management.
     * @param heartImage  The renderable representing the image of a heart (extra life).
     * @param lives       The counter to keep track of remaining lives in the game.
     * @param bricksLeft  The counter to keep track of remaining bricks in the game.
     */
    public ExtraHPCollisionStrategy(GameObjectCollection gameObjects, Renderable heartImage,
                                    Counter lives, Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.gameObjects = gameObjects;
        this.heartImage = heartImage;
        this.lives = lives;
        this.setTag(EXTRA_HP);
    }
    /**
     * Handles the collision event between two game objects.
     * Calls the onCollision method of the superclass (BasicCollisionStrategy)
     * and adds a heart (extra life) object to the game at the position of the collided brick.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        Vector2 startPosition = obj1.getCenter();
        super.onCollision(obj1, obj2);
        Heart heart = new Heart(Vector2.ZERO,
                new Vector2(HEART_SIZE, HEART_SIZE),
                this.heartImage, this.lives, this.gameObjects);
        heart.setCenter(startPosition);
        heart.setVelocity(Vector2.DOWN.mult(HEART_SPEED));
        this.gameObjects.addGameObject(heart);
    }
}
