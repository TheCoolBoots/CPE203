package base;

public class AllFalseEntityVisitor implements EntityVisitor{

	@Override
	public Object visit(Blacksmith smith) {
		return false;
	}

	@Override
	public Object visit(MinerFull miner) {
		return false;
	}

	@Override
	public Object visit(MinerNotFull miner) {
		return false;
	}

	@Override
	public Object visit(Obstacle rock) {
		return false;
	}

	@Override
	public Object visit(Ore ore) {
		return false;
	}

	@Override
	public Object visit(OreBlob blob) {
		return null;
	}

	@Override
	public Object visit(Quake quake) {
		return null;
	}

	@Override
	public Object visit(Vein vein) {
		return null;
	}

}
