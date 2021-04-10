import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

@SuppressWarnings("serial")
public class Game extends JFrame implements Runnable {
	private Area area = new Area();
	private Field field = new Field(area);
	private UnitController unitController = new UnitController(area, field);
	private boolean flag = true;
	
	public Game() {
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// реагирует на событие клика мышки по окну
				
				unitController.mouseClicked(e);
				repaint();
			}
		});
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				flag = false;
			}
		});
	}
	
	public static void main(String[] args) {
		Game frame = new Game();
		
		frame.setBounds(100, 5, 1080, 720);
		frame.setDefaultCloseOperation(2);
		frame.setVisible(true);
		
		Thread t = new Thread(frame);
		t.start(); // запускаем отдельный поток с функцией run
	}

	@Override
	public void paint(Graphics g) {
		/*
		 * метод, перерисовывающий поле
		 * вызывается встроенным в JFrame методом repaint()
		 */
		
		this.createBufferStrategy(2);
		BufferStrategy bs = this.getBufferStrategy();
		g = bs.getDrawGraphics();
		super.paint(g);
		
		Wall wall = new Wall(area, field);
		wall.render(g);
		field.render(g);
		unitController.render(g);
		
		bs.show();
	}

	@Override
	public void run() {
		/*
		 * вечный цикл
		 * раз в 500 милисекунд вызывает метод tick в
		 * unitController и перерисовывает поле
		 */
		
		while (flag) {
			unitController.tick();
			repaint();
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
