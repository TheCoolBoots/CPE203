package base;

import java.util.List;

import processing.core.PImage;

public class Obstacle implements Entity {
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;

	public Obstacle(String id, Point position, List<PImage> images) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;

	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point point) {
		this.position = point;
	}

	public List<PImage> getImages() {
		return images;
	}

	public String getId() {
		return id;
	}

	public int getImageIndex() {
		return imageIndex;
	}


}
