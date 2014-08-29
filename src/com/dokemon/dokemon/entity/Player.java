package com.dokemon.dokemon.entity;

import java.util.HashSet;
import java.util.Set;

public class Player {
	private static Set<Player> players = new HashSet<Player>();
	
	private String name;
	private int serverId;
	
	public Player(String name, int serverId) {
		players.add(this);
		
		this.name = name;
		this.serverId = serverId;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setServerId(int serverId) {
		this.serverId = serverId;
	}

	public static Player get(int serverId) {
		for (Player player : players) {
			if(player.serverId == serverId) {
				return player;
			}
		}
		
		return null;
	}
}
