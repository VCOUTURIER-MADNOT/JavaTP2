import java.util.Map;
import java.util.Set;

import server.API;
import server.RequestHandler;

public class Main {
	
	public static void main(String[] args)
	{
		Map<String, Map<String, String>> methods = API.getMethods(true, "user001:user001password");
		
		Set<String> set = methods.keySet();
		
		for (String s : set)
		{
			System.out.println(s + " " + methods.get(s));
		}		
		
		//RequestHandler rh = new RequestHandler();
		
	}
}
