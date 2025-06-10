package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.*;

import java.util.Random;


/**
 * PuckCollisionStrategy represents a collision strategy for a brick in a brick breaker game that
 * introduces multiple Puck objects.
 * It extends the BasicCollisionStrategy and handles the collision event by adding two Puck objects to
 * the game.
 */
public class PuckCollisionStrategy extends BasicCollisionStrategy {
    private final GameObjectCollection gameObjects;
    private final Renderable puckImage;
    private final Sound collsionSound;
    private final Random random;

    /**
     * Constructor for PuckCollisionStrategy.
     *
     * @param gameObjects    The collection of game objects for collision management.
     * @param puckImage      The renderable representing the image of the puck.
     * @param collisionSound The sound for collision events.
     * @param bricksLeft     The counter to keep track of remaining bricks in the game.
     */
    public PuckCollisionStrategy(GameObjectCollection gameObjects, Renderable puckImage,
                                 Sound collisionSound, Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.puckImage = puckImage;
        this.collsionSound = collisionSound;
        this.gameObjects = gameObjects;
        this.random = new Random();
        this.setTag(PUCK);
    }

    private Vector2 puckVelocity() {
        double angle = random.nextDouble() * Math.PI;
        float velX = (float) Math.cos(angle) * BALL_SPEED;
        float velY = (float) Math.sin(angle) * BALL_SPEED;
        return new Vector2(velX, velY);
    }

    /**
     * Handles the collision event between two game objects.
     * Calls the onCollisionEnter method of the superclass (BasicCollisionStrategy)
     * and adds two Puck objects to the game at the position of the collided brick.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        Vector2 startPosition = obj1.getCenter();

        super.onCollision(obj1, obj2);

        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            Ball puck = new Ball(Vector2.ZERO, new Vector2(PUCK_DIMENSION, PUCK_DIMENSION),
                    this.puckImage, this.collsionSound);
            puck.setTag(PUCK_NAME);
            puck.setCenter(startPosition);
            puck.setVelocity(this.puckVelocity());
            this.gameObjects.addGameObject(puck);
        }
    }
}
