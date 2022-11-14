package Frames;
import javax.swing.JMenuBar;

import menus.EditMenu;
import menus.FileMenu;
import menus.StrokeAttributeMenu;


public class MenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	
	private FileMenu fileMenu;
	private EditMenu editMenu;
	private StrokeAttributeMenu strokeAttributeMenu;
	private DrawingPanel drawingPanel;

	public MenuBar() {
		this.fileMenu = new FileMenu("file");
		this.add(this.fileMenu);
		
		this.editMenu = new EditMenu("edit");
		this.add(this.editMenu);
		
		this.strokeAttributeMenu = new StrokeAttributeMenu("Strock");
		this.add(this.strokeAttributeMenu);
	}

	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
		this.fileMenu.associate(this.drawingPanel);
		this.editMenu.associate(this.drawingPanel);
		this.strokeAttributeMenu.associate(this.drawingPanel);
	}

	public void initialize() {
		fileMenu.initialize();
		editMenu.initialize();
		strokeAttributeMenu.initialize();
		
	}
	public void exit() {
		fileMenu.Quit();
	}

	
}