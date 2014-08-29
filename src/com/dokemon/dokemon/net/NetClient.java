package com.dokemon.dokemon.net;

import java.io.IOException;

import com.dokemon.dokemon.Main;
import com.dokemon.dokemon.net.packages.PlayerLoginPackage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.EndPoint;
import com.esotericsoftware.kryonet.Listener.ThreadedListener;

public class NetClient {

	public static Client client;

	public static Kryo kryo;

	public NetClient(){
		client = new Client();
		client.start();

		// Register the classes that will be sent over the network.
		registerKryoClasses(client);

		// Add NetListener
		client.addListener(new ThreadedListener(new NetListener()));

		// Connect
		try {
			client.connect(10000, "127.0.0.1", 54555, 54556);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		
		// Send Login
		PlayerLoginPackage playerLogin = new PlayerLoginPackage();
		playerLogin.name = Main.dokemon.getMainPlayer().getName();
		playerLogin.sendTCP();
	}


	//Kryo
	public static void registerKryoClasses (EndPoint endPoint) {
		kryo = endPoint.getKryo();

		//Initialize kryo classes
		kryo.register(PlayerLoginPackage.class);

		//TODO: Automatisieren
	}
}
