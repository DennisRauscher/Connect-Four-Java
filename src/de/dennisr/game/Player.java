package de.dennisr.game;

import java.awt.image.BufferedImage;

import de.dennisr.core.GameCore;

public class Player {
	public enum Fraction{
		BLUE(new Player(1, "/gfx/blueCoin.png")), RED(new Player(2, "/gfx/redCoin.png"));
		
		public Player instance;
		Fraction(Player p){
			this.instance = p;
		}
	}
	
	private int id = 0;
	private BufferedImage coinSprite;
	
	public Player(int id, String imageLoc){
		this.id = id;
		this.coinSprite = GameCore.getInstance().getImageFromRes(imageLoc);
	}
	
	public BufferedImage getCoinSprite(){
		return this.coinSprite;
	}
	
	public int getID(){
		return this.id;
	}
	
}
