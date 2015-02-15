package com.android.mina.exception;

/**
 * @author linj
 * 连接服务器出错异常
 */
public class ConnectErrorException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectErrorException() {
		// TODO Auto-generated constructor stub
	
		super("connect to server error");
	}
}
