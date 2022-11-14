package Frames;

import java.awt.BorderLayout;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import transFormer.TransFormer;


public class MainFrame extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private MenuBar menuBar;
	private ToolBar toolBar;
	private DrawingPanel drawingPanel;


	
	public MainFrame() { 
		this.setSize(800,600);
		
		BorderLayout layoutManager = new BorderLayout();
		this.setLayout(layoutManager);
		
		this.setTitle("Drawing Tool");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		WindowsHandler windowsHandler = new WindowsHandler();
		this.addWindowListener(windowsHandler);
		
		this.menuBar = new MenuBar();
		this.setJMenuBar(this.menuBar);
		
		this.toolBar = new ToolBar();
		this.add(this.toolBar, layoutManager.NORTH);

		this.drawingPanel = new DrawingPanel();
		this.add(this.drawingPanel,layoutManager.CENTER);

		this.toolBar.associate(this.drawingPanel);
		this.menuBar.associate(this.drawingPanel);
		this.drawingPanel.associate(this.menuBar);
	}
	public void initialize() {
		this.menuBar.initialize();
		this.toolBar.initialize();
		this.drawingPanel.initialize();
	}
	private class WindowsHandler implements WindowListener {

		@Override
		public void windowActivated(WindowEvent arg0) {
		}

		@Override
		public void windowClosed(WindowEvent arg0) {
		}

		@Override
		public void windowClosing(WindowEvent event) {
			menuBar.exit();
		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {
		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {
		}

		@Override
		public void windowIconified(WindowEvent arg0) {
		}

		@Override
		public void windowOpened(WindowEvent arg0) {
		}
		
	}
}

	