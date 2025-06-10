package bricker.brick_strategies;

import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

import java.util.Random;

import static bricker.main.Constants.*;

/**
 * StrategyFactory is a factory class responsible for creating various CollisionStrategy objects
 * based on a probability limit.
 * It utilizes the Random class to determine which strategy to create and provides flexibility for
 * extending strategies in the future.
 */
public class StrategyFactory {
    private final Random random;
    private final ImageReader imageReader;
    private final SoundReader soundReader;
    private final GameObjectCollection gameObjects;
    private final Vector2 windowDimensions;
    private final UserInputListener inputListener;
    private final Counter lives;
    private final Counter bricksLeft;
    private final Counter cameraFlag;


    /**
     * Constructor for StrategyFactory.
     *
     * @param imageReader      The ImageReader for reading images.
     * @param soundReader      The SoundReader for reading sounds.
     * @param gameObjects      The collection of game objects for collision management.
     * @param windowDimensions The dimensions of the game window.
     * @param inputListener    The user input listener for controlling the paddle.
     * @param lives            The counter to keep track of player lives.
     * @param bricksLeft       The counter to keep track of remaining bricks in the game.
     * @param cameraFlag       The flag to know if the camera strategy should be active/inactive.
     */
    public StrategyFactory(ImageReader imageReader, SoundReader soundReader,
                           GameObjectCollection gameObjects, Vector2 windowDimensions,
                           UserInputListener inputListener, Counter lives, Counter bricksLeft,
                           Counter cameraFlag) {
        this.random = new Random();
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.gameObjects = gameObjects;
        this.windowDimensions = windowDimensions;
        this.inputListener = inputListener;
        this.lives = lives;
        this.bricksLeft = bricksLeft;
        this.cameraFlag = cameraFlag;
    }

    /**
     * Chooses and creates a CollisionStrategy based on a probability limit.
     *
     * @param limit   The probability limit for strategy selection.
     * @param isFirstDouble A variable stating whether the DoubleCollisionStrategy will be first one
     *                      or not.
     * @return A CollisionStrategy object based on the chosen strategy.
     */
    public CollisionStrategy chooseStrategy(int limit, boolean isFirstDouble) {
        int strategyProbability = this.random.nextInt(limit);
        CollisionStrategy strategy;

        switch (strategyProbability) {

            case PUCK:
                Renderable puckImage = imageReader.readImage(PUCK_IMAGE, true);
                Sound collisionSound = soundReader.readSound(COLLISION_SOUND);
                strategy = new PuckCollisionStrategy(this.gameObjects, puckImage, collisionSound,
                        this.bricksLeft);
                return strategy;

            case GHOST_PADDLE:
                Renderable paddleImage = imageReader.readImage(PADDLE_IMAGE, true);
                strategy = new PaddleCollisionStrategy(this.gameObjects, paddleImage,
                        this.inputListener, this.windowDimensions, this.bricksLeft);
                return strategy;

            case CAMERA:
                strategy = new CameraCollisionStrategy(this.gameObjects, this.bricksLeft, this.cameraFlag);
                return strategy;

            case EXTRA_HP:
                Renderable heartImage = imageReader.readImage(HEART_PATH, true);
                strategy = new ExtraHPCollisionStrategy(this.gameObjects, heartImage, this.lives,
                        this.bricksLeft);
                return strategy;

            case DOUBLE_BEHAVIOR:
                strategy = new DoubleCollisionStrategy(this, isFirstDouble);
                return strategy;

            default:
                strategy = new BasicCollisionStrategy(this.gameObjects, this.bricksLeft);
                return strategy;
        }
    }

}
