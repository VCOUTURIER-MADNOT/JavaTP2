package util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import server.API;

public class MethodParam {
	private Method method;
	private ArrayList<String> paramsValue;
	
	public MethodParam(Method _method, ArrayList<String> _paramsValue)
	{
		this.method = _method;
		this.paramsValue = _paramsValue;
	}
	
	public Method getMethod()
	{
		return this.method;
	}
	
	public ArrayList<String> getParamsValue()
	{
		return this.paramsValue;
	}
	
	public void setMethod(Method _method)
	{
		this.method = _method;
	}
	
	public void setParamValue(ArrayList<String> _paramsValue)
	{
		this.paramsValue = _paramsValue;
	}
	
	public void addParamValue(String _paramValue)
	{
		this.paramsValue.add(_paramValue);
	}
	
	public void clearParamsValue()
	{
		this.paramsValue.clear();
	}
	
	public void invokeMethod()
	{
		try {
			this.method.invoke(null, this.paramsValue);
		} catch (IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
