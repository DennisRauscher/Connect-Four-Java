package de.dennisr.gui;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import de.dennisr.core.GameCore;
import de.dennisr.engine.Time;

public class MainMenuGUI extends GUI{

	private BufferedImage splash;
	private Font font;
	
	private float textScale = .99F;
	
	public MainMenuGUI() {
		this.splash = GameCore.getInstance().getImageFromRes("/gfx/splash.jpg");
		this.font = new Font("Press [enter] to start!");
		this.font.setScale(2);
	}
	
	@Override
	public void onEnter() {
		GameCore.getInstance().setCurrentGUI(new LobbyMenuGUI());
	}

	@Override
	public void onUp() {
		
	}

	@Override
	public void onDown() {
		
	}
	
	@Override
	public void onUpdate() {
		if(this.font.getWidth() <= 100)this.textScale = 1.01F;
		if(this.font.getWidth() >= 200)this.textScale = .99F;
		this.font.setWidth((int)(this.font.getWidth() * textScale));
	}
	
	@Override
	public void Draw(Graphics2D g) {
		g.drawImage(splash, 0, 0, GameCore.WIN_WIDTH, GameCore.WIN_HEIGHT, null);
		font.render(g, GameCore.WIN_WIDTH/2 - font.getCenter()[0], 300);
	}

	

}
