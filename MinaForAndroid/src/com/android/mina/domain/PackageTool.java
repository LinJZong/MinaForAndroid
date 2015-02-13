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
			msgPack.setMsgMethod(0);
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
	private static void unpackMsgWithoutReturn(MsgPack msgPack) {
		ByteArrayInputStream bais = null;
		try {
			// 反序列化
			bais = new ByteArrayInputStream(msgPack.getBytes());
			ObjectInputStream ois = new ObjectInputStream(bais);
			msgPack.setObj((Serializable) ois.readObject());
		} catch (Exception e) {

		}
	}




	public static MsgPack packMsg(MinaRPCService service, String methodName,
			Serializable obj) {
		// TODO Auto-generated method stub
		MsgPack msgPack=packMsg(obj);
		if(msgPack!=null){
			msgPack.setMsgMethod(methodName.hashCode());
			int type= service.getClass().getAnnotation(MinaRPCType.class).typeName().hashCode();
			msgPack.setMsgStatus(type);
			return msgPack;
		}	
		return null;
	}

}
