/**
 * Represents a bot player with a genius-level strategy in a Tic-Tac-Toe game.
 * The GeniusPlayer analyzes the game board to identify streaks and make strategic moves.
 */
public class GeniusPlayer implements Player {
    private static final int HORIZONTAL = 0;
    private static final int VERTICAL = 1;

    /**
     * Constructs a GeniusPlayer instance.
     */
    public GeniusPlayer() {
    }

    /**
     * Allows the GeniusPlayer to make a move on the game board.
     *
     * @param board The game board on which the turn is played.
     * @param mark  The mark (X or O) representing the GeniusPlayer making the move.
     */
    @Override
    public void playTurn(Board board, Mark mark) {
        int[] maxHorizontalStreak = this.getMaxHorizontalStreak(board, mark);
        int[] maxVerticalStreak = this.getMaxVerticalStreak(board, mark);
        int maxStreakType = (maxHorizontalStreak[0] > maxVerticalStreak[0]) ? HORIZONTAL : VERTICAL;
        int[] maxStreakInformation = getStreakInfo(maxHorizontalStreak, maxVerticalStreak, maxStreakType);
        int[] positions;
        positions = getValidPositions(board, maxStreakType, maxStreakInformation);
        if (positions == null) {
            int otherStreakType = (maxStreakType == HORIZONTAL) ? VERTICAL : HORIZONTAL;
            positions = getValidPositions(board, otherStreakType, maxStreakInformation);
        }
        if (positions == null) {
            positions = resetPositions(board);
        }
        if (positions[0] == 1 && positions[1] == 0) {
            if (board.getMark(0, 1) == Mark.BLANK) {
                positions[0] = 0;
                positions[1] = 1;
            }
        }
        if (positions == null) {
            positions = resetPositions(board);
        }
        board.putMark(mark, positions[0], positions[1]);
    }

    private int[] getStreakInfo(int[] maxHorizontalStreak, int[] maxVerticalStreak, int maxStreakType) {
        int[] maxStreakInformation;
        if (maxStreakType == HORIZONTAL) {
            maxStreakInformation = maxHorizontalStreak;
        } else {
            maxStreakInformation = maxVerticalStreak;
        }
        return maxStreakInformation;
    }

    private int[] resetPositions(Board board) {
        int[] positions = new int[]{0, 0};
        for (int row = 0; row < board.getSize(); row++) {
            for (int col = 0; col < board.getSize(); col++) {
                if (board.getMark(row, col) == Mark.BLANK) {
                    positions[0] = row;
                    positions[1] = col;
                    break;
                }
            }
        }
        return positions;
    }

    private int[] getMaxHorizontalStreak(Board board, Mark mark) {
        int size = board.getSize();
        int maxHorizontalStreak = 0;
        int streakRow = 0;
        int streakCol = 0;
        for (int r = 0; r < size; r++) {
            int tempMaxHorz = 0;
            for (int c = 0; c < size; c++) {
                if (board.getMark(r, c) == mark) {
                    tempMaxHorz += 1;
                }
                if (maxHorizontalStreak < tempMaxHorz) {
                    maxHorizontalStreak = tempMaxHorz;
                    streakRow = r;
                    streakCol = c;
                }
                if (board.getMark(r, c) != mark) {
                    tempMaxHorz = 0;
                }
            }
        }
        return new int[]{maxHorizontalStreak, streakRow, streakCol};
    }

    private int[] getMaxVerticalStreak(Board board, Mark mark) {
        int size = board.getSize();
        int maxVerticalStreak = 0;
        int streakRow = 0;
        int streakCol = 0;
        for (int c = 0; c < size; c++) {
            int tempMaxVert = 0;
            for (int r = 0; r < size; r++) {
                if (board.getMark(r, c) == mark) {
                    tempMaxVert += 1;
                }
                if (maxVerticalStreak < tempMaxVert) {
                    maxVerticalStreak = tempMaxVert;
                    streakCol = c;
                    streakRow = r;
                }
                if (board.getMark(r, c) != mark) {
                    tempMaxVert = 0;
                }
            }
        }
        return new int[]{maxVerticalStreak, streakRow, streakCol};
    }

    private int[] getValidPositions(Board board, int streakDirection, int[] streakInformation) {
        int size = board.getSize();
        int row = streakInformation[1];
        int col = streakInformation[2];
        if (streakDirection == HORIZONTAL) {
            // Check right
            if (col + 1 < size && board.getMark(row, col + 1) == Mark.BLANK) {
                return new int[]{row, col + 1};
            }
            // Check left
            else if (col - streakInformation[0] >= 0 &&
                    board.getMark(row, col - streakInformation[0]) == Mark.BLANK) {
                return new int[]{row, col - streakInformation[0]};
            }
        } else if (streakDirection == VERTICAL) {
            // Check down
            if (row + 1 < size && board.getMark(row + 1, col) == Mark.BLANK) {
                return new int[]{row + 1, col};
            }
            // Check up
            else if (row - streakInformation[0] >= 0 &&
                    board.getMark(row - streakInformation[0], col) == Mark.BLANK) {
                return new int[]{row - streakInformation[0], col};
            }
        }
        return null;
    }
}
