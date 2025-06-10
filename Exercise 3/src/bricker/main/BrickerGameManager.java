package bricker.main;

import bricker.brick_strategies.*;
import bricker.gameobjects.*;
import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.components.CoordinateSpace;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.Camera;
import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import danogl.gui.Sound;

import static bricker.main.Constants.*;


import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * The main game manager class for the Brick Breaker game.
 * Manages game initialization, updates, and handles user input.
 */
public class BrickerGameManager extends GameManager {


    //************ LIVES ************//
    private Counter lives;

    //************ BRICKS ************//

    private final int brickRows;
    private final int brickCols;
    private Counter bricksLeft;
    private Counter cameraFlag;


    //************ BALL ************//
    private Ball ball;
    private int ballCollisionCounter;


    //************ WINDOW ************//
    private Vector2 windowDimensions;
    private WindowController windowController;
    private UserInputListener inputListener;
    private ImageReader imageReader;
    private SoundReader soundReader;


    /**
     * Constructs a new BrickerGameManager with default brick rows and columns.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions) {
        this(windowTitle, windowDimensions, DEFAULT_BRICK_ROWS, DEFAULT_BRICK_COLS);
    }

    /**
     * Constructs a new BrickerGameManager with specified brick rows and columns.
     *
     * @param windowTitle      The title of the game window.
     * @param windowDimensions The dimensions of the game window.
     * @param brickRows        The number of rows for bricks.
     * @param brickCols        The number of columns for bricks.
     */
    public BrickerGameManager(String windowTitle, Vector2 windowDimensions, int brickRows,
                              int brickCols) {
        super(windowTitle, windowDimensions);

        if (brickRows <= 0 || brickCols <= 0) {
            this.brickCols = 0;
            this.brickRows = 0;
        } else {
            this.brickRows = brickRows;
            this.brickCols = brickCols;
        }

    }

