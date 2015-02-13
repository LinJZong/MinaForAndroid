package com.android.mina.domain;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * @see 自定义数据包
 * @author Herman.Xiong
 * @date 2014年6月11日 11:31:45
 */
public class MsgPack implements Serializable{
	/**
	 * 序列化和反序列化的版本号
	 */
	private static final long serialVersionUID = 1L;
	//消息长度
	private int msgLength;
	//消息方法
	private int msgMethod;
	//消息状态
	private int msgStatus;
	//编码方式
	private  final String charset="UTF-8";
	//序列化后的字节数组
	private byte[] bytes;
	//序列化的对象
	private Serializable obj;
	public MsgPack() {}

	public int getMsgLength() {
		return msgLength;
	}

	public void setMsgLength(int msgLength) {
		this.msgLength = msgLength;
	}

	public int getMsgMethod() {
		return msgMethod;
	}

	public void setMsgMethod(int msgMethod) {
		this.msgMethod = msgMethod;
	}


	public String toString() {
		return "MsgPack [msgLength=" + msgLength + ", msgMethod=" + msgMethod
		+ ",msgStatus="+msgStatus+"]";
	}

	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}

	public int getMsgStatus() {
		return msgStatus;
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
