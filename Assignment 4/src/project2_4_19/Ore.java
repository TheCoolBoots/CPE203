package project2_4_19;

import java.util.List;
import java.util.Random;

import processing.core.PImage;

public class Ore implements Entity {
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;

	// NOT USED:
	private int resourceLimit;
	private int resourceCount;

	private int actionPeriod;

	// NOT USED;
	private int animationPeriod;

	private static final Random rand = new Random();

	public static final String ORE_KEY = "ore";
	public static final String ORE_ID_PREFIX = "ore -- ";
	public static final int ORE_CORRUPT_MIN = 20000;
	public static final int ORE_CORRUPT_MAX = 30000;

	// ADDED AFTER COMPILE ERRORS:
	public static final String BLOB_KEY = "blob";
	public static final String BLOB_ID_SUFFIX = " -- blob";
	public static final int BLOB_PERIOD_SCALE = 4;
	public static final int BLOB_ANIMATION_MIN = 50;
	public static final int BLOB_ANIMATION_MAX = 150;

	public Ore(String id, Point position, List<PImage> images, int actionPeriod) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		// NOT USED:
		this.resourceLimit = 0;
		this.resourceCount = 0;

		this.actionPeriod = actionPeriod;
		// NOT USED:
		this.animationPeriod = 0;
	}

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
	}

	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Point pos = this.position; // store current position before removing

		world.removeEntity(this);
		scheduler.unscheduleAllEvents(this);

		OreBlob blob = pos.createOreBlob(this.id + BLOB_ID_SUFFIX, this.actionPeriod / BLOB_PERIOD_SCALE,
				BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
				imageStore.getImageList(BLOB_KEY));

		world.addEntity((Entity) blob);
		blob.scheduleActions(scheduler, world, imageStore);
	}

	public int getactionPeriod() {
		return actionPeriod;
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
	public void nextImage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getAnimationPeriod() {
		// TODO Auto-generated method stub
		return 0;
	}

}