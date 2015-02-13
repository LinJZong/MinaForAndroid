package com.android.mina.manager;

import java.io.Serializable;


/**
 * 消息处理器工厂类，创建消息处理器对象
 * @author linj
 *
 */
public class DispathFactory {

	/**
	 * 根据type创建消息处理对象
	 * @param type 消息类别
	 * @param content 消息内容字节数组
	 * @return Manager对象，如果为Null，代表该消息类别不是合法类别
	 */
	public MinaMsgManager createManager(byte type,Serializable content) {
		MinaMsgManager manager=null;
		switch (type) {

		default:
			break;
		}
		return manager;
	}
}
