package bricker.gameobjects;
import danogl.GameObject;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.awt.*;

import static bricker.main.Constants.*;

/**
 * LifeText represents a game object in a brick breaker game that displays the player's remaining lives
 * as text.
 * It extends GameObject and uses a TextRenderable to render the text based on the current number of lives.
 */
public class LifeText extends GameObject {

    private final Counter lives;
    private final TextRenderable text;

    /**
     * Constructor for LifeText.
     *
     * @param topLeftCorner Position of the object, in window coordinates (pixels).
     *                      Note that (0,0) is the top-left corner of the window.
     * @param dimensions Width and height in window coordinates.
     * @param renderable The renderable representing the text. Can be null, in which case
     *                   the text will not be rendered.
     * @param lives The counter for tracking player lives.
     */
    public LifeText(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable, Counter lives) {
        super(topLeftCorner, dimensions, renderable);
        this.lives = lives;
        this.text = (TextRenderable) renderable;
        this.text.setString(LIVES_STRING + this.lives.value());
        this.setColor();
    }

    /**
     * Handles the update logic for the LifeText, updating the displayed text and color based on the
     * current number of lives.
     *
     * @param deltaTime The time elapsed since the last frame.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        this.text.setString(LIVES_STRING + this.lives.value());
        this.setColor();
    }
    private void setColor(){
        if(this.lives.value() == RED_AMOUNT){
            this.text.setColor(Color.red);
        } else if (this.lives.value() == YELLOW_AMOUNT) {
            this.text.setColor(Color.yellow);
        }
        else{
            this.text.setColor(Color.green);
        }
    }
}
