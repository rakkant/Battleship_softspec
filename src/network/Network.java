package network;
import com.esotericsoftware.kryo.Kryo;

import com.esotericsoftware.kryonet.EndPoint;

import game.Board;
import game.Boat;
import game.Bullet;
import game.Field;
import game.Game;
import game.GameLogic;
import game.Player;
import game.Ship;


public class Network {
public static final int CONNECT = 1;
	
	
	public static void register(EndPoint endpoint) {
		Kryo k = endpoint.getKryo();
		//  need to register all classes that we want to send over network
		k.register(Game.class);
		k.register(Player.class);
		k.register(Field.class);
		k.register(Ship.class);
		k.register(Bullet.class);
		k.register(Board.class);
		k.register(GameLogic.class);
		k.register(Boat.class);
		k.register(int[][].class);
		k.register(int[].class);
		k.register(java.util.ArrayList.class);
	}
}
