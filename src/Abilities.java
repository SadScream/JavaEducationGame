
public class Abilities {
	Area area;
	
	public Abilities(Area area) {
		this.area = area;
	}
	
	public void build(Unit unit) {
		switch (unit.getTrend()) {
		
			case 0: {
				area.build(unit.getX(), unit.getY()+1);
				break;
			}
			case 1: {
				area.build(unit.getX()-1,unit.getY()+1);
				break;
			}
			case 2: {
				area.build(unit.getX()-1,unit.getY());
				break;
			}
			case 3: {
				area.build(unit.getX(),unit.getY()-1);
				break;
			}
			case 4: {
				area.build(unit.getX(),unit.getY()-1);
				break;
			}
			case 5: {
				area.build(unit.getX()+1,unit.getY()-1);
				break;
			}
			case 6: {
				area.build(unit.getX()+1,unit.getY());
				break;
			}
			case 7: {
				area.build(unit.getX()+1,unit.getY()+1);
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
