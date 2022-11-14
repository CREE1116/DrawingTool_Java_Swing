package global;


import java.awt.geom.AffineTransform;
import java.util.Stack;
import java.util.Vector;

import shapes.TShape;

public class MemoryManager {
	private Stack<Vector<TShape>> undoMemory = new Stack<Vector<TShape>>();
	private Stack<Vector<TShape>> redoMemory = new Stack<Vector<TShape>>();
	private	Stack<Vector<TShape>> Temp = new Stack<Vector<TShape>>();
	private TShape CopyMemory;
	private int Maxcount;
	private Vector<TShape> buffer;

	Vector<TShape> emptyShapes = new Vector<>();
	
	public MemoryManager() {
	}
	
	@SuppressWarnings("unchecked")
	public void initialize(Vector<TShape> shapes) {
		this.undoMemory.clear();
		this.redoMemory.clear();
		this.Temp.clear();
		this.undoMemory.push((Vector<TShape>) emptyShapes.clone());
		this.Maxcount = 10;
		
	}
	
	@SuppressWarnings("unchecked")
	public void push(Vector<TShape> shapes){
		if(undoMemory.size() < 2) {
			undoMemory.clear();
			this.undoMemory.push((Vector<TShape>) emptyShapes.clone());
		}
		undoMemory.push(this.CopyShape(shapes));
		this.redoMemory.clear();
		System.out.println("push");
	}
	
	@SuppressWarnings("unchecked")
	public Vector<TShape> undo() {
		if(undoMemory.size() > 1 ) {
			redoMemory.push(undoMemory.pop());
			buffer = CopyShape(undoMemory.peek());
			return buffer;
		}return null;
	}
	public void eraseUndo() {
		undoMemory.pop();   
		System.out.println("eraseUndo");
	}
	
	public Vector<TShape> redo() {
		if(redoMemory.size() > 0 ) {
			undoMemory.push(redoMemory.pop());
			buffer = undoMemory.peek();
			System.out.println("redo Info: "+redoMemory);
			return buffer;
		}return null;
	}
	
	public void Copy(TShape shape) {
		this.CopyMemory = shape.CopyShape();
	}
	public TShape Paste() {
		if(CopyMemory != null) {
		AffineTransform Temp = this.CopyMemory.getAffineTransform();
		Temp.translate(10, 10);
		this.CopyMemory.setAffineTransform(Temp);
		System.out.println("Paste Success");
		return this.CopyMemory.CopyShape();
		
		}return null;
	}
	
	public Vector<TShape> CopyShape(Vector<TShape> Shapes){
		Vector<TShape> Temp = new Vector<>();
		for(TShape shape: Shapes) {
			Temp.add(shape.CopyShape());
		}
		return Temp;
	}
	

	
}
