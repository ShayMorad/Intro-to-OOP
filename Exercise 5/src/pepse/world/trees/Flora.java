package pepse.world.trees;

import danogl.GameObject;
import danogl.collisions.Layer;
import danogl.gui.rendering.OvalRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.world.Observer;
import pepse.world.Subject;

import java.awt.*;

import java.util.HashMap;
import java.util.Random;
import java.util.function.*;




/**
 * Represents the flora (trees and plants) in the game world.
 */
public class Flora {
    private static final String TRUNK = "trunk";
    private static final String LEAF = "leaf";
    private static final String FRUIT = "fruit";
    private static final int TREE_ELEMENT_X_OFFSET = 3 * Leaf.SIZE;
    private static final int TREE_ELEMENT_Y_OFFSET = 3 * Leaf.SIZE;
    private static final float FRUIT_PROBABILITY = 0.87f;
    private static final int FRUIT_DISTANCE_INTERVAL = 1;
    private static final int TREE_DISTANCE_INTERVAL = 3;
    private static final float TREE_PROBABILITY = 0.8f;
    private static final float LEAF_PROBABILITY = 0.3f;
    private static final int BASE_TRUNK_HEIGHT = 210;
    private static final int TREE_HEIGHT_FACTOR = 30;
    private static final int HEIGHT_VARIANCE = 3;
    private static final int LEAF_LAYER = -50;
    private final Function<Float, Float> groundHeightAt;
    private final Consumer<Float> addEnergy;
    private final Random random;
    private final float cycleTime;
    private final Subject subject;

    /**
     * Constructs a Flora object.
     *
     * @param groundHeightAt Function to get the ground height at a specific x-coordinate.
     * @param subject    The subject we add the tree elements as observers to its observers list.
     *                   Used for the logics of whenever the avatar jumps.
     * @param addEnergy      Consumer to add energy to the avatar.
     * @param cycleTime      The cycle time.
     */
    public Flora(Function<Float, Float> groundHeightAt,Subject subject, Consumer<Float> addEnergy,
                 float cycleTime) {
        this.groundHeightAt = groundHeightAt;
        this.random = new Random();
        this.subject = subject;
        this.addEnergy = addEnergy;
        this.cycleTime = cycleTime;
    }

    /**
     * Creates trees and plants within the specified range.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return A map of GameObjects representing trees by tree elements and their layers.
     */
    public HashMap<GameObject, Integer> createInRange(int minX, int maxX) {
        HashMap<GameObject, Integer> treeElements = new HashMap<>();
        minX = (int) Math.floor((float) minX / Trunk.WIDTH) * Trunk.WIDTH;
        maxX = (int) Math.ceil((float) maxX / Trunk.WIDTH) * Trunk.WIDTH;
        int[] distance = {0};
        for (float i = minX; i < maxX; i += Trunk.WIDTH) {

            if (!shouldMakeTree(distance)) {
                continue;
            }
            float trunkHeight = (float) Math.floor(trunkHeightAt() / TREE_HEIGHT_FACTOR) * TREE_HEIGHT_FACTOR;
            float yCoordinate = (float) Math.floor((groundHeightAt.apply(i) - trunkHeight) /
                    TREE_HEIGHT_FACTOR) * TREE_HEIGHT_FACTOR;
            this.createTrunk(i, yCoordinate, trunkHeight, treeElements);

            this.createTreeElements(i, yCoordinate, treeElements);
        }
        addObserversToAvatar(treeElements);
        return treeElements;
    }

    private void addObserversToAvatar(HashMap<GameObject, Integer> treeElements) {
        for(GameObject object : treeElements.keySet()) {
            if(object.getTag().equals(TRUNK) || object.getTag().equals(LEAF) ||
                    object.getTag().equals(FRUIT)) {
                this.subject.registerObserver((Observer) object);
            }
        }
    }

    private float trunkHeightAt() {
        float height = BASE_TRUNK_HEIGHT;
        int additionFactor = this.random.nextInt(HEIGHT_VARIANCE) * TREE_HEIGHT_FACTOR;
        boolean direction = this.random.nextBoolean();
        return direction ? height + additionFactor : height - additionFactor;
    }

    private boolean shouldMakeTree(int[] distance) {
        double makeTree = Math.random();
        if (makeTree <= TREE_PROBABILITY || distance[0] > 0) {
            distance[0]--;
            return false;
        }
        distance[0] = TREE_DISTANCE_INTERVAL;
        return true;
    }

    private void createTrunk(float i, float yCoordinate, float trunkHeight, HashMap<GameObject,
            Integer> trees) {
        Trunk trunk = new Trunk(new Vector2(i, yCoordinate), trunkHeight);
        trees.put(trunk, Layer.STATIC_OBJECTS);
    }

    private void createTreeElements(float i, float yCoordinate, HashMap<GameObject, Integer> trees) {
        int[] fruitDistance = {0};
        for (float j = i - TREE_ELEMENT_X_OFFSET; j < i + TREE_ELEMENT_X_OFFSET; j += Leaf.SIZE) {
            for (float k = yCoordinate - TREE_ELEMENT_Y_OFFSET;
                 k < yCoordinate + TREE_ELEMENT_Y_OFFSET; k += Leaf.SIZE) {
                Leaf leaf = this.createLeaf(j, k);
                if (leaf != null) {
                    trees.put(leaf, LEAF_LAYER);
                }
                Fruit fruit = createFruit(j, k, fruitDistance, yCoordinate);
                if (fruit != null) {
                    trees.put(fruit, Layer.DEFAULT);
                }
            }
            fruitDistance[0]--;
        }
    }


    private Leaf createLeaf(float j, float k) {
        double probability = Math.random();
        if (probability < LEAF_PROBABILITY) {
            return null;
        }
        return new Leaf(new Vector2(j, k));
    }

    private boolean shouldMakeFruit(int[] fruitDistance, float yCoordinate, float k) {
        double makeFruit = Math.random();
        if (makeFruit < FRUIT_PROBABILITY || fruitDistance[0] > 0 || (k>=yCoordinate)) {
            fruitDistance[0]--;
            return false;
        }
        fruitDistance[0] = FRUIT_DISTANCE_INTERVAL;
        return true;
    }

    private Fruit createFruit(float j, float k, int[] fruitDistance, float yCoordinate) {
        if (!shouldMakeFruit(fruitDistance, yCoordinate, k)) {
            return null;
        }
        Renderable fruitRenderable =
                new OvalRenderable(Color.red);
        return new Fruit(new Vector2(j, k), fruitRenderable, addEnergy, cycleTime);
    }

}





