package bricker.brick_strategies;
import danogl.GameObject;
import java.util.ArrayList;
import static bricker.main.Constants.*;

/**
 * DoubleCollisionStrategy represents a decorator for a collision strategy for a brick in a
 * brick breaker game with the ability to have multiple collision strategies.
 * It implements the CollisionStrategy interface and allows the brick to have up to two additional
 * collision strategies.
 */
public class DoubleCollisionStrategy implements CollisionStrategy {
    private final ArrayList<CollisionStrategy> strategies;
    private final StrategyFactory strategyFactory;
    private final boolean isFirstDouble;
    private int tag;

    /**
     * Constructor for DoubleCollisionStrategy.
     *
     * @param strategyFactory The factory for choosing additional collision strategies.
     * @param isFirstDouble   A boolean stating whether this is the first double or not.
     */
    public DoubleCollisionStrategy(StrategyFactory strategyFactory, boolean isFirstDouble) {
        this.setTag(DOUBLE_BEHAVIOR);
        this.isFirstDouble = isFirstDouble;
        this.strategies = new ArrayList<>();
        this.strategyFactory = strategyFactory;
        this.rollStrategies();
    }

    private void rollStrategies() {
        if (isFirstDouble) {
            int amountOfStrategies = 2;
            int curDuplicates = 1;
            int factoryLimiter = DOUBLE_BEHAVIOR_FACTORY_LIMIT;
            CollisionStrategy rolledStrategy;

            for (int i = 0; i < amountOfStrategies; i++) {
                rolledStrategy = strategyFactory.chooseStrategy(factoryLimiter, false);

                if (rolledStrategy.getTag() == DOUBLE_BEHAVIOR) {
                    amountOfStrategies += 2;
                    curDuplicates++;
                    if (DOUBLE_BEHAVIOR_LIMIT <= curDuplicates) {
                        factoryLimiter--;
                    }
                }
                this.strategies.add(rolledStrategy);
            }
        }
    }

    /**
     * Calls all the collision methods for the strategies the double behavior have.
     *
     * @param obj1 The first game object involved in the collision.
     * @param obj2 The second game object involved in the collision.
     */
    @Override
    public void onCollision(GameObject obj1, GameObject obj2) {
        for (CollisionStrategy strategy : this.strategies) {
            strategy.onCollision(obj1, obj2);
            System.out.println(strategy);
        }
    }

    /**
     * Gets the object's tag. The tag has no inherent meaning on its own
     * and is not used by the BasicCollisionStrategy class itself;
     * it can be assigned any meaning by the user of the class,
     * and is simply a convenience placeholder for custom info.
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Sets the object's tag. The tag has no inherent meaning on its own
     * and is not used by the BasicCollisionStrategy class itself;
     * it can be assigned any meaning by the user of the class,
     * and is simply a convenience placeholder for custom info.
     *
     * @param tag the tag of the strategy, can get a tag from the Constants class.
     */
    public void setTag(int tag) {
        this.tag = tag;
    }
}
