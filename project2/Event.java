package Fresh;

final class Event {
	private Action action;
	private long time;
	private Entity entity;

	public Event(Action action, long time, Entity entity) {
		this.setAction(action);
		this.setTime(time);
		this.setEntity(entity);
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Entity getEntity() {
		return entity;
	}

	public void setEntity(Entity entity) {
		this.entity = entity;
	}

}
