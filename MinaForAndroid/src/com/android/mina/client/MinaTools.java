package com.android.mina.client;

import java.nio.charset.Charset;
/**
 * netty 通信工具类 
 * @author Administrator
 *
 */
public class MinaTools {
	/**
	 *组装数据称字节数组 
	 * @param type
	 * @param msg
	 * @return
	 */
	public static byte[] packageDatas(byte type,String msg) {
		//获取content内容数组
		byte[] content=msg.getBytes(Charset.forName("UTF-8"));
		//4个字节表示长度 1个字节表示类型 content长度
		byte[] result=new byte[content.length+5];
		//Content 字节长度 需要加一个字节作为类型
		byte[] length=intToByte(content.length+1);
		//拷贝长度字节
		System.arraycopy(length, 0, result,0 , 4);
		//设置类型
		result[4]=type;
		//拷贝content
		System.arraycopy(content, 0, result, 5, content.length);
		//返回封装后的结果
		return result;
	}
	
	
	/**
	 *组装数据成字节数组 
	 * @param type
	 * @param msg
	 * @return
	 */
	public static byte[] packageDatas2(String msg) {
		//获取content内容数组
		byte[] content=msg.getBytes(Charset.forName("UTF-8"));
		//4个字节表示长度 1个字节表示类型 content长度
		byte[] result=new byte[content.length+4];
		//Content 字节长度 需要加一个字节作为类型
		byte[] length=intToByte(content.length);
		//拷贝长度字节
		System.arraycopy(length, 0, result,0 , 4);
		//设置类型
		//拷贝content
		System.arraycopy(content, 0, result, 4, content.length);
		//返回封装后的结果
		return result;
	}
	
	/**
     * 注释：int到字节数组的转换！
     *
     * @param number
     * @return
     */

	public static byte[] intToByte(int number) {
        int temp = number;
        byte[] b = new byte[4];
        for (int i = 0; i < b.length; i++) {
            b[4-i-1] = new Integer(temp & 0xff).byteValue();//高位在前
            temp = temp >> 8; // 向右移8位
        }
        return b;
    }

 

    /**

     * 注释：字节数组到int的转换！
     *
     * @param b
     * @return
     */
	private static int byteToInt(byte[] b) {
        int s = 0;
        int s0 = b[3] & 0xff;//高位在前  最低位为b[3]
        int s1 = b[2] & 0xff;
        int s2 = b[1] & 0xff;
        int s3 = b[0] & 0xff;
        s3 <<= 24;
        s2 <<= 16;
        s1 <<= 8;
        s = s0 | s1 | s2 | s3;
        return s;

    }
}
