package base;

public class CheckIfBlacksmith implements EntityVisitor<Boolean>{

	@Override
	public Boolean visit(Blacksmith smith) {
		return smith.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(MinerFull miner) {
		return miner.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(MinerNotFull miner) {
		return miner.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(Obstacle rock) {
		return rock.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(Ore ore) {
		return ore.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(OreBlob blob) {
		return blob.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(Quake quake) {
		return quake.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(Vein vein) {
		return vein.getClass().equals(Blacksmith.class);
	}

	@Override
	public Boolean visit(Koopa koopa) {
		return koopa.getClass().equals(Blacksmith.class);
	}

}
