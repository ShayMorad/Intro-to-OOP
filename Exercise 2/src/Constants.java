/**
 * Constants class containing string constants used in a tic-tac-toe game.
 */
public class Constants {
    /**
     * Message indicating that the player name is unknown or not chosen.
     */
    public final static String UNKNOWN_PLAYER_NAME =
            "Choose a player, and start again.\nThe players: [human, " +
            "clever, whatever, genius]";
    /**
     * Message indicating that the renderer name is unknown or not chosen.
     */
    public final static String UNKNOWN_RENDERER_NAME =
            "Choose a renderer, and start again. \nPlease choose " +
            "one of the following [console, none]";
    /**
     * Message indicating that the mark position is invalid.
     */
    public final static String INVALID_COORDINATE =
            "Invalid mark position, please choose a different position.\n" +
            "Invalid coordinates, type again: ";
    /**
     * Message indicating that the mark position is already occupied.
     */
    public final static String OCCUPIED_COORDINATE = "Mark position is already occupied.\n" +
            "Invalid coordinates, type again: ";

    /**
     * Use this method to generate the text that HumanPlayer should send
     *
     * @param markSymbol according to the Mark the player uses in the current turn.
     * @return String to be printed to the player.
     */
    public static String playerRequestInputString(String markSymbol) {
        return "Player " + markSymbol + ", type coordinates: ";

    }
}
