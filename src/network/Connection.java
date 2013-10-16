package network;

import java.io.IOException;

public interface Connection {

	String readData() throws IOException;
	void write(String str) throws IOException;
	
}
