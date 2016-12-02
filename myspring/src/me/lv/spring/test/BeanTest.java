package me.lv.spring.test;

import me.lv.spring.bean.HelloBean;
import me.lv.spring.factory.AnnotationBeanFactory;
import me.lv.spring.factory.BeanFactory;

public class BeanTest {
	public static void main(String[] args) {
		BeanFactory beanFactory = new AnnotationBeanFactory("me.lv.spring.bean");

		Object object = beanFactory.getBean("person");
		Object object2 = beanFactory.getBean("person");
		
		System.out.println(object);
		System.out.println(object2);

		HelloBean bean = beanFactory.getBean("hello", HelloBean.class);
		bean.sayHello();
	}
}
