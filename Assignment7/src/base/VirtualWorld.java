package base;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.LinkedList;

import processing.core.*;

public final class VirtualWorld extends PApplet {
	private static final int TIMER_ACTION_PERIOD = 100;

	private static final int VIEW_WIDTH = 640;
	private static final int VIEW_HEIGHT = 480;
	private static final int TILE_WIDTH = 32;
	private static final int TILE_HEIGHT = 32;
	private static final int WORLD_WIDTH_SCALE = 2;
	private static final int WORLD_HEIGHT_SCALE = 2;

	private static final int VIEW_COLS = VIEW_WIDTH / TILE_WIDTH;
	private static final int VIEW_ROWS = VIEW_HEIGHT / TILE_HEIGHT;
	private static final int WORLD_COLS = VIEW_COLS * WORLD_WIDTH_SCALE;
	private static final int WORLD_ROWS = VIEW_ROWS * WORLD_HEIGHT_SCALE;

	private static final String IMAGE_LIST_FILE_NAME = "imagelist";
	private static final String DEFAULT_IMAGE_NAME = "background_default";
	private static final int DEFAULT_IMAGE_COLOR = 0x808080;

	private static final String LOAD_FILE_NAME = "gaia.sav";

	private static final String FAST_FLAG = "-fast";
	private static final String FASTER_FLAG = "-faster";
	private static final String FASTEST_FLAG = "-fastest";
	private static final double FAST_SCALE = 0.5;
	private static final double FASTER_SCALE = 0.25;
	private static final double FASTEST_SCALE = 0.10;

	private static double timeScale = 1.0;

	private ImageStore imageStore;
	private WorldModel world;
	private WorldView view;
	private EventScheduler scheduler;
	private boolean blitz;

	private int waitFrames = 0;
	
	private Koopa koopa;
	private long next_time;
	private LinkedList<Koopa> koopas;

	public void settings() {
		size(VIEW_WIDTH, VIEW_HEIGHT);
	}

	/*
	 * Processing entry point for "sketch" setup.
	 */
	public void setup() {
		this.imageStore = new ImageStore(createImageColored(TILE_WIDTH, TILE_HEIGHT, DEFAULT_IMAGE_COLOR));
		this.world = new WorldModel(WORLD_ROWS, WORLD_COLS, createDefaultBackground(imageStore));
		this.view = new WorldView(VIEW_ROWS, VIEW_COLS, this, world, TILE_WIDTH, TILE_HEIGHT);
		this.scheduler = new EventScheduler(timeScale);
		

		loadImages(IMAGE_LIST_FILE_NAME, imageStore, this);
		//koopa  = new Koopa("koopa", new Point(1,1), imageStore.getImageList("koopa"), 600);
		//world.addEntity(koopa);
		loadWorld(world, LOAD_FILE_NAME, imageStore);


		scheduleActions(world, scheduler, imageStore);
		
		koopas = new LinkedList<Koopa>();

		next_time = System.currentTimeMillis() + TIMER_ACTION_PERIOD;
		blitz = true;
		//this.blitzkreig();
		//world.setBackground(new Point(1,1), new Background("koopa", imageStore.getImageList("koopa")));
		
	}

	public void draw() {
		long time = System.currentTimeMillis();
		waitFrames++;
		if(waitFrames > 70 && blitz) {
			for(Koopa k:koopas) {
				k.moveToFull(world, scheduler, imageStore);
			}
			waitFrames = 0;
		}
		if (time >= next_time) {
			this.scheduler.updateOnTime(time);
			next_time = time + TIMER_ACTION_PERIOD;
		}

		view.drawViewport();
	}
	
	public void mouseClicked() {
		this.blitzkreig();
		blitz = true;
	}

	public void keyPressed() {
		if (key == CODED) {
			int dx = 0;
			int dy = 0;
			

			switch (keyCode) {
			case UP:
				dy = -1;
				break;
			case DOWN:
				dy = 1;
				break;
			case LEFT:
				dx = -1;
				break;
			case RIGHT:
				dx = 1;
//				world.removeEntityAt(new Point(1,1));
//				world.addEntity(new Koopa("koopa", new Point(1,1), imageStore.getImageList("koopa")));
				break;
			}
			view.shiftView(dx, dy);
		}
	}

	private static Background createDefaultBackground(ImageStore imageStore) {
		return new Background(DEFAULT_IMAGE_NAME, imageStore.getImageList(DEFAULT_IMAGE_NAME));
	}

	private static PImage createImageColored(int width, int height, int color) {
		PImage img = new PImage(width, height, RGB);
		img.loadPixels();
		for (int i = 0; i < img.pixels.length; i++) {
			img.pixels[i] = color;
		}
		img.updatePixels();
		return img;
	}

	private static void loadImages(String filename, ImageStore imageStore, PApplet screen) {
		try {
			Scanner in = new Scanner(new File(filename));
			imageStore.loadImages(in, screen);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void loadWorld(WorldModel world, String filename, ImageStore imageStore) {
		try {
			Scanner in = new Scanner(new File(filename));
			world.load(in, imageStore);
		} catch (FileNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	private static void scheduleActions(WorldModel world, EventScheduler scheduler, ImageStore imageStore) {
		for (Entity entity : world.getEntities()) {
			//System.out.println(entity.getId());
			if (entity.accept(new CheckIfExecutable()))
				((ExecutableEntity)entity).scheduleActions(scheduler, world, imageStore);
		}
	}

	private static void parseCommandLine(String[] args) {
		for (String arg : args) {
			switch (arg) {
			case FAST_FLAG:
				timeScale = Math.min(FAST_SCALE, timeScale);
				break;
			case FASTER_FLAG:
				timeScale = Math.min(FASTER_SCALE, timeScale);
				break;
			case FASTEST_FLAG:
				timeScale = Math.min(FASTEST_SCALE, timeScale);
				break;
			}
		}
	}

	public void blitzkreig() {
		String id = "koopa";
		for(int i = 0; i<world.getNumRows(); i++) {
			for(int j = 0; j < world.getNumCols(); j++) {
				double chance = Math.random();
				if (chance <= .3) {
					Point thisPoint = new Point(j,i);
					world.setBackground(thisPoint, new Background("fire", imageStore.getImageList("fire")));
					if(world.isOccupied(thisPoint)) {
						world.removeEntityAt(thisPoint);
						Koopa k = new Koopa("koopa"+String.valueOf(i)+String.valueOf(j), thisPoint, imageStore.getImageList("koopa"), 1);
						koopas.add(k);
						world.addEntity(k);
						world.setOccupancyCell(thisPoint, k);
						k.setPosition(thisPoint);
					}
					//change occupancy to koopa
						//remove entity at point
						//tryadd new koopa to point
						//set occupancy of point to koopa
				}
			}
		}
	}

	public static void main(String[] args) {
		parseCommandLine(args);
		PApplet.main(VirtualWorld.class);
	}

}
