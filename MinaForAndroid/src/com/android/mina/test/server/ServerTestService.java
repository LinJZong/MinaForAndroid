package com.android.mina.test.server;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.test.AbstractTestService;
import com.android.mina.test.TestBean;

public class ServerTestService extends AbstractTestService{
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
