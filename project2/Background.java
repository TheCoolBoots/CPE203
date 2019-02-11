package Fresh;

import java.util.List;
import java.util.Optional;

import processing.core.PImage;

final class Background {
	
	private List<PImage> images;
	private int imageIndex;

	public Background(String id, List<PImage> images) {
		this.setImages(images);
	}

	private static Background getBackgroundCell(WorldModel world, Point pos) {
		return world.getBackground()[pos.getY()][pos.getX()];
	}

	private static void setBackgroundCell(WorldModel world, Point pos, Background background) {
		world.getBackground()[pos.getY()][pos.getX()] = background;
	}

	public static Optional<PImage> getBackgroundImage(WorldModel world, Point pos) {
		if (withinBounds(world, pos)) {
			return Optional.of(Entity.getCurrentImage(getBackgroundCell(world, pos)));
		} else {
			return Optional.empty();
		}
	}

	public static void setBackground(WorldModel world, Point pos, Background background) {
		if (withinBounds(world, pos)) {
			setBackgroundCell(world, pos, background);
		}
	}

	public static boolean withinBounds(WorldModel world, Point pos) {
		return pos.getY() >= 0 && pos.getY() < world.getNumRows() && pos.getX() >= 0 && pos.getX() < world.getNumCols();
	}

	public static boolean isOccupied(WorldModel world, Point pos) {
		return withinBounds(world, pos) && getOccupancyCell(world, pos) != null;
	}

	public static Entity getOccupancyCell(WorldModel world, Point pos) {
		return world.getOccupancy()[pos.getY()][pos.getX()];
	}

	public List<PImage> getImages() {
		return images;
	}

	public void setImages(List<PImage> images) {
		this.images = images;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

}
