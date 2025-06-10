import static java.lang.String.format;

/**
 * Represents a tournament of Tic-Tac-Toe games between two players.
 * Manages the tournament flow, players, and rendering.
 */
public class Tournament {
    private static final String RESULTS = "######### Results #########\n" +
            "Player 1, %s won: %d rounds\n" +
            "Player 2, %s won: %d rounds\n" +
            "Ties: %d";

    private final int rounds;
    private final Renderer renderer;
    private final Player player1;
    private final Player player2;

    /**
     * Constructs a Tournament with specified parameters.
     *
     * @param rounds   The number of rounds in the tournament.
     * @param renderer The renderer to display the game board.
     * @param player1  The first player participating in the tournament.
     * @param player2  The second player participating in the tournament.
     */
    public Tournament(int rounds, Renderer renderer, Player player1, Player player2) {
        this.rounds = rounds;
        this.renderer = renderer;
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * Plays the tournament, running Tic-Tac-Toe games for the specified number of rounds.
     *
     * @param size        The size of the Tic-Tac-Toe game board.
     * @param winStreak   The length of the streak required for victory.
     * @param playerName1 The name of the first player.
     * @param playerName2 The name of the second player.
     */
    public void playTournament(int size, int winStreak, String playerName1, String playerName2) {
        int player1RoundsWon = 0;
        int player2RoundsWon = 0;
        for (int match = 0; match < rounds; match++) {
            if(match%2 == 0){
                Game game = new Game(player1, player2, size, winStreak, renderer);
                Mark winner = game.run();
                if(winner == Mark.X){
                    player1RoundsWon+=1;
                }
                else if (winner == Mark.O){
                    player2RoundsWon+=1;
                }
            }
            else{
                Game game = new Game(player2, player1, size, winStreak, renderer);
                Mark winner = game.run();
                if(winner == Mark.X){
                    player2RoundsWon+=1;
                }
                else if (winner == Mark.O){
                    player1RoundsWon+=1;
                }
            }
        }
        System.out.println(format(RESULTS, playerName1.toLowerCase(),
                player1RoundsWon, playerName2.toLowerCase(),
                player2RoundsWon, rounds - (player1RoundsWon + player2RoundsWon)));
    }

    /**
     * Main method to run the tournament based on command line arguments.
     *
     * @param args Command line arguments:
     *             [rounds, size, winStreak, rendererType, player1Type, player2Type].
     */
    public static void main(String[] args){
        int rounds = Integer.parseInt(args[0]);
        int size = Integer.parseInt(args[1]);
        int winStreak = Integer.parseInt(args[2]);
        RendererFactory rendererFactory = new RendererFactory();
        Renderer renderer = rendererFactory.buildRenderer(args[3], size);
        if(renderer == null) {
            System.out.println(Constants.UNKNOWN_RENDERER_NAME);
            return;
        }
        PlayerFactory playerFactory = new PlayerFactory();
        Player player1 = playerFactory.buildPlayer(args[4]);
        Player player2 = playerFactory.buildPlayer(args[5]);
        if(player1 == null || player2 == null) {
            System.out.println(Constants.UNKNOWN_PLAYER_NAME);
            return;
        }
        Tournament tournament = new Tournament(rounds, renderer, player1, player2);
        tournament.playTournament(size, winStreak, args[4], args[5]);
    }
}

