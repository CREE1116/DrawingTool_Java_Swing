package transFormer;


import java.awt.geom.AffineTransform;



import Frames.DrawingPanel;

import shapes.TAnchors;
import shapes.TShape;


public abstract class TransFormer {
		protected TShape shape;
		protected DrawingPanel drawingPanel;
		protected TAnchors Anchors;
		protected double Px;
		protected double Py;
		protected AffineTransform affineTransform;
		protected int movecount_x;
		protected int movecount_y;
		
		public TransFormer(TShape shape, DrawingPanel drawingPanel) {
			this.shape = shape;
			this.drawingPanel = drawingPanel;
			this.affineTransform = shape.getAffineTransform();
			this.Anchors = shape.getAnchors();
			this.movecount_x = 0;
			this.movecount_y = 0;
		}
		public abstract void prepare(int x, int y) ;
		public abstract void keepTransForm(int x, int y) ;
		public abstract void finalizeTransForm(int x, int y) ;
		public boolean isMove() {
			if(( movecount_x > 1 || movecount_x < -1) || (movecount_y > 1 || movecount_y < -1)) {
				return true;
			}
			return false;
		}
}
