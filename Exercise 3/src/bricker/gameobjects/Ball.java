package bricker.gameobjects;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import static bricker.main.Constants.BALL_NAME;

/**
 * Ball represents a game object in a brick breaker game that acts as the game's ball.
 * It extends GameObject and implements collision handling with other game objects.
 */
public class Ball extends GameObject {
    private final Sound collisionSound;
    private int collisionCounter;
    /**
     * Constructor for Ball.
     *
     * @param topLeftCorner  Position of the object, in window coordinates (pixels).
     *                       Note that (0,0) is the top-left corner of the window.
     * @param dimensions     Width and height in window coordinates.
     * @param renderable     The renderable representing the object. Can be null, in which case
     *                       the GameObject will not be rendered.
     * @param collisionSound The sound for collision events.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
        this.collisionCounter = 0;
        this.setTag(BALL_NAME);

    }

    /**
     * Handles the collision event between the ball and another game object.
     * Reverses the velocity of the ball and plays the collision sound.
     *
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = this.getVelocity().flipped(collision.getNormal());
        this.setVelocity(newVel);
        this.collisionCounter++;
        this.collisionSound.play();
    }

    /**
     * Retrieves the current value of the collision counter.
     *
     * @return The current value of the collision counter.
     */
    public int getCollisionCounter() {
        return collisionCounter;
    }
}
