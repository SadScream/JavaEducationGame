import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class UnitController {
	public static HashMap<String, Color> coalitions = new HashMap<String, Color>();
	
	private SpriteSheet sheet = new SpriteSheet();
	private Field field;
	private Unit[] units = new Unit[5];
	
	private boolean[] selected = new boolean[units.length];
	private boolean ControlPressed = false; // зажата ли клавиша CTRL
	private boolean makingSelection = false; // нужно ли в данный момент отрисовывать выделение
	
	private int selectionWidth, selectionHeight; // параметры ширины и высоты выделения rectAngle
	private int anim = 0;
	
	private Point selectionStart = new Point(0, 0), // позиция левой верхней вершины прямоугольника(выделения)
		mouseStart = new Point(-1, -1),
		mouseCurrent = new Point(0, 0);
	
	private Color selectionColor;
	private Font font;
	
	public UnitController(Area area, Field field) {
		coalitions.put("Alliance", new Color(0,168,119));
		coalitions.put("Horde", new Color(255,8,0));
		coalitions.put("Undead", new Color(0,0,15));
		
		font = new Font("Calibri", Font.PLAIN, 11);
		
		this.field = field;
		for (int i = 0; i < 2; i++) {
			units[i] = new Hoplit(area, "Horde", i, i*2);
		}
		
		units[2] = new Hoplit(area, "Alliance", 2, 4);
		
		for (int i = 3; i < units.length; i++) {
			units[i] = new Archer(area, "Undead", i, i*2);
		}
		
		selectionColor = new Color(18,211,0);
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
	
	public void mousePressed(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		
		mouseStart.set(e.getX(), e.getY());
		mouseCurrent.set(e.getX(), e.getY());
	}
	
	public void mouseReleased(MouseEvent e) {
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		
		mouseStart.set(-1, -1);
		stopSelection();
	}
	
	public void mouseMoved(MouseEvent e) {		
		if (mouseStart.getX() == -1 && mouseStart.getY() == -1) {
			// start.x и start.y перестают быть равны -1 только когда сработало mousePressed
			// если это так, значит нажата ЛКМ
			
			return;
		}
		
		mouseCurrent.set(e.getX(), e.getY());
		
		if (Math.abs(mouseCurrent.getX()-mouseStart.getX()) > 3 || Math.abs(mouseCurrent.getY()-mouseStart.getY()) > 3) {
			// для разрешения создания выделения нужно чтобы мышка сместилась хотя бы
			// на 3 пикселя в любую сторону. Это нужно чтобы исключить ситуацию
			// когда пользователь имел цель просто кликнуть мышкой и немного сдвинул курсор
			
			startSelection();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == 17) {
			ControlPressed = false;
		}
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == 17) {
			ControlPressed = true;
		}
	}

	public void render(Graphics gl) {
		/*
		 * отрисовывает каждого юнита
		 */
		
		Graphics2D g = (Graphics2D)gl;
		g.setFont(font);
		
		drawSelection(g);
		
		for (int i = 0; i < units.length; i++) {			
			int x = units[i].getX()*field.width+field.left;
			int y = units[i].getY()*field.height+field.top;
			
			if (units[i].isKilled()) {
				drawDeathAnimation(g, x, y, units[i]);
				continue;
			}
			
			drawUnit(g, x, y, units[i]);
			
			if (makingSelection) { // пытается ли пользователь сейчас выделить область
				// если юнит входит в область выделения, то добавляем его
				if (x+25 >= selectionStart.getX() && 
						x+25 <= getSelectionEndX() &&
						y+25 >= selectionStart.getY() &&
						y+25 <= getSelectionEndY()) {
					selected[i] = true;
				}
				// иначе если не нажата клавиша ctrl, то снимаем с него выделение
				// если нажата, то он остается выделенным
				else {
					if (!ControlPressed) {
						selected[i] = false;
					}
				}
			}
			
			if (selected[i]) {
				g.setStroke(new BasicStroke(2f));
				g.setColor(coalitions.get(units[i].getCoalition()));
				g.drawOval(x, y, field.width, field.height);
			}
		}
	}
	
	private void drawDeathAnimation(Graphics2D g, int x, int y, Unit unit) {
		BufferedImage img;
		
		unit.stateStep();
		
		if (unit.getState() == 9)
			return;
		else if (unit.getState() == 5)
			img = sheet.grabSprite(unit.getState(), unit.getTrend());
		else
			img = sheet.grabSprite(5, unit.getState()-5);
		
		g.drawImage(img, x+4, y+7,
				field.width-8, field.height-8, null);
	}

	private void drawUnit(Graphics2D g, int x, int y, Unit unit) {
		BufferedImage img;
		
		if (!unit.isFighting()) {
			img = sheet.grabSprite(anim, unit.getTrend());
		}
		else {
			img = sheet.grabSprite(4, unit.getTrend());
		}
		
		float healthBarWidth = 40f;
		
		g.drawImage(img, x+4, y+7,
				field.width-8, field.height-8, null);
		
		g.setColor(new Color(0,168,119));
		g.drawRect(x, y, (int)healthBarWidth, 5);
		g.setColor(Color.RED);
		g.fillRect(x+1, y+1, (int) ((healthBarWidth-1)*(unit.getHealth()/100.0)), 4);
		
		g.setStroke(new BasicStroke(1f));
		g.setColor(Color.blue);
		g.drawString(unit.getClass().getSimpleName(), x+1, y+field.height);
	}
	
	private void drawSelection(Graphics2D g) {
		// рисуем выделение исходя из текущих координат мышки
		// и координат точки, из которой начали выделение
		
		if (makingSelection) {
			g.setColor(selectionColor);
			
			if (mouseCurrent.getX() >= mouseStart.getX()) {
				if (mouseCurrent.getY() >= mouseStart.getY()) {
					selectionStart.set(mouseStart);
					selectionWidth = mouseCurrent.getX()-mouseStart.getX();
					selectionHeight = mouseCurrent.getY()-mouseStart.getY();
				}
				else {
					selectionStart.set(mouseStart.getX(), mouseCurrent.getY());
					selectionWidth = mouseCurrent.getX() - mouseStart.getX();
					selectionHeight = mouseStart.getY() - mouseCurrent.getY();
				}
			}
			else {
				if (mouseCurrent.getY() >= mouseStart.getY()) {
					selectionStart.set(mouseCurrent.getX(), mouseStart.getY());
					selectionWidth = mouseStart.getX()-mouseCurrent.getX();
					selectionHeight = mouseCurrent.getY()-mouseStart.getY();
				}
				else {
					selectionStart.set(mouseCurrent);
					selectionWidth = mouseStart.getX()-mouseCurrent.getX();
					selectionHeight = mouseStart.getY()-mouseCurrent.getY();
				}
			}
			g.setStroke(new BasicStroke(1f));
			g.drawRect(selectionStart.getX(), selectionStart.getY(), selectionWidth, selectionHeight);
		}
	}
	
	private int getSelectionEndX() {
		return selectionStart.getX()+selectionWidth;
	}
	
	private int getSelectionEndY() {
		return selectionStart.getY()+selectionHeight;
	}
	
	public void stopSelection() {
		makingSelection = false;
	}
	
	public void startSelection() {
		makingSelection = true;
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
			if (!u.isKilled())
				u.tick(units, anim);
		}
	}
}
