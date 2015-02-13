package com.android.mina.domain;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;

import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCService;

public class PackageTool {
	/**
	 * MethodName: SerializePerson 
	 * Description: 序列化Serializable对象
	 */
	public static MsgPack packMsg(Serializable obj){
		MsgPack msgPack=new MsgPack();
		ObjectOutputStream oos=null;
		ByteArrayOutputStream baos =null;
		baos =new ByteArrayOutputStream();
		try {
			oos =new ObjectOutputStream(baos);
			oos.writeObject(obj);
			byte[] bytes=baos.toByteArray();
			msgPack.setMsgLength(bytes.length);
			msgPack.setRpcMethod(0);
			msgPack.setBytes(bytes);
			return msgPack;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}




	/**
	 * MethodName: DeserializePerson 
	 * Description: 反序列Serializable对象
	 */
	public static Serializable unpackMsg(MsgPack msgPack) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(msgPack.getBytes());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Serializable) ois.readObject();
		} catch (Exception e) {

		}
		return null;
	}

	/**
	 * MethodName: DeserializePerson 
	 * Description: 反序列Serializable对象
	 */
	public static void unpackMsgWithoutReturn(MsgPack msgPack) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(msgPack.getBytes());
			ObjectInputStream ois = new ObjectInputStream(bais);
			msgPack.setObj((Serializable) ois.readObject());
		} catch (Exception e) {

		}
	}




	/**  
	*  序列化对象同时对RPC调用的方法赋值
	*  @param service 调用的服务
	*  @param methodName 服务内的方法
	*  @param obj 序列化对象 即参数
	*  @return   
	*/
	public static MsgPack packMsg(MinaRPCService service, String methodName,
			Serializable obj) {
		// TODO Auto-generated method stub
		MsgPack msgPack=packMsg(obj);
		if(msgPack!=null){
			msgPack.setRpcMethod(methodName.hashCode());
			int type= service.getClass().getAnnotation(MinaRPCType.class).typeName().hashCode();
			msgPack.setRpcType(type);
			return msgPack;
		}	
		return null;
	}

}
