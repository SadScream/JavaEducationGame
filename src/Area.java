
public class Area {
	public final int WALL = -2;
	public final int UNIT = -1;
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

	public void set(int x, int y, int status) {
		if (x>=0 && y>=0 && x < map[0].length && y<map.length)
			map[y][x] = status;
	}

	public int get(int cx, int cy) {
		return map[cy][cx];
	}
}
