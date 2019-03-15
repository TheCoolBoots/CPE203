package base;
import java.util.List;

import processing.core.PImage;

public interface Entity
{	
//	public void scheduleActions(EventScheduler scheduler, WorldModel world, ImageStore imageStore);
//	public void executeActivity(WorldModel world,ImageStore imageStore,EventScheduler scheduler);
//	public void nextImage();
//	public int getAnimationPeriod();
	
	public Point getPosition();
	public void setPosition(Point pos);
	public List<PImage> getImages();
	public String getId();
	public int getImageIndex();

	<R> R accept(EntityVisitor<R> visitor);
}
