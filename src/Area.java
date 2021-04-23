
public class Area {
	public static final int WALL = -2;
	public static final int FREE = -1;
	public static final int UNIT = -5;
	private int col = 13, row = 10;
	
	private int[][] map = new int[row][col];
	// хранит в виде массива текущее состояние игрового поля
	
	public Area() {
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				map[i][j] = -1;
			}
		}
		map[3][4] = -2;
		map[3][5] = -2;
		map[7][6] = -2;
		map[6][6] = -2;
		map[2][2] = -2;
//		print();
	}
	
	public int[][] getMap() {
		return map;
	}

	public void setMap(int[][] map) {
		this.map = map;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public void print() {
		/*
		 * печатает в консоль содержимое map
		 */
		
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				System.out.print(map[i][j]+" ");
			}
			System.out.println();
		}
	}
	
	public boolean coordinatesAreCorrect(int x, int y) {
		if (x>=0 && y>=0 && x < map[0].length && y < map.length)
			return true;
		
		return false;
	}

	private void set(int x, int y, int status) {
		if (coordinatesAreCorrect(x, y))
			map[y][x] = status;
	}

	public void addWall(int x, int y) {
		if (coordinatesAreCorrect(x, y) && get(x,y) == FREE) {
			set(x, y, WALL);
		}
	}
	
	public void addUnit(int x, int y) {
		if (coordinatesAreCorrect(x, y) && get(x,y) == FREE) {
			set(x, y, UNIT);
		}
	}

	public void removeWall(int x, int y) {
		if (coordinatesAreCorrect(x, y) && get(x,y) == WALL) {
			set(x, y, FREE);
		}
	}
	
	public void removeUnit(int x, int y) {
		if (coordinatesAreCorrect(x, y) && get(x,y) == UNIT) {
			set(x, y, FREE);
		}
	}
	
	public int get(int cx, int cy) {
		return map[cy][cx];
	}
	
}
