package com.android.mina.client;

import org.apache.mina.core.session.IoSession;
import com.android.mina.domain.MsgPack;
import com.android.mina.exception.ConnectErrorException;
import com.android.mina.exception.SendErrorException;

public abstract class MinaClient {
	protected MinaClient(){
		
	}
	protected  abstract IoSession clientStart();

	
	/**  
	*  发送消息至服务端
	*  @param msgPack 发送的数据包
	*  @return 返回的数据包
	*  @throws SendErrorException 发送或接收失败异常
	*  @throws ConnectErrorException   连接服务器失败异常
	*/
	public abstract MsgPack sendMsg(MsgPack msgPack) throws  SendErrorException,ConnectErrorException;
}
