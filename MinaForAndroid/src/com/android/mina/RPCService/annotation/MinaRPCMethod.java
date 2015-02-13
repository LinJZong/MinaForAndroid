package com.android.mina.RPCService.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** 
* @ClassName: MinaRPCMethod 
* @Description: 表面可被RPC调用的方法  
* @author LinJ
* @date 2015-2-13 下午5:17:01 
*  
*/
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface MinaRPCMethod{
}