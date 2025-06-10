package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import danogl.util.Counter;

import static bricker.main.Constants.*;

/**
 * Heart represents a game object in a brick breaker game that provides extra lives when collected.
 * It extends GameObject and implements collision handling with the paddle, incrementing lives on collision.
 */
public class Heart extends GameObject {

    private final Counter lives;
    private final GameObjectCollection gameObjects;

    /**
     * Constructor for Heart.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the object. Can be null, in which case
     *                   the GameObject will not be rendered.
     * @param lives The counter for tracking player lives.
     * @param gameObjects The collection of game objects for managing collisions.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter lives,
                 GameObjectCollection gameObjects) {
        super(topLeftCorner, dimensions, renderable);
        this.setTag(HEART_NAME);
        this.lives = lives;
        this.gameObjects = gameObjects;
    }

    /**
     * Handles the collision event between the heart and another game object.
     * Increments lives and removes the heart if the collision is with the paddle and lives
     * are below the limit.
     *
     * @param other The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        if (this.shouldCollideWith(other)) {
            if(this.lives.value() < LIVES_LIMIT){
                this.lives.increment();
            }
            this.gameObjects.removeGameObject(this);
        }
    }

    /**
     * Determines whether the heart should collide with a specific game object.
     *
     * @param other The other game object involved in the collision.
     * @return True if the heart should collide with the specified game object, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals(PADDLE_NAME);
    }
}
 