package com.dokemon.dokemon.ui;

import java.util.concurrent.CopyOnWriteArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.KeyListener;

import com.dokemon.dokemon.net.packages.ChatPackage;
import com.dokemon.dokemon.util.Reversed;

public class UIChat {
	private static final int TIME_UNTIL_FADE = 5000;
	private static final int TIME_FADE_SPAN = 2000;
	private static final int TIME_CURSER_SHOWN = 1000;
	private static final int DISPLAY_MESSAGE_COUNT = 7;
	private static final int DISPLAY_MESSAGE_HEIGHT = 20;
	
	private CopyOnWriteArrayList<String> messages = new CopyOnWriteArrayList<String>();
	private int fadeTimer;
	
	private boolean chatMode;
	
	private String chatText;
	private int chatCursorTimer;
	
	public void addMessage(String message) {
		messages.add(message);
		fadeTimer = TIME_UNTIL_FADE + TIME_FADE_SPAN;
	}
	
	public void update (GameContainer gc, int delta) {
		Input input = gc.getInput();

		if (chatMode) {
			// Cursor
			chatCursorTimer -= delta;
			if (chatCursorTimer < 0) 
				chatCursorTimer = TIME_CURSER_SHOWN * 2;
			
			// Send message
			if (input.isKeyPressed(Input.KEY_ENTER)) {
				if(chatText.length() > 0) {
					ChatPackage cPackage = new ChatPackage();
					cPackage.message = chatText;
					cPackage.sendTCP();
				}
				
				chatMode = false;
			}
			
			// Close chat
			if (input.isKeyPressed(Input.KEY_ESCAPE)) {
				chatMode = false;
			}
		} else {
			if (input.isKeyPressed(Input.KEY_ENTER)) {
				chatMode = true;
				chatText = "";

				input.removeAllKeyListeners();
				input.addKeyListener(new ChatKeyListener(this));
			}
		}
		
		// Fading
		if (fadeTimer > 0) 
			fadeTimer -= delta;
	}
	
	public void render (GameContainer gc, Graphics g) {
		Reversed<String> reversedMessages = Reversed.reversed(messages);
		
		if (chatMode) {
			int count = 0;
			
			g.setColor(new Color(255, 255, 255));
			
			// Chat
			for (String message : reversedMessages) {
				count++;
				if (count > DISPLAY_MESSAGE_COUNT) 
					break;
				
				g.drawString(message, 10, gc.getHeight() - DISPLAY_MESSAGE_HEIGHT - count * DISPLAY_MESSAGE_HEIGHT);
			}
			
			// ChatText
			g.drawString(this.chatText, 10, gc.getHeight() - DISPLAY_MESSAGE_HEIGHT);
			
			// Cursor
			if (chatCursorTimer > TIME_CURSER_SHOWN) {
				float cursorX = 10 + g.getFont().getWidth(this.chatText);
				g.fillRect(cursorX, gc.getHeight() - DISPLAY_MESSAGE_HEIGHT + 2, 2, DISPLAY_MESSAGE_HEIGHT - 4);
			}
		} else {
			if (fadeTimer > 0) {
				int count = 0;
				
				for (String message : reversedMessages) {
					count++;
					if (count > DISPLAY_MESSAGE_COUNT) 
						break;
					
					if (fadeTimer < TIME_FADE_SPAN) {
						float fading = (float) 1.0 / TIME_FADE_SPAN * fadeTimer;
						g.setColor(new Color(255, 255, 255, fading));
					} else {
						g.setColor(new Color(255, 255, 255));
					}

					g.drawString(message, 10, gc.getHeight() - count * DISPLAY_MESSAGE_HEIGHT);
				}
			}
		}
	}

	class ChatKeyListener implements KeyListener {		
		private UIChat uiChat;
		
		public ChatKeyListener(UIChat uiChat) {
			this.uiChat = uiChat;
		}

		@Override
		public void keyPressed(int key, char c) {
			this.uiChat.chatText += c;
			
			System.out.println(Input.getKeyName(key));
			
			if (key == Input.KEY_BACK && this.uiChat.chatText.length() > 0) {
				this.uiChat.chatText = this.uiChat.chatText.substring(0, this.uiChat.chatText.length() - 2);
			}
		}

		@Override
		public void keyReleased(int key, char c) {
			
		}

		@Override
		public boolean isAcceptingInput() {
			return true;
		}
		
		@Override
		public void inputEnded() {}

		@Override
		public void inputStarted() {}

		@Override
		public void setInput(Input input) {}
	}
}
