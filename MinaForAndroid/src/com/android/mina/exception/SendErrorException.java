package com.android.mina.exception;

/**
 * @author linj
 * 发送消息出错异常
 */
public class SendErrorException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SendErrorException() {
		// TODO Auto-generated constructor stub
		super("get response message time out");
	}
	
	public SendErrorException(String message){
		super(message);
	}
}
