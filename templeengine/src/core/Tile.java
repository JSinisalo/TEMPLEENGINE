package templeengine.src.core;

import javafx.scene.image.Image;

/**
 * The {@code Tile} is a {@link GameObject} used in a {@link TileMap}.
 *
 * Tile consists of an image and cant be collided with, essentially doing nothing.
 */
public class Tile extends GameObject {

    /**
     * Constructs a tile and sets it to noncollidable.
     *
     * @param sheet The tilesheet of the tile.
     */
     public Tile(Image sheet) {

        this.setImage(sheet);
        this.isCollidable = false;
    }
}
