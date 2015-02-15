package com.android.mina.RPCService.service;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.client.MinaClient;
import com.android.mina.client.asyn.MinaAsynClient;
import com.android.mina.client.syn.MinaSynClient;
import com.android.mina.domain.MsgPack;
import com.android.mina.domain.PackageTool;

/** 
* @ClassName: MinaRPCService 
* @Description:  RPC服务父类，所有RPC服务必须继承该类
* @author LinJ
* @date 2015-2-13 下午5:20:28 
*  
*/
public class MinaRPCServerService extends MinaRPCService {

	public MinaRPCServerService() {

	}
	
}
