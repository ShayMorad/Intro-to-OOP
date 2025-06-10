package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Stack;

import static bricker.main.Constants.HEART_SIZE;

/**
 * LifeGraphic represents a game object in a brick breaker game that visually displays the player's
 * remaining lives.
 * It extends GameObject and manages a stack of Heart objects to represent the player's lives.
 */
public class LifeGraphic extends GameObject {
    private final Counter counter;
    private final Vector2 topLeftCorner;
    private final Vector2 dimensions;
    private final Renderable renderable;
    private final GameObjectCollection gameObjects;
    private final Stack<GameObject> heartsStack;

    /**
     * Constructor for LifeGraphic.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height of each heart in window coordinates.
     * @param renderable The renderable representing the heart. Can be null, in which case
     *                   the hearts will not be rendered.
     * @param gameObjects The collection of game objects for managing collisions.
     * @param counter The counter for tracking player lives.
     */
    public LifeGraphic(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       GameObjectCollection gameObjects, Counter counter) {

        super(topLeftCorner, Vector2.ZERO, null);
        this.gameObjects = gameObjects;
        this.counter = counter;
        this.heartsStack = new Stack<>();
        this.topLeftCorner = topLeftCorner;
        this.dimensions = dimensions;
        this.renderable = renderable;
        makeHearts(this.counter.value());
    }

    private void makeHearts(int amountOfHeartsToMake) {
        int x = this.heartsStack.size();
        for (int i = 0; i < amountOfHeartsToMake; i++) {
            GameObject heart =
                    new Heart(
                            new Vector2(topLeftCorner.x() + (i + x) * (topLeftCorner.x()/2 +
                                    ((int)HEART_SIZE)),
                            topLeftCorner.y()),
                            dimensions,
                            renderable,
                            this.counter,
                            this.gameObjects
                    );
            this.heartsStack.add(heart);
            this.gameObjects.addGameObject(heart, Layer.UI);
        }

    }

    /**
     * Handles the update logic for the LifeGraphic, removing or adding hearts based on player lives.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if (this.counter.value() < this.heartsStack.size()) {
            int extraLives = this.heartsStack.size() - this.counter.value();
            for (int i = 0; i < extraLives; i++) {
                GameObject heart = this.heartsStack.pop();
                this.gameObjects.removeGameObject(heart, Layer.UI);
            }
        }
        if (this.heartsStack.size() < this.counter.value()) {
            int extraLives = this.counter.value() - this.heartsStack.size();
            this.makeHearts(extraLives);
        }
    }
}
