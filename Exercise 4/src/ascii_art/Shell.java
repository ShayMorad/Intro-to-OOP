package ascii_art;

import ascii_output.AsciiOutput;
import ascii_output.ConsoleAsciiOutput;
import ascii_output.HtmlAsciiOutput;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;
import exceptions.*;

import java.io.IOException;

/**
 * Represents a command-line interface (CLI) for generating ASCII art from images.
 */
public class Shell {
    private static final String DEFAULT_PATH = "cat.jpeg";
    private static final char[] DEFAULT_CHARACTER_SET = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
    private static final String CHANGED_RESOLUTION = "Resolution set to %s.";
    private static final String RES_FORMAT_ERR = "change resolution";
    private static final String OUTPUT_ERR = "change output method";
    private static final String SPLIT_DELIMITER = " ";
    private static final String OUTPUT_PATH = "out.html";
    private static final String OUTPUT_FONT = "Courier New";
    private static final String EMPTY_STRING = "";
    private static final char DASH = '-';
    private static final String INPUT_PREFIX = ">>> ";
    private static final String EXIT_COMMAND = "exit";
    private static final String CHARS_COMMAND = "chars";
    private static final String ADD_COMMAND = "add";
    private static final String REMOVE_COMMAND = "remove";
    private static final String RES_COMMAND = "res";
    private static final String IMAGE_COMMAND = "image";
    private static final String OUTPUT_COMMAND = "output";
    private static final String ASCII_COMMAND = "asciiArt";
    private static final String INCREASE_RESOLUTION = "up";
    private static final String DECREASE_RESOLUTION = "down";
    private static final String HTML = "html";
    private static final String CONSOLE = "console";
    private static final String ADD_ALL_CHARACTERS = "all";
    private static final String ADD_SPACE_CHARACTER = "space";
    private static final int ADD_RANGE_OF_CHARACTERS = 3;
    private static final int ADD_SINGLE_CHARACTER = 1;
    private static final char STARTING_CHAR = 32;
    private static final char ENDING_CHAR = 126;
    private final AsciiArtAlgorithm asciiAlgo;
    private final SubImgCharMatcher imageCharMatcher;
    private final ImageProcessor imageProcessor;
    private AsciiOutput output;
    private boolean isHtmlOutput = false;


    /**
     * Initializes the Shell with default parameters.
     *
     * @throws IOException If there's an issue with the image file.
     */
    public Shell() throws IOException {
        this.imageCharMatcher = new SubImgCharMatcher(DEFAULT_CHARACTER_SET);
        this.output = new ConsoleAsciiOutput();
        this.imageProcessor = new ImageProcessor(DEFAULT_PATH);
        this.asciiAlgo = new AsciiArtAlgorithm(this.imageCharMatcher, this.imageProcessor);
    }

    /**
     * Runs the CLI for generating ASCII art.
     */
    public void run() {
        String userInput = EMPTY_STRING;
        while (!userInput.equals(EXIT_COMMAND)) {
            System.out.print(INPUT_PREFIX);
            userInput = KeyboardInput.readLine();
            try {
                this.checkInput(userInput);
            } catch (IOException ioException) {
                System.out.println(ioException.getMessage());
            }
        }
    }

    private void checkInput(String userInput) throws IOException {
        String[] tokens = userInput.split(SPLIT_DELIMITER);
        String command = tokens[0];
        switch (command) {
            case CHARS_COMMAND:
                this.charsCommand();
                break;
            case ADD_COMMAND:
                this.changeCharSetCommand(tokens, ADD_COMMAND);
                break;
            case REMOVE_COMMAND:
                this.changeCharSetCommand(tokens, REMOVE_COMMAND);
                break;
            case RES_COMMAND:
                this.resCommand(tokens);
                break;
            case IMAGE_COMMAND:
                this.imageCommand(tokens);
                break;
            case OUTPUT_COMMAND:
                this.outputCommand(tokens);
                break;
            case ASCII_COMMAND:
                this.asciiCommand();
                break;
            case EXIT_COMMAND:
                break;
            default:
                throw new IncorrectCommandException();
        }
    }

    private void charsCommand() {
        System.out.println(this.imageCharMatcher);
    }

