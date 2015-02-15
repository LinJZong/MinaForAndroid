package com.android.mina.client.asyn;

import java.io.UnsupportedEncodingException;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.android.mina.domain.MsgPack;


/**
 * @see 发送心跳包的内容
 * getResponse()---->isResponse();获取数据判断心跳事件（目的是判断是否触发心跳超时异常）
 * isRequest()----->getRequest(); 写回数据是心跳事件触发的数据（目的写回给服务器（客户端）心跳包）
 * @author Herman.Xiong
 */
public class ClientKeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{
	 private static  MsgPack HEARTBEATREQUEST;
	 static{
		 HEARTBEATREQUEST=new MsgPack();
		//请求协议
		 HEARTBEATREQUEST.setRpcMethod(0);
	}
	//心跳包内容
   
    
	/**
     * @see 返回给客户端的心跳包数据 return 返回结果才是客户端收到的心跳包数据
     * @author Herman.Xiong
     */
    public Object getRequest(IoSession session) {
    	System.out.println("发送给服务端心跳事件: " + HEARTBEATREQUEST);
        return HEARTBEATREQUEST;
    }

    /**
     * @see 接受到的客户端数据包
     * @author Herman.Xiong
     */
    public Object getResponse(IoSession session, Object request) {
        return null;
    }

    /**
     * @see 判断是否是客户端发送来的的心跳包此判断影响 KeepAliveRequestTimeoutHandler实现类判断是否心跳包发送超时
     * @author Herman.Xiong
     */
    public boolean isRequest(IoSession session, Object message) {

        return false;
    }

    /**
     * @see  判断发送信息是否是心跳数据包此判断影响 KeepAliveRequestTimeoutHandler实现类 判断是否心跳包发送超时
     * @author Herman.Xiong
     */
    public boolean isResponse(IoSession session, Object message) {
    	MsgPack msgPack=(MsgPack)message;
        if(msgPack!=null&&msgPack.getRpcMethod()==HEARTBEATREQUEST.getRpcMethod()){
        	System.out.println("服务器返回发心跳事件: " + message);
            return true;
        }
        return false;
    }
}
