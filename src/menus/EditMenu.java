package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;
import java.util.Vector;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Frames.DrawingPanel;
import global.Constants.EEditMenu;
import global.Constants.EFileMenu;
import global.MemoryManager;
import menus.FileMenu.ActionHandler;
import shapes.TShape;

public class EditMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;
	
	
	public EditMenu(String title) {
		super(title);
		ActionHandler actionHandler = new ActionHandler();
		for(EEditMenu eMenu : EEditMenu.values() ) {
			JMenuItem menuItem = new JMenuItem(eMenu.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setToolTipText(eMenu.Tooltip());
			menuItem.setAccelerator(eMenu.Accelerator());
			this.add(menuItem);	
		}
	}
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	public void undo() {
		drawingPanel.undo();
		}
	public void redo() {
		drawingPanel.redo();
	}
	public void copy() {
		drawingPanel.copy();
	}
	public void paste() {
		drawingPanel.paste();
	}
	public void delete() {
		drawingPanel.delete();
	}



	
	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EEditMenu.eUndo.getLabel())) {
				undo();
		}else if(e.getActionCommand().equals(EEditMenu.eRedo.getLabel())) {
				redo();
		}else if(e.getActionCommand().equals(EEditMenu.eCopy.getLabel())) {
			copy();
		}else if(e.getActionCommand().equals(EEditMenu.ePaste.getLabel())) {
			paste();
		}else if(e.getActionCommand().equals(EEditMenu.eDelete.getLabel())) {
			delete();
		}else if(e.getActionCommand().equals(EEditMenu.eCut.getLabel())) {
			copy();
			delete();
		}
		}
	}
	public void initialize() {
		
	}
}
