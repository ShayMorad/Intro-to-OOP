package pepse.world;

import danogl.gui.rendering.RectangleRenderable;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import pepse.util.ColorSupplier;
import pepse.util.NoiseGenerator;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the terrain in the game world.
 */
public class Terrain {
    /** The factor determining the height of the terrain. */
    public static final float TERRAIN_HEIGHT_FACTOR = (2 / 3f);
    private static final int TERRAIN_DEPTH = 20;
    private static final Color BASE_GROUND_COLOR = new Color(212, 123, 74);
    private final int groundHeightAtX0;
    private static final float BLOCK_FACTOR = Block.SIZE * 7f;
    private final NoiseGenerator generator;


    /**
     * Constructs a Terrain object.
     *
     * @param windowDimensions The dimensions of the game window.
     * @param seed             The seed value for terrain generation.
     */
    public Terrain(Vector2 windowDimensions, int seed) {
        this.groundHeightAtX0 = (int) (windowDimensions.y() * TERRAIN_HEIGHT_FACTOR);
        this.generator = new NoiseGenerator(seed, this.groundHeightAtX0);
    }

    /**
     * Calculates the height of the terrain at the given x-coordinate.
     *
     * @param x The x-coordinate.
     * @return The height of the terrain at the specified x-coordinate.
     */
    public float groundHeightAt(float x) {
        float noise = (float) generator.noise(x, BLOCK_FACTOR);
        return (float) groundHeightAtX0 + noise;
    }

    /**
     * Creates a list of blocks within the specified range.
     *
     * @param minX The minimum x-coordinate.
     * @param maxX The maximum x-coordinate.
     * @return The list of blocks.
     */
    public List<Block> createInRange(int minX, int maxX) {
        minX = (int) Math.floor((float) minX / Block.SIZE) * Block.SIZE;
        maxX = (int) Math.ceil((float) maxX / Block.SIZE) * Block.SIZE;
        List<Block> terrain = new ArrayList<Block>();
        for (float i = minX; i < maxX; i += Block.SIZE) {
            float height = groundHeightAt(i) / Block.SIZE;
            float yCoordinate = (float) Math.floor(height) * (float) Block.SIZE;
            for (int j = 0; j < TERRAIN_DEPTH; j++) {
                Renderable blockRenderable = new RectangleRenderable(
                        ColorSupplier.approximateColor(BASE_GROUND_COLOR));
                Block block = new Block(new Vector2(i, yCoordinate + j * Block.SIZE), blockRenderable);
                terrain.add(block);
            }
        }
        return terrain;
    }
}
