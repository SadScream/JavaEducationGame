import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class UnitController {
	private SpriteSheet sheet = new SpriteSheet();
	private Field field;
	private Unit[] units = new Unit[5];
	private boolean[] selected = new boolean[units.length];
	int anim = 0;
	
	public UnitController(Area area, Field field) {
		this.field = field;
		for (int i = 0; i < 3; i++) {
			units[i] = new Hoplit(area, i, i*2);
		}
		for (int i = 3; i < units.length; i++) {
			units[i] = new Archer(area, i, i*2);
		}
	}

	public void mouseClicked(MouseEvent e) {
		/*
		 * обрабатывает события:
		 * нажатия мыши по юниту,
		 * либо нажатие внутри игрового поля
		 */
		
		if (e.getX()<field.left || e.getY()<field.top) {
			return;
		}
		
		int sx = (e.getX()-field.left)/field.width;
		int sy = (e.getY()-field.top)/field.height;
		
		if (e.getButton() == 1) { // нажата левая кнопка мыши
			for (int i = 0; i < units.length; i++) {
				if (units[i].getX() == sx && units[i].getY() == sy) {
					selected[i] = !selected[i];
				}
			}
		}
		else if (e.getButton() == 3) { // нажата правая кнопка
			for (int i = 0; i < units.length; i++) {
				if (selected[i]==true) {
					units[i].setTarget(sx, sy);
				}
			}
		}
	}

	public void render(Graphics gl) {
		/*
		 * отрисовывает каждого юнита
		 */
		
		Graphics2D g = (Graphics2D)gl;
		
		for (int i = 0; i < units.length; i++) {			
			BufferedImage img = sheet.grabSprite(anim, units[i].getTrend());
			
			int x = units[i].getX()*field.width+field.left;
			int y = units[i].getY()*field.height+field.top;
			
			if (field.hasSelection()) { // пытается ли пользователь сейчас выделить область
				// проверяем, какие юниты попадают внутрь выделения
				
				int tempX1, tempY1, tempX2, tempY2;
				tempX1 = field.getSelectionStartX();
				tempY1 = field.getSelectionStartY();
				tempX2 = field.getSelectionEndX();
				tempY2 = field.getSelectionEndY();
				
				// тем, кто попадает в выделение ставим selected = true, а всем остальным - false
				if (x+25 >= tempX1 && x+25 <= tempX2 && y+25 >= tempY1 && y+25 <= tempY2) {
					selected[i] = true;
				}
				else {
					selected[i] = false;
				}
			}
			
			g.drawImage(img, x+4, y+4,
						field.width-8, field.height-8, null);
			
			if (selected[i]) {
				int coalition = units[i].getCoalition();
				Color temp_color = new Color(
								60*coalition, 
								95*coalition, 
								130*coalition);
				g.setStroke(new BasicStroke(2f));
				g.setColor(temp_color);
				g.drawOval(x, y, field.width, field.height);
			}
			
			g.setColor(Color.blue);
			
			if (units[i].getClass().getSimpleName()=="Hoplit") {
				g.drawString("Hoplit", x+1, y+field.height);
			}else if (units[i].getClass().getSimpleName()=="Archer") {
				g.drawString("Archer", x+1, y+field.height);
			}
		}
	}

	public void tick() {
		/*
		 * у каждого юнита вызывает метод tick
		 */
		
		if (anim<3) {
			anim++;
		}
		else {
			anim=0;
		}
		
		for (Unit u: units) {
			u.tick();
		}
	}
}
