package image_char_matching;

import exceptions.EmptyCharsetException;
import exceptions.IncorrectFormatException;



import java.util.*;

import static image_char_matching.CharConverter.DEFAULT_PIXEL_RESOLUTION;


/**
 * This class matches ASCII characters to sub-images based on brightness values.
 */
public class SubImgCharMatcher {
    private static final String ADD_ERR = "add";
    private static final String REMOVE_ERR = "remove";
    private TreeMap<Double, TreeSet<Character>> normalizedBrightnessMap;
    private double minInitialBrightness;
    private double maxInitialBrightness;
    private final TreeMap<Character, AbstractMap.SimpleEntry<Double, Boolean>> initialBrightnessMap;
    private static final char MIN_ASCII = 32;
    private static final char MAX_ASCII = 126;


    /**
     * Constructs a SubImgCharMatcher object with the specified character set.
     *
     * @param charset The character set to initialize the matcher with.
     */
    public SubImgCharMatcher(char[] charset) {
        this.normalizedBrightnessMap = new TreeMap<>();
        this.initialBrightnessMap = new TreeMap<>();
        this.maxInitialBrightness = 0;
        this.minInitialBrightness = 1;
        this.createInitialBrightness(charset);
        this.createNormalMap();
    }

    /**
     * Retrieves the ASCII character corresponding to the given brightness value.
     *
     * @param brightness The brightness value of the sub-image.
     * @return The ASCII character matched to the brightness.
     * @throws EmptyCharsetException If the character set is empty.
     */
    public char getCharByImageBrightness(double brightness) throws EmptyCharsetException {
        if (this.normalizedBrightnessMap.isEmpty()) {
            throw new EmptyCharsetException();
        }
        double lowerBrightness = this.normalizedBrightnessMap.floorEntry(brightness).getKey();

        double upperBrightness = this.normalizedBrightnessMap.ceilingEntry(brightness).getKey();

        if (lowerBrightness != upperBrightness) {
            double lowerDelta = Math.abs(lowerBrightness - brightness);
            double upperDelta = Math.abs(upperBrightness - brightness);
            if (lowerDelta < upperDelta) {
                return this.normalizedBrightnessMap.get(lowerBrightness).first();
            }
            return this.normalizedBrightnessMap.get(upperBrightness).first();
        } else {
            return this.normalizedBrightnessMap.get(lowerBrightness).first();
        }
    }

    /**
     * Adds a character to the character set.
     *
     * @param c The character to add.
     * @throws IncorrectFormatException If the character is invalid.
     */
    public void addChar(char c) throws IncorrectFormatException {
        if (!isLegalCharacter(c)) {
            throw new IncorrectFormatException(ADD_ERR);
        }
        if (!this.initialBrightnessMap.containsKey(c)) {
            this.calcInitialBrightness(c);
        }

        if (!this.initialBrightnessMap.get(c).getValue()) {
            AbstractMap.SimpleEntry<Double, Boolean> characterPair = this.initialBrightnessMap.get(c);
            this.initialBrightnessMap.put(c, new AbstractMap.SimpleEntry<>(characterPair.getKey(), true));
        }

        double initialBrightness = this.initialBrightnessMap.get(c).getKey();

        if ((initialBrightness > this.maxInitialBrightness) ||
                (initialBrightness < this.minInitialBrightness)) {
            this.maxInitialBrightness = Math.max(this.maxInitialBrightness, initialBrightness);
            this.minInitialBrightness = Math.min(this.minInitialBrightness, initialBrightness);
            this.createNormalMap();
            return;
        }
        this.addNormalValue(c);
    }

