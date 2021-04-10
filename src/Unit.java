import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Unit {
	private Area area;
	private int coalition = -1;
	private int x = 0, y = 0;
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Integer> trends = new ArrayList<Integer>();
	private HashMap<Integer, Integer> trendMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> turnMap = new HashMap<Integer, Integer>();
	
	private int state = 0; // 0-3

	public Unit(Area area, int x, int y) {
		this.area = area;
		this.x = x;
		this.y = y;
		area.set(x, y, -5);
		
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
	
	public void setCoalition(int x) {
		coalition = x;
	}

	public int getCoalition() {
		return coalition;
	};
	
	public int getX() {
		// TODO Auto-generated method stub
		return x;
	}

	public int getY() {
		// TODO Auto-generated method stub
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

	public void tick() {
		if (path != null && path.size() > 1) {
			Point p = path.get(1);

			int newTrend = trendMap.get((p.x-x)+10*(p.y-y)); //-10,-9,1,11,10,9,-1,11
			
			if (trends.get(0) == newTrend) { // юнит смотрит туда куда надо
				if (area.get(p.x, p.y) == -1) {
					area.set(x, y, -1);
					x = p.x;
					y = p.y;
					area.set(x, y, -5);
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
