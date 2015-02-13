package com.android.mina.test;

import java.io.Serializable;

public class TestBean implements Serializable{



	/**
	 * 
	 */
	private static final long serialVersionUID = -4279243890150154154L;
	private String name;
	private int age;
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public int getAge() {
		return age;
	}
}
