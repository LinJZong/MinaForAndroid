package com.android.mina.test.server;

import com.android.mina.minaserver.MinaServer;


public class ServerTest {

	public static void main(String[] args) {
		//启动mina服务器
		if (MinaServer.serverStart()) {
			MinaServer.addService(new ServerTestService());
			System.err.println("netty start success");
		}else {
			System.err.println("netty start fail");
		}
		new Thread(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				while(true){
					System.err.println("count:"+MinaServer.getAcceptor().getManagedSessionCount());
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
