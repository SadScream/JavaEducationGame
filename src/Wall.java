import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Wall {
	private Field field;
	private Area area;
	private int width, height;
	private int left, top;
	
	public Wall(Area area, Field field) {
		this.field = field;
		this.area = area;
		width = this.field.width*3/4;
		height = this.field.height*3/4;
		left = this.field.left+field.width/8;
		top = this.field.top+field.height/8;
	}
	
	public void render(Graphics g) {
		/*
		 * отрисовывает стены
		 */

		Image img = null;
		try {
			img = ImageIO.read(new File("wall.jpg"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	
		g.setColor(new Color(120, 50, 50));
		
		for (int n = 0; n < area.getRow(); n++) {
			for (int i = 0; i < area.getCol(); i++) {
				if (area.getMap()[n][i] == -2) {
					g.drawImage(img, i*field.width+left, 
							n*field.height+top,
							width,
							height,
							null);
				}
			}
		}
	}
}
