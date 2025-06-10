import java.util.Arrays;

/**
 * Represents a game board for a two-player game, such as tic-tac-toe.
 */
public class Board {
    private static final int DEFAULT_SIZE = 4;
    private final int size;
    private final Mark[][] board;

    /**
     * Constructs a default game board with the default size.
     */
    public Board() {
        this.size = DEFAULT_SIZE;
        board = new Mark[this.size][this.size];
        for (Mark[] row : board) {
            Arrays.fill(row, Mark.BLANK);
        }
    }

    /**
     * Constructs a game board with a specified size.
     *
     * @param size The size of the game board.
     */
    public Board(int size) {
        this.size = size;
        board = new Mark[this.size][this.size];
        for (Mark[] rows : board) {
            Arrays.fill(rows, Mark.BLANK);
        }
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getSize() {
        return this.size;
    }

    /**
     * Places a mark on the game board at the specified position.
     *
     * @param mark The mark to be placed (X or O).
     * @param row  The row index of the mark's position.
     * @param col  The column index of the mark's position.
     * @return True if the mark was successfully placed, false otherwise.
     */
    public boolean putMark(Mark mark, int row, int col) {
        if (row < 0 || col < 0 || row > (this.size - 1) || col > (this.size - 1)) {
            return false;
        }
        if (this.board[row][col] != Mark.BLANK) {
            return false;
        }
        this.board[row][col] = mark;
        return true;
    }

    /**
     * Gets the mark at the specified position on the game board.
     *
     * @param row The row index of the mark's position.
     * @param col The column index of the mark's position.
     * @return The mark at the specified position, or BLANK if out of bounds.
     */
    public Mark getMark(int row, int col) {
        if (row < 0 || col < 0 || row > (this.size - 1) || col > (this.size - 1)) {
            return Mark.BLANK;
        }
        return this.board[row][col];
    }

}
