import java.awt.Color;
import java.awt.Graphics;

public class Field {
	private Area area;
	private int col, row;
	public final int left = 100, top = 100;
	public final int width = 50, height = 50;
	
	public Field(Area area) {
		this.area = area;
		col = this.area.getCol();
		row = this.area.getRow();
	}
	
	public Field() {
	}
	
	public void render(Graphics g) {
		/*
		 * отрисовывает поле
		 */
		
		g.setColor(Color.BLUE);
		
		for (int i = 0; i < row+1; i++) {
			g.drawLine(left, i*height+top, 
					col*width+left, i*height+top);
		}
	
		g.setColor(Color.RED);
		
		for (int i = 0; i < col+1; i++) {
			g.drawLine(i*width+left, top, 
					i*width+left, row*height+top);
		}
	}
}
