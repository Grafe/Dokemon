package com.dokemon.dokemon;

import java.util.Random;

import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

import com.dokemon.dokemon.entity.MainPlayer;
import com.dokemon.dokemon.net.NetClient;
import com.dokemon.dokemon.ui.UIChat;

public class Dokemon extends BasicGame{
	public Dokemon dokemon;
	
	private MainPlayer mainPlayer;
	private NetClient netClient;
	
	// UI
	private UIChat uiChat;
	
	public Dokemon(String title) {
		super(title);
		
		dokemon = this;
	}

	@Override
	public void init(GameContainer container) throws SlickException {
		Random rand = new Random();
		int randomNum = rand.nextInt((1000 - 1) + 1) + 1;
		this.mainPlayer = new MainPlayer("Frank" + randomNum, 0);
		
		this.uiChat = new UIChat();

		this.netClient = new NetClient();
	}

	@Override
	public void update(GameContainer container, int delta) throws SlickException {
		this.uiChat.update(container, delta);
	}
	
	@Override
	public void render(GameContainer container, Graphics g) throws SlickException {		
		this.uiChat.render(container, g);
	}
	
	public MainPlayer getMainPlayer() {
		return this.mainPlayer;
	}

	public UIChat getUIChat() {
		return this.uiChat;
	}
}
