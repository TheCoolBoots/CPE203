package project2_4_19;

import java.util.Optional;

import processing.core.PApplet;
import processing.core.PImage;

final class WorldView {
	public PApplet screen;
	public WorldModel world;
	public int tileWidth;
	public int tileHeight;
	public Viewport viewport;

	public WorldView(int numRows, int numCols, PApplet screen, WorldModel world, int tileWidth, int tileHeight) {
		this.screen = screen;
		this.world = world;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		this.viewport = new Viewport(numRows, numCols);
	}

	public void shiftView(int colDelta, int rowDelta) {
		int newCol = clamp(viewport.getCol() + colDelta, 0, world.getNumCols() - viewport.getNumCols());
		int newRow = clamp(viewport.getRow() + rowDelta, 0, world.getNumRows() - viewport.getNumRows());

		viewport.shift(newCol, newRow);
	}

	public static int clamp(int value, int low, int high) {
		return Math.min(high, Math.max(value, low));
	}

	public void drawBackground() {
		for (int row = 0; row < viewport.getNumRows(); row++) {
			for (int col = 0; col < viewport.getNumCols(); col++) {
				Point worldPoint = viewportToWorld(viewport, col, row);
				Optional<PImage> image = this.world.getBackgroundImage(world, worldPoint);
				if (image.isPresent()) {
					screen.image(image.get(), col * tileWidth, row * tileHeight);
				}
			}
		}
	}

	public void drawEntities() {
		for (Entity entity : world.getEntities()) {
			Point pos = entity.getPosition();

			if (this.viewport.contains(pos)) {
				Point viewPoint = worldToViewport(viewport, pos.x, pos.y);
				screen.image(WorldModel.getCurrentImage(entity), viewPoint.x * tileWidth, viewPoint.y * tileHeight);
			}
		}
	}

	public void drawViewport() {
		drawBackground();
		drawEntities();
	}

	public static Point viewportToWorld(Viewport viewport, int col, int row) {
		return new Point(col + viewport.getCol(), row + viewport.getRow());
	}

	public static Point worldToViewport(Viewport viewport, int col, int row) {
		return new Point(col - viewport.getCol(), row - viewport.getRow());
	}

}
