package base;

import java.util.Optional;

import processing.core.PApplet;
import processing.core.PImage;

final class WorldView {
	private PApplet screen;
	private WorldModel world;
	private int tileWidth;
	private int tileHeight;
	private Viewport viewport;

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

	private static int clamp(int value, int low, int high) {
		return Math.min(high, Math.max(value, low));
	}

	private void drawBackground() {
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

	private void drawEntities() {
		for (Entity entity : world.getEntities()) {
			Point pos = entity.getPosition();

			if (this.viewport.contains(pos)) {
				Point viewPoint = worldToViewport(viewport, pos.getX(), pos.getY());
				screen.image(WorldModel.getCurrentImage(entity), viewPoint.getX() * tileWidth, viewPoint.getY() * tileHeight);
			}
		}
	}

	public void drawViewport() {
		drawBackground();
		drawEntities();
	}

	private static Point viewportToWorld(Viewport viewport, int col, int row) {
		return new Point(col + viewport.getCol(), row + viewport.getRow());
	}

	private static Point worldToViewport(Viewport viewport, int col, int row) {
		return new Point(col - viewport.getCol(), row - viewport.getRow());
	}

}