    /**
     * Initializes the game, including background, borders, ball, paddles, bricks, and UI elements.
     *
     * @param imageReader      The image reader for loading images.
     * @param soundReader      The sound reader for loading sounds.
     * @param inputListener    The user input listener for handling keyboard input.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader,
                               SoundReader soundReader,
                               UserInputListener inputListener,
                               WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        this.windowController = windowController;
        this.lives = new Counter(DEFAULT_LIVES);
        this.windowDimensions = windowController.getWindowDimensions();
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.cameraFlag = new Counter(0);
        this.bricksLeft = new Counter(this.brickRows * this.brickCols);
        this.ballCollisionCounter = 0;
        this.initializeBackGround();
        this.initializeBorders();
        this.initializeBall();
        this.initializePaddles();
        this.initializeBricks();
        this.initializeLifeGraphic();
        this.initializeLifeText();
    }

    private void initializeBall() {

        Sound collisionSound = this.soundReader.readSound(COLLISION_SOUND);
        Renderable ballImage = this.imageReader.readImage(BALL_IMAGE, true);
        this.ball = new Ball(Vector2.ZERO, new Vector2(BALL_DIMENSION, BALL_DIMENSION), ballImage,
                collisionSound);
        this.setBallRandomVelocity();
        ball.setCenter(this.windowDimensions.mult(MID_OF_BOARD));
        this.gameObjects().addGameObject(ball);
    }

    private void initializePaddles() {
        Renderable paddleImage = this.imageReader.readImage(PADDLE_IMAGE, true);

        GameObject userPaddle = new Paddle(Vector2.ZERO, PADDLE_DIMENSIONS, paddleImage,
                this.inputListener, this.windowDimensions);
        userPaddle.setCenter(new Vector2(this.windowDimensions.x() / 2,
                this.windowDimensions.y() - PADDLE_Y_DISTANCE));
        this.gameObjects().addGameObject(userPaddle);
    }

    private void initializeBorders() {

        Vector2[] bordersDimensions = {
                new Vector2(BORDER_WIDTH, this.windowDimensions.y()),
                new Vector2(this.windowDimensions.x(), (float) BORDER_WIDTH),
                new Vector2(BORDER_WIDTH, this.windowDimensions.y())
        };

        for (int i = 0; i < bordersDimensions.length; i++) {
            GameObject border = new GameObject(Vector2.ZERO, bordersDimensions[i],
                    new RectangleRenderable(BORDER_COLOR));
            // i=2 is the right border, so we need to manually set it's center
            if (i == RIGHT_BORDER) {
                border.setCenter(new Vector2(windowDimensions.x(), windowDimensions.y() / 2));
            }
            this.gameObjects().addGameObject(border, Layer.STATIC_OBJECTS);
        }
    }

    private void initializeBackGround() {
        Renderable backgroundImage = this.imageReader.readImage(BACKGROUND_PATH, false);
        GameObject background = new GameObject(Vector2.ZERO, new Vector2(this.windowDimensions.x(),
                this.windowDimensions.y()), backgroundImage);
        background.setCoordinateSpace(CoordinateSpace.CAMERA_COORDINATES);
        this.gameObjects().addGameObject(background, Layer.BACKGROUND);
    }

    private void initializeBricks() {
        Renderable brickImage = this.imageReader.readImage(BRICK_PATH, false);
        float brickWidth = ((this.windowDimensions.x() - BORDER_WIDTH - BORDER_WIDTH) / this.brickCols);

        StrategyFactory factory = new StrategyFactory(this.imageReader, this.soundReader,
                this.gameObjects(), this.windowDimensions, this.inputListener, this.lives,
                this.bricksLeft, this.cameraFlag);
        for (int i = 0; i < this.brickRows; i++) {
            for (int j = 0; j < this.brickCols; j++) {
                GameObject brick = new Brick(
                        Vector2.ZERO,
                        new Vector2(brickWidth - (1 / brickWidth), BRICK_HEIGHT),
                        brickImage,
                        factory.chooseStrategy(BRICK_STRATEGY_LIMIT, true));
                brick.setCenter(new Vector2(BORDER_WIDTH + 1 + (j) * (brickWidth) + brickWidth / 2,
                        BORDER_WIDTH + 2 + (float) (i * (BRICK_HEIGHT + 2)) +
                                (float) BRICK_HEIGHT / 2));
                this.gameObjects().addGameObject(brick, Layer.STATIC_OBJECTS);
            }
        }
    }

    private void initializeLifeGraphic() {
        Renderable heartImage = this.imageReader.readImage(HEART_PATH, true);
        GameObject lifeGraphic = new LifeGraphic(
                new Vector2(BORDER_WIDTH * 2,
                        this.windowDimensions.y() - (HEART_SIZE + BORDER_WIDTH) * 2),
                new Vector2(HEART_SIZE, HEART_SIZE),
                heartImage,
                this.gameObjects(),
                this.lives);
        this.gameObjects().addGameObject(lifeGraphic, Layer.UI);
    }

    private void initializeLifeText() {
        TextRenderable text = new TextRenderable(EMPTY_PROMT, FONT_NAME, false, true);
        GameObject LifeText = new LifeText(
                new Vector2(BORDER_WIDTH * 2, this.windowDimensions.y() -
                        (HEART_SIZE + BORDER_WIDTH)),
                new Vector2(HEART_SIZE, HEART_SIZE),
                text,
                this.lives);
        this.gameObjects().addGameObject(LifeText, Layer.UI);
    }

    /**
     * Updates the game state by handling various aspects such as:
     * - Updating hearts based on the ball's position.
     * - Checking for game-ending conditions (win or lose).
     * - Checking if the 'W' key is pressed to manually end the game.
     * - Removing game objects that have gone out of bounds.
     * - Changing camera focus.
     * - Resetting camera focus.
     *
     * @param deltaTime The time elapsed since the last frame, used for time-based calculations.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.changeCamera();
        this.resetCamera();
        this.checkHearts();
        this.checkForGameEnd();
        this.checkPressedW();
        this.deleteOutOfBoundries();
    }

    private void checkHearts() {
        float ballHeight = ball.getCenter().y();
        if (ballHeight > this.windowDimensions.y()) {
            this.lives.decrement();
            if (this.lives.value() > 0) {
                this.setBallRandomVelocity();
                this.ball.setCenter(this.windowDimensions.mult(0.5F));
            }
        }
    }

    private void checkForGameEnd() {
        String promt = EMPTY_PROMT;

        if (this.lives.value() <= 0) {
            promt = LOSE_PROMT;
        }
        if (this.bricksLeft.value() <= 0) {
            promt = WIN_PROMT;
        }
        if (!promt.isEmpty()) {
            promt += REPLAY_PROMT;
            if (this.windowController.openYesNoDialog(promt)) {
                this.windowController.resetGame();
            } else {
                this.windowController.closeWindow();
            }
        }
    }

    private void checkPressedW() {
        if (this.inputListener.isKeyPressed(KeyEvent.VK_W)) {
            String promt = WIN_PROMT + REPLAY_PROMT;
            if (this.windowController.openYesNoDialog(promt)) {
                this.windowController.resetGame();
            } else {
                this.windowController.closeWindow();
            }
        }
    }

    private void deleteOutOfBoundries() {
        for (GameObject obj : this.gameObjects()) {
            if (obj.getTag().equals(PUCK_NAME) || obj.getTag().equals(HEART_NAME)) {
                float ballHeight = obj.getCenter().y();
                if (ballHeight > this.windowDimensions.y()) {
                    this.gameObjects().removeGameObject(obj);
                }
            }
        }
    }

    private void changeCamera() {
        if (this.camera() == null && this.cameraFlag.value() > 0) {
            this.setCamera(new Camera(
                    this.ball,
                    Vector2.ZERO,
                    windowController.getWindowDimensions().mult(ZOOM),
                    windowController.getWindowDimensions()
            ));
            this.ballCollisionCounter = this.ball.getCollisionCounter();
        }
    }

    private void resetCamera() {
        if (this.ball.getCollisionCounter() - this.ballCollisionCounter >= BALL_RESET_CAMERA_CONDITION) {
            this.setCamera(null);
            this.cameraFlag.reset();
        }
    }

    private void setBallRandomVelocity() {
        float ballVelX = BALL_SPEED;
        float ballVelY = BALL_SPEED;
        Random random = new Random();
        if (random.nextBoolean()) {
            ballVelX *= -1;
        }
        if (random.nextBoolean()) {
            ballVelY *= -1;
        }
        ball.setVelocity(new Vector2(ballVelX, ballVelY));
    }

    /**
     * Runs the Brick Breaker game.
     *
     * @param arg Command-line arguments (optional).
     */
    public static void main(String[] arg) {
        BrickerGameManager manager;
        if (arg.length == PROGRAM_PARAMETERS) {
            int brickCols = Integer.parseInt(arg[0]);
            int brickRows = Integer.parseInt(arg[1]);
            manager = new BrickerGameManager(GAME_NAME,
                    GAME_DIMENSIONS, brickRows, brickCols);

        } else {
            manager = new BrickerGameManager(GAME_NAME,
                    GAME_DIMENSIONS);
        }
        manager.run();
    }
}
