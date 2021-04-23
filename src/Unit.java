import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Unit {
	private Area area;
	private String coalition;
	private Unit lastHitUnit;
	
	private int x = 0, y = 0;
	private int health = 100;
	private boolean killed = false;
	private boolean fighting = false;
	
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Integer> trends = new ArrayList<Integer>();
	private HashMap<Integer, Integer> trendMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> turnMap = new HashMap<Integer, Integer>();
	
	private int state = 0; // 0-3 casual 4 fighting 5-9 dying

	public Unit(Area area, String coalition, int x, int y) {
		this.area = area;
		this.x = x;
		this.y = y;
		area.addUnit(x, y);
		
		this.coalition = coalition;
		
		for (int i = 0; i < 8; i++) {
			trends.add(i);
		}
		
		trendMap.put(-10, 0);
		trendMap.put(-9, 1);
		trendMap.put(1, 2);
		trendMap.put(11, 3);
		trendMap.put(10, 4);
		trendMap.put(9, 5);
		trendMap.put(-1, 6);
		trendMap.put(-11, 7);
		
		turnMap.put(1, -1); // направо полоборота
		turnMap.put(2, -2); // направо
		turnMap.put(3, -2); // направо + полоборота
		turnMap.put(4, 2); // кругом
		turnMap.put(5, 2); // налево + полоборота
		turnMap.put(6, 2); // налево
		turnMap.put(7, 1); // налево полоборота
	}
	
	public void stateStep() {
		if (state == 9) {
			return;
		}
		
		state += 1;
	}
	
	public boolean isFighting() {
		return fighting;
	}

	public void setFighting(boolean fighting) {
		this.fighting = fighting;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public HashMap<Integer, Integer> getTrendMap() {
		return trendMap;
	}
	
	public ArrayList<Point> getPath() {
		return path;
	}
	
	public void setKilled(boolean killed) {
		this.killed = killed;
		setState(5);
		area.removeUnit(x, y);
	}
	
	public boolean isKilled() {
		return killed;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health < 0) {
			System.out.println("Unit killed");
			setKilled(true);
			return;
		}
		else if (health > 100) {
			System.err.println("Health cannot be below 0 or higher than 100");
		}
		
		this.health = health;
	}

	public void attackedFromUnit(Unit damager, int damage) {
		lastHitUnit = damager;
		setHealth(health-damage);
	}

	public void setCoalition(String s) {
		if (UnitController.coalitions.containsKey(s)) {
			coalition = s;
		}
		else {
			System.err.println("Coalition doesn't exist");
		}
	}

	public String getCoalition() {
		return coalition;
	};
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setTarget(int tx, int ty) {
		/*
		 * устанавливает точку, в которую должен двигаться юнит
		 */
		
		if (tx >= 0 && tx < area.getCol() && ty >= 0 && ty < area.getRow()) {
			path = new WaveAlg().findPath(area.getMap(), x, y, tx, ty);
		}
	}

	public int getTrend() {
		return trends.get(0);
	}

	public void tick(Unit[] units, int state) {
		this.state = state;
		
		if (path != null && path.size() > 1) {
			Point p = path.get(1);

			int newTrend = trendMap.get((p.x-x)+10*(p.y-y)); //-10,-9,1,11,10,9,-1,11
			
			if (trends.get(0) == newTrend) { // юнит смотрит туда куда надо
				if (area.get(p.x, p.y) == -1) {
					area.removeUnit(x, y);
					x = p.x;
					y = p.y;
					area.addUnit(x, y);
					path.remove(1);
				}
			}
			else { // поворот
				int offset = 0;
				for (int i = 0; i < trends.size(); i++) {
					if (trends.get(i) == newTrend) {
						offset = i;
						break;
					}
				}

				Collections.rotate(trends, turnMap.get(offset));
			}
		}
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public Area getArea() {
		return area;
	}
}
