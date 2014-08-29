package com.dokemon.dokemon.net.packages;

import com.dokemon.dokemon.Main;
import com.dokemon.dokemon.entity.MainPlayer;
import com.dokemon.dokemon.entity.Player;
import com.esotericsoftware.kryonet.Connection;

public class PlayerLoginPackage extends NetPackage{
	public String name;
	public int serverId;

	@Override
	public void onRecieve(Connection connection) {
		MainPlayer mainPlayer = Main.dokemon.getMainPlayer();
		
		if(mainPlayer.getName().equals(this.name)){
			mainPlayer.setServerId(this.serverId);
		} else {
			Player player = Player.get(this.serverId);

			if(player == null){
				player = new Player (this.name, this.serverId);
				
				Main.dokemon.getUIChat().addMessage(String.format("Player '%s' logged in!", this.name));
			}
		}
	}
}