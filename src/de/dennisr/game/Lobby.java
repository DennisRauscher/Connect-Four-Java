package de.dennisr.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import de.dennisr.core.GameCore;
import de.dennisr.engine.Drawable;
import de.dennisr.engine.Updateable;
import de.dennisr.gui.Font;

public class Lobby implements Drawable, Updateable{

	private LobbySettings ls;

	private Player.Fraction currentPlayer;
	private Board board;
	private BufferedImage background, fadeOut;
	
	private Player.Fraction winner;
	private boolean gameOver = false;
	private Random random;
	private Font curPlayerFont;
	
	public Lobby(){
		this.ls = new LobbySettings(true, false);
		this.random = new Random();
		curPlayerFont = new Font();
		
		this.determCurrentPlayer();
		this.board = new Board(this, 45, GameCore.WIN_WIDTH/2 - (int)(45*7)/2, GameCore.WIN_HEIGHT/2 - (int)(45*6)/2, 7, 6);
		this.background = GameCore.getInstance().getImageFromRes("/gfx/bg.png");
		this.fadeOut = GameCore.getInstance().getImageFromRes("/gfx/fadeOut.png");
	}

	@Override
	public void Update() {
		this.board.Update();
		
	}

	@Override
	public void Draw(Graphics2D g) {
		g.drawImage(this.background, 0, 0, GameCore.WIN_WIDTH, GameCore.WIN_HEIGHT, null);
		this.board.Draw(g);
		
		curPlayerFont.render(g, GameCore.WIN_WIDTH - 75 - curPlayerFont.getCenter()[0], 50);
		g.drawImage(currentPlayer.instance.getCoinSprite(), GameCore.WIN_WIDTH - 100, 70, 50, 50, null);
		
		if(this.gameOver){
			g.drawImage(this.fadeOut, 0, 0, GameCore.WIN_WIDTH, GameCore.WIN_HEIGHT, null);
			drawEndscreen(g);
		}
	}
	
	private void drawEndscreen(Graphics2D g) {
		Font winnerName = new Font("Player" + this.winner.instance.getID() + " won!");
		winnerName.setScale(2);
		
		Font escapeAdd = new Font("Press [ESC] to go back to menu.");
		escapeAdd.setScale(2);
		
		Font advice = new Font("Press [SPACE] to restart the match.");
		advice.setScale(2);
		
		g.drawImage(this.winner.instance.getCoinSprite(), GameCore.WIN_WIDTH/2 - 50, GameCore.WIN_HEIGHT/2 - 50, 100, 100, null);
		winnerName.render(g, GameCore.WIN_WIDTH/2 - winnerName.getCenter()[0], GameCore.WIN_HEIGHT/2 - 100);
		escapeAdd.render(g, GameCore.WIN_WIDTH/2 - escapeAdd.getCenter()[0], GameCore.WIN_HEIGHT/2 + 80);
		advice.render(g, GameCore.WIN_WIDTH/2 - advice.getCenter()[0], GameCore.WIN_HEIGHT/2 + 120);
	}

	public void execSelection(int selection){
		this.placeCoin(this.currentPlayer, selection);
	}
	
	private void placeCoin(Player.Fraction currentPlayer, int placePos) {
		int lowestY = this.board.getLowestY(placePos);
		if(lowestY == -1){
			// Column is full
			return;
		}else{
			this.board.setCoin(placePos, lowestY, currentPlayer);
			this.checkForWin();
			this.changePlayer();
		}
	}
	
	private void checkForWin() {
		if(board.isWon()){
			GameCore.getInstance().restartCurrent();
		}
	}
	
	private void changePlayer(){
		if(currentPlayer == Player.Fraction.RED){
			currentPlayer = Player.Fraction.BLUE;
			curPlayerFont.setText("Player " + Player.Fraction.BLUE.instance.getID());
		}else{
			currentPlayer = Player.Fraction.RED;
			curPlayerFont.setText("Player " + Player.Fraction.RED.instance.getID());
		}
	}

	private void determCurrentPlayer() {
		if(this.random.nextInt(2) == 1){
			this.currentPlayer = Player.Fraction.RED;
			curPlayerFont.setText("Player " + Player.Fraction.RED.instance.getID());
		}else{
			this.currentPlayer = Player.Fraction.BLUE;
			curPlayerFont.setText("Player " + Player.Fraction.BLUE.instance.getID());
		}
	}
	
	public Player.Fraction getCurrentPlayer(){
		return this.currentPlayer;
	}
	
	public void setLobbySettings(LobbySettings ls){
		this.ls = ls;
	}
	
	public LobbySettings getLobbySettings(){
		return this.ls;
	}

	public boolean isGameOver() {
		return gameOver;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public Player.Fraction getWinner() {
		return winner;
	}

	public void setWinner(Player.Fraction winner) {
		this.winner = winner;
	}

}