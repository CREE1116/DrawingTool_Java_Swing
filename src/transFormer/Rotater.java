package transFormer;

import java.awt.Shape;
import java.awt.geom.AffineTransform;

import Frames.DrawingPanel;
import shapes.TShape;

public class Rotater extends TransFormer{
	protected AffineTransform R_affineTransform;

	public Rotater(TShape shape, DrawingPanel drawingPanel) {
		super(shape, drawingPanel);
	}

	@Override
	public void prepare(int x, int y) {
		movecount_x = movecount_x + x;
		movecount_y = movecount_y + y;
		R_affineTransform = shape.getAffineTransform_R();
		this.Px = x;
		this.Py = y;
	}

	@Override
	public void keepTransForm(int x, int y) {
		drawingPanel.drawOnBuffer(shape);
		R_affineTransform.rotate(getAngle(x,y),shape.getShape().getBounds().getCenterX(),shape.getShape().getBounds().getCenterY());
		this.Px = x;
		this.Py = y;
	}

	@Override
	public void finalizeTransForm(int x, int y) {
		movecount_x = movecount_x - x;
		movecount_y = movecount_y - y;
		drawingPanel.drawOnBuffer(shape);
		shape.finalizeTransform();
		drawingPanel.setSelectedShape(this.shape);
	}
	

	 public double getAngle(int x, int y){
		 	Shape transformedShape = this.affineTransform.createTransformedShape(shape.getShape());
	        double radian = Math.atan2(transformedShape.getBounds().getCenterY()-y,transformedShape.getBounds().getCenterX()-x)-Math.atan2(transformedShape.getBounds().getCenterY()-Py,transformedShape.getBounds().getCenterX()-Px);
	        return radian;
	    }

}
