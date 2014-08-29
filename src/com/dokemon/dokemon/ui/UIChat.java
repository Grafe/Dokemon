package com.dokemon.dokemon.ui;

import java.util.Stack;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

public class UIChat {
	private static final int TIME_UNTIL_FADE = 5000;
	private static final int TIME_FADE_SPAN = 2000;
	private static final int DISPLAY_MESSAGE_COUNT = 7;
	private static final int DISPLAY_MESSAGE_HEIGHT = 20;
	
	private Stack<String> messages = new Stack<String>();
	private int fadeTimer;
	
	public void addMessage(String message) {
		messages.add(message);
		fadeTimer = TIME_UNTIL_FADE + TIME_FADE_SPAN;
	}
	
	public void update (GameContainer gc, int delta) {
		if (fadeTimer > 0) 
			fadeTimer = fadeTimer - delta;
	}
	
	public void render (GameContainer gc, Graphics g) {
		int count = 1;
		
		if (fadeTimer > 0) {
			for (String message : messages) {
				if (fadeTimer < TIME_FADE_SPAN) {
					float fading = (float) 1.0 / TIME_FADE_SPAN * fadeTimer;
					g.setColor(new Color(255, 255, 255, fading));
				} else {
					g.setColor(new Color(255, 255, 255));
				}

				g.drawString(message, 10, gc.getHeight() - count * DISPLAY_MESSAGE_HEIGHT);
				
				count++;
				if (count > DISPLAY_MESSAGE_COUNT) 
					break;
			}
		}
	}
}
