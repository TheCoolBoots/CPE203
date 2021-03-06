package project2_4_19;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

import processing.core.PImage;

final class WorldModel {
	private int numRows;
	public int getNumRows() {
		return numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	private int numCols;
	private Background background[][];
	private Entity occupancy[][];
	private Set<Entity> entities;
	
	public static final int PROPERTY_KEY = 0;

	private static final int ORE_REACH = 1;
	private static final String VEIN_KEY = "vein";
	private static final int VEIN_NUM_PROPERTIES = 5;
	private static final int VEIN_ID = 1;
	private static final int VEIN_COL = 2;
	private static final int VEIN_ROW = 3;
	private static final int VEIN_ACTION_PERIOD = 4;
	
	private static final String BGND_KEY = "background";
	private static final int BGND_NUM_PROPERTIES = 4;
	private static final int BGND_ID = 1;
	private static final int BGND_COL = 2;
	private static final int BGND_ROW = 3;

	private static final String MINER_KEY = "miner";
	private static final int MINER_NUM_PROPERTIES = 7;
	private static final int MINER_ID = 1;
	private static final int MINER_COL = 2;
	private static final int MINER_ROW = 3;
	private static final int MINER_LIMIT = 4;
	private static final int MINER_ACTION_PERIOD = 5;
	private static final int MINER_ANIMATION_PERIOD = 6;

	private static final String OBSTACLE_KEY = "obstacle";
	private static final int OBSTACLE_NUM_PROPERTIES = 4;
	private static final int OBSTACLE_ID = 1;
	private static final int OBSTACLE_COL = 2;
	private static final int OBSTACLE_ROW = 3;

	private static final String ORE_KEY = "ore";
	private static final int ORE_NUM_PROPERTIES = 5;
	private static final int ORE_ID = 1;
	private static final int ORE_COL = 2;
	private static final int ORE_ROW = 3;
	private static final int ORE_ACTION_PERIOD = 4;

	private static final String SMITH_KEY = "blacksmith";
	private static final int SMITH_NUM_PROPERTIES = 4;
	private static final int SMITH_ID = 1;
	private static final int SMITH_COL = 2;
	private static final int SMITH_ROW = 3;

	public WorldModel(int numRows, int numCols, Background defaultBackground) {
		this.numRows = numRows;
		this.numCols = numCols;
		this.background = new Background[numRows][numCols];
		this.occupancy = new Entity[numRows][numCols];
		this.entities = new HashSet<>();

		for (int row = 0; row < numRows; row++) {
			Arrays.fill(this.background[row], defaultBackground);
		}
	}

	public void removeEntity(Entity entity) {
		removeEntityAt(entity.getPosition());
	}

	public void removeEntityAt(Point pos) {
		if (withinBounds(pos) && getOccupancyCell(pos) != null) {
			Entity entity = getOccupancyCell(pos);

			/*
			 * this moves the entity just outside of the grid for debugging purposes
			 */
			entity.setPosition(new Point(-1, -1));
			this.entities.remove(entity);
			setOccupancyCell(pos, null);
		}
	}

	public boolean isOccupied(Point pos) {
		return withinBounds(pos) && getOccupancyCell(pos) != null;
	}

	public void moveEntity(Entity entity, Point pos) {
		Point oldPos = entity.getPosition();
		if (withinBounds(pos) && !pos.equals(oldPos)) {
			setOccupancyCell(oldPos, null);
			removeEntityAt(pos);
			setOccupancyCell(pos, entity);
			entity.setPosition(pos);
		}
	}

	public Optional<Entity> getOccupant(Point pos) {
		if (isOccupied(pos)) {
			return Optional.of(getOccupancyCell(pos));
		} else {
			return Optional.empty();
		}
	}

	public void addEntity(Entity entity) {
		if (withinBounds(entity.getPosition())) {
			setOccupancyCell(entity.getPosition(), entity);
			this.entities.add(entity);
		}
	}

	public Optional<Point> findOpenAround(Point pos) {
		for (int dy = -ORE_REACH; dy <= ORE_REACH; dy++) {
			for (int dx = -ORE_REACH; dx <= ORE_REACH; dx++) {
				Point newPt = new Point(pos.x + dx, pos.y + dy);
				if (withinBounds(newPt) && !isOccupied(newPt)) {
					return Optional.of(newPt);
				}
			}
		}

		return Optional.empty();
	}

	public Set<Entity> getEntities() {
		return entities;
	}

	public boolean withinBounds(Point pos) {
		return pos.y >= 0 && pos.y < this.numRows && pos.x >= 0 && pos.x < this.numCols;
	}

	public Entity getOccupancyCell(Point pos) {
		return this.occupancy[pos.y][pos.x];
	}

	public void setOccupancyCell(Point pos, Entity entity) {
		this.occupancy[pos.y][pos.x] = entity;
	}

	public void load(Scanner in, ImageStore imageStore) {
		int lineNumber = 0;
		while (in.hasNextLine()) {
			try {
				if (!processLine(in.nextLine(), imageStore)) {
					System.err.println(String.format("invalid entry on line %d", lineNumber));
				}
			} catch (NumberFormatException e) {
				System.err.println(String.format("invalid entry on line %d", lineNumber));
			} catch (IllegalArgumentException e) {
				System.err.println(String.format("issue on line %d: %s", lineNumber, e.getMessage()));
			}
			lineNumber++;
		}
	}

	public boolean processLine(String line, ImageStore imageStore) {
		String[] properties = line.split("\\s");
		if (properties.length > 0) {
			switch (properties[PROPERTY_KEY]) {
			case BGND_KEY:
				return parseBackground(properties, imageStore);
			case MINER_KEY:
				return parseMiner(properties, imageStore);
			case OBSTACLE_KEY:
				return parseObstacle(properties, imageStore);
			case ORE_KEY:
				return parseOre(properties, imageStore);
			case SMITH_KEY:
				return parseSmith(properties, imageStore);
			case VEIN_KEY:
				return parseVein(properties, imageStore);
			}
		}

		return false;
	}

	public boolean parseBackground(String[] properties, ImageStore imageStore) {
		if (properties.length == BGND_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[BGND_COL]), Integer.parseInt(properties[BGND_ROW]));
			String id = properties[BGND_ID];
			setBackground(pt, new Background(id, imageStore.getImageList(id)));
		}

