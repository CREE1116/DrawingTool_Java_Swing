package Frames;

import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import global.Constants.ETools;
import shapes.TShape;

import javax.swing.JRadioButton;
import javax.swing.JSlider;

import java.awt.Choice;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;


public class ToolBar extends JToolBar {

	private static final long serialVersionUID = 1L;
	
	private DrawingPanel drawingPanel;
	private Color selectedColor = Color.black;

	
	
	public ToolBar() {
		
		this.setBackground(Color.lightGray);
		ButtonGroup buttonGroup = new ButtonGroup();
		ActionHandler actionHandler = new ActionHandler();

		for(ETools etools : ETools.values() ) {
			JRadioButton drawingTool = new JRadioButton(etools.label());
			drawingTool.setActionCommand(etools.name());
			drawingTool.setSelectedIcon(etools.SelectedIcon());
			drawingTool.setIcon(etools.Icon());
			drawingTool.addActionListener(actionHandler);
			drawingTool.setToolTipText(etools.Tooltip());
			
			this.add(drawingTool);
			buttonGroup.add(drawingTool);
		}
		Image Temp = new ImageIcon(getClass().getClassLoader().getResource("ColorChooser.png")).getImage();
		ImageIcon Icon = new ImageIcon(Temp.getScaledInstance(35,35,Image.SCALE_SMOOTH));
		Temp = new ImageIcon(getClass().getClassLoader().getResource("Pressed-ColorChooser.png")).getImage();
		ImageIcon SelectedIcon = new ImageIcon(Temp.getScaledInstance(35,35,Image.SCALE_SMOOTH));
		
		JButton ColorChooser = new JButton("Color");
		ColorChooser.setActionCommand("ColorChooser");
		ColorChooser.addActionListener(actionHandler);
		ColorChooser.setIcon(Icon);
		ColorChooser.setPressedIcon(SelectedIcon);
		this.add(ColorChooser);
	
}
		
	private class ActionHandler implements ActionListener{
		@SuppressWarnings("static-access")
		@Override
		public void actionPerformed(ActionEvent e) {
			if(!e.getActionCommand().equals("ColorChooser")) {
			drawingPanel.setSelectionButton(ETools.valueOf(e.getActionCommand()));
			}else {
				JColorChooser chooser=new JColorChooser();
				selectedColor= chooser.showDialog(null,"Color",selectedColor);
				if(selectedColor != null) {
					System.out.println("ToolBar! Color:  "+selectedColor);
					drawingPanel.setColor(selectedColor);
				}
		}
	}
	}
	 public void initialize() {
		 
	 }

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		JRadioButton Temp = (JRadioButton)(this.getComponent(ETools.eSelectTool.ordinal()));
		Temp.doClick();
	}
}