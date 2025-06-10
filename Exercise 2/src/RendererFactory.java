/**
 * Factory class responsible for creating instances of Renderer based on the provided type.
 */
public class RendererFactory {

    /**
     * Constructs a RendererFactory.
     */
    public RendererFactory() {
    }

    /**
     * Builds and returns an instance of Renderer based on the provided type and size.
     *
     * @param renderer The type of renderer to be created.
     * @param size     The size parameter used for creating certain types of renderers.
     * @return An instance of Renderer corresponding to the provided type,
     * or null if the type is not recognized.
     */
    public Renderer buildRenderer(String renderer, int size) {
        String rendererType = renderer.toLowerCase();
        switch (rendererType) {
            case "none":
                return new VoidRenderer();
            case "console":
                return new ConsoleRenderer(size);
            default:
                return null;
        }
    }
}
