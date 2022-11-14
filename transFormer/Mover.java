package transFormer;


import Frames.DrawingPanel;
import global.Constants.ETools;
import shapes.TShape;

public class Mover extends TransFormer{
	
	public Mover(TShape shape, DrawingPanel drawingPanel) {
		super(shape, drawingPanel);
	}

	@Override
	public void prepare(int x, int y) {
		this.Px = x;
		this.Py = y;
		movecount_x = movecount_x + x;
		movecount_y = movecount_y + y;
	}

	@Override
	public void keepTransForm(int x, int y) {
		drawingPanel.drawOnBuffer(shape);
		shape.setSelectd(false);
		this.affineTransform.translate(x - Px, y - Py);
		this.Px = x;
		this.Py = y;
	}

	@Override
	public void finalizeTransForm(int x, int y) {
		movecount_x = movecount_x - x;
		movecount_y = movecount_y - y;
		drawingPanel.drawOnBuffer(shape);
		TShape temp = drawingPanel.getSelectedShape();
		if(temp != null) {
		temp.setSelectd(false);
		}
		shape.setSelectd(true);
		drawingPanel.setSelectedShape(this.shape);
		if(drawingPanel.getSelectedTool() !=  ETools.eSelectTool) {
		drawingPanel.addShape(this.shape);
		}
		shape.finalizeTransform();
	}
	
	
}
