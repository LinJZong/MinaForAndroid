package com.android.mina.minaserver;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;

import com.android.mina.domain.MsgPack;
/**
 * @see 发送心跳包的内容
 * getResponse()---->isResponse();获取数据判断心跳事件（目的是判断是否触发心跳超时异常）
 * isRequest()----->getRequest(); 写回数据是心跳事件触发的数据（目的写回给服务器（客户端）心跳包）
 * @author Herman.Xiong
 */
public class ServerKeepAliveMessageFactoryImpl implements KeepAliveMessageFactory{


    
	/**
     * @see 返回给客户端的心跳包数据 return 返回结果才是客户端收到的心跳包数据
     * @author Herman.Xiong
     */
    public Object getRequest(IoSession session) {
        return null;
    }

    /**
     * @see 接受到的客户端数据包
     * @author Herman.Xiong
     */
    public Object getResponse(IoSession session, Object request) {
    	System.out.println("response"+request);
    	return request;
    }

    /**
     * @see 判断是否是客户端发送来的的心跳包此判断影响 KeepAliveRequestTimeoutHandler实现类判断是否心跳包发送超时
     * @author Herman.Xiong
     */
    public boolean isRequest(IoSession session, Object message) {
        if(message instanceof MsgPack&&((MsgPack)message).getRpcMethod()==0){
	        return true;
	    }
        return false;
    }

    /**
     * @see  判断发送信息是否是心跳数据包此判断影响 KeepAliveRequestTimeoutHandler实现类 判断是否心跳包发送超时
     * @author Herman.Xiong
     */
    public boolean isResponse(IoSession session, Object message) {
        return false;
    }
}
