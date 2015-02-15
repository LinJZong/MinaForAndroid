package com.android.mina.client.syn;

import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.filterchain.IoFilter.NextFilter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.future.ReadFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCService;
import com.android.mina.client.MinaClient;
import com.android.mina.domain.MsgPack;
import com.android.mina.exception.ConnectErrorException;
import com.android.mina.exception.SendErrorException;
import com.android.mina.protocol.MsgProtocol;

/**
 * @author linj
 * 同步通信客户端，使用该客户端将完成同步通信，接收返回值,用以RPC调用
 */
public class MinaSynClient extends MinaClient{
	private static int Method_NOT_EXIST=-1;
	private MinaSynClient() {

	}

	public static MinaSynClient obtain(){
		return new MinaSynClient();
	}

	@Override
	protected  IoSession clientStart( ){
		// 创建客户端连接器
		NioSocketConnector	connector = new NioSocketConnector(); 
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MsgProtocol()));
		connector.setConnectTimeoutMillis(10000); // 设置连接超时
		//设置同步读写
		connector.getSessionConfig().setUseReadOperation(true);
		//服务器地址和端口号
		String ip="127.0.0.1";
		int port=4888;
		connector.setDefaultRemoteAddress(new InetSocketAddress(ip,port));// 设置默认访问地址
		ConnectFuture cf = connector.connect();// 建立连接
		cf.awaitUninterruptibly();// 等待连接创建完成
		try {  
			IoSession is=cf.getSession();
			return is;
		} catch (RuntimeIoException e) {  

		}    
		return null;
	}


	/**
	 * 发送消息至server  
	 * @param activity 发送请求的activity
	 * @throws UnsupportedEncodingException 
	 */
	@Override
	public  MsgPack sendMsg(MsgPack msgPack) throws  SendErrorException,ConnectErrorException
	{  
		IoSession ioSession=clientStart();
		if(ioSession==null){
			throw new ConnectErrorException();
		}
		try{
			//开始写信息
			if(msgPack==null) throw new SendErrorException("msgPack is null");
			ioSession.write(msgPack).awaitUninterruptibly(); 
			ReadFuture readFuture = ioSession.read();
			//30秒内无回应 抛出异常
			if (readFuture.awaitUninterruptibly(30, TimeUnit.SECONDS)) {
				Object msg = readFuture.getMessage();
				if(msg==null||!(msg instanceof MsgPack)) 
					throw new SendErrorException("return message error");
				MsgPack result=(MsgPack)msg;
				//
				if(result.getRpcType()==Method_NOT_EXIST){
					throw new SendErrorException("can't find method in server");
				}
				return result;
			} else {
				//抛出异常
				throw new SendErrorException();
			}
		}catch(Exception exception){
			//抛出异常
			throw new SendErrorException(exception.toString());
		} finally {
			// 断开
			ioSession.close(true);
			ioSession.getService().dispose();
		}
	}
}
