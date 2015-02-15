package com.android.mina.RPCService.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.client.MinaClient;
import com.android.mina.client.asyn.MinaAsynClient;
import com.android.mina.client.syn.MinaSynClient;
import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;

/** 
 * @ClassName: MinaRPCService 
 * @Description:  RPC服务父类，所有RPC服务必须继承该类
 * @author LinJ
 * @date 2015-2-13 下午5:20:28 
 *  
 */
public class MinaRPCClientService extends MinaRPCService{

	private final MinaClient client;
	public MinaRPCClientService() {
		super();
		client=MinaSynClient.obtain();
	}

	/**  
	 *  发送信息至服务端
	 *  @param obj   
	 */
	public Serializable sendMsg(Serializable obj){
		StackTraceElement[] elements=Thread.currentThread().getStackTrace();
		elements=Thread.currentThread().getStackTrace();
		if(elements.length<3)
			throw new RuntimeException("Illegal method");
		String methodName=elements[2].getMethodName();
		if(methodMap.get(methodName.hashCode())==null)
			throw new RuntimeException("Illegal method name");
		MsgPack msgPack=PackageTool.packMsg(this,methodName,obj);
		MsgPack object=client.sendMsg(msgPack);
		Serializable result=PackageTool.unpackMsg((MsgPack)object);
		return result;
	}
}
