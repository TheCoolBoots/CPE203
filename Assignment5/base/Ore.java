package base;

import java.util.List;
import java.util.Random;

import processing.core.PImage;

public class Ore implements ExecutableEntity {
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;

	private static final Random rand = new Random();

	public static final String ORE_KEY = "ore";
	public static final String ORE_ID_PREFIX = "ore -- ";
	public static final int ORE_CORRUPT_MIN = 20000;
	public static final int ORE_CORRUPT_MAX = 30000;

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
		this.actionPeriod = actionPeriod;
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