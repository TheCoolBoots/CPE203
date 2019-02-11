package Fresh;

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

	private Point viewportToWorld(int col, int row) {
		return new Point(col + viewport.getCol(), row + viewport.getRow());
	}

	private Point worldToViewport(int col, int row) {
		return new Point(col - viewport.getCol(), row - viewport.getRow());
	}

	private static int clamp(int value, int low, int high) {
		return Math.min(high, Math.max(value, low));
	}

	public void shiftView(int colDelta, int rowDelta) {
		int newCol = clamp(viewport.getCol() + colDelta, 0, world.getNumCols() - viewport.getNumCols());
		int newRow = clamp(viewport.getRow() + rowDelta, 0, world.getNumRows() - viewport.getNumRows());

		this.shift(viewport, newCol, newRow);
	}

	private void drawBackground(WorldView view) {
		for (int row = 0; row < view.viewport.getNumRows(); row++) {
			for (int col = 0; col < view.viewport.getNumCols(); col++) {
				Point worldPoint = this.viewportToWorld(col, row);
				Optional<PImage> image = Background.getBackgroundImage(view.world, worldPoint);
				if (image.isPresent()) {
					view.screen.image(image.get(), col * view.tileWidth, row * view.tileHeight);
				}
			}
		}
	}

	private void drawEntities(WorldView view) {
		for (Entity entity : view.world.getEntities()) {
			Point pos = entity.getPosition();

			if (viewport.contains(pos)) {
				Point viewPoint = this.worldToViewport(pos.getX(), pos.getY());
				view.screen.image(Entity.getCurrentImage(entity), viewPoint.getX() * view.tileWidth,
						viewPoint.getY() * view.tileHeight);
			}
		}
	}

	public void drawViewport(WorldView view) {
		this.drawBackground(view);
		this.drawEntities(view);
	}

	private void shift(Viewport viewport, int col, int row) {
		viewport.setCol(col);
		viewport.setRow(row);
	}
}
