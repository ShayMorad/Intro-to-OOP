import java.util.Random;

/**
 * Represents a bot player that makes random moves in a Tic-Tac-Toe game.
 */
public class WhateverPlayer implements Player {
    private Random rand;

    /**
     * Constructs a WhateverPlayer instance.
     */
    public WhateverPlayer() {
        this.rand = new Random();
    }

    /**
     * Allows the WhateverPlayer to make a move on the game board.
     *
     * @param board The game board on which the turn is played.
     * @param mark  The mark (X or O) representing the WhateverPlayer making the move.
     */
    @Override
    public void playTurn(Board board, Mark mark) {

        // Generate random indices for row and column
        int randomIndex = rand.nextInt(board.getSize());
        int inputRow = randomIndex;
        randomIndex = rand.nextInt(board.getSize());
        int inputCol = randomIndex;

        // Check for invalid input and handle it
        Mark checkBoardMark = board.getMark(inputRow,inputCol);
        if (checkBoardMark == Mark.BLANK){
            board.putMark(mark, inputRow, inputCol);
            return;
        }
        while (checkBoardMark != Mark.BLANK) {
            randomIndex = rand.nextInt(board.getSize());
            inputRow = randomIndex;
            randomIndex = rand.nextInt(board.getSize());
            inputCol = randomIndex;
            checkBoardMark = board.getMark(inputRow,inputCol);
        }
        board.putMark(mark, inputRow, inputCol);
    }
}
