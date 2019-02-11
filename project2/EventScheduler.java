package Fresh;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Random;

final class EventScheduler {
	private static final String BLOB_KEY = "blob";
	private static final String BLOB_ID_SUFFIX = " -- blob";
	private static final int BLOB_PERIOD_SCALE = 4;
	private static final int BLOB_ANIMATION_MIN = 50;
	private static final int BLOB_ANIMATION_MAX = 150;

	private static final String ORE_ID_PREFIX = "ore -- ";
	private static final int ORE_CORRUPT_MIN = 20000;
	private static final int ORE_CORRUPT_MAX = 30000;
	private static final int ORE_REACH = 1;

	private static final String QUAKE_KEY = "quake";
	private static final int QUAKE_ANIMATION_REPEAT_COUNT = 10;
	private static final String ORE_KEY = "ore";

	private PriorityQueue<Event> eventQueue;
	private Map<Entity, List<Event>> pendingEvents;
	private double timeScale;

	public EventScheduler(double timeScale) {
		this.eventQueue = new PriorityQueue<>(new EventComparator());
		this.pendingEvents = new HashMap<>();
		this.timeScale = timeScale;
	}

	public void scheduleEvent(Entity entity, Action action, long afterPeriod) {
		long time = System.currentTimeMillis() + (long) (afterPeriod * this.timeScale);
		Event event = new Event(action, time, entity);

		this.eventQueue.add(event);

		// update list of pending events for the given entity
		List<Event> pending = this.pendingEvents.getOrDefault(entity, new LinkedList<>());
		pending.add(event);
		this.pendingEvents.put(entity, pending);
	}

	private void unscheduleAllEvents(Entity entity) {
		List<Event> pending = this.pendingEvents.remove(entity);

		if (pending != null) {
			for (Event event : pending) {
				this.eventQueue.remove(event);
			}
		}
	}

	private void removePendingEvent(EventScheduler scheduler, Event event) {
		List<Event> pending = scheduler.pendingEvents.get(event.getEntity());

		if (pending != null) {
			pending.remove(event);
		}
	}

	public void updateOnTime(EventScheduler scheduler, long time) {
		while (!scheduler.eventQueue.isEmpty() && scheduler.eventQueue.peek().getTime() < time) {
			Event next = scheduler.eventQueue.poll();

			removePendingEvent(scheduler, next);

			executeAction(next.getAction());
		}
	}

	public void scheduleActions(Entity entity, WorldModel world,
			ImageStore imageStore) {
		switch (entity.getKind()) {
		case MINER_FULL:
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
			scheduleEvent(entity, Action.createAnimationAction(entity, 0),
					Action.getAnimationPeriod(entity));
			break;

		case MINER_NOT_FULL:
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
			scheduleEvent(entity, Action.createAnimationAction(entity, 0),
					Action.getAnimationPeriod(entity));
			break;

		case ORE:
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
			break;

		case ORE_BLOB:
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
			scheduleEvent(entity, Action.createAnimationAction(entity, 0),
					Action.getAnimationPeriod(entity));
			break;

		case QUAKE:
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
			scheduleEvent(entity, Action.createAnimationAction(entity, QUAKE_ANIMATION_REPEAT_COUNT),
					Action.getAnimationPeriod(entity));
			break;

		case VEIN:
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
			break;

		default:
		}
	}

	private void executeAction(Action action) {
		switch (action.getKind()) {
		case ACTIVITY:
			executeActivityAction(action);
			break;

		case ANIMATION:
			executeAnimationAction(action);
			break;
		}
	}

	private void executeAnimationAction(Action action) {
		nextImage(action.getEntity());

		if (action.getRepeatCount() != 1) {
			scheduleEvent(action.getEntity(),
					Action.createAnimationAction(action.getEntity(), Math.max(action.getRepeatCount() - 1, 0)),
					Action.getAnimationPeriod(action.getEntity()));
		}
	}

	private static void nextImage(Entity entity) {
		entity.setImageIndex((entity.getImageIndex() + 1) % entity.getImages().size());
	}

	private void executeActivityAction(Action action) {
		switch (action.getEntity().getKind()) {
		case MINER_FULL:
			executeMinerFullActivity(action.getEntity(), action.getWorld(), action.getImageStore());
			break;

		case MINER_NOT_FULL:
			executeMinerNotFullActivity(action.getEntity(), action.getWorld(), action.getImageStore());
			break;

		case ORE:
			executeOreActivity(action.getEntity(), action.getWorld(), action.getImageStore());
			break;

		case ORE_BLOB:
			executeOreBlobActivity(action.getEntity(), action.getWorld(), action.getImageStore());
			break;

		case QUAKE:
			executeQuakeActivity(action.getEntity(), action.getWorld(), action.getImageStore());
			break;

		case VEIN:
			executeVeinActivity(action.getEntity(), action.getWorld(), action.getImageStore());
			break;

		default:
			throw new UnsupportedOperationException(
					String.format("executeActivityAction not supported for %s", action.getEntity().getKind()));
		}
	}

