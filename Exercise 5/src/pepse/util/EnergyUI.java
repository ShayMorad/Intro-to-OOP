package pepse.util;

import danogl.GameObject;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;

import java.util.function.Supplier;

/**
 * Represents a user interface element displaying energy level.
 */
public class EnergyUI extends GameObject {
    private static final Vector2 SIZE = new Vector2(50,50);
    private final TextRenderable text;
    private final Supplier<Float> supplier;


    /**
     * Constructs an EnergyUI object.
     *
     * @param topLeftCorner   The top-left corner position of the UI element.
     * @param dimensions      The dimensions of the UI element.
     * @param renderable      The text renderable for displaying energy level.
     * @param energySupplier  The supplier for fetching the energy level.
     */
    public EnergyUI(Vector2 topLeftCorner, Vector2 dimensions, TextRenderable renderable,
                    Supplier<Float> energySupplier) {
        super(topLeftCorner, dimensions.add(SIZE), renderable);
        this.text = renderable;
        this.supplier = energySupplier;
    }

    /**
     * Updates the energy UI element.
     *
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        int energy = (int) Math.floor(this.supplier.get());
        this.text.setString(energy+"%");
    }
}
