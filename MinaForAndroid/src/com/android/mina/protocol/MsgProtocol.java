package com.android.mina.protocol;

import java.nio.charset.Charset;

import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFactory;
import org.apache.mina.filter.codec.ProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolEncoder;

import com.android.mina.charset.MsgProtocolDecoder;
import com.android.mina.charset.MsgProtocolEncoder;
/**
 * @see 自定义协议
 * @author Herman.Xiong
 * @date 2014年6月11日 10:30:40
 */
public class MsgProtocol implements ProtocolCodecFactory{

    public ProtocolDecoder getDecoder(IoSession session) throws Exception {  
        return new MsgProtocolDecoder();
    }  
  
    public ProtocolEncoder getEncoder(IoSession session) throws Exception {  
        return new MsgProtocolEncoder();
    }
}
