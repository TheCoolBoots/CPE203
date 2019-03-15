package base;

import java.util.LinkedList;
import java.util.List;
import processing.core.PImage;

import java.util.Optional;

public class MinerNotFull implements AnimatableEntity, ExecutableEntity{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int resourceLimit;
	private int resourceCount;
	private int actionPeriod;
	private int animationPeriod;

	public MinerNotFull(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod,
			int animationPeriod) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.resourceLimit = 4;
		this.resourceCount = resourceLimit;
		this.actionPeriod = actionPeriod;
		this.animationPeriod = animationPeriod;
	}

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent((Entity) this, Activity.createActivityAction(this, world, imageStore),
				this.actionPeriod);
		scheduler.scheduleEvent((Entity) this, Animation.createAnimationAction(this, 0), this.animationPeriod);
	}

	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Entity> notFullTarget = this.findNearest(world, this.position, this);

		if (!notFullTarget.isPresent() || !this.moveToFull(world, notFullTarget.get(), scheduler)
				|| !this.transformNotFull(world, scheduler, imageStore)) {
			scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
		}
	}

	// 5
	public Optional<Entity> findNearest(WorldModel world, Point pos, Entity e) {
		List<Entity> ofType = new LinkedList<>();
		for (Entity entity : world.getEntities()) {
			if (entity.accept(new CheckIfOre())) {
				ofType.add(entity);
			}
		}

		return nearestEntity(ofType, pos);
	}

	// 15
	public Optional<Entity> nearestEntity(List<Entity> entities, Point pos) {
		if (entities.isEmpty()) {
			return Optional.empty();
		} else {
			Entity nearest = entities.get(0);
			int nearestDistance = nearest.getPosition().distanceSquared(pos);

			for (Entity other : entities) {
				int otherDistance = other.getPosition().distanceSquared(pos);

				if (otherDistance < nearestDistance) {
					nearest = other;
					nearestDistance = otherDistance;
				}
			}

			return Optional.of(nearest);
		}
	}

	// THIS IS MOVETONOTFULL
	public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
		if (this.position.adjacent(target.getPosition())) {
			resourceCount += 1;
			world.removeEntity(target);
			scheduler.unscheduleAllEvents(target);

			return true;
		} else {
			Point nextPos = nextPosition(world, target.getPosition());

			if (!position.equals(nextPos)) {
				Optional<Entity> occupant = world.getOccupant(nextPos);
				if (occupant.isPresent()) {
					scheduler.unscheduleAllEvents(occupant.get());
				}

				world.moveEntity((Entity) this, nextPos);
			}
			return false;
		}
	}

	// 13
	public Point nextPosition(WorldModel world, Point destPos) {
		SingleStepPathingStrategy nextStep = new SingleStepPathingStrategy();
		
		List<Point> newPos = nextStep.computePath(position, destPos, p -> !world.isOccupied(p)
				, (p,q)->true, new GetNeighbors());

		if(newPos.size()>0)
			return newPos.get(0);
		return position;
	}

	// 8
	public boolean transformNotFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		if (this.resourceCount >= this.resourceLimit) {
			MinerFull miner = this.position.createMinerFull(this.id, this.resourceLimit, this.actionPeriod,
					this.animationPeriod, this.images);

			world.removeEntity((Entity) this);
			scheduler.unscheduleAllEvents((Entity) this);

			world.addEntity((Entity) miner);
			miner.scheduleActions(scheduler, world, imageStore);

			return true;
		}

		return false;
	}

	public int getActionPeriod() {
		return actionPeriod;
	}

	public int getAnimationPeriod() {
		return animationPeriod;
	}

	public int getResourceLimit() {
		return resourceLimit;
	}

	public int getResourceCount() {
		return resourceCount;
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
