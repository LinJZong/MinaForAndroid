package com.android.mina.charset;

import java.io.File;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.AttributeKey;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.android.mina.domain.MsgPack;
/**
 * @see 协议解码
 * @author Herman.Xiong
 * @date 2014年6月11日 16:47:24
 */
public class MsgProtocolDecoder extends CumulativeProtocolDecoder  {    
	private final AttributeKey CONTEXT =new AttributeKey(getClass(), "context" );
	public MsgProtocolDecoder() {  
	}  


	public void dispose(IoSession arg0) throws Exception {

	}

	public void finishDecode(IoSession arg0, ProtocolDecoderOutput arg1)
	throws Exception {

	}



	protected boolean doDecode(IoSession session, IoBuffer ioBuffer, ProtocolDecoderOutput out) throws Exception {
		//高位字节放在内存的高端
		ioBuffer.order(ByteOrder.LITTLE_ENDIAN); 
		MsgPack mp = (MsgPack) session.getAttribute(CONTEXT); // 从session对象中获取“xhs-upload”属性值 
		if(null==mp){
			if (ioBuffer.remaining() >= 12) {
				//取消息体长度
				int msgLength = ioBuffer.getInt(); 
				int msgMethod = ioBuffer.getInt();
				int msgStatus=ioBuffer.getInt();
				mp=new MsgPack();
				mp.setMsgLength(msgLength);
				mp.setMsgMethod(msgMethod);
				mp.setMsgStatus(msgStatus);
				session.setAttribute(CONTEXT,mp);
				if(msgLength==0){
					session.removeAttribute(CONTEXT);	
					out.write(mp);
				}
				return true;
			}
			return false;
		}
		if(ioBuffer.remaining()>=mp.getMsgLength()){
			byte [] msgPack=new byte[mp.getMsgLength()];
			ioBuffer.get(msgPack);
			mp.setBytes(msgPack);
			session.removeAttribute(CONTEXT);	
			out.write(mp);
			return true;
		}
		return false;
	}   

}