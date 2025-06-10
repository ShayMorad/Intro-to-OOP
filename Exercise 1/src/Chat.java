import java.util.Scanner;

/**
 * Represents a simple chat program where ChatterBots engage in a conversation.
 */
class Chat {
    /**
     * Name of the first ChatterBot.
     */
    static final String BOT1_NAME = "Bell";
    /**
     * Name of the second ChatterBot.
     */
    static final String BOT2_NAME = "Taric";

    /**
     * The main method that initializes and runs the chat program.
     *
     * @param args Command line arguments (not used in this program).
     */
    public static void main(String[] args) {

        //Creation of 2 ChatterBots in an array

        String[] illegalRequestsForBot1 = {
                "You gave bad input so I cant say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "WHAT IS " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
        };
        String[] illegalRequestsForBot2 = {
                "NOT SAYING " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                "I don't want to say " + ChatterBot.PLACEHOLDER_FOR_ILLEGAL_REQUEST,
        };
        String[] repliesToLegalRequestForBot1 = {
                "okay, here goes: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "this is my favorite thing to say. there: " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE,
                "you want me to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "? hihi i just said it"
        };
        String[] repliesToLegalRequestForBot2 = {
                "saying " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE + "is easy",
                "i don't want to say " + ChatterBot.PLACEHOLDER_FOR_REQUESTED_PHRASE +
                        ". oops! i said it anyway",
        };

        ChatterBot[] bots = {
                new ChatterBot(
                        BOT1_NAME,
                        repliesToLegalRequestForBot1,
                        illegalRequestsForBot1),
                new ChatterBot(
                        BOT2_NAME,
                        repliesToLegalRequestForBot2,
                        illegalRequestsForBot2)
        };

        //Creating a scanner and getting an initial statement from the user
        Scanner scanner = new Scanner(System.in);
        String statement = scanner.nextLine();

        //Iterating a conversation between the bots
        while (true) {
            for (ChatterBot bot : bots) {
                statement = bot.replyTo(statement);
                System.out.print(bot.getName() + ": " + statement);
                scanner.nextLine();
            }
        }
    }
}
