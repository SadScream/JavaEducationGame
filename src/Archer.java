
public class Archer extends Unit {
	Abilities abilities;
	
	public Archer(Area area, String coalition, int x, int y) {
		super(area, coalition, x, y);
		abilities = new Abilities(area);
	}

	@Override
	public void tick(Unit[] units, int state) {
		super.tick(units, state);
		
		abilities.build(this);
		abilities.destroy(this);
	}
}
