import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WaveAlg {
	public static void main(String[] args) {
		int[][] map = new int[][] {
			{-1, -1, -1, -1, -1, -1},
			{-1, -5, -1, -1, -1, -1},
			{-1, -5, -1, -1, -1, -1},
			{-1, -5, -1, -1, -1, -1},
			{-1, -1, -1, -5, -1, -1},
			{-1, -1, -1, -5, -1, -1}
		};
		WaveAlg alg = new WaveAlg();
		ArrayList<Point> wave = alg.findPath(map, 0, 2, 5, 5);
	}
	public ArrayList<Point> findPath(int[][] map, int x, int y, int tx, int ty) {
//		if (map[y][x]==-2 || map[ty][tx] == -2) {
//			System.out.println("указана зан€та€ кубиком клетка");
//			return null;
//		}
		ArrayList<Point> wave = new ArrayList<Point>();
		
		int cloneMap[][] = clone(map);
		cloneMap[ty][tx] = -1; // чтобы можно было нацелевать на клетку, зан€тую юнитом
		List<Point> oldWave = new ArrayList<Point>();
		oldWave.add(new Point(x, y));
		
		int nstep = 0;
		cloneMap[y][x] = nstep;
		
		//int[] dx = {0,1,0,-1};
		//int[] dy = {-1,0,1,0};
		int[] dx = {0,1,0,-1,-1,-1,1,1};
		int[] dy = {-1,0,1,0,-1,1,-1,1};
		int neighbourNum = dx.length;
		
		while(oldWave.size() > 0) {
			nstep++;
			wave.clear();
			
			for(Point p: oldWave) {
				for(int d = 0; d < neighbourNum; d++) {
					x = p.x + dx[d];
					y = p.y + dy[d];
					
					if (x >= 0 && x < cloneMap[0].length && y>=0 && y <cloneMap.length) {
						if (cloneMap[y][x] == -1) {
							cloneMap[y][x] = nstep;
							wave.add(new Point(x, y));
						}
					}
				}
			}
			oldWave = new ArrayList<Point>(wave);
		}
//		print(cloneMap);
		
		boolean flag = true;
		wave.clear();
		wave.add(new Point(tx, ty));
		
		while(cloneMap[ty][tx] != 0) {
			flag = true;
			
			for(int d = 0; d < neighbourNum; d++) {
				x = tx + dx[d];
				y = ty + dy[d];
				
				if (x >= 0 && x < cloneMap[0].length && y>=0 && y <cloneMap.length) {
					if (cloneMap[ty][tx]-1 == cloneMap[y][x]) {
						tx = x;
						ty = y;
						flag = false;
						wave.add(new Point(tx, ty));
						break;
					}
				}
			}
			if (flag == true) {
				System.out.println("ѕуть не найден");
				break;
			}
		}
		Collections.reverse(wave);
		
//		waveOut(wave);
		for (Point p: wave) {
			cloneMap[p.y][p.x] = 0;
		}
//		print(cloneMap);
		return wave;
	}
	
	private void waveOut(ArrayList<Point> wave) {
		for (Point p: wave) {
			System.out.println("x="+p.x+", y="+p.y);
		}
	}

	private void print(int[][] cloneMap) {
		for (int n = 0; n < cloneMap.length; n++) {
			for (int i = 0; i < cloneMap[0].length; i++) {
				System.out.print(cloneMap[n][i]+",");
			}
			System.out.println();
		}
	}
	private int[][] clone(int[][] map) {
		int[][] cloneMap = new int[map.length][map[0].length];
		
		for (int n = 0; n < map.length; n++) {
			for (int i = 0; i < map[0].length; i++) {
				cloneMap[n][i] = map[n][i];
			}
		}
		
		return cloneMap;
	}
}
