package me.lv.spring.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.lv.spring.annotation.Bean;

public class AbstractBeanFactory implements BeanFactory {

	protected static List<BeanAttribute> attributes = new ArrayList<>();
	protected static Map<String, Object> singletonMap = new HashMap<>();
	protected static Map<String, Class<?>> propertyMap = new HashMap<>();

	protected static void retainBean(String name, Class<?> clazz, boolean isSingleton) {
		if (!isSingleton) {
			propertyMap.put(name, clazz);
		} else {
			try {
				Object object = clazz.newInstance();
				singletonMap.put(name, object);
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	protected void resovleDependence(Object className) {
		Class<?> clazz = className.getClass();

		Field[] fields = clazz.getDeclaredFields();

		for (Field field : fields) {
			if (!field.isAnnotationPresent(Bean.class)) {
				continue;
			}
			Bean bean = field.getAnnotation(Bean.class);
			String beanName = bean.value();
			Object beanClass = getBean(beanName);

			if (beanClass == null) {
				System.out.println("Ã»ÓÐ¸Ãbean");
			}

			invoke(className, field, beanClass);
		}

	}

	public void invoke(Object className, Field field, Object beanClass) {
		StringBuffer fieldName = new StringBuffer(field.getName());

		fieldName.setCharAt(0, Character.toUpperCase(fieldName.charAt(0)));

		String setMethod = "set" + fieldName;
		try {
			Method method = className.getClass().getMethod(setMethod, field.getType());
			method.invoke(className, beanClass);
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object getBean(String name) {
		if (singletonMap.containsKey(name)) {
			return singletonMap.get(name);
		}

		Class<?> c = propertyMap.get(name);
		Object obj = null;

		try {
			obj = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return obj;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(String name, Class<T> clazz) {
		Object obj = getBean(name);

		if (obj != null) {
			return (T) obj;
		}
		return null;
	}

	public static void main(String[] args) {

		StringBuffer fieldName = new StringBuffer("aaa");
		fieldName.setCharAt(0, Character.toUpperCase(fieldName.charAt(0)));

		String setMethod = "set" + fieldName;
		System.out.println(setMethod);
	}

}
