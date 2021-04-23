
public class Abilities {
	Area area;
	
	public Abilities(Area area) {
		this.area = area;
	}
	
	public void build(Unit unit) {
		switch (unit.getTrend()) {
		
			case 0: {
				area.addWall(unit.getX(), unit.getY()+1);
				break;
			}
			case 1: {
				area.addWall(unit.getX()-1,unit.getY()+1);
				break;
			}
			case 2: {
				area.addWall(unit.getX()-1,unit.getY());
				break;
			}
			case 3: {
				area.addWall(unit.getX(),unit.getY()-1);
				break;
			}
			case 4: {
				area.addWall(unit.getX(),unit.getY()-1);
				break;
			}
			case 5: {
				area.addWall(unit.getX()+1,unit.getY()-1);
				break;
			}
			case 6: {
				area.addWall(unit.getX()+1,unit.getY());
				break;
			}
			case 7: {
				area.addWall(unit.getX()+1,unit.getY()+1);
				break;
			}

		}
	}
	
	public void destroy(Unit unit) {
		switch (unit.getTrend()) {
		
		case 0: {
			area.removeWall(unit.getX(),unit.getY()-1);
			break;
		}
		case 1: {
			area.removeWall(unit.getX()+1,unit.getY()-1);
			break;
		}
		case 2: {
			area.removeWall(unit.getX()+1,unit.getY());
			break;
		}
		case 3: {
			area.removeWall(unit.getX()+1,unit.getY()+1);
			break;
		}
		case 4: {
			area.removeWall(unit.getX(),unit.getY()+1);
			break;
		}
		case 5: {
			area.removeWall(unit.getX()-1,unit.getY()+1);
			break;
		}
		case 6: {
			area.removeWall(unit.getX()-1,unit.getY());
			break;
		}
		case 7: {
			area.removeWall(unit.getX()-1,unit.getY()-1);
			break;
		}

		}
	}

}
