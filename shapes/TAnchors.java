package shapes;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;

import javax.swing.ImageIcon;

import shapes.TAnchors.EAnchors;

public class TAnchors implements Serializable{
	private static final long serialVersionUID = 1L;
	private final int WIDTH = 15, HEIGHT = 15;
	private EAnchors eSelectedAnchor;
	private EAnchors eResizeAnchor;
	static  Toolkit tk = Toolkit.getDefaultToolkit();
	static Image temp = tk.createImage("../DrawingTool_Java_Swing/Image/Rotate_Cursor.png");	
	static Image Rotate = temp.getScaledInstance(20, 20, java.awt.Image.SCALE_SMOOTH);
	
	public EAnchors getResizeAnchor() {
		return this.eResizeAnchor;
	}
	public void setResizeAnchor(EAnchors eResizeAnchor) {
		this.eResizeAnchor = eResizeAnchor;
	}
	
	public EAnchors getSelectedAnchor() {
		return eSelectedAnchor;
	}
	public void setSelectedAnchor(EAnchors eSelectedAnchor) {
		this.eSelectedAnchor = eSelectedAnchor;
	}
	public enum EAnchors{
		
		eNN(new Cursor(Cursor.N_RESIZE_CURSOR)),
		eNE(new Cursor(Cursor.NE_RESIZE_CURSOR)),
		eEE(new Cursor(Cursor.E_RESIZE_CURSOR)),
		eSE(new Cursor(Cursor.SE_RESIZE_CURSOR)),
		eSS(new Cursor(Cursor.S_RESIZE_CURSOR)),
		eSW(new Cursor(Cursor.SW_RESIZE_CURSOR)),
		eWW(new Cursor(Cursor.W_RESIZE_CURSOR)),
		eNW(new Cursor(Cursor.NW_RESIZE_CURSOR)),
		eRR(tk.createCustomCursor(Rotate,new Point(12,12),"")),
		eMove(new Cursor(Cursor.HAND_CURSOR));
		
	private Cursor cursor;
		private EAnchors(Cursor cursor){
			this.cursor = cursor;
		}
		
	public Cursor getCursor() {
		return cursor;
	}
	
	}
	
	

private Ellipse2D anchors[];
	
	// constructors
	public TAnchors() {
		this.anchors = new Ellipse2D[EAnchors.values().length-1];
		for (int i=0; i<EAnchors.values().length-1; i++) {
			this.anchors[i] = new Ellipse2D.Double();
		}
	}
	
	// methods
	public boolean contains(int x, int y) {
		for (int i=0; i<EAnchors.values().length-1; i++) {			
			if (this.anchors[i].contains(x, y)) {
				this.eSelectedAnchor = EAnchors.values()[i];
				return true;
			}
		}
		return false;
	}
	
	
	
	
	public void draw(Graphics2D graphics2D, Rectangle boundingRectangle) {
		for (int i=0; i<EAnchors.values().length-1; i++) {
			EAnchors eAnchor = EAnchors.values()[i];
			int x =  boundingRectangle.x;
			int y =  boundingRectangle.y;
			int w =  boundingRectangle.width;
			int h =  boundingRectangle.height;
			
			switch (eAnchor) {
		case eNN: x = x+w/2; 					break;
		case eNE: x = x+w; 						break;
		case eEE: x = x+w; 		y = y+h/2;		break;
		case eSE: x = x+w; 		y = y+h; 		break;
		case eSS: x = x+w/2; 	y = y+h; 		break;
		case eSW: y = y+h; 						break;
		case eWW: y = y+h/2; 					break;
		case eNW: 								break;
		case eRR: x = x+w/2; 	y = y-h/3; 		break;
		}
		this.anchors[eAnchor.ordinal()].setFrame(x-WIDTH/2,y-HEIGHT/2,WIDTH,HEIGHT);
		graphics2D.setColor(Color.white);
		graphics2D.fill(this.anchors[eAnchor.ordinal()]);
		graphics2D.setColor(Color.black);
		graphics2D.draw(this.anchors[eAnchor.ordinal()]);
		}
}

	public Point2D getResizeAnchorPoint() {
		switch(this.eSelectedAnchor){
		case eNN: eResizeAnchor = EAnchors.eSS;		break;
		case eNE: eResizeAnchor = EAnchors.eSW;		break;
		case eEE: eResizeAnchor = EAnchors.eWW;		break;
		case eSE: eResizeAnchor = EAnchors.eNW;		break;
		case eSS: eResizeAnchor = EAnchors.eNN;		break;
		case eSW: eResizeAnchor = EAnchors.eNE;		break;
		case eWW: eResizeAnchor = EAnchors.eEE;		break;
		case eNW: eResizeAnchor = EAnchors.eSE;		break;
		default:eResizeAnchor = null;				break;
		}
		double Cx = this.anchors[eResizeAnchor.ordinal()].getCenterX();
		double Cy = this.anchors[eResizeAnchor.ordinal()].getCenterY();
		return new Point2D.Double(Cx,Cy);
		//return new Point2D.Double(this.anchors[EAnchors.eNW.ordinal()].getCenterX(),this.anchors[EAnchors.eNW.ordinal()].getCenterY());
	}

	
	

}