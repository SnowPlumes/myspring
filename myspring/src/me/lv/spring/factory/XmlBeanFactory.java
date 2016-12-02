package me.lv.spring.factory;

import java.io.File;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XmlBeanFactory {

	public XmlBeanFactory(String xmlPath) {
		parse(xmlPath);
	}

	public void parse(String xmlPath) {
		SAXReader reader = new SAXReader();
		try {
			Document document = reader.read(new File(xmlPath));
			Element root = document.getRootElement();

			@SuppressWarnings("unchecked")
			List<Element> beanElement = root.elements("bean");

			for (Element element : beanElement) {
				String id = element.attributeValue("id");
				String className = element.attributeValue("class");
				String singleton = element.attributeValue("singleton");

				BeanAttribute attribute = new BeanAttribute();
				attribute.setId(id);
				attribute.setClassName(className);

				if (singleton != null) {
					boolean isSingleton = Boolean.parseBoolean(singleton);
					attribute.setSingleton(isSingleton);
				}

				
				
			}

		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		XmlBeanFactory beanFactory = new XmlBeanFactory("src/mybean.xml");
	}

}