	private static final Random rand = new Random();

	private void executeMinerFullActivity(Entity entity, WorldModel world, ImageStore imageStore) {
		Optional<Entity> fullTarget = findNearest(world, entity.getPosition(), EntityKind.BLACKSMITH);

		if (fullTarget.isPresent() && moveToFull(entity, world, fullTarget.get())) {
			this.transformFull(entity, world, imageStore);
		} else {
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
		}
	}

	private void executeMinerNotFullActivity(Entity entity, WorldModel world, ImageStore imageStore) {
		Optional<Entity> notFullTarget = findNearest(world, entity.getPosition(), EntityKind.ORE);

		if (!notFullTarget.isPresent() || !moveToNotFull(entity, world, notFullTarget.get())
				|| !transformNotFull(entity, world, imageStore)) {
			scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
					entity.getActionPeriod());
		}
	}

	private void executeOreActivity(Entity entity, WorldModel world, ImageStore imageStore) {
		Point pos = entity.getPosition(); // store current position before removing

		WorldModel.removeEntity(world, entity);
		unscheduleAllEvents(entity);

		Entity blob = Entity.createOreBlob(entity.getId() + BLOB_ID_SUFFIX, pos,
				entity.getActionPeriod() / BLOB_PERIOD_SCALE,
				BLOB_ANIMATION_MIN + rand.nextInt(BLOB_ANIMATION_MAX - BLOB_ANIMATION_MIN),
				imageStore.getImageList(BLOB_KEY));

		WorldModel.addEntity(world, blob);
		scheduleActions(blob, world, imageStore);
	}

	private void executeOreBlobActivity(Entity entity, WorldModel world, ImageStore imageStore) {
		Optional<Entity> blobTarget = findNearest(world, entity.getPosition(), EntityKind.VEIN);
		long nextPeriod = entity.getActionPeriod();

		if (blobTarget.isPresent()) {
			Point tgtPos = blobTarget.get().getPosition();

			if (moveToOreBlob(entity, world, blobTarget.get())) {
				Entity quake = Entity.createQuake(tgtPos, imageStore.getImageList(QUAKE_KEY));

				WorldModel.addEntity(world, quake);
				nextPeriod += entity.getActionPeriod();
				scheduleActions(quake, world, imageStore);
			}
		}

		scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore), nextPeriod);
	}

	private static Optional<Entity> findNearest(WorldModel world, Point pos, EntityKind kind) {
		List<Entity> ofType = new LinkedList<>();
		for (Entity entity : world.getEntities()) {
			if (entity.getKind() == kind) {
				ofType.add(entity);
			}
		}

		return nearestEntity(ofType, pos);
	}

	private static Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
		if (entities.isEmpty()) {
			return Optional.empty();
		} else {
			Entity nearest = entities.get(0);
			int nearestDistance = distanceSquared(nearest.getPosition(), pos);

			for (Entity other : entities) {
				int otherDistance = distanceSquared(other.getPosition(), pos);

				if (otherDistance < nearestDistance) {
					nearest = other;
					nearestDistance = otherDistance;
				}
			}

			return Optional.of(nearest);
		}
	}

	private static int distanceSquared(Point p1, Point p2) {
		int deltaX = p1.getX() - p2.getX();
		int deltaY = p1.getY() - p2.getY();

		return deltaX * deltaX + deltaY * deltaY;
	}

	private void executeQuakeActivity(Entity entity, WorldModel world, ImageStore imageStore) {
		unscheduleAllEvents(entity);
		WorldModel.removeEntity(world, entity);
	}

	private void executeVeinActivity(Entity entity, WorldModel world, ImageStore imageStore) {
		Optional<Point> openPt = findOpenAround(world, entity.getPosition());

		if (openPt.isPresent()) {
			Entity ore = Entity.createOre(ORE_ID_PREFIX + entity.getId(), openPt.get(),
					ORE_CORRUPT_MIN + rand.nextInt(ORE_CORRUPT_MAX - ORE_CORRUPT_MIN),
					imageStore.getImageList(ORE_KEY));
			WorldModel.addEntity(world, ore);
			scheduleActions(ore, world, imageStore);
		}

		scheduleEvent(entity, Action.createActivityAction(entity, world, imageStore),
				entity.getActionPeriod());
	}

	private boolean transformNotFull(Entity entity, WorldModel world,
			ImageStore imageStore) {
		if (entity.getResourceCount() >= entity.getResourceLimit()) {
			Entity miner = Entity.createMinerFull(entity.getId(), entity.getResourceLimit(), entity.getPosition(),
					entity.getActionPeriod(), entity.getAnimationPeriod(), entity.getImages());

			WorldModel.removeEntity(world, entity);
			unscheduleAllEvents(entity);

			WorldModel.addEntity(world, miner);
			scheduleActions(miner, world, imageStore);

			return true;
		}

		return false;
	}

	private void transformFull(Entity entity, WorldModel world, ImageStore imageStore) {
		Entity miner = Entity.createMinerNotFull(entity.getId(), entity.getResourceLimit(), entity.getPosition(),
				entity.getActionPeriod(), entity.getAnimationPeriod(), entity.getImages());

		WorldModel.removeEntity(world, entity);
		unscheduleAllEvents(entity);

		WorldModel.addEntity(world, miner);
		scheduleActions(miner, world, imageStore);
	}

	private boolean moveToNotFull(Entity miner, WorldModel world, Entity target) {
		if (adjacent(miner.getPosition(), target.getPosition())) {
			miner.setResourceCount(miner.getResourceCount() + 1);
			WorldModel.removeEntity(world, target);
			unscheduleAllEvents(target);

			return true;
		} else {
			Point nextPos = nextPositionMiner(miner, world, target.getPosition());

			if (!miner.getPosition().equals(nextPos)) {
				Optional<Entity> occupant = WorldModel.getOccupant(world, nextPos);
				if (occupant.isPresent()) {
					unscheduleAllEvents(occupant.get());
				}

				WorldModel.moveEntity(world, miner, nextPos);
			}
			return false;
		}
	}

	private boolean moveToFull(Entity miner, WorldModel world, Entity target) {
		if (adjacent(miner.getPosition(), target.getPosition())) {
			return true;
		} else {
			Point nextPos = nextPositionMiner(miner, world, target.getPosition());

			if (!miner.getPosition().equals(nextPos)) {
				Optional<Entity> occupant = WorldModel.getOccupant(world, nextPos);
				if (occupant.isPresent()) {
					unscheduleAllEvents(occupant.get());
				}

				WorldModel.moveEntity(world, miner, nextPos);
			}
			return false;
		}
	}

	private boolean moveToOreBlob(Entity blob, WorldModel world, Entity target) {
		if (adjacent(blob.getPosition(), target.getPosition())) {
			WorldModel.removeEntity(world, target);
			unscheduleAllEvents(target);
			return true;
		} else {
			Point nextPos = nextPositionOreBlob(blob, world, target.getPosition());

			if (!blob.getPosition().equals(nextPos)) {
				Optional<Entity> occupant = WorldModel.getOccupant(world, nextPos);
				if (occupant.isPresent()) {
					unscheduleAllEvents(occupant.get());
				}

				WorldModel.moveEntity(world, blob, nextPos);
			}
			return false;
		}
	}

	private static Point nextPositionMiner(Entity entity, WorldModel world, Point destPos) {
		int horiz = Integer.signum(destPos.getX() - entity.getPosition().getX());
		Point newPos = new Point(entity.getPosition().getX() + horiz, entity.getPosition().getY());

		if (horiz == 0 || Background.isOccupied(world, newPos)) {
			int vert = Integer.signum(destPos.getY() - entity.getPosition().getY());
			newPos = new Point(entity.getPosition().getX(), entity.getPosition().getY() + vert);

			if (vert == 0 || Background.isOccupied(world, newPos)) {
				newPos = entity.getPosition();
			}
		}

		return newPos;
	}

	private static Point nextPositionOreBlob(Entity entity, WorldModel world, Point destPos) {
		int horiz = Integer.signum(destPos.getX() - entity.getPosition().getX());
		Point newPos = new Point(entity.getPosition().getX() + horiz, entity.getPosition().getY());

		Optional<Entity> occupant = WorldModel.getOccupant(world, newPos);

		if (horiz == 0 || (occupant.isPresent() && !(occupant.get().getKind() == EntityKind.ORE))) {
			int vert = Integer.signum(destPos.getY() - entity.getPosition().getY());
			newPos = new Point(entity.getPosition().getX(), entity.getPosition().getY() + vert);
			occupant = WorldModel.getOccupant(world, newPos);

			if (vert == 0 || (occupant.isPresent() && !(occupant.get().getKind() == EntityKind.ORE))) {
				newPos = entity.getPosition();
			}
		}

		return newPos;
	}

	private static boolean adjacent(Point p1, Point p2) {
		return (p1.getX() == p2.getX() && Math.abs(p1.getY() - p2.getY()) == 1)
				|| (p1.getY() == p2.getY() && Math.abs(p1.getX() - p2.getX()) == 1);
	}

	private static Optional<Point> findOpenAround(WorldModel world, Point pos) {
		for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
			for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
				Point newPt = new Point(pos.getX() + dx, pos.getY() + dy);
				if (Background.withinBounds(world, newPt) && !Background.isOccupied(world, newPt)) {
					return Optional.of(newPt);
				}
			}
		}

		return Optional.empty();
	}
}
