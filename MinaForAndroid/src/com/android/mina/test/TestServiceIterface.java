package com.android.mina.test;

/** 
* @ClassName: TestServiceIterface 
* @Description:  测试用自定义接口
* @author LinJ
* @date 2015-2-15 下午1:24:00 
*  
*/
public  interface TestServiceIterface{

	public abstract String sayHello(TestBean testBean);

	public abstract int getAge(TestBean testBean);
}
