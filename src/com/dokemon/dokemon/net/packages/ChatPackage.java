package com.dokemon.dokemon.net.packages;

import com.dokemon.dokemon.Main;
import com.dokemon.dokemon.entity.Player;
import com.esotericsoftware.kryonet.Connection;

public class ChatPackage extends NetPackage{
	private int playerId;
	public String message;
	
	@Override
	public void onRecieve(Connection connection) {
		Player player = Player.get(this.playerId);
		
		if (player != null) {
			Main.dokemon.getUIChat().addMessage(String.format("%s: %s", player.getName(), this.message));
		}
	}
}
