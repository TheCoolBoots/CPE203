package project2_4_19;

import java.util.List;

import processing.core.PImage;

public class Blacksmith implements Entity {
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	// Unsure on below variables:
	private int resourceLimit;
	private int resourceCount;
	private int actionPeriod;
	private int animationPeriod;

	public Blacksmith(String id, Point position, List<PImage> images) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		// All zero for BlackSmith below:
		this.resourceLimit = 0;
		this.resourceCount = 0;
		this.actionPeriod = 0;
		this.animationPeriod = 0;
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

	@Override
	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nextImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAnimationPeriod() {
		// TODO Auto-generated method stub
		return 0;
	}

}
