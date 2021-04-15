
public class Archer extends Unit {
	Abilities abilities;
	
	public Archer(Area area, int x, int y) {
		super(area, x, y);
		abilities = new Abilities(area);
	}

	@Override
	public void tick() {
		super.tick();
		
		abilities.destroy(this);
	}
}
