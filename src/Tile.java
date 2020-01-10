
import java.awt.*;

/**
 * A basic game object starting in the upper left corner of the game court. It is displayed as a
 * square of a specified color.
 */
public class Tile {
	//the length/width of the tile(square)
	//public static final int SIZE = 100;

	private Color color;
	private int value;
	private int size;
	//the left corner coordinate of each tile
	private int px;
	private int py;
	/**
	 * Note that, because we don't need to do anything special when constructing a Square, we simply
	 * use the superclass constructor called with the correct parameters.
	 */
	public Tile(int px, int py, int value) {
		this.value = value;
		this.color = getColor(value);
		this.px = px;
		this.py = py;
		this.size = 90;
	}

	public int getPx() {
		return px;
	}

	public int getPy() {
		return py;
	}

	public void changeSize(int s) {
		size = s;
	}

	public void draw(Graphics g) {
		g.setColor(this.color);
		g.fillRect(this.getPx(), this.getPy(), size, size);
		g.setColor(Color.black);
		//draw string on non zero tile
		if(value!=0) {
			g.setFont(g.getFont().deriveFont(20f));
			g.drawString(Integer.toString(value), px+35, py+55);
		}
	}

	//set the value of the tile
	public void setV(int v) {
		value = v;
		//color is changed with the value
		color = getColor(v);
	}
	//get value of the tile
	public int getV() {
		return value;
	}

	public Color getColor (int v) {
		if(v==0) {
			return new Color(244,244,244);
		} else if(v==2) {
			return new Color(255,205,205);
		} else if(v==4) {
			return new Color(255,185,185);
		} else if(v==8) {
			return new Color(255,165,165);
		} else if(v==16) {
			return new Color(255,145,145);
		} else if(v==32) {
			return new Color(255,125,125);
		} else if(v==64) {
			return new Color(255,105,105);
		} else if(v==128) {
			return new Color(255,85,85);
		} else if(v==256) {
			return new Color(255,65,65);
		} else if(v==512) {
			return new Color(255,45,45);
		} else {
			return new Color(255,25,25);
		}	
	}

}