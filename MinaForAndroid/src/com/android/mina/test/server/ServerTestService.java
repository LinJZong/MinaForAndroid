package com.android.mina.test.server;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCServerService;
import com.android.mina.client.MinaClient;
import com.android.mina.test.TestServiceIterface;
import com.android.mina.test.TestBean;
/** 
* @ClassName: ServerTestService 
* @Description:  测试用自定义服务，服务需继承MinaRPCServerService，同时继承需要实现功能的接口
* @author LinJ
* @date 2015-2-15 下午1:24:10 
*  
*/
@MinaRPCType(typeName = "test")
public class ServerTestService extends MinaRPCServerService implements TestServiceIterface{

	@MinaRPCMethod
	public String sayHello(TestBean testBean){
		if(testBean!=null)
			return "hello "+testBean.getName();
		return "hello world";
	}
	@MinaRPCMethod
	public int getAge(TestBean testBean){
		if(testBean!=null)
			return testBean.getAge();
		return 0;
	}
}
