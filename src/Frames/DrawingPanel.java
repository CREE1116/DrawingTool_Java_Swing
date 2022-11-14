 package Frames;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.event.MouseInputListener;

import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import global.MemoryManager;
import global.Constants.ETools;
import global.Constants.ETransformationStyle;
import menus.EditMenu;
import shapes.TAnchors.EAnchors;
import shapes.TSelect;
import shapes.TShape;

import transFormer.Drawer;
import transFormer.Mover;
import transFormer.Rotater;
import transFormer.Resizer;
import transFormer.TransFormer;


//----------------------------------------------------------------------------------------------------------
public class DrawingPanel extends JPanel {

	//attribute
	private static final long serialVersionUID = 1L;
	
	
	//components
	private Vector<TShape> shapes;
	private MemoryManager memoryManager;
	
	//associate attribute
	private ETools selectedTool;
	private TShape selectedShape;
	private TShape currentShape;
	private TransFormer transformer;
	private Color defaultColor;

	
	
	//working variables
	private boolean isSave = true;
	private enum EDrawingState{
		eIdle,
		e2PointTransformation,
		eNPointTransformation,
		eMoving
	}
	private BufferedImage bufferedImage;
	EDrawingState eDrawingState =EDrawingState.eIdle;
	
//----------------------------------------------------------------------------------------------------------

	public DrawingPanel() {
		this.eDrawingState=EDrawingState.eIdle;
		this.shapes = new Vector<TShape>();
		this.memoryManager = new MemoryManager();
		MouseHandler mouseHandler = new MouseHandler();
		this.addMouseListener(mouseHandler);
		this.addMouseMotionListener(mouseHandler);
	}
	
	public void associate(MenuBar menuBar) {
		
	}
	public void initialize() {
		this.setBackground(Color.WHITE);
		this.memoryManager.initialize(shapes);
		this.transformer = null;
		this.selectedShape = null;
		this.currentShape = null;
		this.defaultColor = null;
		this.bufferedImage = (BufferedImage) this.createImage(this.getWidth(),this.getHeight());
	}
	
	public Object getShapes() {
		return this.shapes;
	}
	@SuppressWarnings("unchecked")
	public void setShapes(Object shapes) {
		if(shapes != null) {
			this.shapes = (Vector<TShape>)shapes;
			this.selectedShape = null;
			for(TShape shape:this.shapes) {
				if(shape.isSelected()) {
					selectedShape = shape;
				}
			}
			repaint();
			}
	}
	public void clearShape() {
		this.shapes.clear();
		this.selectedShape = null;
		this.repaint();
	}
	public void setIsSave (boolean isSave){
		this.isSave = isSave;
	}
	public boolean getIsSave() {
		return this.isSave;
	}
	public void setSelectionButton(ETools eTools) {
		this.eDrawingState=EDrawingState.eIdle;
		setSelectedTool(eTools);
	}
	public void paint(Graphics graphics) {
		super.paint(graphics);
		for(TShape shape:this.shapes) {
			shape.draw((Graphics2D)graphics);
		}
	}
	public void setSelectedTool(TShape currentShape) {
		this.currentShape = currentShape;
	}
	public void setColor(Color color) {
		this.defaultColor = color;
	}
	public void setLineColor(Color color) {
		if(selectedShape != null) {
			selectedShape.setLineColor(color);
			memoryManager.push(this.shapes);
			repaint();
		}
	}
	public void setStrock(int Strock) {
		if(selectedShape != null) {
			selectedShape.setStrock(Strock);
			memoryManager.push(this.shapes);
			repaint();
		}
	}
	public ETools getSelectedTool() {
		return selectedTool;
	}
	public void setSelectedTool(ETools selectedTool) {
		this.selectedTool = selectedTool;
	}
	public void setSelectedShape(TShape shape) {
		this.selectedShape = shape;
	}
	public TShape getSelectedShape() {
		return selectedShape;
	}
	public void addShape(TShape shape) {
		this.shapes.add(shape);
	}
	public void deselectAll() {
		for(TShape shape:this.shapes) {
			shape.setSelectd(false);	
		}
	}
	public BufferedImage getBufferedImage() {
		return this.bufferedImage;
	}
	public void undo() {
		setShapes(memoryManager.undo());
	}
	
	public void redo() {
		setShapes(memoryManager.redo());
	}
	
	public void copy() {
		if(selectedShape != null) {
		memoryManager.Copy(selectedShape);
		}
	}
	public void paste() {
		TShape temp = memoryManager.Paste();
		if(temp != null) {
			selectedShape.setSelectd(false);
			this.addShape(temp);
			selectedShape = temp;
			selectedShape.setSelectd(true);
			drawOnBuffer(temp);
			memoryManager.push(this.shapes);
		}
	}
	public void delete() {
		if(selectedShape != null) {
			shapes.remove(this.shapes.indexOf(selectedShape));
			this.selectedShape = null;
			memoryManager.push(this.shapes);
			repaint();
			
		}
	}
	public void drawOnBuffer(TShape shape){        
		Image X = this.createImage(this.getWidth(),this.getHeight());
		Graphics2D DrawingGraphics = (Graphics2D) X.getGraphics();
	    this.paint(DrawingGraphics);
	    shape.draw(DrawingGraphics);// 
	   this.getGraphics().drawImage(X,0,0,null);
	   this.bufferedImage = (BufferedImage) X;
	  }
	
	
//----------------------------------------------------------------------------------------------------------


