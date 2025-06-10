package bricker.brick_strategies;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

import static bricker.main.Constants.BALL_NAME;
import static bricker.main.Constants.CAMERA;

/**
 * CameraCollisionStrategy represents a collision strategy for handling collisions involving the
 * camera in a brick breaker game.
 * It extends the BasicCollisionStrategy and inherits its collision handling behavior.
 */
public class CameraCollisionStrategy extends BasicCollisionStrategy{

    private final Counter cameraFlag;


    /**
     * Constructor for CameraCollisionStrategy.
     *
     * @param gameObjects The collection of game objects for collision management.
     * @param bricksLeft  The counter to keep track of remaining bricks in the game.
     * @param cameraFlag  The flag for indicating when the camera strategy should be active/inactive.
     */
    public CameraCollisionStrategy(GameObjectCollection gameObjects, Counter bricksLeft, Counter cameraFlag) {
        super(gameObjects, bricksLeft);
        this.setTag(CAMERA);
        this.cameraFlag = cameraFlag;
    }

    /**
     * Handles the collision event between two game objects involving the camera.
     * This method overrides the behavior of the superclass (BasicCollisionStrategy).
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        super.onCollision(obj1, obj2);
        if(obj2.getTag().equals(BALL_NAME)){
            this.cameraFlag.reset();
            this.cameraFlag.increment();
        }
    }
}
