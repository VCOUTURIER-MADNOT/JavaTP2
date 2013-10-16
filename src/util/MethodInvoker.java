package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MethodInvoker {
	private Method method;
	private Object[] paramsValue;
	
	public MethodInvoker(Method _method, Object ... _paramsValue)
	{
		this.method = _method;
		this.paramsValue = _paramsValue;
	}
	
	public Method getMethod()
	{
		return this.method;
	}
	
	public Object invokeMethod()
	{
		try {
			return this.method.invoke(null, this.paramsValue );
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
