package com.android.mina.client;

import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;

import com.android.mina.domain.MsgPack;




public class MinaHandler extends IoHandlerAdapter{


	@Override
	public void sessionCreated(IoSession session) {
		System.out.println(session.getRemoteAddress().toString());
	}

	@Override
	public void messageReceived(IoSession session, Object message)  {
		try {
			MsgPack mp=(MsgPack)message;
			System.out.println("收到客户端数据messageReceived----------："+ mp);
			byte type=(byte)mp.getRpcMethod();

			
		} catch (Exception e) {
			e.printStackTrace();
			//处理返回结果出现异常，则关闭对话框
			
		}
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable e)
			throws Exception {
		// Close the connection when an exception is raised.
		System.err.println("Unexpected exception from downstream.");
	}


	@Override
	public void sessionClosed(IoSession session) throws Exception {
		// TODO Auto-generated method stub
		super.sessionClosed(session);
	}
	
	
}
