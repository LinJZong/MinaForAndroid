package com.android.mina.test.client;

import com.android.mina.test.TestServiceIterface;
import com.android.mina.test.TestBean;

public class ClientTest {
	
	public static void main(String[] args) {
		TestBean testBean=new TestBean();
		testBean.setAge(10);
		testBean.setName(null);
		ClientTestService testService=new ClientTestService();
		int myAge=testService.getAge(testBean);
		String returnString=testService.sayHello(testBean);
		System.out.println(myAge+" "+returnString);
		//测试调用服务端未定义的方法，抛出异常
//		 returnString=testService.syaHello2(testBean);
//		System.out.println(myAge+" "+returnString);
	}

}
