/**
 * Represents a player in a game.
 */
public interface Player{
    /**
     * Plays a turn on the provided game board for the specified mark.
     *
     * @param board The game board on which the turn is played.
     * @param mark  The mark (X or O) representing the player making the move.
     */
    void playTurn(Board board, Mark mark);
}