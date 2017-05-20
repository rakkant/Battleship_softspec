package network;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;



public class Network {
public static final int CONNECT = 1;
	
	
	public static void register(EndPoint endpoint) {
		Kryo k = endpoint.getKryo();
		//  need to register all classes that we want to send over network
	}
}
