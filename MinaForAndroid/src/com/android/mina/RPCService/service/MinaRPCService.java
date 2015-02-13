package com.android.mina.RPCService.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.client.MinaClient;
import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;

/** 
* @ClassName: MinaRPCService 
* @Description:  RPC服务父类，所有RPC服务必须继承该类
* @author LinJ
* @date 2015-2-13 下午5:20:28 
*  
*/
public class MinaRPCService {

	/**  存放子类所有RPC方法的Map */ 
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
	
	/**  
	*  发送信息至服务端
	*  @param obj   
	*/
	public void sendMsg(Serializable obj){
		//获取当前调用sendMsg的方法的方法名
		StackTraceElement[] elements=Thread.currentThread().getStackTrace();
    	elements=Thread.currentThread().getStackTrace();
    	if(elements.length<3)
    		return;
    	String methodName=elements[2].getMethodName();
    	//判断该方法是否是RPC方法，如不是，抛出异常
		if(methodMap.get(methodName.hashCode())==null)
			throw new RuntimeException("Illegal method name");
		//封装消息包，添加服务名和方法名
		MsgPack msgPack=PackageTool.packMsg(this,methodName,obj);
		MinaClient.sendMsg(msgPack);
	}
}
