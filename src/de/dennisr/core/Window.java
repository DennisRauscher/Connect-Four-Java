package de.dennisr.core;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Dimension;

import javax.swing.JFrame;

public class Window extends Canvas{

	private static final long serialVersionUID = 1L;
	
	private JFrame frame;
	
	public Window(){
		
		this.setMinimumSize(new Dimension(GameCore.WIDTH * GameCore.SCALE, GameCore.HEIGHT * GameCore.SCALE));
		this.setMaximumSize(new Dimension(GameCore.WIDTH * GameCore.SCALE, GameCore.HEIGHT * GameCore.SCALE));
		this.setPreferredSize(new Dimension(GameCore.WIDTH * GameCore.SCALE, GameCore.HEIGHT * GameCore.SCALE));

		frame = new JFrame(GameCore.NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		frame.setIconImage(GameCore.getInstance().getImageFromRes("/gfx/ico.jpg"));
		
		GameCore.WIN_WIDTH = this.getWidth();
		GameCore.WIN_HEIGHT = this.getHeight();
	}

}
