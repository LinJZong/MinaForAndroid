package com.android.mina.test;

import com.android.mina.RPCService.annotation.MinaRPCMethod;
import com.android.mina.RPCService.annotation.MinaRPCType;
import com.android.mina.RPCService.service.MinaRPCService;
@MinaRPCType(typeName = "test")
public abstract class AbstractTestService extends MinaRPCService{
	@MinaRPCMethod
	public abstract String sayHello(TestBean testBean);
	@MinaRPCMethod
	public abstract int getAge(TestBean testBean);
}
