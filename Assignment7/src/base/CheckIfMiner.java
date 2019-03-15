package base;

public class CheckIfMiner implements EntityVisitor<Boolean>{

	@Override
	public Boolean visit(Blacksmith smith) {
		return false;
	}

	@Override
	public Boolean visit(MinerFull miner) {
		return miner.getClass().equals(MinerFull.class);
	}

	@Override
	public Boolean visit(MinerNotFull miner) {
		return miner.getClass().equals(MinerNotFull.class);
	}

	@Override
	public Boolean visit(Obstacle rock) {
		return false;
	}

	@Override
	public Boolean visit(Ore ore) {
		return false;
	}

	@Override
	public Boolean visit(OreBlob blob) {
		return false;
	}

	@Override
	public Boolean visit(Quake quake) {
		return false;
	}

	@Override
	public Boolean visit(Vein vein) {
		return false;
	}

	@Override
	public Boolean visit(Koopa koopa) {
		return false;
	}

}
