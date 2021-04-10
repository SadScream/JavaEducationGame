
public class Abilities {
	Area area;
	
	public Abilities(Area area) {
		this.area = area;
	}
	
	public void build(Unit unit) {
		switch (unit.getTrend()) {
		
		case 0: {
			area.set(unit.getX(), unit.getY()+1, Area.WALL);
			break;
		}
		case 1: {
			area.set(unit.getX()-1,unit.getY()+1, Area.WALL);
			break;
		}
		case 2: {
			area.set(unit.getX()-1,unit.getY(), Area.WALL);
			break;
		}
		case 3: {
			area.set(unit.getX(),unit.getY()-1, Area.WALL);
			break;
		}
		case 4: {
			area.set(unit.getX(),unit.getY()-1, Area.WALL);
			break;
		}
		case 5: {
			area.set(unit.getX()+1,unit.getY()-1, Area.WALL);
			break;
		}
		case 6: {
			area.set(unit.getX()+1,unit.getY(), Area.WALL);
			break;
		}
		case 7: {
			area.set(unit.getX()+1,unit.getY()+1, Area.WALL);
			break;
		}

		}
	}
	
	public void destroy(Unit unit) {
		switch (unit.getTrend()) {
		
		case 0: {
			area.remove(unit.getX(),unit.getY()-1);
			break;
		}
		case 1: {
			area.remove(unit.getX()+1,unit.getY()-1);
			break;
		}
		case 2: {
			area.remove(unit.getX()+1,unit.getY());
			break;
		}
		case 3: {
			area.remove(unit.getX()+1,unit.getY()+1);
			break;
		}
		case 4: {
			area.remove(unit.getX(),unit.getY()+1);
			break;
		}
		case 5: {
			area.remove(unit.getX()-1,unit.getY()+1);
			break;
		}
		case 6: {
			area.remove(unit.getX()-1,unit.getY());
			break;
		}
		case 7: {
			area.remove(unit.getX()-1,unit.getY()-1);
			break;
		}

		}
	}

}
