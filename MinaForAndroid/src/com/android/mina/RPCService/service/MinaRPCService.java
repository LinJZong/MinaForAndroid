package com.android.mina.RPCService.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.client.MinaClient;
import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;

public class MinaRPCService {

	private final HashMap<Integer, Method> methodMap=new HashMap<Integer, Method>();
	public MinaRPCService() {
		Method[] methods=getClass().getMethods();
		for (Method method : methods) {
			if(method.isAnnotationPresent(MinaRPCMethod.class)){
				System.out.println(method.getName()+" "+method.getName().hashCode());
				methodMap.put(method.getName().hashCode(), method);
			}
		}
	}
	public void sendMsg(Serializable obj){
		StackTraceElement[] elements=Thread.currentThread().getStackTrace();
    	elements=Thread.currentThread().getStackTrace();
    	if(elements.length<3)
    		return;
    	String methodName=elements[2].getMethodName();
		if(methodMap.get(methodName.hashCode())==null)
			throw new RuntimeException("Illegal method name");
		MsgPack msgPack=PackageTool.packMsg(this,methodName,obj);
		MinaClient.sendMsg(msgPack);
	}
}
