package transFormer;


import Frames.DrawingPanel;
import global.Constants.ETools;
import shapes.TShape;

public class Drawer extends TransFormer{
	public Drawer(TShape shape, DrawingPanel drawingPanel) {
		super(shape, drawingPanel);
		
	}

	@Override
	public void prepare(int x, int y) {
		movecount_x = movecount_x + x;
		movecount_y = movecount_y + y;
		this.shape.prepareDrawing(x, y);
	}

	@Override
	public void keepTransForm(int x, int y) {
		drawingPanel.drawOnBuffer(shape);
		this.shape.keepDrawing(x, y);
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
	}
	
}

