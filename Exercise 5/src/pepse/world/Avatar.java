package pepse.world;

import danogl.GameObject;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.AnimationRenderable;
import danogl.util.Vector2;


import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * Represents the avatar character in the game.
 */
public class Avatar extends GameObject implements Subject{
    private static final String[] idleImages = {"assets/idle_0.png", "assets/idle_1.png", "assets" +
            "/idle_2.png", "assets/idle_3.png"};
    private static final String[] runImages = {"assets/run_0.png", "assets/run_1.png", "assets/run_2" +
            ".png", "assets/run_3.png", "assets/run_4.png", "assets/run_5.png"};
    private static final String[] jumpImages = {"assets/jump_0.png", "assets/jump_1.png", "assets" +
            "/jump_2.png", "assets/jump_3.png"};
    private static final String AVATAR = "assets/idle_0.png";
    private static final float WIDTH = 30f;
    private static final float HEIGHT = 50f;
    private static final float GRAVITY = 500f;
    private static final float VELOCITY_X = 300f;
    private static final float VELOCITY_Y = -550;
    private static final float MOVEMENT_COST = 0.5f;
    private static final float JUMP_COST = 10f;
    private static final float MAX_ENERGY = 100f;
    private static final float ANIMATION_TIME = 0.2f;
    private static final String TAG = "avatar";
    private static final float ENERGY_INCREMENT = 1f;
    private float energy = 100f;
    private final UserInputListener inputListener;
    private AnimationRenderable idleAnimation;
    private AnimationRenderable runAnimation;
    private AnimationRenderable jumpAnimation;
    private final ArrayList<Observer> observers;


    /**
     * Constructs an Avatar object.
     *
     * @param pos          The position of the avatar.
     * @param inputListener The input listener for controlling the avatar.
     * @param imageReader  The image reader for loading avatar images.
     */
    public Avatar(Vector2 pos, UserInputListener inputListener, ImageReader imageReader) {
        super(pos.add(new Vector2(0, -HEIGHT*2)), new Vector2(WIDTH, HEIGHT),
                imageReader.readImage(AVATAR, false));
        physics().preventIntersectionsFromDirection(Vector2.ZERO);
        transform().setAccelerationY(GRAVITY);
        this.inputListener = inputListener;
        this.initializeAnimations(imageReader);
        this.setTag(TAG);
        this.observers = new ArrayList<>();
    }

    /**
     * Retrieves the current energy level of the avatar.
     *
     * @return The current energy level.
     */
    public float getEnergy() {
        return this.energy;
    }

    /**
     * Increases the energy level of the avatar.
     *
     * @param energy The energy to add.
     */
    public void addEnergy(float energy) {
        this.energy = Math.min(MAX_ENERGY, this.energy + energy);
    }

    /**
     * Updates the avatar's state and behavior.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        updatePosition();
        updateEnergy();
        updateAnimation();
    }

    private void updatePosition() {
        float xVel = 0;
        boolean leftKeyPressed = inputListener.isKeyPressed(KeyEvent.VK_LEFT);
        boolean rightKeyPressed = inputListener.isKeyPressed(KeyEvent.VK_RIGHT);
        if ((leftKeyPressed || rightKeyPressed) && this.energy >= MOVEMENT_COST) {
            if(inputListener.isKeyPressed(KeyEvent.VK_LEFT))
                xVel -= VELOCITY_X;
            if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT))
                xVel += VELOCITY_X;
        }
        if(xVel!=0) {
            this.decreaseEnergy(MOVEMENT_COST);
        }
        transform().setVelocityX(xVel);
        if (inputListener.isKeyPressed(KeyEvent.VK_SPACE) && getVelocity().y() == 0 &&
                this.energy >= JUMP_COST) {
            transform().setVelocityY(VELOCITY_Y);
            this.decreaseEnergy(JUMP_COST);
            notifyObservers();
        }

    }

    /**
     * Registers a new observer
     * @param observer the observer to add to the subjects list.
     * @return true if successful, false otherwise
     */
    public boolean registerObserver(Observer observer){
        return this.observers.add(observer);
    }

    /**
     * Unregisters observer
     * @param observer the observer to remove from the subjects list.
     * @return true if successful, false otherwise
     */
    public boolean unregisterObserver(Observer observer){
        return this.observers.remove(observer);
    }

    /**
     * Notifies all the observers that the avatar has jumped right now.
     */
    public void notifyObservers() {
        for(Observer observer : observers){
            observer.pushJumpNotification();
        }
    }

    private void decreaseEnergy(float cost) {
        this.energy -= cost;
    }

    private void updateEnergy() {
        if (this.getVelocity().x() == 0f && this.getVelocity().y() == 0f && this.energy < MAX_ENERGY) {
            this.energy = Math.min(MAX_ENERGY, this.energy + ENERGY_INCREMENT);
        }
    }

    private void updateAnimation() {
        boolean flipped = this.renderer().isFlippedHorizontally();
        if (this.getVelocity().x() < 0 || this.getVelocity().x() > 0) {
            this.renderer().setRenderable(runAnimation);
            flipped = this.getVelocity().x() < 0;
        } else if (this.getVelocity().y() != 0) {
            this.renderer().setRenderable(jumpAnimation);
        } else {
            this.renderer().setRenderable(idleAnimation);
        }
        this.renderer().setIsFlippedHorizontally(flipped);
    }

    private void initializeAnimations(ImageReader imageReader) {
        this.idleAnimation = new AnimationRenderable(idleImages, imageReader, false, ANIMATION_TIME);
        this.runAnimation = new AnimationRenderable(runImages, imageReader, false, ANIMATION_TIME);
        this.jumpAnimation = new AnimationRenderable(jumpImages, imageReader, false, ANIMATION_TIME);
    }
}
