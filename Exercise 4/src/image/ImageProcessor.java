package image;

import java.awt.*;
import java.io.IOException;
import java.util.LinkedList;

import exceptions.*;


/**
 * Represents an image processor for creating sub-images and managing image resolution.
 */
public class ImageProcessor {
    private static final double RED_WEIGHT = 0.2126;
    private static final double GREEN_WEIGHT = 0.7152;
    private static final double BLUE_WEIGHT = 0.0722;
    private static final double MAX_RGB = 255;
    private Image image;
    private static final int DEFAULT_RESOLUTION = 128;
    private int curRes = DEFAULT_RESOLUTION;
    private int totalImagesCounter = 0;
    private static final String IMAGE_ERROR = "Did not execute due to problem with image file.";

    /**
     * Initializes the AsciiArtAlgorithm with the provided image path.
     * Forced to initialize the ImageProcessor with an image already to avoid having exceptions such
     * as NullPointerException and more.
     *
     * @param imagePath The path to an image file.
     * @throws IOException If there's an issue with the image file.
     */
    public ImageProcessor(String imagePath) throws IOException {
        this.setImage(imagePath);
    }

    /**
     * Creates sub-images from the main image. Must insert an Image object to this object before using
     * or else a NullPointerException will be thrown. An Image can be set using the method setImage.
     *
     * @return A linked list containing the brightness values of the sub-images.
     */
    public LinkedList<Double> createSubImages() {
        LinkedList<Double> subImages = new LinkedList<>();

        int subImgSize = this.image.getWidth() / this.curRes;
        int rowsOfImg = this.image.getHeight() / subImgSize;
        for (int i = 0; i < rowsOfImg; i++) {
            for (int j = 0; j < this.curRes; j++) {
                this.insertSubImage(subImages, i, j, subImgSize);
            }
        }
        return subImages;
    }

    /**
     * Gets the current resolution of the image processor.
     *
     * @return The current resolution.
     */
    public int getResolution() {
        return this.curRes;
    }

    /**
     * Gets the number of sub-images that can be in a column, calculated by a specific formula.
     *
     * @return The number of sub-images that can be in a column.
     */
    public int getColSize() {
        int subImgSize = this.image.getWidth() / this.curRes;
        return this.image.getHeight() / subImgSize;
    }

    /**
     * Increases the resolution of the image processor.
     *
     * @throws OutOfBoundariesException If the new resolution exceeds the image width.
     */
    public void increaseRes() throws OutOfBoundariesException {
        this.curRes *= 2;
        if (this.curRes > this.image.getWidth()) {
            this.curRes /= 2;
            throw new OutOfBoundariesException();
        }
    }

    /**
     * Decreases the resolution of the image processor.
     *
     * @throws OutOfBoundariesException If the new resolution is less than the minimum allowed.
     */
    public void decreaseRes() throws OutOfBoundariesException {
        int minCharsInRow = Math.max(1, (this.image.getWidth()) / (this.image.getHeight()));
        if (this.curRes / 2 < minCharsInRow) {
            throw new OutOfBoundariesException();
        }
        this.curRes /= 2;

    }

    /**
     * Sets the image of the image processor.
     *
     * @param imagePath The path of the new image file.
     * @throws IOException If there's an issue with the new image file.
     */
    public void setImage(String imagePath) throws IOException {
        try{
            this.image = new Image(imagePath);
        }
        catch (IOException e){
            throw new IOException(IMAGE_ERROR);
        }
        this.totalImagesCounter++;
        this.padImage();
    }

    /**
     * Retrieves the total count of images processed so far by ImageProcessor. This can be useful for
     * checking whether we work on the same image or it has been changed.
     *
     * @return The total count of images processed.
     */
    public int getLastImageCounter() {
        return this.totalImagesCounter;
    }

    private void padImage() {
        int height = this.image.getHeight();
        int width = this.image.getWidth();
        int heightPad = (int) Math.ceil(Math.log(height) / Math.log(2));
        int widthPad = (int) Math.ceil(Math.log(width) / Math.log(2));
        int heightDelta = (int) Math.pow(2, heightPad) - height;
        int widthDelta = (int) Math.pow(2, widthPad) - width;
        Color[][] newImagePixels = new Color[heightDelta + height][widthDelta + width];
        for (int row = 0; row < heightDelta + height; row++) {
            for (int col = 0; col < widthDelta + width; col++) {
                // we add the pixel of the original picture if we are within the picture dimensions
                if ((heightDelta / 2 <= row) && (row < (heightDelta / 2 + height))) {
                    if ((widthDelta / 2 <= col) && (col < (widthDelta / 2 + width))) {
                        newImagePixels[row][col] = this.image.getPixel(row - heightDelta / 2,
                                col - widthDelta / 2);
                        continue;
                    }
                }
                newImagePixels[row][col] = Color.WHITE;
            }
        }
        this.image = new Image(newImagePixels, widthDelta + width, heightDelta + height);
    }

    private void insertSubImage(LinkedList<Double> subImages, int i, int j, int size) {
        double sumPixel = 0;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                Color curPixel = this.image.getPixel(row + size * i, col + size * j);
                sumPixel += (curPixel.getRed() * RED_WEIGHT) +
                        (curPixel.getGreen() * GREEN_WEIGHT) +
                        (curPixel.getBlue() * BLUE_WEIGHT);

            }
        }
        sumPixel = sumPixel / ((size * size) * MAX_RGB);
        subImages.add(sumPixel);
    }


}
