package menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import Frames.DrawingPanel;
import global.Constants.EFileMenu;


public class FileMenu extends JMenu {
	private static final long serialVersionUID = 1L;
	private DrawingPanel drawingPanel;
	private File currentFile;

	public FileMenu(String title) {
		super(title);
		ActionHandler actionHandler = new ActionHandler();
		for(EFileMenu eMenu : EFileMenu.values() ) {
			JMenuItem menuItem = new JMenuItem(eMenu.getLabel());
			menuItem.addActionListener(actionHandler);
			menuItem.setToolTipText(eMenu.Tooltip());
			menuItem.setAccelerator(eMenu.Accelerator());
			this.add(menuItem);	
		}
		currentFile = null; 
	}
	public void associate(DrawingPanel drawingPanel) {
		this.drawingPanel = drawingPanel;
	}
	
	private void load(File file) {
		drawingPanel.setIsSave(true);
		try {
			FileInputStream fileInputStream;
			fileInputStream = new FileInputStream(file);
			ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
			Object object = objectInputStream.readObject();
			this.drawingPanel.setShapes(object);
			objectInputStream.close();
			}catch (FileNotFoundException e) {
				e.printStackTrace();
			}catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
	}
	private void store(File file) {
		try {
		FileOutputStream fileOutputStream;
		fileOutputStream = new FileOutputStream(file);
		ObjectOutputStream ObjectOutputStream = new ObjectOutputStream(fileOutputStream);
		ObjectOutputStream.writeObject(this.drawingPanel.getShapes());
		ObjectOutputStream.close();
		drawingPanel.setIsSave(true);
		}catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
}
	private int askDialog() {
		int ans;
		String Q[] = {"Yes", "No","Cancel"};
		ans = JOptionPane.showOptionDialog(this, "저장하시겠습니까", "파일 저장", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, Q[0]);
		return ans;
	}
	
	private void Open() {
		JFileChooser chooser = new JFileChooser(currentFile);
		chooser.setCurrentDirectory(new File("c:\\Graphic_Tool"));
		int returnValue = chooser.showOpenDialog(this.drawingPanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.currentFile = chooser.getSelectedFile();
			this.load(currentFile);
		}
	}
	private void Save() {
		if(currentFile == null) {
			this.SaveAs();
			
		}else {
			this.store(currentFile);
		}
	}
	private void SaveAs() {
		JFileChooser chooser = new JFileChooser(currentFile);
		chooser.setCurrentDirectory(new File("c:\\Graphic_Tool"));
		int returnValue = chooser.showSaveDialog(this.drawingPanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			this.currentFile = chooser.getSelectedFile();
			this.store(currentFile);
		}
	}
	public void Quit() {
		int ans=0;
		if(!drawingPanel.getIsSave()) {
			ans = askDialog();
			if(ans == 0) this.Save();
		}
		if(ans !=2)System.exit(0);
		
	}
	private void New() {
		int ans=0;
		if(!drawingPanel.getIsSave()) {
			ans = askDialog();
			if(ans == 0) this.Save();
		}
		if(ans !=2)drawingPanel.clearShape();
		drawingPanel.initialize();
	}
	private void saveAsImage() {
		File temp = null;
		JFileChooser chooser = new JFileChooser(currentFile);
		chooser.setCurrentDirectory(new File("c:\\Graphic_Tool"));
		int returnValue = chooser.showSaveDialog(this.drawingPanel);
		if(returnValue == JFileChooser.APPROVE_OPTION) {
			File temp2 = chooser.getSelectedFile();
			temp = new File(temp2.getPath()+".jpg");
		}
			try {
				ImageIO.write(drawingPanel.getBufferedImage(), "jpg", temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public void initialize() {
		
	}


		class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getActionCommand().equals(EFileMenu.eOpen.getLabel())) {
				Open();
			}else if(e.getActionCommand().equals(EFileMenu.eSave.getLabel())) {
				Save();
			}else if(e.getActionCommand().equals(EFileMenu.eSaveAs.getLabel())) {
				SaveAs();
			}else if(e.getActionCommand().equals(EFileMenu.eQuit.getLabel())) {
				Quit();
			}else if(e.getActionCommand().equals(EFileMenu.eNew.getLabel())) {
				New();
			}else if(e.getActionCommand().equals(EFileMenu.ePrint.getLabel())) {
				saveAsImage();
	}
		
	}
}
	
}

