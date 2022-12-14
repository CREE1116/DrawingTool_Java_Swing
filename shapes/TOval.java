package shapes;

import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Float;

public class TOval extends TShape {
	private static final long serialVersionUID = 1L;
	
	public TOval() {
		this.shape = new Ellipse2D.Float();
	}
	
	public TShape clone() {
		return new TOval();
	}
	
	public void prepareDrawing(int x, int y) {
		Ellipse2D.Float ellipse = (Float)this.shape;
		ellipse.setFrame (x,y,0,0);
	}

	
	public void keepDrawing(int x, int y) {
		Ellipse2D.Float ellipse = (Float)this.shape;
		ellipse.width = (float) (x-ellipse.getX());
		ellipse.height = (float) (y - ellipse.getY());
	}



}