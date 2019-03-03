package base;

public interface ExecutableEntity extends Entity{

	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
	public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler);
	
}
