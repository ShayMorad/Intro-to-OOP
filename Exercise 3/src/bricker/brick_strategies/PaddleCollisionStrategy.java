package bricker.brick_strategies;

import bricker.gameobjects.GhostPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.*;

/**
 * PaddleCollisionStrategy represents a collision strategy for a brick in a brick breaker game that
 * introduces a GhostPaddle.
 * It extends the BasicCollisionStrategy and handles the collision event by adding a GhostPaddle to the game.
 */
public class PaddleCollisionStrategy extends BasicCollisionStrategy {

    private final GameObjectCollection gameObjects;
    private final Renderable paddleImage;
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;
    private static Counter counter;

    /**
     * Constructor for PaddleCollisionStrategy.
     *
     * @param gameObjects The collection of game objects for collision management.
     * @param paddleImage The renderable representing the image of the paddle.
     * @param inputListener The user input listener for controlling the paddle.
     * @param windowDimensions The dimensions of the game window.
     * @param bricksLeft The counter to keep track of remaining bricks in the game.
     */
    public PaddleCollisionStrategy(GameObjectCollection gameObjects, Renderable paddleImage,
                                   UserInputListener inputListener, Vector2 windowDimensions,
                                   Counter bricksLeft) {
        super(gameObjects, bricksLeft);
        this.gameObjects = gameObjects;
        this.paddleImage = paddleImage;
        this.inputListener = inputListener;
        PaddleCollisionStrategy.counter = new Counter(0);
        this.windowDimensions = windowDimensions;
        this.setTag(GHOST_PADDLE);
    }

    /**
     * Handles the collision event between two game objects.
     * Calls the onCollisionEnter method of the superclass (BasicCollisionStrategy)
     * and adds a GhostPaddle to the game if a specific condition is met.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if (counter.value() == 0) {
            GhostPaddle ghostPaddle = new GhostPaddle(Vector2.ZERO, PADDLE_DIMENSIONS, this.paddleImage,
                    this.inputListener, counter,this.gameObjects, this.windowDimensions);
            ghostPaddle.setCenter(this.windowDimensions.mult(MID_OF_BOARD));
            counter.increment();
            this.gameObjects.addGameObject(ghostPaddle);
        }
    }

}
