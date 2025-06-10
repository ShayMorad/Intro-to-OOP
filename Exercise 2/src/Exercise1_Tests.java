import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Exercise1_Tests {

    private double percentagedTournament(int rounds, int size, int streak, Player[] players) {
        int wins1 = 0;

        for (int i = 0; i < rounds; i++) {
            Mark res =  (new Game(i % 2 == 0 ?
                    players[0] : players[1], i % 2 == 0 ? players[1] : players[0], size, streak,
                    new VoidRenderer()).run());

            switch (res){
                case X:
                    if(i % 2 == 0){
                        wins1++;
                    }
                    break;
                case  O:
                    if(i % 2 != 0){
                        wins1++;
                    }
                    break;
            }
        }

        return (double) wins1 / rounds;
    }

    @Test
    public void test_genius_clever() {
        runTestWithDifferentRounds("genius", "clever");
    }

    @Test
    public void test_genius_whatever() {
        runTestWithDifferentRounds("genius", "whatever");
    }

    @Test
    public void test_clever_whatever() {
        runTestWithDifferentRounds("clever", "whatever");
    }

    private void runTestWithDifferentRounds(String playerOneType, String playerTwoType) {
        Player[] players = {
                new PlayerFactory().buildPlayer(playerOneType),
                new PlayerFactory().buildPlayer(playerTwoType)
        };

        int[] roundsArray = {10000, 5000, 1000, 500, 100};
        for (int rounds : roundsArray) {
            for (int i = 4; i < 10; i++) {
                for (int j = 3; j < 10; j++) {
                    if(!(percentagedTournament(rounds, i, j, players) >= 0.55)){
                        System.out.println(i + " " + j + playerOneType + playerTwoType);
                    }
                    Assertions.assertTrue(percentagedTournament(rounds, i, j, players) >= 0.55);

                }
            }
        }
    }
}
