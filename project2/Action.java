package Fresh;

final class Action {
	private ActionKind kind;
	private Entity entity;
	private WorldModel world;
	private ImageStore imageStore;
	private int repeatCount;

	public Action(ActionKind kind, Entity entity, WorldModel world, ImageStore imageStore, int repeatCount) {
		this.setKind(kind);
		this.setEntity(entity);
		this.setWorld(world);
		this.setImageStore(imageStore);
		this.setRepeatCount(repeatCount);
	}

	public static Action createAnimationAction(Entity entity, int repeatCount) {
		return new Action(ActionKind.ANIMATION, entity, null, null, repeatCount);
	}

	public static Action createActivityAction(Entity entity, WorldModel world, ImageStore imageStore) {
		return new Action(ActionKind.ACTIVITY, entity, world, imageStore, 0);
	}

	public static int getAnimationPeriod(Entity entity) {
		switch (entity.getKind()) {
		case MINER_FULL:
		case MINER_NOT_FULL:
		case ORE_BLOB:
		case QUAKE:
			return entity.getAnimationPeriod();
		default:
			throw new UnsupportedOperationException(
					String.format("getAnimationPeriod not supported for %s", entity.getKind()));
		}
	}

	public ActionKind getKind() {
		return kind;
	}

	public void setKind(ActionKind kind) {
		this.kind = kind;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	public int getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(int repeatCount) {
		this.repeatCount = repeatCount;
	}

	public WorldModel getWorld() {
		return world;
	}

	public void setWorld(WorldModel world) {
		this.world = world;
	}

	public ImageStore getImageStore() {
		return imageStore;
	}

	public void setImageStore(ImageStore imageStore) {
		this.imageStore = imageStore;
	}
}