		return properties.length == BGND_NUM_PROPERTIES;
	}

	public boolean parseMiner(String[] properties, ImageStore imageStore) {
		if (properties.length == MINER_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[MINER_COL]), Integer.parseInt(properties[MINER_ROW]));
			Entity entity = pt.createMinerNotFull(properties[MINER_ID], Integer.parseInt(properties[MINER_LIMIT]),
					Integer.parseInt(properties[MINER_ACTION_PERIOD]),
					Integer.parseInt(properties[MINER_ANIMATION_PERIOD]), imageStore.getImageList(MINER_KEY));
			tryAddEntity(entity);
		}

		return properties.length == MINER_NUM_PROPERTIES;
	}

	public boolean parseObstacle(String[] properties, ImageStore imageStore) {
		if (properties.length == OBSTACLE_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
					Integer.parseInt(properties[OBSTACLE_ROW]));
			Entity entity = pt.createObstacle(properties[OBSTACLE_ID], imageStore.getImageList(OBSTACLE_KEY));
			tryAddEntity(entity);
		}

		return properties.length == OBSTACLE_NUM_PROPERTIES;
	}

	public boolean parseOre(String[] properties, ImageStore imageStore) {
		if (properties.length == ORE_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[ORE_COL]), Integer.parseInt(properties[ORE_ROW]));
			Entity entity = pt.createOre(properties[ORE_ID], Integer.parseInt(properties[ORE_ACTION_PERIOD]),
					imageStore.getImageList(ORE_KEY));
			tryAddEntity(entity);
		}

		return properties.length == ORE_NUM_PROPERTIES;
	}

	public boolean parseSmith(String[] properties, ImageStore imageStore) {
		if (properties.length == SMITH_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[SMITH_COL]), Integer.parseInt(properties[SMITH_ROW]));
			Entity entity = pt.createBlacksmith(properties[SMITH_ID], imageStore.getImageList(SMITH_KEY));
			tryAddEntity(entity);
		}

		return properties.length == SMITH_NUM_PROPERTIES;
	}

	public boolean parseVein(String[] properties, ImageStore imageStore) {
		if (properties.length == VEIN_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[VEIN_COL]), Integer.parseInt(properties[VEIN_ROW]));
			Entity entity = pt.createVein(properties[VEIN_ID], Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
					imageStore.getImageList(VEIN_KEY));
			tryAddEntity(entity);
		}

		return properties.length == VEIN_NUM_PROPERTIES;
	}

	public void tryAddEntity(Entity entity) {
		if (isOccupied(entity.getPosition())) {
			// arguably the wrong type of exception, but we are not
			// defining our own exceptions yet
			throw new IllegalArgumentException("position occupied");
		}

		addEntity(entity);
	}

	/*
	 * Assumes that there is no entity currently occupying the intended destination
	 * cell.
	 */

	public Optional<PImage> getBackgroundImage(WorldModel world, Point pos) {
		if (withinBounds(pos)) {
			return Optional.of(getCurrentImage(getBackgroundCell(pos)));
		} else {
			return Optional.empty();
		}
	}

	public void setBackground(Point pos, Background background) {
		if (withinBounds(pos)) {
			setBackgroundCell(pos, background);
		}
	}

	public Background getBackgroundCell(Point pos) {
		return this.background[pos.y][pos.x];
	}

	public void setBackgroundCell(Point pos, Background background) {
		this.background[pos.y][pos.x] = background;
	}

	public static PImage getCurrentImage(Object entity) {
		if (entity instanceof Background) {
			return ((Background) entity).images.get(((Background) entity).imageIndex);
		} else if (entity instanceof Entity) {
			return ((Entity) entity).getImages().get(((Entity) entity).getImageIndex());
		} else {
			throw new UnsupportedOperationException(String.format("getCurrentImage not supported for %s", entity));
		}
	}
	
}
