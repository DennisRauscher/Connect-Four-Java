package de.dennisr.ai;

public class AINode {

	private int value = 0;
	private int column = 0;
	
	public AINode(int column){
		this.column = column;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getColumn() {
		return column;
	}
	
}
