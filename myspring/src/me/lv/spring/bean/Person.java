package me.lv.spring.bean;

import me.lv.spring.annotation.Bean;

@Bean("person")
public class Person {
	private String name = "no name";

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

//	@Override
//	public String toString() {
//		return "Person [name=" + name + "]";
//	}

}
