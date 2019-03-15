package base;

import java.util.LinkedList;
import java.util.List;
import processing.core.PImage;

import java.util.Optional;

public class OreBlob implements AnimatableEntity, ExecutableEntity{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int actionPeriod;
	private int animationPeriod;

	public OreBlob(String id, Point position, List<PImage> images, int actionPeriod, int animationPeriod) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.actionPeriod = actionPeriod;
		this.animationPeriod = animationPeriod;
	}

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
		scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), this.animationPeriod);
	}

	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		Optional<Entity> blobTarget = this.findNearest(world, this.position, this);
		long nextPeriod = this.actionPeriod;

		if (blobTarget.isPresent()) {
			Point tgtPos = blobTarget.get().getPosition();

			if (this.moveToFull(world, blobTarget.get(), scheduler)) {
				Quake quake = tgtPos.createQuake(imageStore.getImageList(Quake.QUAKE_KEY));

				world.addEntity(quake);
				nextPeriod += this.actionPeriod;
				quake.scheduleActions(scheduler, world, imageStore);
			}
		}

		scheduler.scheduleEvent(this, Activity.createActivityAction(this, world, imageStore), nextPeriod);
	}

	// 5
	public Optional<Entity> findNearest(WorldModel world, Point pos, Entity e) {
		List<Entity> ofType = new LinkedList<>();
		for (Entity entity : world.getEntities()) {
			if (entity.accept(new CheckIfVein())) {
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

	// 12 moveToOreBlob Originally.
	public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
		if (this.position.adjacent(target.getPosition())) {
			world.removeEntity(target);
			scheduler.unscheduleAllEvents(target);
			return true;
		} else {
			Point nextPos = this.nextPosition(world, target.getPosition());

			if (!this.position.equals(nextPos)) {
				Optional<Entity> occupant = world.getOccupant(nextPos);
				if (occupant.isPresent()) {
					scheduler.unscheduleAllEvents(occupant.get());
				}

				world.moveEntity(this, nextPos);
			}
			return false;
		}
	}

	// 14
	public Point nextPosition(WorldModel world, Point destPos) {
		SingleStepPathingStrategy nextStep = new SingleStepPathingStrategy();
		
		List<Point> newPos = nextStep.computePath(position, destPos, p -> !world.isOccupied(p)
				, (p,q)->true, new GetNeighbors());

		if(newPos.size()>0)
			return newPos.get(0);
		return position;
	}

	public int getActionPeriod() {
		return actionPeriod;
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
