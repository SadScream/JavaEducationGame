import java.awt.Color;

public class Coalition {
	private int id;
	private String name;
	private Color color;
	
	public Coalition(int id, String name, Color color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public Color getColor() {
		return color;
	}
}
