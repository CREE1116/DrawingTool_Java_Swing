 package global;
import java.awt.Color;
import java.io.File;

import Frames.MainFrame;

public class Main {

	public static void main(String[] args) {
		MainFrame mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.initialize();
		String path = "c:\\Graphic_Tool"; //���� ���
		File Folder = new File(path);
		if (!Folder.exists()) {
			try{
			    Folder.mkdir(); //���� �����մϴ�.
			    System.out.println("Folder Success");
		        } 
		        catch(Exception e){
			    e.getStackTrace();
			}        
	         }else {
			System.out.println("Folder X");
		}

	}

}
