
public class Hoplit extends Unit {	
	Abilities abilities;
	
	public Hoplit(Area area, String coalition, int x, int y) {
		super(area, coalition, x, y);
		abilities = new Abilities(area);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		abilities.build(this);
	}

}