    private void changeCharSetCommand(String[] tokens, String command) throws IOException {
        IncorrectFormatException error = command.equals(ADD_COMMAND) ?
                new IncorrectFormatException(ADD_COMMAND) : new IncorrectFormatException(REMOVE_COMMAND);
        // might not be necessary to check parameters count but still safer overall.
        if (tokens.length != 2) {
            throw error;
        }
        String stringToChange = tokens[1];
        if (stringToChange.length() == ADD_SINGLE_CHARACTER) {
            this.changeSingleCharacter(stringToChange.charAt(0), command);
        } else if (stringToChange.equals(ADD_ALL_CHARACTERS)) {
            this.changeAll(command);
        } else if (stringToChange.equals(ADD_SPACE_CHARACTER)) {
            this.changeSpace(command);
        } else if (stringToChange.length() == ADD_RANGE_OF_CHARACTERS) {
            this.changeRange(stringToChange, command);
        } else {
            throw error;
        }
    }

    private void resCommand(String[] tokens) throws IOException {
        if (tokens.length != 2 || (!tokens[1].equals(INCREASE_RESOLUTION) &&
                !tokens[1].equals(DECREASE_RESOLUTION))) {
            throw new IncorrectFormatException(RES_FORMAT_ERR);
        }
        if (tokens[1].equals(INCREASE_RESOLUTION)) {
            this.imageProcessor.increaseRes();
        } else {
            this.imageProcessor.decreaseRes();
        }
        System.out.println(String.format(CHANGED_RESOLUTION, this.imageProcessor.getResolution()));
    }

    private void imageCommand(String[] tokens) throws IOException {
        String imagePath = tokens[1];
        this.imageProcessor.setImage(imagePath);
    }

    private void outputCommand(String[] tokens) throws IOException {
        if (tokens.length != 2 || (!tokens[1].equals(HTML) && !tokens[1].equals(CONSOLE))) {
            throw new IncorrectFormatException(OUTPUT_ERR);
        }
        this.isHtmlOutput = tokens[1].equals(HTML);
    }

    private void asciiCommand() throws IOException {
        char[][] newImage = this.asciiAlgo.run();
        if (this.isHtmlOutput) {
            this.output = new HtmlAsciiOutput(OUTPUT_PATH, OUTPUT_FONT);
        } else {
            this.output = new ConsoleAsciiOutput();
        }
        this.output.out(newImage);
    }

    private void changeSingleCharacter(char c, String command) throws IOException {
        if (command.equals(ADD_COMMAND)) {
            this.imageCharMatcher.addChar(c);
        } else {
            this.imageCharMatcher.removeChar(c);
        }
    }

    private void changeAll(String command) throws IOException {
        for (char i = STARTING_CHAR; i <= ENDING_CHAR; i++) {
            if (command.equals(ADD_COMMAND)) {
                this.imageCharMatcher.addChar(i);
                continue;
            }
            this.imageCharMatcher.removeChar(i);
        }
    }

    private void changeSpace(String command) throws IOException {
        if (command.equals(ADD_COMMAND)) {
            this.imageCharMatcher.addChar(STARTING_CHAR);
        } else {
            this.imageCharMatcher.removeChar(STARTING_CHAR);
        }
    }

    private void changeRange(String rangeString, String command) throws IOException {

        char start = (char) Math.min(rangeString.charAt(0), rangeString.charAt(2));
        char end = (char) Math.max(rangeString.charAt(0), rangeString.charAt(2));

        // just added another check for if the range itself is valid because it wasn't mentioned in
        // the exercise description and unclear on the forums answers...
        // this might not be necessary but added just to make sure.
        boolean startLegality = start <= ENDING_CHAR && start >= STARTING_CHAR;
        boolean endLegality = end <= ENDING_CHAR && end >= STARTING_CHAR;
        if (!startLegality || !endLegality || rangeString.charAt(1) != DASH) {
            throw command.equals(ADD_COMMAND) ? new IncorrectFormatException(ADD_COMMAND) :
                    new IncorrectFormatException(REMOVE_COMMAND);
        }

        for (char i = start; i <= end; i++) {
            if (command.equals(ADD_COMMAND)) {
                this.imageCharMatcher.addChar(i);
                continue;
            }
            this.imageCharMatcher.removeChar(i);
        }
    }


    /**
     * The main entry point of the program.
     * It initializes a Shell object and executes its run method, handling any IOExceptions that may
     * occur during initialization.
     *
     * @param args Command-line arguments passed to the program (not used in this context).
     */
    public static void main(String[] args) {
        Shell shell;
        // just a simple try-catch for the case of having a bad default path for the image...
        // not necessary by the exercise description but safer overall.
        try {
            shell = new Shell();
        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            return;
        }
        shell.run();
    }
}





