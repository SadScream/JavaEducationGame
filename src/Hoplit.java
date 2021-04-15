
public class Hoplit extends Unit {	
	Abilities abilities;
	
	public Hoplit(Area area, int x, int y) {
		super(area, x, y);
		abilities = new Abilities(area);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		abilities.build(this);
	}

}
