package shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.io.Serializable;

import shapes.TAnchors.EAnchors;

abstract public class TShape implements Serializable{
		// attribute
		private static final long serialVersionUID = 1L;
		private boolean bSelected;
		
		// components
		protected Shape shape;
		protected TAnchors Anchors;
		
		
		//working
		protected AffineTransform affineTransform;
		protected AffineTransform affineTransform_R;
		private Color lineColor;
		private Color fillColor;
		
		private int Strock;
	


		
		//constructor
		public TShape() {
			this.Anchors = new TAnchors();
			this.bSelected = false;
			this.affineTransform = new AffineTransform();
			this.affineTransform.setToIdentity(); // �빆�벑�썝
			this.affineTransform_R = new AffineTransform();
			this.affineTransform_R.setToIdentity(); // �빆�벑�썝
			this.fillColor = new Color(0,0,0,0);
			this.lineColor = Color.black;
			this.Strock = 3;
		}
		
		public abstract TShape clone(); 
		
		public TShape CopyShape() {
			TShape Temp = this.clone();
			Temp.setAffineTransform((AffineTransform) affineTransform.clone());
			Temp.setAffineTransform_R((AffineTransform) affineTransform_R.clone());
			Temp.setFillColor(fillColor);
			Temp.setLineColor(lineColor);
			Temp.setShape(shape);
			Temp.setStrock(Strock);
			Temp.setSelectd(bSelected);
			return Temp;
		}
		public void initialize() {};
		
		
		//setters and getters
		public void addPoint(int x, int y) {}	
		public boolean isSelected() {
			return this.bSelected;
		}
		
		public void setSelectd(boolean bSelected) {
			this.bSelected = bSelected;
		}
		
		
		public EAnchors getSelectedAnchor() {
			return this.Anchors.getSelectedAnchor();
		}
		
		public void setLineColor(Color lineColor) {
			this.lineColor = lineColor;
		}
		
		public void setStrock(int strock) {
			this.Strock = strock;
		}

		public TAnchors getAnchors() {
			return Anchors;
		}
		
		public void setShape(Shape shape) {
			this.shape = shape;
		}
		
		public Shape getShape() {
			return this.shape;
		}
		
		public void setAffineTransform(AffineTransform affineTransform) {
			this.affineTransform = affineTransform;
		}
		public void setAffineTransform_R(AffineTransform affineTransform) {
			this.affineTransform_R = affineTransform;
		}
		
		public AffineTransform getAffineTransform() {
			return this.affineTransform;
		}
		public AffineTransform getAffineTransform_R() {
			return this.affineTransform_R;
		}
		public void setFillColor(Color C) {
			this.fillColor = C;
		}

		//methods
		public void draw(Graphics2D graphics2D) {
			Graphics2D D_g = (Graphics2D) graphics2D.create();
			Shape transformedShape = this.affineTransform.createTransformedShape(shape);
			 D_g.setStroke(new BasicStroke(Strock,BasicStroke.CAP_ROUND,0));
			 D_g.transform(affineTransform_R);
			 D_g.setColor(fillColor);
			 D_g.fill(transformedShape);
			 D_g.setColor(lineColor);
			 if(lineColor == null) {
				 D_g.setColor(Color.BLACK);
			 }
			 D_g.draw(transformedShape);
			if(isSelected()) {
				 D_g.setStroke(new BasicStroke(2,BasicStroke.CAP_ROUND,0));
				 D_g.setColor(Color.black);
				this.Anchors.draw(D_g,(Rectangle)transformedShape.getBounds());
			}	
		}
		

		public boolean contains(int x, int y) {// override in line
			Shape transformedShape = this.affineTransform.createTransformedShape(shape);
			if(isSelected()) {
				if(this.Anchors.contains(x,y)) {
					return true;
				}
			}
			if(transformedShape.contains(x,y)) {
				this.Anchors.setSelectedAnchor(EAnchors.eMove);
				return true;
			}
			return false;
		}

		public abstract void prepareDrawing(int x, int y);
		public abstract void keepDrawing(int x, int y);
		
		
		public void finalizeTransform() {
			this.shape = this.affineTransform.createTransformedShape(this.shape);
			this.shape = this.affineTransform_R.createTransformedShape(this.shape);
			this.affineTransform_R.setToIdentity();
			this.affineTransform.setToIdentity();
		}

		
		
	}