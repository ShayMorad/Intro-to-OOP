package bricker.main;

import danogl.util.Vector2;

import java.awt.*;

/**
 * Constants class containing various parameters and paths used in the Bricker game.
 */
public final class Constants {

    private Constants(){
        // No need to instantiate the class, we can hide its constructor
    }

    //************ TEXT ************//

    /**
     * Default font name used for rendering text in the game.
     */
    public static final String FONT_NAME = "OCR A Extended";
    /**
     * Empty string prompt used in dialog boxes.
     */
    public static final String EMPTY_PROMT = "";
    /**
     * Lose prompt displayed when the player loses the game.
     */
    public static final String LOSE_PROMT = "You lose!";
    /**
     * Win prompt displayed when the player wins the game.
     */
    public static final String WIN_PROMT = "You win!";
    /**
     * Replay prompt asking if the player wants to play again.
     */
    public static final String REPLAY_PROMT = " Play again?";
    /**
     * Represents the name of the game which is set to the window that is opened when the game starts.
     */
    public static final String GAME_NAME = "Bouncing Ball";
    /**
     * Dimensions of the game's window.
     */
    public static final Vector2 GAME_DIMENSIONS = new Vector2(700,500);

    //************ LIVES ************//

    /**
     * Default number of lives a player starts with.
     */
    public static final int DEFAULT_LIVES = 3;
    /**
     * Maximum limit for the number of lives.
     */
    public static final int LIVES_LIMIT = 4;
    /**
     * Size of the heart graphic representing a life.
     */
    public static final float HEART_SIZE = 20;
    /**
     * File path for the heart graphic.
     */
    public static final String HEART_PATH = "assets/heart.png";
    /**
     * Speed of the heart graphic movement.
     */
    public static final float HEART_SPEED = 100;
    /**
     * Tag for identifying heart game objects.
     */
    public static final String HEART_NAME = "heart";
    /**
     * The initial text for representing the lives in the game in a textual manner.
     */
    public static final String LIVES_STRING = "Lives:";
    /**
     * The amount of lives for yellow text
     */
    public static final int YELLOW_AMOUNT = 2;
    /**
     * The amount of lives for red text
     */
    public static final int RED_AMOUNT = 1;

    //************ BG ************//

    /**
     * File path for the background image.
     */
    public static final String BACKGROUND_PATH = "assets/DARK_BG2_small.jpeg";

    //************ BRICKS ************//

    /**
     * File path for the brick image.
     */
    public static final String BRICK_PATH = "assets/brick.png";
    /**
     * Tag for identifying brick game objects.
     */
    public static final String BRICK_NAME = "brick";
    /**
     * Default number of rows for bricks in the game.
     */
    public static final int DEFAULT_BRICK_ROWS = 7;
    /**
     * Default number of columns for bricks in the game.
     */
    public static final int DEFAULT_BRICK_COLS = 8;
    /**
     * Height of each brick in pixels.
     */
    public static final int BRICK_HEIGHT = 15;
    /**
     * Amount of pucks to make for the Puck behavior.
     */
    public static final int NUM_OF_PUCKS = 2;
    /**
     * Strategy code for the Puck collision.
     */
    public static final int PUCK = 0;
    /**
     * Strategy code for the Ghost Paddle collision.
     */
    public static final int GHOST_PADDLE = 1;
    /**
     * Strategy code for the Camera collision.
     */
    public static final int CAMERA = 2;
    /**
     * Strategy code for the Extra HP (Heart) collision.
     */
    public static final int EXTRA_HP = 3;
    /**
     * Strategy code for the Double Behavior collision.
     */
    public static final int DOUBLE_BEHAVIOR = 4;
    /**
     * Strategy code for the Basic collision.
     */
    public static final int BASIC = -1;

    /**
     * Limit for the random selection of brick collision strategies.
     */
    public static final int BRICK_STRATEGY_LIMIT = 10;
    /**
     * Limit for the random selection of brick collision strategies for when they are made from the
     * DoubleCollisionStrategy.
     */
    public static final int DOUBLE_BEHAVIOR_FACTORY_LIMIT = 5;

    /**
     * Limit for the amount of brick collision strategies that can be rolled for a Double Behavior.
     */
    public static final int DOUBLE_BEHAVIOR_LIMIT = 2;


    //************ BALL ************//

    /**
     * File path for the collision sound of the ball.
     */
    public static final String COLLISION_SOUND = "assets/blop_cut_silenced.wav";
    /**
     * File path for the ball image.
     */
    public static final String BALL_IMAGE = "assets/ball.png";
    /**
     * Tag for identifying the ball game object.
     */
    public static final String BALL_NAME = "ball";
    /**
     * Diameter of the ball in pixels.
     */
    public static final float BALL_DIMENSION = 20;
    /**
     * Diameter of the ball in pixels.
     */
    public static final float PUCK_DIMENSION = BALL_DIMENSION*0.75F;

    /**
     * Speed of the ball movement.
     */
    public static final float BALL_SPEED = 250;
    /**
     * Condition for resetting the camera after a certain number of ball collisions.
     */
    public static final int BALL_RESET_CAMERA_CONDITION = 4;
    /**
     * File path for the Puck image.
     */
    public static final String PUCK_IMAGE = "assets/mockBall.png";

    /**
     * Tag for identifying the Puck game object.
     */
    public static final String PUCK_NAME = "puck";


    //************ PADDLES ************//

    /**
     * File path for the paddle image.
     */
    public static final String PADDLE_IMAGE = "assets/paddle.png";
    /**
     * Tag for identifying the paddle game object.
     */
    public static final String PADDLE_NAME = "paddle";
    /**
     * Tag for identifying the ghost paddle game object.
     */
    public static final String GHOST_PADDLE_NAME = "ghostPaddle";
    /**
     * Dimensions of the standard paddle in pixels (width x height).
     */
    public static final Vector2 PADDLE_DIMENSIONS = new Vector2(100, 15);
    /**
     * Vertical distance from the top of the window to the paddle.
     */
    public static final int PADDLE_Y_DISTANCE = 30;
    /**
     * Movement speed of the standard paddle.
     */
    public static final float PADDLE_MOVEMENT_SPEED = 300;
    /**
     * Limit for the ghost paddle appearance.
     */
    public static final float GHOST_PADDLE_LIMIT = 4;

    //************ BORDERS ************//

    /**
     * Width of the borders surrounding the game window.
     */
    public static final int BORDER_WIDTH = 5;
    /**
     * Color of the borders.
     */
    public static final Color BORDER_COLOR = Color.WHITE;
    /**
     * Right border index
     */
    public static final int RIGHT_BORDER = 2;

    //************ MISC ************//
    /**
     * The amount of allowed parameters for the program.
     */
    public static final int PROGRAM_PARAMETERS = 2;

    /**
     * A constant representing when we want to multiply to get the mid of the board from the full size.
     */
    public static final float MID_OF_BOARD = 0.5F;

    /**
     * A constant representing the zoom of the camera.
     */
    public static final float ZOOM = 1.2F;
}
