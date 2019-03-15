package base;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import processing.core.PImage;

public class Vein implements ExecutableEntity {
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;

	// NOT USED;
	private int animationPeriod;

	private static final Random rand = new Random();

	public Vein(String id, Point position, List<PImage> images, int actionPeriod) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.actionPeriod = actionPeriod;
		this.animationPeriod = 0;
	}

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
	}

	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Point> openPt = world.findOpenAround(this.position);

		if (openPt.isPresent()) {
			Ore ore = openPt.get().createOre(Ore.ORE_ID_PREFIX + this.id,
					Ore.ORE_CORRUPT_MIN + rand.nextInt(Ore.ORE_CORRUPT_MAX - Ore.ORE_CORRUPT_MIN),
					imageStore.getImageList(Ore.ORE_KEY));
			world.addEntity(ore);
			ore.scheduleActions(scheduler, world, imageStore);
		}

		scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
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
