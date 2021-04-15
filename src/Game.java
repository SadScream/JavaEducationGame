import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
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
		this.addMouseMotionListener(new MouseMotionListener() {
			
			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				// функция вызывается когда мы зажали клавишу мыши и двигаем ей
				
				unitController.mouseMoved(e);
			}
		});
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// нажата клавиша алфавита
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// клавиша отпушена
				
				unitController.keyReleased(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// клавиша нажата
				
				unitController.keyPressed(e);
			}
			
		});
		
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// реагирует на событие клика мышки по окну
				
				unitController.mouseClicked(e);
				repaint();
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// зажатие кнопки
				
				super.mousePressed(e);
				unitController.mousePressed(e);
				repaint();
			}
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// отжатие кнопки
				
				super.mouseReleased(e);
				unitController.mouseReleased(e);
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
		
		frame.setBounds(100, 5, 850, 700);
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
		int time = 30, slept = 0;
		
		while (flag) {
			if (slept == time*6) {
				unitController.tick();
				slept = 0;
			}
			repaint();
			try {
				Thread.sleep(time);
				slept += time;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
