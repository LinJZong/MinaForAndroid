package com.android.mina.minaserver;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.HashMap;

import net.sf.json.util.NewBeanInstanceStrategy;

import org.apache.mina.core.filterchain.DefaultIoFilterChainBuilder;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCServerService;
import com.android.mina.RPCService.service.MinaRPCService;
import com.android.mina.protocol.MsgProtocol;

/**
 * @see 服务器启动类
 * @file MinaServer.java
 * @version 1.0
 * @since jdk1.6,mina 2.0
 */
public class MinaServer {
	//定义监听端口 
	private static final int PORT = 4888;
	//30秒后超时 
	private static final int IDELTIMEOUT = 60;
	//15秒发送一次心跳包
	private static final int HEARTBEATRATE = 60;

	private static SocketAcceptor acceptor;

	private static final HashMap<Integer,MinaRPCService> services=new HashMap<Integer, MinaRPCService>();
	private static final HashMap<Integer,HashMap<Integer,Method>> methods=new HashMap<Integer, HashMap<Integer,Method>>();

	private MinaServer() {}

	public static SocketAcceptor getAcceptor(){
		if(null==acceptor){
			// 创建非阻塞的server端的Socket连接
			acceptor = new NioSocketAcceptor();	
			services.clear();
			methods.clear();
		}
		return acceptor;
	}

	public static boolean serverStart() {
		DefaultIoFilterChainBuilder filterChain = getAcceptor().getFilterChain();
		// 设置核心消息业务处理器
		filterChain.addLast("codec", new ProtocolCodecFilter(new MsgProtocol()));
		getAcceptor().setHandler(new MinaServerHandler());
		KeepAliveMessageFactory heartBeatFactory = new ServerKeepAliveMessageFactoryImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE);
		// 是否回发 
		heartBeat.setForwardEvent(true);
		// 发送频率 
		heartBeat.setRequestInterval(HEARTBEATRATE);
		filterChain.addLast("heartbeat", heartBeat);
		// 设置session配置，30秒内无操作进入空闲状态
		getAcceptor().getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELTIMEOUT);


		try {
			// 绑定端口
			getAcceptor().bind(new InetSocketAddress(PORT));
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return false;
	}
    
	/**  
	*  添加服务
	*  @param service   
	*/
	public static void addService(MinaRPCServerService service){
		if(service==null)
			throw new RuntimeException("service is null");
		if(!service.getClass().isAnnotationPresent(MinaRPCType.class))
			throw new RuntimeException(service.getClass().getName() +" is not MinaRPCType");
		if(!(service instanceof MinaRPCServerService))
			throw new RuntimeException(service.getClass().getName() +" is not MinaRPCServerService");
		String typeName=service.getClass().getAnnotation(MinaRPCType.class).typeName();
		if(typeName==null||typeName.trim().equals("") )
			throw new RuntimeException("empty typename is illegal");
		if(services.containsKey(typeName.hashCode())){
			throw new RuntimeException("service key is exist");
		}
		services.put(typeName.hashCode(), service);
		methods.put(typeName.hashCode(),service.methodMap);
		System.out.println("add service:"+typeName+" "+typeName.hashCode());
	}

	/**  
	*  根据服务名获取服务
	*  @param key
	*  @return   
	*/
	public static MinaRPCService getService(int key){
		return services.get(key);
	}
	
	/**  
	*  根据服务名和方法名，获取方法
	*  @param typeKey
	*  @param methodKey
	*  @return   
	*/
	public static Method getMethod(int typeKey,int methodKey){
		if(methods.containsKey(typeKey))
			return methods.get(typeKey).get(methodKey);
		return null;
	}

}
