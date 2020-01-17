package de.dennisr.gui;

import java.awt.Graphics2D;

import de.dennisr.core.GameCore;
import de.dennisr.engine.Drawable;
import de.dennisr.engine.Updateable;
import de.dennisr.managers.InputManager;

public abstract class GUI implements Drawable, Updateable{

	public abstract void onEnter();
	public abstract void onUp();
	public abstract void onDown();
	public abstract void onUpdate();
	
	private InputManager im;
	
	public GUI(){
		im = GameCore.getInstance().getInputManager();
	}
	
	@Override
	public void Update() {
		if(im.enter.isJustPressed()){
			this.onEnter();
		}
		
		if(im.up.isJustPressed()){
			this.onUp();
		}
		
		if(im.down.isJustPressed()){
			this.onDown();
		}
		
		this.onUpdate();
	}

}
