package shapes;

import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.awt.geom.Line2D.Float;

import shapes.TAnchors.EAnchors;


public class TLine extends TShape {
	private static final long serialVersionUID = 1L;
	
	public TLine() {
		this.shape = new Line2D.Double();
	}
	
	public TShape clone() {
		return new TLine();
	}
	
	public void prepareDrawing(int x, int y) {
		Line2D.Double line = (java.awt.geom.Line2D.Double) this.shape;
		line.setLine(x,y,x,y);
	}
	
	
	public void keepDrawing(int x, int y) {
		Line2D.Double line = (java.awt.geom.Line2D.Double)this.shape;
		line.x1 = x;
		line.y1 = y;
		
	}
	

	@Override
	public boolean contains(int x, int y) {// override in line
		Shape transformedShape = this.affineTransform.createTransformedShape(shape);
		
		if(isSelected()) {
			if(this.Anchors.contains(x,y)) {
				return true;
			}
		}
		if(transformedShape.getBounds().contains(x,y)) {
			this.Anchors.setSelectedAnchor(EAnchors.eMove);
			return true;
		}
		return false;
	}


	
}