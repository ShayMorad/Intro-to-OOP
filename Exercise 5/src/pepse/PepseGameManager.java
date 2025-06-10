package pepse;

import danogl.GameManager;
import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.ImageReader;
import danogl.gui.SoundReader;
import danogl.gui.UserInputListener;
import danogl.gui.WindowController;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import pepse.util.EnergyUI;
import pepse.world.Avatar;
import pepse.world.Block;
import pepse.world.Sky;
import pepse.world.Terrain;
import pepse.world.daynight.Night;
import pepse.world.daynight.Sun;
import pepse.world.daynight.SunHalo;
import pepse.world.trees.Flora;

import java.util.*;
/**
 * The main class responsible for managing the PEPSE simulator.
 */
public class PepseGameManager extends GameManager {
    private static final float CYCLE_LENGTH = 30;
    private static final String FONT = "Courier New";
    private static final boolean IS_ITALIC = false;
    private static final boolean IS_BOLD = true;
    private static final int SEED_BOUND = 10;
    private WindowController windowController;
    private UserInputListener inputListener;
    private ImageReader imageReader;



    /**
     * Initializes the game with required resources and objects.
     *
     * @param imageReader      The image reader for loading game assets.
     * @param soundReader      The sound reader for loading game audio.
     * @param inputListener    The input listener for handling user input.
     * @param windowController The window controller for managing the game window.
     */
    @Override
    public void initializeGame(ImageReader imageReader, SoundReader soundReader,
                               UserInputListener inputListener, WindowController windowController) {
        super.initializeGame(imageReader, soundReader, inputListener, windowController);
        gameObjects().addGameObject(Sky.create(windowController.getWindowDimensions()), Layer.BACKGROUND);
        this.windowController = windowController;
        this.inputListener = inputListener;
        this.imageReader = imageReader;
        Terrain terrain = this.createTerrain();
        this.createDayNight();
        this.createSun();
        Avatar avatar = this.createAvatar(terrain);
        this.createEnergyUI(avatar);
        this.createFlora(terrain, avatar);
    }

    private Terrain createTerrain() {
        Terrain terrain = new Terrain(windowController.getWindowDimensions(),
                new Random().nextInt(SEED_BOUND));
        List<? extends GameObject> blocks = terrain.createInRange(0,
                (int) windowController.getWindowDimensions().x());
        for (GameObject block : blocks) {
            gameObjects().addGameObject(block, Layer.STATIC_OBJECTS);
        }
        return terrain;
    }

    private void createDayNight() {
        gameObjects().addGameObject(Night.create(windowController.getWindowDimensions(), CYCLE_LENGTH),
                Layer.FOREGROUND);
    }

    private void createSun() {
        GameObject sun = Sun.create(windowController.getWindowDimensions(), CYCLE_LENGTH);
        gameObjects().addGameObject(sun, Layer.BACKGROUND);
        gameObjects().addGameObject(SunHalo.create(sun), Layer.BACKGROUND);
    }

    private Avatar createAvatar(Terrain terrain) {
        float height = this.chooseHeight(terrain);
        Avatar avatar = new Avatar(
                new Vector2(windowController.getWindowDimensions().x() / 2 - (float) Block.SIZE / 2,
                        (float) Math.floor(height) * Block.SIZE),
                inputListener,
                imageReader);
        gameObjects().addGameObject(avatar);
        return avatar;
    }

    private float chooseHeight(Terrain terrain){
        float middleHeight =
                terrain.groundHeightAt(windowController.getWindowDimensions().x() / 2) / Block.SIZE;
        float leftHeight =
                terrain.groundHeightAt(windowController.getWindowDimensions().x() / 2 - Block.SIZE)
                        / Block.SIZE;
        float rightHeight =
                terrain.groundHeightAt(windowController.getWindowDimensions().x() / 2 + Block.SIZE)
                        / Block.SIZE;
        return Math.min(Math.min(middleHeight, leftHeight), rightHeight);
    }

    private void createEnergyUI(Avatar avatar) {
        TextRenderable energyRenderable = new TextRenderable(new String(), FONT, IS_ITALIC, IS_BOLD);
        GameObject energyUI = new EnergyUI(Vector2.ZERO, Vector2.ZERO, energyRenderable, avatar::getEnergy);
        gameObjects().addGameObject(energyUI, Layer.UI);
    }

    private void createFlora(Terrain terrain, Avatar avatar) {
        Flora flora = new Flora(terrain::groundHeightAt, avatar, avatar::addEnergy, CYCLE_LENGTH);
        Map<? extends GameObject, Integer> trees = flora.createInRange(0,
                (int) windowController.getWindowDimensions().x());
        for (Map.Entry<? extends GameObject, Integer> entry : trees.entrySet()) {
            gameObjects().addGameObject(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Main method to start the PEPSE game.
     *
     * @param args Command line arguments (unused).
     */
    public static void main(String[] args) {
        new PepseGameManager().run();
    }
}
