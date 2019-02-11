package Fresh;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;
import java.util.Scanner;
import java.util.Set;

final class WorldModel {
	private static final int PROPERTY_KEY = 0;

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

	private static final String VEIN_KEY = "vein";
	private static final int VEIN_NUM_PROPERTIES = 5;
	private static final int VEIN_ID = 1;
	private static final int VEIN_COL = 2;
	private static final int VEIN_ROW = 3;
	private static final int VEIN_ACTION_PERIOD = 4;

	private int numRows;
	private int numCols;
	private Background background[][];
	private Entity occupancy[][];
	private Set<Entity> entities;

	public WorldModel(int numRows, int numCols, Background defaultBackground) {
		this.setNumRows(numRows);
		this.setNumCols(numCols);
		this.setBackground(new Background[numRows][numCols]);
		this.setOccupancy(new Entity[numRows][numCols]);
		this.setEntities(new HashSet<>());

		for (int row = 0; row < numRows; row++) {
			Arrays.fill(this.getBackground()[row], defaultBackground);
		}
	}

	public void load(Scanner in, WorldModel world, ImageStore imageStore) {
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

	private boolean processLine(String line, ImageStore imageStore) {
		String[] properties = line.split("\\s");
		if (properties.length > 0) {
			switch (properties[PROPERTY_KEY]) {
			case BGND_KEY:
				return this.parseBackground(properties, imageStore);
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

	private boolean parseBackground(String[] properties, ImageStore imageStore) {
		if (properties.length == BGND_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[BGND_COL]), Integer.parseInt(properties[BGND_ROW]));
			String id = properties[BGND_ID];
			Background.setBackground(this, pt, new Background(id, imageStore.getImageList(id)));
		}

		return properties.length == BGND_NUM_PROPERTIES;
	}

	private boolean parseMiner(String[] properties, ImageStore imageStore) {
		if (properties.length == MINER_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[MINER_COL]), Integer.parseInt(properties[MINER_ROW]));
			Entity entity = Entity.createMinerNotFull(properties[MINER_ID], Integer.parseInt(properties[MINER_LIMIT]),
					pt, Integer.parseInt(properties[MINER_ACTION_PERIOD]),
					Integer.parseInt(properties[MINER_ANIMATION_PERIOD]),
					imageStore.getImageList(MINER_KEY));
			tryAddEntity(entity);
		}

		return properties.length == MINER_NUM_PROPERTIES;
	}

	private boolean parseObstacle(String[] properties, ImageStore imageStore) {
		if (properties.length == OBSTACLE_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[OBSTACLE_COL]),
					Integer.parseInt(properties[OBSTACLE_ROW]));
			Entity entity = Entity.createObstacle(properties[OBSTACLE_ID], pt,
					imageStore.getImageList(OBSTACLE_KEY));
			tryAddEntity(entity);
		}

		return properties.length == OBSTACLE_NUM_PROPERTIES;
	}

	private boolean parseOre(String[] properties, ImageStore imageStore) {
		if (properties.length == ORE_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[ORE_COL]), Integer.parseInt(properties[ORE_ROW]));
			Entity entity = Entity.createOre(properties[ORE_ID], pt, Integer.parseInt(properties[ORE_ACTION_PERIOD]),
					imageStore.getImageList(ORE_KEY));
			tryAddEntity(entity);
		}

		return properties.length == ORE_NUM_PROPERTIES;
	}

	private boolean parseSmith(String[] properties, ImageStore imageStore) {
		if (properties.length == SMITH_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[SMITH_COL]), Integer.parseInt(properties[SMITH_ROW]));
			Entity entity = Entity.createBlacksmith(properties[SMITH_ID], pt,
					imageStore.getImageList(SMITH_KEY));
			tryAddEntity(entity);
		}

		return properties.length == SMITH_NUM_PROPERTIES;
	}

	private boolean parseVein(String[] properties, ImageStore imageStore) {
		if (properties.length == VEIN_NUM_PROPERTIES) {
			Point pt = new Point(Integer.parseInt(properties[VEIN_COL]), Integer.parseInt(properties[VEIN_ROW]));
			Entity entity = Entity.createVein(properties[VEIN_ID], pt, Integer.parseInt(properties[VEIN_ACTION_PERIOD]),
					imageStore.getImageList(VEIN_KEY));
			tryAddEntity(entity);
		}

		return properties.length == VEIN_NUM_PROPERTIES;
	}

	private void tryAddEntity(Entity entity) {
		if (Background.isOccupied(this, entity.getPosition())) {
			// arguably the wrong type of exception, but we are not
			// defining our own exceptions yet
			throw new IllegalArgumentException("position occupied");
		}

		addEntity(this, entity);
	}

	/*
	 * Assumes that there is no entity currently occupying the intended destination
	 * cell.
	 */
	public static void addEntity(WorldModel world, Entity entity) {
		if (Background.withinBounds(world, entity.getPosition())) {
			setOccupancyCell(world, entity.getPosition(), entity);
			world.getEntities().add(entity);
		}
	}

	public static void moveEntity(WorldModel world, Entity entity, Point pos) {
		Point oldPos = entity.getPosition();
		if (Background.withinBounds(world, pos) && !pos.equals(oldPos)) {
			setOccupancyCell(world, oldPos, null);
			removeEntityAt(world, pos);
			setOccupancyCell(world, pos, entity);
			entity.setPosition(pos);
		}
	}

	public static void removeEntity(WorldModel world, Entity entity) {
		removeEntityAt(world, entity.getPosition());
	}

	public static void removeEntityAt(WorldModel world, Point pos) {
		if (Background.withinBounds(world, pos) && Background.getOccupancyCell(world, pos) != null) {
			Entity entity = Background.getOccupancyCell(world, pos);

			/*
			 * this moves the entity just outside of the grid for debugging purposes
			 */
			entity.setPosition(new Point(-1, -1));
			world.getEntities().remove(entity);
			setOccupancyCell(world, pos, null);
		}
	}

	public static Optional<Entity> getOccupant(WorldModel world, Point pos) {
		if (Background.isOccupied(world, pos)) {
			return Optional.of(Background.getOccupancyCell(world, pos));
		} else {
			return Optional.empty();
		}
	}

	private static void setOccupancyCell(WorldModel world, Point pos, Entity entity) {
		world.getOccupancy()[pos.getY()][pos.getX()] = entity;
	}

	public Background[][] getBackground() {
		return background;
	}

	public void setBackground(Background background[][]) {
		this.background = background;
	}

	public int getNumRows() {
		return numRows;
	}

	public void setNumRows(int numRows) {
		this.numRows = numRows;
	}

	public int getNumCols() {
		return numCols;
	}

	public void setNumCols(int numCols) {
		this.numCols = numCols;
	}

	public Entity[][] getOccupancy() {
		return occupancy;
	}

	public void setOccupancy(Entity occupancy[][]) {
		this.occupancy = occupancy;
	}

	public Set<Entity> getEntities() {
		return entities;
	}

	public void setEntities(Set<Entity> entities) {
		this.entities = entities;
	}

}