	private void prepareTransformation(int x, int y) {
		setIsSave(false);
		 if (selectedTool == ETools.eSelectTool) {
			currentShape = onShape(x, y);
			if (currentShape != null ) {
				if (currentShape.getSelectedAnchor() == EAnchors.eMove) {
					this.transformer = new Mover(currentShape,this);
				} else if (currentShape.getSelectedAnchor() == EAnchors.eRR) {
					this.transformer = new Rotater(currentShape,this);
				} else {
					this.transformer = new Resizer(currentShape,this);
				}
			}
			else {
				setIsSave(true);
				this.currentShape = this.selectedTool.newShape();
				this.transformer = new Drawer(currentShape,this);
			}
		}
		 else{
			this.currentShape = this.selectedTool.newShape();
			this.transformer = new Drawer(currentShape,this);
		}
		this.transformer.prepare(x, y);
	}
	

	private void keepTransformation(int x, int y) {
		this.transformer.keepTransForm(x, y);
	}

	private void continueTransformation(int x, int y) {
		this.currentShape.addPoint(x, y);
		}

	private void finishTransformation(int x, int y) {
		this.transformer.finalizeTransForm(x, y);
			if(!(currentShape instanceof TSelect)) {
				if(transformer.isMove()) {
						memoryManager.push(this.shapes);
				}
			System.out.println(transformer);
			this.isSave = false;
			}
		repaint();
		
	}	


	private void changeCursor(int x, int y) {
		Cursor cursor;
		if(getSelectedTool() != ETools.eSelectTool) {
			cursor = new Cursor(Cursor.CROSSHAIR_CURSOR);
		}else {
		cursor = new Cursor(Cursor.DEFAULT_CURSOR);
		if(onShape(x,y)!=null) {
			cursor =onShape(x,y).getSelectedAnchor().getCursor();
		}
		}
		setCursor(cursor);
	}
	
	private void fillColor(int x, int y) {
			Color fillColor = defaultColor;
			currentShape = onShape(x, y);
			if(currentShape != null) {
			currentShape.setFillColor(fillColor);
			}
			repaint();
			memoryManager.push(this.shapes);
	}
	
	
	private void changeSelection(int x, int y) {
		if(selectedTool == ETools.eSelectTool) {
		// erase previous selection
		this.selectedShape.setSelectd(false);
		this.repaint();
		// draw new selection
		this.selectedShape = this.onShape(x, y);
		if (this.selectedShape!= null) {
			selectedShape.setSelectd(true);
			selectedShape.draw((Graphics2D) this.getGraphics());
		}
		}
	}
		
	private TShape onShape(int x, int y) {
		for (TShape shape: this.shapes) {
			if (shape.contains(x, y)) {
				return shape;
			}
		}
		return null;
	}
	
	
	
	private void cancleDrawing(int x, int y) {
		if(getSelectedTool() != ETools.eSelectTool && getSelectedTool() != ETools.eFillTool) {
				shapes.remove(shapes.size()-1);
		}
	}


	//----------------------------------------------------------------------------------------------------------
	
	private class MouseHandler implements MouseInputListener{
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getButton() == MouseEvent.BUTTON1) {
				if(e.getClickCount()==1) {
					
					this.lButtonClicked(e);
				}
				else if(e.getClickCount()==2) {
					
					this.lButtonDoubleClicked(e);
				}
			}
		}

		private void lButtonClicked(MouseEvent e) {
			if(getSelectedTool() == ETools.eFillTool) {
				fillColor(e.getX(), e.getY());
			}
			else if (eDrawingState == EDrawingState.eIdle) {
				changeSelection(e.getX(), e.getY());
				if (selectedTool.getTransformationStyle() == ETransformationStyle.eNPoint) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.eNPointTransformation;
				}else {
					cancleDrawing(e.getX(), e.getY());
				}
			} else if (eDrawingState == EDrawingState.eNPointTransformation) {
					continueTransformation(e.getX(), e.getY());
			}
		}
		private void lButtonDoubleClicked(MouseEvent e) {			
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseMoved(MouseEvent e) {
			if (eDrawingState == EDrawingState.eNPointTransformation) {
				keepTransformation(e.getX(), e.getY());
			} else if (eDrawingState == EDrawingState.eIdle) {
				changeCursor(e.getX(), e.getY());
			}
		}		

		@Override
		public void mousePressed(MouseEvent e) {
			if (eDrawingState == EDrawingState.eIdle) {
					if (selectedTool.getTransformationStyle() == ETransformationStyle.e2Point) {
					prepareTransformation(e.getX(), e.getY());
					eDrawingState = EDrawingState.e2PointTransformation;
				}
			}
		}
		@Override
		public void mouseDragged(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				keepTransformation(e.getX(), e.getY());
			}
		}
		@Override
		public void mouseReleased(MouseEvent e) {
			if (eDrawingState == EDrawingState.e2PointTransformation) {
				finishTransformation(e.getX(), e.getY());
				eDrawingState = EDrawingState.eIdle;
			}
		}
		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {	
	
		}


	}
}