package menus;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;

import Frames.DrawingPanel;
import Frames.MenuBar;
import global.Constants.EStrockAttribute;
import menus.StrokeAttributeMenu.ActionHandler;

class SubFrame extends JFrame{
	private StrokeAttributeMenu StrokeAttributeMenu;
    public SubFrame(){
        super("StrockChooser");
        setSize(100,70);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocation(200, 0);
        setUndecorated(true);

        
        ActionHandler actionHandler = new ActionHandler();
        JComboBox<Integer> ChooseBox = new JComboBox<Integer>();
        for(int i = 1; i < 31; i++) {
        	ChooseBox.addItem(i);
        }
        ChooseBox.setSelectedIndex(2);
        ChooseBox.addActionListener(actionHandler); 
        this.add(ChooseBox);	
    }
    public void initialize() {
    	
    }
    public void dispose() {
		this.dispose();
    }
    public void associate(StrokeAttributeMenu StrokeAttributeMenu) {
		this.StrokeAttributeMenu = StrokeAttributeMenu;
	}
    class ActionHandler implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource(); 
			int index = cb.getSelectedIndex();
			StrokeAttributeMenu.setStrock(index+1);
			StrokeAttributeMenu.DisposeSubFrame();
		
	}
}
}