package com.android.mina.test.client;

import java.lang.reflect.Method;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.test.AbstractTestService;
import com.android.mina.test.TestBean;

public class ClientTestService extends AbstractTestService{

    @MinaRPCMethod
	public int getAge(TestBean testBean) {
		sendMsg(testBean);
		return 0;
	}
    @MinaRPCMethod
	public String sayHello(TestBean testBean) {
		// TODO Auto-generated method stub
    	sendMsg(testBean);
		return null;
	}

}
