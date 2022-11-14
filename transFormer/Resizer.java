package transFormer;

import java.awt.geom.Point2D;

import Frames.DrawingPanel;
import shapes.TAnchors;
import shapes.TAnchors.EAnchors;
import shapes.TShape;

public class Resizer extends TransFormer{

	
	private TAnchors Anchors;
	private double Cx, Cy, xScale, yScale;
	
	
	public Resizer(TShape shape, DrawingPanel drawingPanel) {
		super(shape, drawingPanel);
		Anchors = shape.getAnchors();
		this.affineTransform = shape.getAffineTransform();
	}

	@Override
	public void prepare(int x, int y) {
		movecount_x = movecount_x - x;
		movecount_y = movecount_y - y;
	
		this.Px = x;
		this.Py = y;
		Point2D resizeAnchorPoint = Anchors.getResizeAnchorPoint();
		this.Cx = resizeAnchorPoint.getX();
		this.Cy = resizeAnchorPoint.getY();
		System.out.println("ResizeAnchor!: "+resizeAnchorPoint);
		
	}

	@Override
	public void keepTransForm(int x, int y) {
		drawingPanel.drawOnBuffer(shape);
		this.getResizeScale(x,y);
		
		this.affineTransform.translate(Cx,Cy);
		
		this.affineTransform.scale(xScale, yScale);
	
		this.affineTransform.translate(-Cx,-Cy);
		
		this.Px = x;
		this.Py = y;
	}

	@Override
	public void finalizeTransForm(int x, int y) {
		movecount_x = movecount_x + x;
		movecount_y = movecount_y + y;
		drawingPanel.drawOnBuffer(shape);
		shape.finalizeTransform();
		drawingPanel.setSelectedShape(this.shape);
		System.out.println("finalize resize");
	}
	
	public void getResizeScale(int x, int y) {
		EAnchors eResizeAnchor = this.Anchors.getResizeAnchor();
		double W1 = Px - Cx;
		double W2 = x -  Cx;
		double H1 = Py - Cy;
		double H2 = y -  Cy;
		switch(eResizeAnchor){
		case eNN: this.xScale = 1;			 this.yScale = H2/H1;		break;
		case eNE: this.xScale = W2/W1;		 this.yScale = H2/H1;		break;
		case eEE: this.xScale = W2/W1;		 this.yScale = 1;			break;
		case eSE: this.xScale = W2/W1; 		 this.yScale = H2/H1;		break;
		case eSS: this.xScale = 1;			 this.yScale = H2/H1;		break;
		case eSW: this.xScale = W2/W1;	 	 this.yScale = H2/H1;		break;
		case eWW: this.xScale = W2/W1;       this.yScale = 1;			break;
		case eNW: this.xScale = W2/W1;  	 this.yScale = H2/H1;		break;
		default:  this.xScale = 1;			 this.yScale = 1;			break;
		}
	}
}

