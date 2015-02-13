package com.android.mina.test.client;

import com.android.mina.client.MinaClient;
import com.android.mina.test.AbstractTestService;
import com.android.mina.test.TestBean;

public class ClientTest {
	
	public static void main(String[] args) {
		MinaClient.clientStart();
		TestBean testBean=new TestBean();
		testBean.setAge(10);
		testBean.setName("LinJ");
		AbstractTestService testService=new ClientTestService();
		testService.getAge(testBean);
		testService.sayHello(testBean);
	}

}
