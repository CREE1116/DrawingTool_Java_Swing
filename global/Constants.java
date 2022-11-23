package global;
import shapes.TShape;

import java.awt.Event;
import java.awt.Image;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import shapes.TLine;
import shapes.TOval;
import shapes.TPolygon;
import shapes.TRectangle;
import shapes.TSelect;

public class Constants {
	
	public enum ETools{
		eRectangle(new TRectangle(),null,"Drawing Rectangle","Rect.png","Selected-Rect.png",ETransformationStyle.e2Point),
		eOval(new TOval(),null,"Drawing Oval","Oval.png","Selected-Oval.png",ETransformationStyle.e2Point),
		eLine(new TLine(),null,"Drawing Line","Line.png","Selected-Line.png",ETransformationStyle.e2Point),
		ePoLygon(new TPolygon(),null,"Drawing Polygon ","Polygon.png","Selected-Polygon.png",ETransformationStyle.eNPoint),
		eSelectTool(new TSelect(),null,"Select Tool","Select.png","Selected-Select.png",ETransformationStyle.e2Point),
		eFillTool(null,null,"Fill color of shape","fiilShape.png","fillShape_Selected.png",null);
		private TShape Tool;
		private String label;
		private String ToolTip;
		private ImageIcon Icon;
		private ImageIcon SelectedIcon;
		private ETransformationStyle eTransformationStyle;
		private ETools(TShape Tool ,String label,String ToolTip,String IconPath,String SeletedIconPath, ETransformationStyle eTransformationStyle) {
			this.Tool = Tool;
			this.label  = label;
			this.ToolTip = ToolTip;
			this.eTransformationStyle = eTransformationStyle;
			Image Temp = new ImageIcon(getClass().getClassLoader().getResource(IconPath)).getImage();
			this.Icon = new ImageIcon(Temp.getScaledInstance(35,35,Image.SCALE_SMOOTH));
			Temp = new ImageIcon(getClass().getClassLoader().getResource(SeletedIconPath)).getImage();
			this.SelectedIcon = new ImageIcon(Temp.getScaledInstance(38,38,Image.SCALE_SMOOTH));
		}
	
		public TShape newShape() {
			return this.Tool.clone();
		}
		public String label() {
			return label;
		}
		public String Tooltip() {
			return ToolTip;
		}
		public Icon Icon() {
			return Icon;
		}
		public Icon SelectedIcon() {
			return SelectedIcon;
		}
		public ETransformationStyle getTransformationStyle() {
			return this.eTransformationStyle;
		}
	
	}
	public enum EFileMenu{
		eNew("new","파일을 새로 시작합니다",KeyStroke.getKeyStroke('N', Event.CTRL_MASK)),
		eOpen("open","저장된 파일을 불러옵니다",null),
		eSave("save","파일로 저장합니다",KeyStroke.getKeyStroke('S', Event.CTRL_MASK)),
		eSaveAs("save as","이름과 경로를 지정하여 저장합니다",null),
		ePrint("save as image","사진파일로 저장합니다",null),
		eQuit("quit","프로그램을 종료합니다",null);
		
		private String label;
		private String ToolTip;
		private KeyStroke keyStorck;
		private EFileMenu(String label,String ToolTip, KeyStroke keystrock) {
			this.label = label;
			this.ToolTip = ToolTip;
			this.keyStorck = keystrock;
		}
		public String getLabel() {
			return this.label; 
		}
		public String Tooltip() {
			return ToolTip;
		}
		public KeyStroke Accelerator() {
			return keyStorck;
		}
	}
	
	public enum EEditMenu{
		eDelete("Delete","Erase the seletedShape",KeyStroke.getKeyStroke(8,Event.CTRL_MASK)),
		eCut("Cut","Copy and Delete",KeyStroke.getKeyStroke('L', Event.CTRL_MASK)),
		eCopy("Copy","Copy Selected shape",KeyStroke.getKeyStroke('C', Event.CTRL_MASK)),
		ePaste("Paste","Paste Copied shape",KeyStroke.getKeyStroke('V', Event.CTRL_MASK)),
		eUndo("Undo","Undo",KeyStroke.getKeyStroke('Z', Event.CTRL_MASK)),
		eRedo("Redo","Redo",KeyStroke.getKeyStroke('R', Event.CTRL_MASK));
		private String label;
		private String ToolTip;
		private KeyStroke keystrock;
		
		private EEditMenu(String label,String ToolTip,KeyStroke keystrock) {
			this.label = label;
			this.ToolTip = ToolTip;
			this.keystrock = keystrock;
		}
		public String getLabel() {
			return this.label; 
		}
		public String Tooltip() {
			return ToolTip;
		}
		public KeyStroke Accelerator() {
			return keystrock;
		}
	
	}
	
	public enum EStrockAttribute{

		eThickness("thickness","Adjust the line thickness"),
		eColor("Color","Adjust the line color");
		private String label;
		private String ToolTip;
	
		private EStrockAttribute(String label,String ToolTip) {
			this.label = label;
			this.ToolTip = ToolTip;
		}
		public String getLabel() {
			return this.label; 
		}
		public String Tooltip() {
			return ToolTip;
		}
		
	}
	
	public enum ETransformationStyle{
		e2Point,
		eNPoint
	}
}