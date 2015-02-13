package com.android.mina.domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;


/** 
* @ClassName: MsgPack 
* @Description:  传输的数据包
* @author LinJ
* @date 2015-2-13 下午5:08:13 
*  
*/
public class MsgPack implements Serializable{
	/**
	 * 序列化和反序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	//数据包长度
	private int msgLength;
	//RPC对象的方法
	private int rpcMethod;
	//RPC对象的类型
	private int rpcType;
	//序列化后的字节数组
	private byte[] bytes;
	//序列化的对象，为真正需要传输的数据对象
	private Serializable obj;
	public MsgPack() {}

	public int getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		this.msgLength = msgLength;
	}

	public int getRpcMethod() {
		return rpcMethod;
	}

	public void setRpcMethod(int msgMethod) {
		this.rpcMethod = msgMethod;
	}


	public String toString() {
		return "MsgPack [msgLength=" + msgLength + ", msgMethod=" + rpcMethod
		+ ",msgStatus="+rpcType+"]";
	}

	public void setRpcType(int rpcType) {
		this.rpcType = rpcType;
	}

	public int getRpcType() {
		return rpcType;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
		this.msgLength=bytes.length;
	}

	public byte[] getBytes() {
		return bytes;
	}
	public Serializable getObj(){
		return obj;
	}
	protected void setObj(Serializable obj){
		this.obj=obj;
	}

}
