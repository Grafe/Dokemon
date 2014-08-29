package com.dokemon.dokemon;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

public class Main {
	private static Dokemon dokemon;
	private static AppGameContainer appGameContainer;
	
	public static void main(String args[]) {
		dokemon = new Dokemon("Dokemon");
		
		try {
			appGameContainer = new AppGameContainer(dokemon);
			appGameContainer.setDisplayMode(640, 480, false);
			appGameContainer.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
		
		return;
	}
}
