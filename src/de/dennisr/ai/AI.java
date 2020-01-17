package de.dennisr.ai;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.dennisr.game.Player;

public class AI {

	private List<AINode> nodes;
	private Random random;
	private int[][] boardData;
	private int width, height;
	
	public int getWanted(int width, int height, int[][] boardData){
		this.boardData = boardData;
		this.width = width;
		this.height = height;

		random = new Random();
		
		int res = random.nextInt(width);
		
		while(getLowestY(res) == -1){
			res = random.nextInt(width);
		}
		
		return res;
	}
	
	public int getWanted2(int width, int height, int[][] boardData){
		this.boardData = boardData;
		this.width = width;
		this.height = height;
		
		nodes = new ArrayList<AINode>();
		random = new Random();
		
		System.out.println(boardData.toString());
		for(int x = 0; x < width; x++){
			int y = getLowestY(x);
			if(y != -1){
				AINode node = new AINode(x);
				node.setValue(getValueByPoint(x, y));
				nodes.add(node);
			}
		}
		
		AINode highest = null;
		
		for(AINode n : nodes){
			if(highest == null || n.getValue() > highest.getValue()){
				highest = n;
			}
		}
		
		return highest.getColumn();
	}

	private int getValueByPoint(int x, int y) {
		int value = 0;
		
		value += rateBelow(x, y);
			
		System.out.println("[" + x + "," + y + "] -> " + value);
		return value;
	}
	
	public int getLowestY(int x){
		for(int y = this.height-1; y > -1; y--){
			if(this.boardData[x][y] == 0)
			return y;
		}
		return -1;
	}
		
	public int rateBelow(int x, int y){
		int cnt = 0;
		
		for(int i = 0; i < 3; i++){
			if(this.boardData[x][y+i] != 0){
				cnt++;
			}else{
				break;
			}
		}
		
		return valueCntEnemy(cnt);
	}

	private int valueCntEnemy(int cnt) {
		switch(cnt){
			case 1:
				return 1;
			case 2:
				return 4;
			case 3:
				return 20;
			default: 
				return 0;
		}
	}
		
	
	
	
}
