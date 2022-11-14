package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class TSelect extends TShape {
	private static final long serialVersionUID = 1L;

	public TSelect() {
		this.shape = new Rectangle();
	}
	
	public TShape clone() {
		return new TSelect();
	}
	@Override
	public void draw(Graphics2D graphics2D) {
		graphics2D.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,0));
		graphics2D.setColor(Color.blue);
		graphics2D.draw(shape);
	}
	public void prepareDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		rectangle.setBounds(x,y,0,0);
	}

	
	public void keepDrawing(int x, int y) {
		Rectangle rectangle = (Rectangle)this.shape;
		rectangle.setSize(x-rectangle.x, y-rectangle.y);
	}


	
}