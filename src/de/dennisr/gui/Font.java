package de.dennisr.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.dennisr.core.GameCore;

public class Font {

	private static String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ!?%0123456789-,:;-[]";
	private BufferedImage map, img;
	private int width, height, scale = 1;
	
	public Font(String msg){
		this.map = GameCore.getInstance().getImageFromRes("/gfx/font.png");
		
		if(msg != "")setText(msg);
	}
	
	public Font(){
		this.map = GameCore.getInstance().getImageFromRes("/gfx/font.png");
	}
	
	
	public void setText(String msg){
		createPreBuffer(msg);
		
		this.width = img.getWidth();
		this.height = img.getHeight();
	}
	
	private void createPreBuffer(String msg) {
		img = new BufferedImage (msg.length() * 8, 8, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g = img.createGraphics();
	    
	    msg = msg.toUpperCase();
		
		for(int i = 0; i < msg.length(); i++){
			int charIndex = chars.indexOf(msg.charAt(i));
			if(charIndex >= 0){
				BufferedImage bf = this.map.getSubimage(charIndex*8, 0, 8, 8);
				g.drawImage(bf, (i*8), 0, 8, 8, null);
			}
		}
		
		g.dispose();
	}

	public void render(Graphics2D g, int x, int y){
		g.drawImage(img, x, y, width*scale, height*scale, null);
	}
	
	public int[] getCenter(){
		return new int[]{width*scale/2, height*scale/2};
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}
	
	
	
}
