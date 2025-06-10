/**
 * Represents a Tic-Tac-Toe game with customizable settings such as board size and win streak length.
 * Manages the game flow, player turns, and win/tie conditions.
 */
public class Game {
    private final Board board;
    private final int winStreak;
    private final Player playerX;
    private final Player playerO;
    private final Renderer renderer;
    private Player curPlayer;

    private final static int DEFAULT_STREAK = 3;

    /**
     * Constructs a Game instance with default win streak length.
     *
     * @param playerX  The player representing 'X'.
     * @param playerO  The player representing 'O'.
     * @param renderer The renderer to display the game board.
     */
    public Game(Player playerX, Player playerO, Renderer renderer) {
        this.board = new Board();
        this.winStreak = DEFAULT_STREAK;
        this.playerO = playerO;
        this.playerX = playerX;
        this.renderer = renderer;
        this.curPlayer = playerX;
    }

    /**
     * Constructs a Game instance with customizable board size and win streak length.
     *
     * @param playerX   The player representing 'X'.
     * @param playerO   The player representing 'O'.
     * @param size      The size of the game board.
     * @param winStreak The length of the streak required for victory.
     * @param renderer  The renderer to display the game board.
     */
    public Game(Player playerX, Player playerO, int size, int winStreak, Renderer renderer) {
        this.board = new Board(size);
        this.winStreak = (winStreak < 2 || winStreak > size) ? size : winStreak;
        this.playerO = playerO;
        this.playerX = playerX;
        this.renderer = renderer;
        this.curPlayer = playerX;
    }

    /**
     * Gets the win streak length required for victory.
     *
     * @return The win streak length.
     */
    public int getWinStreak() {
        return this.winStreak;
    }

    /**
     * Gets the size of the game board.
     *
     * @return The size of the game board.
     */
    public int getBoardSize() {
        return this.board.getSize();
    }

    /**
     * Runs the Tic-Tac-Toe game until a winner is determined or a tie occurs.
     *
     * @return The mark of the winning player or BLANK if it's a tie.
     */
    public Mark run() {
        while (!checkForTie()){
            Mark mark;
            if(curPlayer == playerX){
                mark = Mark.X;
            }
            else{
                mark = Mark.O;
            }
            curPlayer.playTurn(board, mark);
            if(curPlayer == playerX){
                curPlayer = playerO;
            }
            else{
                curPlayer = playerX;
            }
            renderer.renderBoard(board);
            for(int row = 0; row < board.getSize(); row++) {
                for(int col = 0; col < board.getSize(); col++) {
                    Mark checkMark = board.getMark(row,col);
                    if(checkMark != Mark.BLANK && (checkRowsWin(mark,row, col) || checkColsWin(mark,row,
                            col) ||
                            checkDiagonalLeftWin(mark,row, col) || checkDiagonalRightWin(mark,row, col))) {
                        return mark;
                    }
                }
            }
        }
        return Mark.BLANK;
    }

    private boolean checkForTie() {
        for (int row = 0; row < this.board.getSize(); row++) {
            for (int col = 0; col < this.board.getSize(); col++) {
                if (this.board.getMark(row, col) == Mark.BLANK) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkColsWin(Mark mark, int row, int col) {
        for (int streak = 0; streak < this.winStreak; streak++) {
            if (row + streak > this.board.getSize() - 1 || this.board.getMark(row + streak, col) != mark) {
                return false;
            }
        }
        return true;
    }

    private boolean checkRowsWin(Mark mark, int row, int col) {
        for (int streak = 0; streak < this.winStreak; streak++) {
            if (col + streak > this.board.getSize() - 1 || this.board.getMark(row, col + streak) != mark) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalRightWin(Mark mark, int row, int col) {
        for (int streak = 0; streak < this.winStreak; streak++) {
            if (row + streak > this.board.getSize() - 1 || col + streak > this.board.getSize() - 1 ||
                    this.board.getMark(row + streak, col + streak) != mark) {
                return false;
            }
        }
        return true;
    }

    private boolean checkDiagonalLeftWin(Mark mark, int row, int col) {
        for (int streak = 0; streak < this.winStreak; streak++) {
            if (col - streak < 0 || row + streak < 0 ||
                    this.board.getMark(row + streak, col - streak) != mark) {
                return false;
            }
        }
        return true;
    }


}
