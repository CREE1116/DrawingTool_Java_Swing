package menus;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JColorChooser;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import Frames.DrawingPanel;
import global.Constants.EStrockAttribute;

public class StrokeAttributeMenu extends JMenu{
	private Color selectedColor;
	private int SelectedStrock;
	private DrawingPanel drawingPanel;
	private SubFrame SF ;
	
	
	public StrokeAttributeMenu(String title) {
		super(title);
		ActionHandler actionHandler = new ActionHandler();
		for(EStrockAttribute eMenu : EStrockAttribute.values() ) {
			JMenuItem menuItem = new JMenuItem(eMenu.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setToolTipText(eMenu.Tooltip());
			this.add(menuItem);	
		}
		SF = new SubFrame();
	}
	
	
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		SF.associate(this);
	}
	
	public void setStrock(int i) {
		this.SelectedStrock = i;
	}
	
	
	public void Colorchoose() {
		JColorChooser chooser=new JColorChooser();
		selectedColor= chooser.showDialog(null,"Color",selectedColor);
		if(selectedColor != null) {
			System.out.println("ToolBar! Color:  "+selectedColor);
			drawingPanel.setLineColor(selectedColor);
		}
	}
	public void Strockchoose() {
		SF.setVisible(true);
	}
	public void DisposeSubFrame() {
		SF.setVisible(false);
		drawingPanel.setStrock(this.SelectedStrock);
	}

	class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EStrockAttribute.eColor.getLabel())) {
				Colorchoose();
			}else if(e.getActionCommand().equals(EStrockAttribute.eThickness.getLabel())) {
				Strockchoose();
			}
		
	}
}
	public void initialize() {
		selectedColor = Color.black;
		SelectedStrock = 3;
		SF.initialize();
	}
}
