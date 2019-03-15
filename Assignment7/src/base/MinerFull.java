package base;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import processing.core.PImage;

import java.util.Optional;
import java.util.stream.Stream;


public class MinerFull implements AnimatableEntity, ExecutableEntity{
	private String id;
	private Point position;
	private List<PImage> images;
	private int imageIndex;
	private int resourceLimit;
	private int actionPeriod;
	private int animationPeriod;

	public MinerFull(String id, Point position, List<PImage> images, int resourceLimit, int actionPeriod, int animationPeriod) {
		this.id = id;
		this.position = position;
		this.images = images;
		this.imageIndex = 0;
		this.resourceLimit = resourceLimit;
		this.actionPeriod = actionPeriod;
		this.animationPeriod = animationPeriod;
	}

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore) {
		//System.out.println("Scheduling Actions");
		scheduler.scheduleEvent((Entity) this, Activity.createActivityAction(this, world, imageStore),
				this.actionPeriod);
		scheduler.scheduleEvent(this, Animation.createAnimationAction(this, 0), this.animationPeriod);
	}

	public void executeActivity(WorldModel world, ImageStore imageStore, EventScheduler scheduler) {
		
		Optional<Entity> fullTarget = this.findNearest(world, this.position, this);

		if (fullTarget.isPresent() && this.moveToFull(world, fullTarget.get(), scheduler)) {
			this.transformFull(world, scheduler, imageStore);
		} else {
			scheduler.scheduleEvent((Entity) this, Activity.createActivityAction(this, world, imageStore), this.actionPeriod);
			//System.out.println(actionPeriod);
		}
	}

	// 5
	public Optional<Entity> findNearest(WorldModel world, Point pos, Entity e) {
		List<Entity> ofType = new LinkedList<>();
		for (Entity entity : world.getEntities()) {
			if (entity.accept(new CheckIfBlacksmith())) {
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

	// 11
	public boolean moveToFull(WorldModel world, Entity target, EventScheduler scheduler) {
		if (this.position.adjacent(target.getPosition())) {
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

	// 13
	public Point nextPosition(WorldModel world, Point destPos) {
		SingleStepPathingStrategy nextStep = new SingleStepPathingStrategy();
				
		List<Point> newPos = nextStep.computePath(position, destPos, p -> !world.isOccupied(p)
				, (p,q)->true, new GetNeighbors());

		if(newPos.size()>0)
			return newPos.get(0);
		return position;
	}

	// 9
	public void transformFull(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		MinerNotFull miner = this.position.createMinerNotFull(this.id, this.resourceLimit, this.actionPeriod, this.animationPeriod, this.images);

		world.removeEntity(this);
		scheduler.unscheduleAllEvents(this);

		world.addEntity(miner);
		((MinerNotFull) miner).scheduleActions(scheduler, world, imageStore);
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

	public void setPosition(Point point){
		this.position = point;
		//throw new RuntimeException("BREAK");
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