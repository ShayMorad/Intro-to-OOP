package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

import java.awt.event.KeyEvent;

import static bricker.main.Constants.*;

/**
 * Paddle represents a game object in a brick breaker game that responds to user input for movement.
 * It extends GameObject and uses a UserInputListener to handle keyboard input for paddle movement.
 */
public class Paddle extends GameObject {
    private final UserInputListener inputListener;
    private final Vector2 windowDimensions;

    /**
     * Constructor for Paddle.
     *
     * @param topLeftCorner    Position of the object, in window coordinates (pixels).
     *                         Note that (0,0) is the top-left corner of the window.
     * @param dimensions       Width and height in window coordinates.
     * @param renderable       The renderable representing the paddle. Can be null, in which case
     *                         the paddle will not be rendered.
     * @param inputListener    The UserInputListener for handling keyboard input.
     * @param windowDimensions The dimensions of the game's window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.setTag(PADDLE_NAME);
    }

    /**
     * Handles the update logic for the Paddle, adjusting its velocity based on user input for movement.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.checkBoundries();
        Vector2 movementDir = Vector2.ZERO;
        if (inputListener.isKeyPressed(KeyEvent.VK_LEFT)) {
            movementDir = movementDir.add(Vector2.LEFT);
        }

        if (inputListener.isKeyPressed(KeyEvent.VK_RIGHT)) {
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        this.setVelocity(movementDir.mult(PADDLE_MOVEMENT_SPEED));
    }

    private void checkBoundries() {
        if (this.getTopLeftCorner().x() <= BORDER_WIDTH) {
            this.setCenter(new Vector2(PADDLE_DIMENSIONS.x() / 2 + BORDER_WIDTH,
                    this.getTopLeftCorner().y() + PADDLE_DIMENSIONS.y() / 2));
        }
        if (this.getTopLeftCorner().x() + PADDLE_DIMENSIONS.x() >=
                this.windowDimensions.x() - BORDER_WIDTH) {
            this.setCenter(new Vector2(this.windowDimensions.x() -
                    PADDLE_DIMENSIONS.x() / 2 - BORDER_WIDTH,
                    this.getTopLeftCorner().y() + PADDLE_DIMENSIONS.y() / 2));
        }
    }
}
