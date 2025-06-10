/**
 * Factory class responsible for creating instances of Player based on the provided type.
 */
public class PlayerFactory {

    /**
     * Constructs a PlayerFactory.
     */
    public PlayerFactory() {
    }

    /**
     * Builds and returns an instance of Player based on the provided type.
     *
     * @param type The type of player to be created.
     * @return An instance of Player corresponding to the provided type,
     * or null if the type is not recognized.
     */
    public Player buildPlayer(String type) {
        String playerType = type.toLowerCase();
        switch (playerType) {
            case "whatever":
                return new WhateverPlayer();
            case "clever":
                return new CleverPlayer();
            case "genius":
                return new GeniusPlayer();
            case "human":
                return new HumanPlayer();
            default:
                return null;
        }
    }
}
