package com.android.mina.minaserver;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;


import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;


public class MinaTool {
	/**
	 *组装数据称字节数组 
	 * @param type
	 * @param msg
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public static byte[] packageDatas(byte type,String msg) throws UnsupportedEncodingException {
		//获取content内容数组
		byte[] content=msg.getBytes("UTF-8");
		//4个字节表示长度 1个字节表示类型 content长度
		byte[] result=new byte[content.length+5];
		//Content 字节长度 需要加一个字节作为类型
		byte[] length=intToByte(content.length+1);
		//拷贝长度字节
		System.arraycopy(length, 0, result,0 , 4);
		//设置类型
		result[4]=type;
		//拷贝content
		System.arraycopy(content, 0, result, 5, content.length);
		//返回封装后的结果
		return result;
	}


	/**
	 * 注释：int到字节数组的转换！
	 *
	 * @param number
	 * @return
	 */

	private static byte[] intToByte(int number) {
		int temp = number;
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++) {
			b[4-i-1] = new Integer(temp & 0xff).byteValue();//高位在前
			temp = temp >> 8; // 向右移8位
		}
		return b;

	}



	/**

	 * 注释：字节数组到int的转换！
	 *
	 * @param b
	 * @return
	 */
	public static int byteToInt(byte[] b) {
		int s = 0;
		int s0 = b[3] & 0xff;//高位在前  最低位为b[3]
		int s1 = b[2] & 0xff;
		int s2 = b[1] & 0xff;
		int s3 = b[0] & 0xff;
		s3 <<= 24;
		s2 <<= 16;
		s1 <<= 8;
		s = s0 | s1 | s2 | s3;
		return s;

	}


	/** 
	 * 发送消息至server 
	 * @throws UnsupportedEncodingException 
	 * return 发送结果
	 */   
	public static boolean sendMsg(IoSession session, byte type,Serializable obj) throws UnsupportedEncodingException  
	{   
		if (obj==null) {
			obj="{\"status_codes\":\""+StatusCode.STATUS_ERROR+"\",\"reason\":\"服务端发生异常\"}";
		}
		if (session!=null&&session.isConnected()) {
			MsgPack msgPack=PackageTool.packMsg(obj);
			if (msgPack==null) return false;
			session.write(msgPack);
			return true;
		}
		return false;

	}
}
