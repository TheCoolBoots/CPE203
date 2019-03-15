package base;

public class CheckIfOre implements EntityVisitor<Boolean>{

	@Override
	public Boolean visit(Blacksmith smith) {
		return smith.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(MinerFull miner) {
		return miner.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(MinerNotFull miner) {
		return miner.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(Obstacle rock) {
		return rock.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(Ore ore) {
		return ore.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(OreBlob blob) {
		return blob.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(Quake quake) {
		return quake.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(Vein vein) {
		return vein.getClass().equals(Ore.class);
	}

	@Override
	public Boolean visit(Koopa koopa) {
		return koopa.getClass().equals(Blacksmith.class);
	}
}
