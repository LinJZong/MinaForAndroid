package com.android.mina.RPCService.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @ClassName: MinaRPCType 
* @Description:  表面RPC服务的注解
* @author LinJ
* @date 2015-2-13 下午5:15:47 
*  
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface MinaRPCType {
	/**  
	*  服务名，不能为空且必须唯一，否则在添加服务时抛出异常
	*  @return   
	*/
	String typeName();
}



