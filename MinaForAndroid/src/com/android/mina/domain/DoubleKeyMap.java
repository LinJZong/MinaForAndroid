package com.android.mina.domain;

import java.util.HashMap;

import org.apache.mina.core.session.IoSession;

/**
 * 键值一一对应的HashMap
 * @author linj
 *
 */
public class DoubleKeyMap<K,V> {
	public final HashMap<K, V> map1;
	public final HashMap<V,K> map2;
	public DoubleKeyMap(){
		map1=new HashMap<K, V>();
		map2=new HashMap<V, K>();
	}
	public void put(K k,V v){
		map1.put(k, v);
		map2.put(v, k);
	}
	public Object get(String o){
		try {
			K k=(K)o;
			return map1.get(k);
		} catch (ClassCastException e) {
			try {
				V v=(V)o;
				return map2.get(v);
			} catch (ClassCastException e2) {

			}
		}
		return null;
	}
}
