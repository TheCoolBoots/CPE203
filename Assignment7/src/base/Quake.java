package base;

import java.util.List;
import processing.core.PImage;

import java.util.Optional;

public class Quake implements AnimatableEntity, ExecutableEntity{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;
	private int animationPeriod;

	public static final String QUAKE_ID = "quake";
	public static final String QUAKE_KEY = "quake";
	public static final int QUAKE_ACTION_PERIOD = 1100;
	public static final int QUAKE_ANIMATION_PERIOD = 100;
	public static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

	public Quake(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.actionPeriod = actionPeriod;
		this.animationPeriod = animationPeriod;
	}

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
		scheduler.scheduleEvent(this, Animation.createAnimationAction(this, QUAKE_ANIMATION_REPEAT_COUNT),
				this.animationPeriod);
	}

	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		scheduler.unscheduleAllEvents(this);
		world.removeEntity(this);
	}


	public int getAnimationPeriod() {
		return animationPeriod;
	}

	public void nextImage() {
		imageIndex = (imageIndex + 1) % images.size();
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
	
	public <R> R accept(EntityVisitor<R> visitor) {
		return visitor.visit(this);
	}

}
