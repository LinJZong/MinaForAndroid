package com.android.mina.protocol;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;


/** 
* @ClassName: MsgProtocol 
* @Description:  自定义协议
* @author LinJ
* @date 2015-2-13 下午5:14:51 
*  
*/
public class MsgProtocol implements ProtocolCodecFactory{

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {  
        return new MsgProtocolDecoder();
    }  
  
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {  
        return new MsgProtocolEncoder();
    }
}
