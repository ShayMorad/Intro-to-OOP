/**
 * Represents a bot player with a clever strategy in a Tic-Tac-Toe game.
 * The CleverPlayer attempts to make a move by iterating through the empty cells on the game board.
 */
public class CleverPlayer implements Player {
    /**
     * Constructs a CleverPlayer instance.
     */
    public CleverPlayer() {
    }

    /**
     * Allows the CleverPlayer to make a move on the game board.
     * The CleverPlayer iterates through the empty cells on the board and makes the first available move.
     *
     * @param board The game board on which the turn is played.
     * @param mark  The mark (X or O) representing the CleverPlayer making the move.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        for (int r = 0, s = board.getSize(); r < s; r++) {
            for (int c = 0; c < s; c++) {
                Mark checkBoardMark = board.getMark(r,c);
                if (checkBoardMark == Mark.BLANK){
                    board.putMark(mark, r, c);
                    return;
                }
            }
        }
    }
}
