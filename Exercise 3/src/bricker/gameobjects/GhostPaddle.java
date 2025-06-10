package bricker.gameobjects;


import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import static bricker.main.Constants.*;

/**
 * GhostPaddle represents a specialized type of Paddle in a brick breaker game with limited hits.
 * It extends the Paddle class and adds functionality to track hits and decrement a counter.
 */
public class GhostPaddle extends Paddle {

    private final Counter ghostPaddlesCounter;
    private int hitCounter;
    private final GameObjectCollection gameObjects;

    /**
     * Constructor for GhostPaddle.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     *                   the GameObject will not be rendered.
     * @param inputListener The user input listener for controlling the paddle.
     * @param counter The counter for tracking the number of ghost paddles.
     * @param gameObjects The collection of game objects for managing collisions.
     * @param windowDimensions The dimensions of the game's window.
     */
    public GhostPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Counter counter,
                       GameObjectCollection gameObjects, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.ghostPaddlesCounter = counter;
        this.hitCounter = 0;
        this.gameObjects = gameObjects;
        this.setTag(GHOST_PADDLE_NAME);
    }

    private void checkHits(){
        if(this.hitCounter >= GHOST_PADDLE_LIMIT){
            this.ghostPaddlesCounter.decrement();
            this.gameObjects.removeGameObject(this);
        }
    }

    /**
     * Updates the ghost paddle, checking hits and performing super.update().
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.checkHits();
    }

    /**
     * Handles the collision event between the ghost paddle and another game object.
     * Tracks hits if the collided object is a ball or puck.
     *
     * @param other The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(other.getTag().equals(BALL_NAME) || other.getTag().equals(PUCK_NAME)){
            this.hitCounter++;
        }
        super.onCollisionEnter(other, collision);
    }
}
