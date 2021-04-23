
public class Hoplit extends Unit {	
	Abilities abilities;
	
	public Hoplit(Area area, String coalition, int x, int y) {
		super(area, coalition, x, y);
		abilities = new Abilities(area);
	}
	
	@Override
	public void tick(Unit[] units, int state) {
		super.tick(units, state);

		for (Unit u: units) {
			if (!u.isKilled() && getTrendMap().get( (u.getX()-getX()) + 10*(u.getY()-getY()) ) != null) {
				setFighting(true);
//				System.out.println(getX() + " " + getY());
//				int enemyTrend = (u.getX()-getX())+10*(u.getY()-getY());
//				System.out.println("Сосед имеется в направлении "+enemyTrend);
				getPath().clear();
				u.attackedFromUnit(this, 8);
			}
			else {
				u.setFighting(false);
			}
		}
	}

}
