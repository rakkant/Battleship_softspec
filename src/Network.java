
import javax.xml.ws.Endpoint;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.EndPoint;


public class Network {
	
	public static final int CONNECT = 1;
	
	
	public static void register(Endpoint endpoint) {
	
		Kryo k = ((EndPoint) endpoint).getKryo();
		
		// need to register all claases that we have to send over network
	}
}