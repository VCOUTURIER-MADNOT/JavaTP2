import java.lang.reflect.Method;

import util.MethodInvoker;

public class Main {
	
	public static void main(String[] args)
	{
//		Map<String, Map<String, String>> methods = API.getMethods(true, "user001:user001password");
//		
//		Set<String> set = methods.keySet();
//		
//		for (String s : set)
//		{
//			System.out.println(s + " " + methods.get(s));
//		}		
		
		Class c = null;
		try {
			c = Class.forName("server.API");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		Method m = null;
		
		try {
			m = c.getMethod("testMethod", int.class, String.class);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		
		MethodInvoker mp = new MethodInvoker(m, 1, "test");
		
		Object obj = mp.invokeMethod();
		
		System.out.print(util.Serialization.ResultToXML(obj));
	}
}