    /**
     * Removes a character from the character set.
     *
     * @param c The character to remove.
     * @throws IncorrectFormatException If the character is invalid.
     */
    public void removeChar(char c) throws IncorrectFormatException {
        if (!isLegalCharacter(c)) {
            throw new IncorrectFormatException(REMOVE_ERR);
        }

        if (!this.initialBrightnessMap.containsKey(c) || !this.initialBrightnessMap.get(c).getValue()) {
            return;
        }

        double cNormalValue = this.calcNormalValue(this.initialBrightnessMap.get(c).getKey());

        if (this.normalizedBrightnessMap.get(cNormalValue).size() == 1) {
            this.normalizedBrightnessMap.remove(cNormalValue);

        } else if (this.normalizedBrightnessMap.get(cNormalValue).size() > 1) {
            this.normalizedBrightnessMap.get(cNormalValue).remove(c);
        }

        AbstractMap.SimpleEntry<Double, Boolean> characterPair = this.initialBrightnessMap.get(c);
        this.initialBrightnessMap.put(c, new AbstractMap.SimpleEntry<>(characterPair.getKey(), false));

        double initialBrightness = this.initialBrightnessMap.get(c).getKey();

        if (initialBrightness != this.maxInitialBrightness &&
                initialBrightness != this.minInitialBrightness) {
            return;
        }
        this.maxInitialBrightness = 0;
        this.minInitialBrightness = 1;

        this.updateMinMax();
        this.createNormalMap();
    }

    /**
     * Returns a string representation of the characters in the character set.
     *
     * @return A string containing all the characters in the character set.
     */
    @Override
    public String toString() {
        String chars = new String();
        for (var c : this.initialBrightnessMap.keySet()) {
            if (this.initialBrightnessMap.get(c).getValue()) {
                chars += c + Character.toString(MIN_ASCII);
            }
        }
        return chars;
    }

    private void createInitialBrightness(char[] charSet) {
        for (char ch : charSet) {
            double curBrightness = this.calcInitialBrightness(ch);
            this.maxInitialBrightness = Math.max(curBrightness, this.maxInitialBrightness);
            this.minInitialBrightness = Math.min(curBrightness, this.minInitialBrightness);
        }

    }

    private double calcInitialBrightness(char ch) {
        boolean[][] charBoolVal = CharConverter.convertToBoolArray(ch);
        int counter = 0;
        for (boolean[] row : charBoolVal) {
            for (boolean value : row) {
                if (value) {
                    counter++;

                }
            }
        }
        double denominator = DEFAULT_PIXEL_RESOLUTION * DEFAULT_PIXEL_RESOLUTION;
        double initialBrightness = counter / (denominator);
        this.initialBrightnessMap.put(ch, new AbstractMap.SimpleEntry<>(initialBrightness, true));
        return initialBrightness;
    }

    private void createNormalMap() {
        this.normalizedBrightnessMap = new TreeMap<>();
        for (char entry : this.initialBrightnessMap.keySet()) {
            // if this character is 'false' in the map which means it is 'removed' from the working set.
            if (!this.initialBrightnessMap.get(entry).getValue()) {
                continue;
            }
            this.addNormalValue(entry);
        }
    }

    private double calcNormalValue(double curVal) {
        return (curVal - minInitialBrightness) / (maxInitialBrightness - minInitialBrightness);
    }

    private void addNormalValue(char entry) {
        // get the current character's initial brightness value
        double curBrightnessValue = this.initialBrightnessMap.get(entry).getKey();
        double curNormalizedValue = calcNormalValue(curBrightnessValue);
        // if the normalized value is not in the treemap, we create a new key,set for it
        if (this.normalizedBrightnessMap.get(curNormalizedValue) == null) {
            this.normalizedBrightnessMap.put(curNormalizedValue, new TreeSet<>());
        }
        // else we just add the character that has the same normalized brightness to the treemap
        this.normalizedBrightnessMap.get(curNormalizedValue).add(entry);
    }

    private void updateMinMax() {
        for (char entry : this.initialBrightnessMap.keySet()) {
            if (this.initialBrightnessMap.get(entry).getValue()) {
                this.maxInitialBrightness = Math.max(this.maxInitialBrightness,
                        this.initialBrightnessMap.get(entry).getKey());
                this.minInitialBrightness = Math.min(this.minInitialBrightness,
                        this.initialBrightnessMap.get(entry).getKey());
            }
        }
    }

    private boolean isLegalCharacter(char c) {
        return (c <= MAX_ASCII && c >= MIN_ASCII);
    }

}
