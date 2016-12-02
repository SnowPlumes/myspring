package me.lv.spring.factory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import me.lv.spring.annotation.Bean;
import me.lv.spring.annotation.Singleton;

public class AnnotationBeanFactory extends AbstractBeanFactory {

	public AnnotationBeanFactory(String packageName) {
		refershBeans(packageName);

		for (Object object : singletonMap.values()) {
			resovleDependence(object);
		}
	}

	private void refershBeans(String packageName) {
		List<String> classList = scanPackage(packageName);

		for (String className : classList) {
			try {
				Class<?> clazz = Class.forName(className);

				if (!clazz.isAnnotationPresent(Bean.class)) {
					continue;
				}

				Bean bean = (Bean) clazz.getAnnotation(Bean.class);
				String beanName = bean.value();
				boolean isSingleton = true;
				if (clazz.isAnnotationPresent(Singleton.class)) {
					Singleton singleton = clazz.getAnnotation(Singleton.class);
					isSingleton = singleton.value();
				}
				retainBean(beanName, clazz, isSingleton);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	private List<String> scanPackage(String packageName) {
		List<String> classList = new ArrayList<>();

		String packagePath = packageName.replace(".", File.separator);
//		System.out.println("packagePath : " + packagePath);

		String systemPath = ClassLoader.getSystemResource("").getPath();
		File systemDir = new File(systemPath);
//		System.out.println("systemDir : " + systemDir.getAbsolutePath());

		File packageDir = new File(systemPath, packagePath);
//		System.out.println("packageDir : " + packageDir.getAbsolutePath());

		classList = listClass(packageDir, systemDir);

		return classList;
	}

	private List<String> listClass(File packageDir, File systemDir) {
		List<String> classList = new ArrayList<>();

		for (File childFile : packageDir.listFiles()) {
			String fileName = childFile.getAbsolutePath();

			if (childFile.isDirectory()) {
				classList.addAll(listClass(childFile, systemDir));
				continue;
			}

			if (!fileName.endsWith(".class")) {
				System.out.println(fileName + " 没有class文件");
				continue;
			}

			String systemPath = systemDir.getAbsolutePath();
			String className = fileName.replace(".class", "").replace(systemPath + File.separator, "")
					.replace(File.separator, ".");
			classList.add(className);
		}

		return classList;
	}

	public static void main(String[] args) {
		AnnotationBeanFactory factory = new AnnotationBeanFactory("");
		List<String> classNames = factory.scanPackage("me.lv.spring");
		System.out.println(classNames);
	}

}
