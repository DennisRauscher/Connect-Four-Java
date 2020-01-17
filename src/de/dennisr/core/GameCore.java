package de.dennisr.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import de.dennisr.engine.Drawable;
import de.dennisr.engine.Time;
import de.dennisr.engine.Updateable;
import de.dennisr.game.Lobby;
import de.dennisr.game.LobbySettings;
import de.dennisr.game.Player;
import de.dennisr.gui.GUI;
import de.dennisr.gui.MainMenuGUI;
import de.dennisr.managers.InputManager;

public class GameCore implements Runnable{

	private static GameCore instance;
	
	public static GameCore getInstance(){
		if(instance == null){
			instance = new GameCore();
		}
		
		return instance;
	}
	
	public static final String NAME = "4 Gewinnt";
	
	/* Screen resolution */
	public static final int WIDTH = 160;
	public static final int HEIGHT = WIDTH/12*9;
	public static int WIN_WIDTH, WIN_HEIGHT;
	public static final int SCALE = 4;
	
	private Window gameWindow;
	private InputManager inputManager;
	
	private boolean running = false, inGame = false;
	private Lobby lobby;
	private GUI currentGUI;
	
	public void start(){
		running = true;
		gameWindow = new Window();
		inputManager = new InputManager(gameWindow);
		
		lobby = new Lobby();
		
		currentGUI = new MainMenuGUI();
		
		Thread game = new Thread(this);
		game.start();
	}
	
	public void restartCurrent(){
		inputManager = new InputManager(gameWindow);
		LobbySettings ls = this.lobby.getLobbySettings();
		lobby = new Lobby();
		lobby.setLobbySettings(ls);
	}
	
	public void restartGame(){
		inputManager = new InputManager(gameWindow);
		
		this.inGame = false;
		
		lobby = new Lobby();
		
		currentGUI = new MainMenuGUI();
	}
	
	@Override
	public void run() {
		long lastLoopTime = System.nanoTime();
	    final int TARGET_FPS = 60;
	    final long OPTIMAL_TIME = 1000000000 / TARGET_FPS;
	    long lastFpsTime = 0;
	    
		while(running){
			long now = System.nanoTime();
	        long updateLength = now - lastLoopTime;
	        lastLoopTime = now;
	        Time.DELTATIME = updateLength / ((double)OPTIMAL_TIME);

	        lastFpsTime += updateLength;
	        if(lastFpsTime >= 1000000000){
	            lastFpsTime = 0;
	        }

	        this.update();

	        this.draw();

	        try{
	            Thread.sleep(10);
	        }catch(Exception e){
	        }
		}
		
		System.exit(0);
		
	}
	
	private void draw(){
		BufferStrategy bs = this.gameWindow.getBufferStrategy();
		
		if(bs == null){
			this.gameWindow.createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D)bs.getDrawGraphics();
		
		g.clearRect(0, 0, this.gameWindow.getWidth(), this.gameWindow.getHeight());
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, this.gameWindow.getWidth(), this.gameWindow.getHeight());
		
		/* draw */
		if(this.inGame){
			this.lobby.Draw(g);
		}else{
			this.currentGUI.Draw(g);
		}
		
		g.dispose();
		bs.show();
	}
	
	private void update(){
		if(this.inGame){
			this.lobby.Update();
		}else{
			this.currentGUI.Update();
		}
	}
	
	public BufferedImage getImageFromRes(String path){
		try {
			return ImageIO.read(GameCore.class.getResourceAsStream(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Window getWindow(){
		return this.gameWindow;
	}
	
	public InputManager getInputManager(){
		return this.inputManager;
	}

	public boolean isInGame() {
		return inGame;
	}

	public void setInGame(boolean inGame) {
		this.inGame = inGame;
	}

	public GUI getCurrentGUI() {
		return currentGUI;
	}

	public void setCurrentGUI(GUI currentGUI) {
		this.currentGUI = currentGUI;
	}

	public Lobby getLobby() {
		return lobby;
	}

	public void setLobby(Lobby lobby) {
		this.lobby = lobby;
	}
	
	
	
}

