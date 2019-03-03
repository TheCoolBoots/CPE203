package base;

public class Animation implements Action
{
	private AnimatableEntity entity;
	private int repeatCount;

	public Animation(Entity entity, int repeatCount)
	{
		this.entity = (AnimatableEntity) entity;
		this.repeatCount = repeatCount;
	}

   	public static Animation createAnimationAction(Entity entity, int repeatCount)
   	{
      	return new Animation(entity, repeatCount);
   	}

	public void executeAction(EventScheduler scheduler)
	{
    	this.entity.nextImage();

      	if (this.repeatCount != 1)
      	{
         	scheduler.scheduleEvent(this.entity,
            	createAnimationAction(this.entity,
               	Math.max(this.repeatCount - 1, 0)),
            	this.entity.getAnimationPeriod());
      	}
	}

	public Entity getEntity() { return entity; }
	public int getRepeatCount() { return repeatCount; }


}
