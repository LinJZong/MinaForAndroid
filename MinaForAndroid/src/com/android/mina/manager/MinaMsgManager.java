package com.android.mina.manager;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

/**
 * 消息处理类基类
 * @author linj
 *
 */
public abstract class MinaMsgManager {
	public Serializable getMsg(byte type,Serializable content) throws UnsupportedEncodingException{
		return excute();
	}
	//执行服务接口的方法
	public abstract String excute() throws UnsupportedEncodingException;
	public abstract String getUserName();//获取用户名，用于和Netty连接绑定

}
