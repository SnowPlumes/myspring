package me.lv.spring.bean;

import me.lv.spring.annotation.Bean;

@Bean("hello")
public class HelloBean {
	@Bean("person")
	private Person person;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public void sayHello() {
		System.out.println("hello : " + person);
	}

//	@Override
//	public String toString() {
//		return "HelloBean [person=" + person + "]";
//	}

}
