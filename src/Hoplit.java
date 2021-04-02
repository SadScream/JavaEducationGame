
public class Hoplit extends Unit {	
	public Hoplit(Area area, int x, int y) {
		super(area, x, y);
	}
	
	@Override
	public void tick() {
		super.tick();
		
		switch (getTrend()) {
		
		case 0: {
			getArea().set(getX(), getY()+1, -2);
			break;
		}
		case 1: {
			getArea().set(getX()-1, getY()+1, -2);
			break;
		}
		case 2: {
			getArea().set(getX()-1, getY(), -2);
			break;
		}
		case 3: {
			getArea().set(getX(), getY()-1, -2);
			break;
		}
		case 4: {
			getArea().set(getX(), getY()-1, -2);
			break;
		}
		case 5: {
			getArea().set(getX()+1, getY()-1, -2);
			break;
		}
		case 6: {
			getArea().set(getX()+1, getY(), -2);
			break;
		}
		case 7: {
			getArea().set(getX()+1, getY()+1, -2);
			break;
		}

	}
	}

}
