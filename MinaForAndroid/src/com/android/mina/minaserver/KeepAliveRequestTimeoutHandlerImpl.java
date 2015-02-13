package com.android.mina.minaserver;

import org.apache.mina.core.future.CloseFuture;
import org.apache.mina.core.future.IoFuture;
import org.apache.mina.core.future.IoFutureListener;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
/**
 * @see 当心跳超时时的处理，也可以用默认处理 这里like
 * KeepAliveRequestTimeoutHandler.LOG的处理
 * @author Herman.Xiong
 */
public class KeepAliveRequestTimeoutHandlerImpl  implements KeepAliveRequestTimeoutHandler {
	/**
     * @see org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler心跳超时处理
     * @author Herman.Xiong
     */
    public void keepAliveRequestTimedOut(KeepAliveFilter filter,
            IoSession session) throws Exception {
        System.out.println("服务器端心跳包发送超时处理(即长时间没有发送（接受）心跳包)---关闭当前长连接");
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
}
