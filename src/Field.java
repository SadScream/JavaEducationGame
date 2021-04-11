import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class Field {
	private Area area;
	private int col, row;
	public final int left = 100, top = 100;
	public final int width = 50, height = 50;
	
	private boolean makingSelection = false; // нужно ли в данный момент отрисовывать выделение
	int selectionStartX, selectionStartY, selectionWidth, selectionHeight; // параметры rectAngle для выделения
	private int startX = -1, startY = -1,
				currentX = 0, currentY = 0;
	
	Color selectionColor;

	public Field(Area area) {
		this.area = area;
		col = this.area.getCol();
		row = this.area.getRow();
		selectionColor = new Color(18,211,0);
	}
	
	public Field() {
		selectionColor = new Color(18,211,0);
	}
	
	public void render(Graphics gl) {
		/*
		 * отрисовывает поле
		 */
		
		Graphics2D g = (Graphics2D)gl;
		
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
		
		// рисуем выделение
		if (makingSelection) {
			g.setColor(selectionColor);
			
			if (currentX >= startX) {
				if (currentY >= startY) {
					selectionStartX = startX;
					selectionStartY = startY;
					selectionWidth = currentX-startX;
					selectionHeight = currentY-startY;
				}
				else {
					selectionStartX = startX;
					selectionStartY = currentY;
					selectionWidth = currentX-startX;
					selectionHeight = startY-currentY;
				}
			}
			else {
				if (currentY >= startY) {
					selectionStartX = currentX;
					selectionStartY = startY;
					selectionWidth = startX-currentX;
					selectionHeight = currentY-startY;
				}
				else {
					selectionStartX = currentX;
					selectionStartY = currentY;
					selectionWidth = startX-currentX;
					selectionHeight = startY-currentY;
				}
			}
			g.setStroke(new BasicStroke(2f));
			g.drawRect(selectionStartX, selectionStartY, selectionWidth, selectionHeight);
		}
	}
	
	public boolean hasSelection() {
		return makingSelection;
	}
	
	public int getSelectionStartX() {
		return selectionStartX;
	}
	
	public int getSelectionStartY() {
		return selectionStartY;
	}
	
	public int getSelectionEndX() {
		return selectionStartX+selectionWidth;
	}
	
	public int getSelectionEndY() {
		return selectionStartY+selectionHeight;
	}

	public void mousePressed(MouseEvent e) {
		// зажали ЛКМ
		
		if (e.getButton() != MouseEvent.BUTTON1)
			return;

		startX = currentX = e.getX();
		startY = currentY = e.getY();
	}

	public void mouseReleased(MouseEvent e) {
		// отжали ЛКМ
		
		if (e.getButton() != MouseEvent.BUTTON1)
			return;
		
		makingSelection = false;
		startX = startY = -1;
	}

	public void mouseMoved(MouseEvent e) {
		// функция вызывается когда мы зажали клавишу мыши и двигаем ей
		
		if (startX == -1 && startY == -1) {
			// startX и startY перестают быть равны -1 только когда сработало mousePressed
			// если это так, значит нажата ЛКМ
			
			return;
		}
		
		currentX = e.getX();
		currentY = e.getY();
		
		if (Math.abs(currentX-startX) > 3 || Math.abs(currentY-startY) > 3) {
			// для разрешения создания выделения нужно чтобы мышка сместилась хотя бы
			// на 3 пикселя в любую сторону. Это нужно чтобы исключить ситуацию
			// когда пользователь имел цель просто кликнуть мышкой и немного сдвинул курсор
			
			makingSelection = true;
		}
	}
}
