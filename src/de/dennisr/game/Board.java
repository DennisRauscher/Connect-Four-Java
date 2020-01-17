package de.dennisr.game;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import de.dennisr.ai.AI;
import de.dennisr.core.GameCore;
import de.dennisr.engine.Drawable;
import de.dennisr.engine.Updateable;
import de.dennisr.game.Player.Fraction;

public class Board implements Drawable, Updateable{

	private BufferedImage backgroundGrid, frontGrid, selector;
	
	private int[][] boardData; /* 0 = not taken */
	
	private int width, height, posX, posY, fieldSize;
	private int selectorPos = 0;
	private boolean won = false;
	
	private Lobby lobby;
	private Random random;
	private AI ai;
	
	public Board(Lobby l, int fieldSize, int x, int y, int width, int height){
		this.lobby = l;
		
		this.fieldSize = fieldSize;
		this.posX = x;
		this.posY = y;
		this.width = width;
		this.height = height;
		
		this.random = new Random();
		this.ai = new AI();
		
		this.backgroundGrid = GameCore.getInstance().getImageFromRes("/gfx/boardBackground.png");
		this.frontGrid = GameCore.getInstance().getImageFromRes("/gfx/boardFront.png");
		this.selector = GameCore.getInstance().getImageFromRes("/gfx/selector.png");
		
		boardData = new int[width][height];
	}

	public void populate(){
		
		for(int y = 0; y < this.height; y++){
			for(int x = 0; x < this.width; x++){
				boardData[x][y] = 0;
			}
		}
		
	}
	
	@Override
	public void Update() {
		if(!this.lobby.isGameOver()){
			if(GameCore.getInstance().getInputManager().right.isJustPressed()){
				moveSelector(1);
			}
			
			if(GameCore.getInstance().getInputManager().left.isJustPressed()){
				moveSelector(-1);
			}
			
			if(GameCore.getInstance().getInputManager().enter.isJustPressed()){
				this.lobby.execSelection(this.selectorPos);
			}
			
			if(!this.lobby.getLobbySettings().isLocalMultiplayer() && this.lobby.getCurrentPlayer() == Player.Fraction.RED){
				this.lobby.execSelection(this.getAI());
			}
		}else{
			if(GameCore.getInstance().getInputManager().esc.isJustPressed()){
				GameCore.getInstance().restartGame();
			}
			
			if(GameCore.getInstance().getInputManager().space.isJustPressed()){
				GameCore.getInstance().restartCurrent();
			}
		}
	}

	@Override
	public void Draw(Graphics2D g) {
		drawSelector(g);
		drawBackground(g);
		drawCoins(g);
		drawFront(g);
	}
	
	public void drawSelector(Graphics2D g){
		g.drawImage(this.lobby.getCurrentPlayer().instance.getCoinSprite(), posX + selectorPos * fieldSize, posY - fieldSize - (fieldSize + 5), fieldSize, fieldSize, null);
		g.drawImage(this.selector, posX + selectorPos * fieldSize, posY - fieldSize + fieldSize/3, fieldSize, fieldSize, null);
	}
	
	public void drawBackground(Graphics2D g){
		
		for(int y = 0; y < this.height; y++){
			for(int x = 0; x < this.width; x++){
				g.drawImage(this.backgroundGrid, posX + x * fieldSize, posY + y * fieldSize, fieldSize, fieldSize, null);
			}
		}
		
	}
	
	public void drawCoins(Graphics2D g){
		for(int y = 0; y < this.height; y++){
			for(int x = 0; x < this.width; x++){
				
				if(boardData[x][y] == Player.Fraction.BLUE.instance.getID()){
					g.drawImage(Player.Fraction.BLUE.instance.getCoinSprite(), posX + x * fieldSize, posY + y * fieldSize, fieldSize, fieldSize, null);
				}else if(boardData[x][y] == Player.Fraction.RED.instance.getID()){
					g.drawImage(Player.Fraction.RED.instance.getCoinSprite(), posX + x * fieldSize, posY + y * fieldSize, fieldSize, fieldSize, null);
				}
				
			}
		}
	}
	
	public void drawFront(Graphics2D g){
		
		for(int y = 0; y < this.height; y++){
			for(int x = 0; x < this.width; x++){
				g.drawImage(this.frontGrid, posX + x * fieldSize, posY + y * fieldSize, fieldSize, fieldSize, null);
			}
		}
		
	}
	
	private void moveSelector(int val){
		this.selectorPos += val;
		
		if(this.selectorPos < 0){
			this.selectorPos = 0;
		}
		
		if(this.selectorPos > this.width-1){
			this.selectorPos = this.width-1;
		}
	}
	
	public int getLowestY(int x){
		for(int y = this.height-1; y > -1; y--){
			if(this.boardData[x][y] == 0){
				return y;
			}
		}
		
		if(this.isFull()){
			GameCore.getInstance().restartCurrent();
		}
		
		return -1;
	}
	
	private boolean isFull() {
		for(int y = 0; y < this.height; y++){
			for(int x = 0; x < this.width; x++){
				if(boardData[x][y] == 0) return false;
			}
		}
		
		return true;
	}

	public int getAI(){
		return this.ai.getWanted(width, height, boardData);
	}
	
	public void setCoin(int x, int y, Fraction currentPlayer) {
		int id = currentPlayer.instance.getID();

		this.boardData[x][y] = id;
		
		if(diagonalCheckLeftRight(x, y, currentPlayer.instance.getID()) || diagonalCheckRightLeft(x, y, currentPlayer.instance.getID()) ||
				horizontalCheck(x, y, currentPlayer.instance.getID()) || verticalCheck(x, y, currentPlayer.instance.getID())){
			this.lobby.setGameOver(true);
			this.lobby.setWinner(currentPlayer);
		}
		
	}
	
	private boolean diagonalCheckLeftRight(int x, int y, int fID){
		int num = 0;
		for(int c = -3; c < 4; c++){
				if(x+c >= 0 && y+c >= 0 && x+c < this.width && y+c < this.height){
					if(this.boardData[x+c][y+c] == fID){
						num++;
					}
				}
		}
		
		if(num >= 4){
			return true;
		}
		
		return false;
	}
	
	private boolean diagonalCheckRightLeft(int x, int y, int fID){
		int num = 0;
		for(int c = -3; c < 4; c++){
				if(x+c >= 0 && y+c*-1 >= 0 && x+c < this.width && y+c*-1 < this.height){
					if(this.boardData[x+c][y+c*-1] == fID){
						num++;
					}
				}
		}
		
		if(num >= 4){
			return true;
		}
		
		return false;
	}
	
	private boolean horizontalCheck(int x, int y, int fID){
		int num = 0;
		for(int c = -3; c < 4; c++){
				if(y+c >= 0 && y+c < this.height){
					if(this.boardData[x][y+c] == fID){
						num++;
					}
				}
		}
		
		if(num >= 4){
			return true;
		}
		
		return false;
	}
	
	private boolean verticalCheck(int x, int y, int fID){
		int num = 0;
		for(int c = -3; c < 4; c++){
				if(x+c >= 0 && x+c < this.width){
					if(this.boardData[x+c][y] == fID){
						num++;
					}
				}
		}
		
		if(num >= 4){
			return true;
		}
		
		return false;
	}
	
	public Dimension getSize(){
		return new Dimension(this.width, this.height);
	}

	public boolean isWon() {
		return this.won;
	}

}
