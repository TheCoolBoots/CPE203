package Fresh;

import java.util.List;
import processing.core.PImage;

final class Entity {
	private EntityKind kind;
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int resourceLimit;
	private int resourceCount;
	private int actionPeriod;
	private int animationPeriod;

	//private static final String QUAKE_KEY = "quake";
	private static final String QUAKE_ID = "quake";
	private static final int QUAKE_ACTION_PERIOD = 1100;
	private static final int QUAKE_ANIMATION_PERIOD = 100;
	//private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;

	public Entity(EntityKind kind, String id, Point position, List<PImage> images, int resourceLimit, int resourceCount,
			int actionPeriod, int animationPeriod) {
		this.setKind(kind);
		this.setId(id);
		this.setPosition(position);
		this.setImages(images);
		this.setImageIndex(0);
		this.setResourceLimit(resourceLimit);
		this.setResourceCount(resourceCount);
		this.setActionPeriod(actionPeriod);
		this.setAnimationPeriod(animationPeriod);
	}

	public static PImage getCurrentImage(Object entity) {
		if (entity instanceof Background) {
			return ((Background) entity).getImages().get(((Background) entity).getImageIndex());
		} else if (entity instanceof Entity) {
			return ((Entity) entity).getImages().get(((Entity) entity).getImageIndex());
		} else {
			throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", entity));
		}
	}

	public static Entity createBlacksmith(String id, Point position, List<PImage> images) {
		return new Entity(EntityKind.BLACKSMITH, id, position, images, 0, 0, 0, 0);
	}

	public static Entity createMinerFull(String id, int resourceLimit, Point position, int actionPeriod,
			int animationPeriod, List<PImage> images) {
		return new Entity(EntityKind.MINER_FULL, id, position, images, resourceLimit, resourceLimit, actionPeriod,
				animationPeriod);
	}

	public static Entity createMinerNotFull(String id, int resourceLimit, Point position, int actionPeriod,
			int animationPeriod, List<PImage> images) {
		return new Entity(EntityKind.MINER_NOT_FULL, id, position, images, resourceLimit, 0, actionPeriod,
				animationPeriod);
	}

	public static Entity createObstacle(String id, Point position, List<PImage> images) {
		return new Entity(EntityKind.OBSTACLE, id, position, images, 0, 0, 0, 0);
	}

	public static Entity createOre(String id, Point position, int actionPeriod, List<PImage> images) {
		return new Entity(EntityKind.ORE, id, position, images, 0, 0, actionPeriod, 0);
	}

	public static Entity createOreBlob(String id, Point position, int actionPeriod, int animationPeriod,
			List<PImage> images) {
		return new Entity(EntityKind.ORE_BLOB, id, position, images, 0, 0, actionPeriod, animationPeriod);
	}

	public static Entity createQuake(Point position, List<PImage> images) {
		return new Entity(EntityKind.QUAKE, QUAKE_ID, position, images, 0, 0, QUAKE_ACTION_PERIOD,
				QUAKE_ANIMATION_PERIOD);
	}

	public static Entity createVein(String id, Point position, int actionPeriod, List<PImage> images) {
		return new Entity(EntityKind.VEIN, id, position, images, 0, 0, actionPeriod, 0);
	}

	public EntityKind getKind() {
		return kind;
	}

	public void setKind(EntityKind kind) {
		this.kind = kind;
	}

	public int getAnimationPeriod() {
		return animationPeriod;
	}

	public void setAnimationPeriod(int animationPeriod) {
		this.animationPeriod = animationPeriod;
	}

	public int getActionPeriod() {
		return actionPeriod;
	}

	public void setActionPeriod(int actionPeriod) {
		this.actionPeriod = actionPeriod;
	}

	public int getImageIndex() {
		return imageIndex;
	}

	public void setImageIndex(int imageIndex) {
		this.imageIndex = imageIndex;
	}

	public List<PImage> getImages() {
		return images;
	}

	public void setImages(List<PImage> images) {
		this.images = images;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getResourceCount() {
		return resourceCount;
	}

	public void setResourceCount(int resourceCount) {
		this.resourceCount = resourceCount;
	}

	public int getResourceLimit() {
		return resourceLimit;
	}

	public void setResourceLimit(int resourceLimit) {
		this.resourceLimit = resourceLimit;
	}
}
