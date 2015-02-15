package com.android.mina.test.client;

import java.io.Serializable;
import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCClientService;
import com.android.mina.test.TestServiceIterface;
import com.android.mina.test.TestBean;
@MinaRPCType(typeName = "test")
public class ClientTestService extends MinaRPCClientService implements TestServiceIterface{

	@MinaRPCMethod
	public int getAge(TestBean testBean) {
		Serializable result=sendMsg(testBean);
		if(result==null)
			return 0;
		return (Integer) result;
	}
	@MinaRPCMethod
	public String sayHello(TestBean testBean) {
		// TODO Auto-generated method stub
		Serializable result=sendMsg(testBean);
		if(result==null)
			return null;
		return (String) result;
	}
	//该方法非接口定义，用以测试能否正确抛出异常
	@MinaRPCMethod
	public String syaHello2(TestBean testBean){
		Serializable result=sendMsg(testBean);
		if(result==null)
			return null;
		return (String) result;
	}

}
