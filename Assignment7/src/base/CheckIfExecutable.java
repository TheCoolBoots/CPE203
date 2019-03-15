package base;

public class CheckIfExecutable implements EntityVisitor<Boolean>{

	@Override
	public Boolean visit(Blacksmith smith) {
		return false;
	}

	@Override
	public Boolean visit(MinerFull miner) {
		return true;
	}

	@Override
	public Boolean visit(MinerNotFull miner) {
		return true;
	}

	@Override
	public Boolean visit(Obstacle rock) {
		return false;
	}

	@Override
	public Boolean visit(Ore ore) {
		return true;
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
		return true;
	}

	@Override
	public Boolean visit(Koopa koopa) {
		return koopa.getClass().equals(Blacksmith.class);
	}
}
