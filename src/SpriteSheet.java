import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

@SuppressWarnings("serial")
public class SpriteSheet extends JFrame {
	ArrayList<BufferedImage> frames = new ArrayList<BufferedImage>();
	int sprNumber = 0;
	
	public static void main(String[] args) {
		SpriteSheet sheet = new SpriteSheet();
		sheet.setBounds(0, 0, 1200, 660);
		sheet.setDefaultCloseOperation(2);
		sheet.setVisible(true);
	}
	
	public SpriteSheet() {
		super();
		BufferedImage img;
		
		try {
			img = ImageIO.read(new File("dragon.png"));
			int w = 90, h = 85, x = 0, y = 0;
			
			for (int i = 0; i < 6; i++) {
				x = 0;
				
				if (i > 0 && i < 3) {
					y += h; h = 83;
				} else if (i == 3) {
					y += h; h = 79;
				} else if (i == 4) {
					y += h; h = 83;
				} else if (i == 5) {
					y += h; h = 89;
				}
				
				for (int j = 0; j < 5; j++) {
					if (j == 1) {
						x += w; w = 90;
					} else if (j == 2) {
						x += w; w = 94;
					} else if (j == 3) {
						x += w; w = 86;
					} else if (j == 4) {
						x += w; w = 90;
					}
					
					BufferedImage dragon = img.getSubimage(x, y, w, h);
					frames.add(dragon);
				}
			}
			sprNumber = frames.size();
			for (int i = 0; i < sprNumber / 5; i++) {
				for (int n = 3; n > 0; n--) {
					int index = i*(sprNumber/6)+n;
					BufferedImage tempBi = new BufferedImage(frames.get(index).getWidth(), frames.get(index).getHeight(), 
															BufferedImage.TYPE_INT_ARGB);
					Graphics g = tempBi.getGraphics();
					g.drawImage(frames.get(index), 0+tempBi.getWidth(), 0, -tempBi.getWidth(), tempBi.getHeight(), null);
					frames.add(tempBi);
				}
			}
			sprNumber = frames.size();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		for (int i = 0; i < frames.size(); i++) {
			g.drawImage(frames.get(i), i*25+10, 50, 25, 25, null);
		}
	}
	
	public BufferedImage grabSprite(int row, int col) {
		int index  = 0;
		
		if (col >= 5) {
			index = 30+row*3+(col-5);
		}
		else {
			int sprRow = sprNumber / 9;
			
			index = row*sprRow + col;
		}
		
		return frames.get(index);
	}
}
