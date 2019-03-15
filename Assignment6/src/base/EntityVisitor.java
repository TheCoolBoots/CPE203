package base;

public interface EntityVisitor<R> {
	R visit(Blacksmith smith);
	R visit(MinerFull miner);
	R visit(MinerNotFull miner);
	R visit(Obstacle rock);
	R visit(Ore ore);
	R visit(OreBlob blob);
	R visit(Quake quake);
	R visit(Vein vein);
}
