package com.android.mina.client.asyn;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.keepalive.KeepAliveRequestTimeoutHandler;
import org.apache.mina.filter.logging.LogLevel;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCService;
import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;
import com.android.mina.protocol.MsgProtocol;
import com.android.mina.test.TestBean;




/**
 * @see 客户端；
 * 异步通信客户端，用于与服务端保持长连接，并向服务端发送消息和接收信息
 * @author LinJ
 * @date 2014年11月11日 11:27:50
 * @version 1.0
 * @serial jdk 1.6
 */
public class MinaAsynClient {

	private static NioSocketConnector connector ;
	private static IoSession is;
	//30秒后超时 
	private static final int IDELTIMEOUT = 30;
	//15秒发送一次心跳包
	private static final int HEARTBEATRATE = 40;
	private static final HashMap<String,MinaRPCService> services=new HashMap<String, MinaRPCService>();
	public static NioSocketConnector getConnector(){
		if(null==connector){
			// 创建非阻塞的server端的Socket连接
			connector = new NioSocketConnector();
		}
		return connector;
	}

	public static IoSession getIoSession(){
		return is;
	}

	public static void clientStart( ){
		// 创建客户端连接器
		NioSocketConnector connector = getConnector(); 
	 
		connector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new MsgProtocol()));

		connector.getSessionConfig().setReceiveBufferSize(2048*5000);//接收缓冲区1M
		connector.setConnectTimeoutMillis(30000); // 设置连接超时
		connector.setHandler(new MinaClientHandler());// 设置消息处理器
		connector.getFilterChain().addFirst("reconnection", new IoFilterAdapter() {  
			@Override  
			public void sessionClosed(NextFilter nextFilter, IoSession ioSession) throws Exception {  
				for(;;){  
					try{
						is=null;//将iosession置为null
						Thread.sleep(5000);  
						ConnectFuture	future = getConnector().connect();  
						future.awaitUninterruptibly();// 等待连接创建成功  
						is = future.getSession();// 获取会话  
						if(is.isConnected()){  

							System.out.println("断线重连["+ getConnector().getDefaultRemoteAddress().getHostName() +":"+ getConnector().getDefaultRemoteAddress().getPort()+"]成功");  
							break;  
						}  else {
							System.out.println("断线重连["+ getConnector().getDefaultRemoteAddress().getHostName() +":"+ getConnector().getDefaultRemoteAddress().getPort()+"]失败");  
							break;  
						}
					}catch(Exception ex){
						is=null;//将iosession置为null
						System.out.println("重连服务器登录失败,5秒再连接一次:" + ex.getMessage());  
					}  
				}
			}  
		});

		KeepAliveMessageFactory heartBeatFactory = new ClientKeepAliveMessageFactoryImpl();
		KeepAliveRequestTimeoutHandler heartBeatHandler = new KeepAliveRequestTimeoutHandlerImpl();
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE,heartBeatHandler);
		// 是否回发 
		heartBeat.setForwardEvent(true);
		// 发送频率 
		heartBeat.setRequestInterval(HEARTBEATRATE);
		//5秒后没接到回应，触发heartBeatHandler
		heartBeat.setRequestTimeout(30);
		connector.getFilterChain().addLast("heartbeat", heartBeat);
		connector.getSessionConfig().setBothIdleTime(30);
		// 设置session配置，30秒内无操作进入空闲状态
		connector.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, IDELTIMEOUT);
		//获取配置文件中服务器地址和端口号
		String ip="127.0.0.1";
		int port=4888;
		connector.setDefaultRemoteAddress(new InetSocketAddress(ip,port));// 设置默认访问地址
		ConnectFuture cf = connector.connect();// 建立连接
		cf.awaitUninterruptibly();// 等待连接创建完成

		for (;;) {  
			try {  
				is=cf.getSession();
				//发送连接建立请求，将session与传输过去的用户ID进行绑定
				System.out.println("连接服务端" + ip + ":" + port + "[成功]" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));  
				
				break;  
			} catch (RuntimeIoException e) {  
				is=null;//将iosession置为null
				System.out.println("连接服务端" + ip + ":" + port + "失败" + ",,时间:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + ", 连接MSG异常,请检查MSG端口、IP是否正确,MSG服务是否启动,异常内容:" + e.getMessage());  
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}// 连接失败后,重连间隔5s  
			}  
		}    

	}

	
	/**
	 * 发送消息至server  
	 * @param activity 发送请求的activity
	 * @param info 提醒的文字信息
	 * @param type 消息类别
	 * @param message 消息内容
	 * @throws UnsupportedEncodingException 
	 */
	public static boolean sendMsg(MsgPack msgPack)  
	{  
		if(getIoSession()==null){
			System.out.println("连接服务端出错,请检查网络状况");
			return false;
		}
		try{
			//开始写信息
			if(msgPack==null) return false;
			getIoSession().write(msgPack); 
			System.out.println(msgPack.toString());
			return true;
		}catch(Exception exception){
			System.err.println("连接服务端出错,请检查网络状况");
			exception.printStackTrace();
		}
		return false;
	}

	public static void addService(MinaRPCService service){
		if(service==null)
			throw new RuntimeException("service is null");
		if(!service.getClass().isAnnotationPresent(MinaRPCType.class))
			throw new RuntimeException(service.getClass().getName() +" is not MinaRPCType");
		String typeName=service.getClass().getName();

		if(services.containsKey(typeName)){
			throw new RuntimeException("service key is exist");
		}
		services.put(typeName, service);
	}


}
