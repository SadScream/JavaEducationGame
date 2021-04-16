import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class Unit {
	private Area area;
	private String coalition;
	
	private int x = 0, y = 0;
	private int health = 100;
	
	private ArrayList<Point> path = new ArrayList<Point>();
	private ArrayList<Integer> trends = new ArrayList<Integer>();
	private HashMap<Integer, Integer> trendMap = new HashMap<Integer, Integer>();
	private HashMap<Integer, Integer> turnMap = new HashMap<Integer, Integer>();
	
	private int state = 0; // 0-3

	public Unit(Area area, String coalition, int x, int y) {
		this.area = area;
		this.x = x;
		this.y = y;
		area.set(x, y, -5);
		
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
		
		turnMap.put(1, -1); // ������� ����������
		turnMap.put(2, -2); // �������
		turnMap.put(3, -2); // ������� + ����������
		turnMap.put(4, 2); // ������
		turnMap.put(5, 2); // ������ + ����������
		turnMap.put(6, 2); // ������
		turnMap.put(7, 1); // ������ ����������
	}
	
	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		if (health < 0 || health > 100) {
			System.err.println("Health cannot be below 0 or higher than 100");
			return;
		}
		
		this.health = health;
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
		 * ������������� �����, � ������� ������ ��������� ����
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
			
			if (trends.get(0) == newTrend) { // ���� ������� ���� ���� ����
				if (area.get(p.x, p.y) == -1) {
					area.set(x, y, -1);
					x = p.x;
					y = p.y;
					area.set(x, y, -5);
					path.remove(1);
				}
			}
			else { // �������
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
