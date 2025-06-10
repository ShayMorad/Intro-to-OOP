package ascii_art;

import exceptions.EmptyCharsetException;
import image.ImageProcessor;
import image_char_matching.SubImgCharMatcher;

import java.util.LinkedList;

/**
 * Represents an algorithm to convert an image into ASCII art.
 */
public class AsciiArtAlgorithm {
    private final SubImgCharMatcher subImgCharMatcher;
    private final ImageProcessor imageProcessor;
    private int lastResolution;
    private LinkedList<Double> currentSubImages;
    private int curImageCounter = 0;

    /**
     * Initializes the AsciiArtAlgorithm with the provided SubImgCharMatcher and ImageProcessor.
     *
     * @param subImgCharMatcher The SubImgCharMatcher used to match image brightness to characters.
     * @param imageProcessor    The ImageProcessor used to process the image.
     */
    public AsciiArtAlgorithm(SubImgCharMatcher subImgCharMatcher, ImageProcessor imageProcessor) {
        this.subImgCharMatcher = subImgCharMatcher;
        this.imageProcessor = imageProcessor;
        this.lastResolution = this.imageProcessor.getResolution();
        this.currentSubImages = this.imageProcessor.createSubImages();
    }

    /**
     * Runs the ASCII art conversion algorithm on the provided image.
     *
     * @return A 2D char array representing the ASCII art version of the image.
     * @throws EmptyCharsetException If the character set used for matching is empty.
     */
    public char[][] run() throws EmptyCharsetException {
        int cols = this.imageProcessor.getResolution();
        int rows = this.imageProcessor.getColSize(); // col size == amount of rows
        this.checkSubImages();
        return this.createAsciiArr(rows, cols);
    }

    private void checkSubImages() {
        // we check here if the last image and resolution are the same, if so, no need to create
        // sub-images again and can use the ones from the last run
        if (this.imageProcessor.getResolution() != this.lastResolution ||
                this.curImageCounter != this.imageProcessor.getLastImageCounter()) {
            this.lastResolution = this.imageProcessor.getResolution();
            this.curImageCounter = this.imageProcessor.getLastImageCounter();
            this.currentSubImages = this.imageProcessor.createSubImages();
        }
    }

    private char[][] createAsciiArr(int rows, int cols)
            throws EmptyCharsetException {

        char[][] asciiArt = new char[rows][cols];
        int i = 0;
        int j = 0;
        for (Double brightness : currentSubImages) {
            asciiArt[i][j] = this.subImgCharMatcher.getCharByImageBrightness(brightness);
            j++;
            if (j == cols) {
                j = 0;
                i++;
            }
        }
        return asciiArt;
    }

}




