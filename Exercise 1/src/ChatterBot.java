import java.util.*;

/**
 * Base file for the ChatterBot exercise.
 * The bot's replyTo method receives a statement.
 * If it starts with the constant REQUEST_PREFIX, the bot returns
 * whatever is after this prefix. Otherwise, it returns one of
 * a few possible replies as supplied to it via its constructor.
 * In this case, it may also include the statement after
 * the selected reply (coin toss).
 *
 * @author Dan Nirel
 */
class ChatterBot {
    /**
     * The prefix indicating a legal request.
     */
    public static final String REQUEST_PREFIX = "say ";
    /**
     * Placeholder for the requested phrase in legal requests.
     */
    public static final String PLACEHOLDER_FOR_REQUESTED_PHRASE = "<phrase>";
    /**
     * Placeholder for an illegal request.
     */
    public static final String PLACEHOLDER_FOR_ILLEGAL_REQUEST = "<request>";
    /**
     * Random number generator for selecting response patterns.
     */
    Random rand = new Random();
    /**
     * Array of replies to illegal requests.
     */
    String[] repliesToIllegalRequest;
    /**
     * Name of the ChatterBot.
     */
    String name;
    /**
     * Array of replies to legal requests.
     */
    String[] legalRequestsReplies;

    /**
     * Constructs a ChatterBot with the specified name and response arrays.
     *
     * @param name                   The name of the ChatterBot.
     * @param repliesToLegalRequest  Array of replies to legal requests.
     * @param repliesToIllegalRequest Array of replies to illegal requests.
     */
    ChatterBot(String name, String[] repliesToLegalRequest,
               String[] repliesToIllegalRequest) {
        this.name = name;

        // Copying replies to illegal requests
        this.repliesToIllegalRequest = new String[repliesToIllegalRequest.length];
        for (int i = 0; i < repliesToIllegalRequest.length; i = i + 1) {
            this.repliesToIllegalRequest[i] = repliesToIllegalRequest[i];
        }

        // Copying replies to legal requests
        this.legalRequestsReplies = new String[repliesToLegalRequest.length];
        for (int i = 0; i < repliesToLegalRequest.length; i++) {
            this.legalRequestsReplies[i] = repliesToLegalRequest[i];
        }
    }

    /**
     * Generates a reply based on the provided statement.
     *
     * @param statement The statement to which the ChatterBot responds.
     * @return The generated reply.
     */
    String replyTo(String statement) {
        if (statement.startsWith(REQUEST_PREFIX)) {
            return replyToLegalRequest(statement);
        }
        return replacePlaceholderInARandomPattern(this.repliesToIllegalRequest,
                PLACEHOLDER_FOR_ILLEGAL_REQUEST,
                statement);
    }

    /**
     * Generates a reply to a legal request.
     *
     * @param statement The legal request statement.
     * @return The generated reply.
     */
    String replyToLegalRequest(String statement){
        String phrase = statement.replaceFirst(REQUEST_PREFIX, "");
        return replacePlaceholderInARandomPattern(this.legalRequestsReplies,
                    PLACEHOLDER_FOR_REQUESTED_PHRASE,
                    phrase);
    }

    /**
     * Replaces a placeholder in a random response pattern.
     *
     * @param patterns     Array of response patterns.
     * @param placeholder  The placeholder to replace.
     * @param replacement  The replacement for the placeholder.
     * @return The response with the placeholder replaced.
     */
    String replacePlaceholderInARandomPattern(String[] patterns, String placeholder, String replacement){
        int randomIndex = rand.nextInt(patterns.length);
        String responsePattern = patterns[randomIndex];
        return responsePattern.replaceAll(placeholder, replacement);
    }

    /**
     * Retrieves the name of the ChatterBot.
     *
     * @return The name of the ChatterBot.
     */
    String getName() {
        return this.name;
    }
}
