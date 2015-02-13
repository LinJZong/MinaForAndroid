package com.android.mina.minaserver;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map.Entry;

import javax.swing.RepaintManager;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

import com.android.mina.RPCService.service.MinaRPCService;
import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;
import com.android.mina.manager.DispathFactory;
import com.android.mina.manager.MinaMsgManager;
import com.android.mina.test.TestBean;


public class MinaHandler extends IoHandlerAdapter{
	public final DispathFactory factory;
	public MinaHandler(){
		factory=new DispathFactory();
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {

	}
	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.err.println("关闭当前session: "+session.getId()+session.getRemoteAddress());

	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		System.err.println("创建一个新连接："+ session.getRemoteAddress()+"  id:  "+session.getId());
	}
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.err.println("当前连接处于空闲状态---关闭当前长连接"+ session.getRemoteAddress()+" "+ status);
		CloseFuture closeFuture = session.close(true);
		closeFuture.addListener(new IoFutureListener<IoFuture>() {
			public void operationComplete(IoFuture future) {
				if (future instanceof CloseFuture) {
					((CloseFuture) future).setClosed();
					System.out.println("sessionClosed CloseFuture setClosed-->"+ future.getSession().getId());
				}
			}
		});
	}
	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.err.println("打开一个session id："+ session.getId()+"  空闲连接个数IdleCount：  "+ session.getBothIdleCount());
	}
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.err.println("服务器发生异常："+ cause.toString());
	}
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		if(!(message instanceof MsgPack)){
			System.err.println(message);
			return;
		}
		MsgPack mp=(MsgPack)message;
		System.err.println("收到客户端数据messageReceived----------："+ mp);
		try
		{

			MinaRPCService service= MinaServer.getService(mp.getMsgStatus());
			Method method=MinaServer.getMethod(mp.getMsgStatus(), mp.getMsgMethod());
			if(service!=null&&method!=null){
				Serializable obj=PackageTool.unpackMsg(mp);
				Object result=method.invoke(service,obj);
				System.out.println(result);
			}
			
		}
		catch(Exception ex){
		}
	}
}