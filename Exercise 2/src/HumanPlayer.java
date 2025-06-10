/**
 * Represents a human player in a Tic-Tac-Toe game.
 */
public class HumanPlayer implements Player {
    private static final int INPUT_BASE = 10;

    /**
     * Constructs a HumanPlayer instance.
     */
    public HumanPlayer() {
    }

    /**
     * Allows the human player to make a move on the game board.
     *
     * @param board The game board on which the turn is played.
     * @param mark  The mark (X or O) representing the player making the move.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int playerCoordinates = getUserInput(mark);
        int row = playerCoordinates / INPUT_BASE;
        int col = playerCoordinates % INPUT_BASE;
        boolean invalidInput = !checkInput(playerCoordinates, board);
        if (!invalidInput) {
            board.putMark(mark, row, col);
        }
        while (invalidInput) {
            playerCoordinates = KeyboardInput.readInt();
            row = playerCoordinates / INPUT_BASE;
            col = playerCoordinates % INPUT_BASE;
            invalidInput = !checkInput(playerCoordinates, board);
            if (!invalidInput) {
                board.putMark(mark, row, col);
            }
        }
    }

    private int getUserInput(Mark mark) {
        //int size = board.getSize() - 1;
        String markSymbol;
        if (mark == Mark.X) {
            markSymbol = "X";
        } else {
            markSymbol = "O";
        }
        System.out.println(Constants.playerRequestInputString(markSymbol));
        int playerCoordinates = KeyboardInput.readInt();
        return playerCoordinates;
    }

    private boolean checkInput(int playerCoordinates, Board board) {
        int row = playerCoordinates / INPUT_BASE;
        int col = playerCoordinates % INPUT_BASE;
        if (row < 0 || col < 0 || row > (board.getSize() - 1) || col > (board.getSize() - 1)) {
            System.out.println(Constants.INVALID_COORDINATE);
            return false;
        }
        if (board.getMark(row, col) != Mark.BLANK) {
            System.out.println(Constants.OCCUPIED_COORDINATE);
            return false;
        }
        return true;
    }
}
