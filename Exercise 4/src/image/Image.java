package image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A package-private class of the package image.
 * @author Dan Nirel
 */
public class Image {

    private final Color[][] pixelArray;
    private final int width;
    private final int height;

    /**
     * Constructs an Image object from the specified image file.
     * @param filename A String representing the path to the image file.
     * @throws IOException If an error occurs while reading the image file.
     */
    public Image(String filename) throws IOException {
        BufferedImage im = ImageIO.read(new File(filename));
        width = im.getWidth();
        height = im.getHeight();


        pixelArray = new Color[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                pixelArray[i][j]=new Color(im.getRGB(j, i));
            }
        }
    }

    /**
     * Constructs an Image object from a pixel array, width, and height.
     * @param pixelArray A 2D array of Color representing the pixels of the image.
     * @param width An int representing the width of the image.
     * @param height An int representing the height of the image.
     */
    public Image(Color[][] pixelArray, int width, int height) {
        this.pixelArray = pixelArray;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the width of the image.
     * @return An int representing the width of the image.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the height of the image.
     * @return An int representing the height of the image.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the Color of the pixel at the specified coordinates (x, y).
     * @param x An int representing the x-coordinate of the pixel.
     * @param y An int representing the y-coordinate of the pixel.
     * @return A Color object representing the color of the pixel at the specified coordinates.
     */
    public Color getPixel(int x, int y) {
        return pixelArray[x][y];
    }

    /**
     * Saves the image to a file with the specified file name.
     * @param fileName A String representing the name of the file to save the image to.
     * @throws RuntimeException If an error occurs while writing the image file.
     */
    public void saveImage(String fileName){
        // Initialize BufferedImage, assuming Color[][] is already properly populated.
        BufferedImage bufferedImage = new BufferedImage(pixelArray[0].length, pixelArray.length,
                BufferedImage.TYPE_INT_RGB);
        // Set each pixel of the BufferedImage to the color from the Color[][].
        for (int x = 0; x < pixelArray.length; x++) {
            for (int y = 0; y < pixelArray[x].length; y++) {
                bufferedImage.setRGB(y, x, pixelArray[x][y].getRGB());
            }
        }
        File outputfile = new File(fileName+".jpeg");
        try {
            ImageIO.write(bufferedImage, "jpeg", outputfile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
